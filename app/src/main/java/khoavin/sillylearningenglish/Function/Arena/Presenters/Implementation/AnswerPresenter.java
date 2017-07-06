package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.ResultActivity;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress;
import khoavin.sillylearningenglish.SingleViewObject.Common;

public class AnswerPresenter implements IAnswerPresenter {

    //The Model and View
    private IAnswerView answerView;

    private AppCompatActivity GetView() {
        return (AppCompatActivity) answerView;
    }

    private Question[] questions;

    //Save current question on list question
    private int currentQuestion = 0;

    @Inject
    IArenaService arenaService;

    @Inject
    IPlayerService userService;

    @Inject
    IVolleyService volleyService;

    /**
     * Initialize answer view.
     *
     * @param answerView
     */
    public AnswerPresenter(final IAnswerView answerView) {

        //set view for presenter
        this.answerView = answerView;
        answerView.blockRayCast(false);

        //inject arena service
        ((SillyApp) ((AppCompatActivity) answerView).getApplication())
                .getDependencyComponent()
                .inject(this);

        //Initialize questions
        questions = arenaService.GetCurrentQuestions();
        currentQuestion = 0;
        if (questions != null && questions.length > 0)
            SetAnswerViewWithQuestion(questions[currentQuestion], currentQuestion + 1);
    }

    /**
     * Chose answer A.
     */
    @Override
    public void ChoseAnswerA() {
        Question q = questions[currentQuestion];
        ChoseAnswer(q, Common.AnswerKey.A);
        if (q.getQuestionType() == Common.QuestionType.LISTENING)
            answerView.stopAnswerPlayer();
    }

    /**
     * Chose answer B.
     */
    @Override
    public void ChoseAnswerB() {
        Question q = questions[currentQuestion];
        ChoseAnswer(q, Common.AnswerKey.B);
        if (q.getQuestionType() == Common.QuestionType.LISTENING)
            answerView.stopAnswerPlayer();
    }

    /**
     * Repeat audio command.
     */
    @Override
    public void RepeatAudio() {
        //Do something to repeat audio.
        Toast.makeText(GetView(), "Repeat audio", Toast.LENGTH_SHORT).show();
    }

    /**
     * Chose answer.
     *
     * @param question  The question identifier.
     * @param answerKey The selected answer.
     */
    private void ChoseAnswer(Question question, final Common.AnswerKey answerKey) {
        if (arenaService == null) return;

        answerView.blockRayCast(true);
        arenaService.ChoseAnswer(
                userService.GetCurrentUser().getUserId(),
                question.getBattleId(),
                question.getQuestionId(),
                answerKey.getValue(),
                GetView(),
                volleyService,
                new IVolleyResponse<AnswerChecker>() {
                    @Override
                    public void onSuccess(AnswerChecker checker) {
                        if (checker.getCheckerTrueFalse()) {
                            answerView.informAnswerResult(answerKey, true);
                        } else {
                            answerView.informAnswerResult(answerKey, false);
                        }

                        //Delay 0.4s
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                answerView.blockRayCast(false);
                                currentQuestion++;
                                if (currentQuestion > 4) {
                                    Intent intent = new Intent(GetView(), ResultActivity.class);
                                    intent.putExtra("TOTAL_ANSWER_TIMES", counter);
                                    GetView().startActivity(intent);
                                } else {
                                    SetAnswerViewWithQuestion(questions[currentQuestion], currentQuestion + 1);
                                }
                            }
                        }, 400);
                    }

                    @Override
                    public void onError(ErrorCode error) {
                        Common.ShowInformMessage(
                                String.format(GetView().getResources().getString(R.string.answer_error), error.getCode().toString()),
                                GetView().getResources().getString(R.string.alert_title),
                                GetView().getResources().getString(R.string.accept_message),
                                GetView(),
                                null);
                    }
                });
    }

    /**
     * Set question value to view.
     *
     * @param question The question.
     */
    private void SetAnswerViewWithQuestion(Question question, int questionNumber) {
        if(currentQuestion == 0)
        {
            StartCountDown();
        }
        this.answerView.setQuestionType(question.getQuestionType());
        this.answerView.setQuestionContent(question.getQuestionContent());
        this.answerView.setAnswerA(question.getAnswerA());
        this.answerView.setAnswerB(question.getAnswerB());
        if (question.getQuestionType() == Common.QuestionType.LISTENING) {
            this.answerView.setMediaUrl(WebAddress.SERVER_URL + question.getAudioSource());
            this.answerView.startAnswerPlayer();
        }
        this.answerView.setQuestionTitle(String.format(GetView().getResources().getString(R.string.answer_number), String.valueOf(questionNumber)));
    }

    int counter = 0;
    int maxTime = 0;

    /**
     * Start count down.
     */
    private void StartCountDown() {
        long answerTimes = userService.GetAppParams(Common.ParamType.MAX_ANSWER_TIMES).getValue();
        counter = 0;
        maxTime = (int)(answerTimes * 60);
        answerTimes = answerTimes * 60 * 1000;
        answerView.setPercentOfTimePass(0);

        new CountDownTimer(answerTimes, 1000) {
            public void onTick(long millisUntilFinished) {
                counter++;
                SetCurrentTimes(counter);
            }

            public void onFinish() {
                Log.d("ANSWER_PRESENTER", "##Over time ##");
            }
        }.start();
    }

    private void SetCurrentTimes(int counter)
    {
        answerView.setPercentOfTimePass(1.0f * counter / maxTime);
        int sec = 0;
        int min = 0;
        min = counter / 60;
        sec = counter % 60;
        String secStr = String.valueOf(sec);
        String minStr = "0" + String.valueOf(min);
        if(sec < 10) secStr = "0" + secStr;
        answerView.setCurrentTime(minStr + ":" + secStr);
    }


}
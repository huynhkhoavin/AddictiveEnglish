package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import com.android.volley.NetworkError;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswers;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ArenaService implements IArenaService {

    //the arena service tag
    private static final String ARENA_SERVICE_TAG = "Arena_service:";

    //Create battle request
    @Override
    public void CreateBattle(String user_id, String enemy_id, final IServerResponse<Questions> receiver) {

        IApiServices APIService = ApiUntils.getAPIService();
        if(APIService != null)
        {
            APIService.createBattle(user_id, enemy_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Questions>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            receiver.onError(new NetworkError(e));
                            Log.e(ARENA_SERVICE_TAG, "Can not create battle:");
                            Log.e(ARENA_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(Questions questions) {
                            receiver.onSuccess(questions);
                            Log.i(ARENA_SERVICE_TAG, "Create battle successfully!");
                        }
                    });
        }
    }

    @Override
    public void FindBattle(String user_id, IServerResponse<Enemy> receiver) {

    }

    @Override
    public void AcceptBattle(String defender_id, int battle_id, final IServerResponse<Questions> receiver) {
        IApiServices APIService = ApiUntils.getAPIService();
        if(APIService != null)
        {
            APIService.acceptBattle(defender_id, battle_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Questions>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            receiver.onError(new NetworkError(e));
                            Log.e(ARENA_SERVICE_TAG, "Something wrong when try accept this battle!");
                            Log.e(ARENA_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(Questions questions) {
                            receiver.onSuccess(questions);
                            Log.i(ARENA_SERVICE_TAG, "Accept battle successfully!");
                        }
                    });
        }
    }

    @Override
    public void ChoseAnswer(String user_id, int battle_id, int question_id, int chose_answer, final IServerResponse<AnswerChecker> receiver) {
        IApiServices APIService = ApiUntils.getAPIService();
        if(APIService != null)
        {
            APIService.choseAnswer(user_id, battle_id, question_id, chose_answer)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<AnswerChecker>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            receiver.onError(new NetworkError(e));
                            Log.e(ARENA_SERVICE_TAG, "Something wrong when sent chose answer request to server!");
                            Log.e(ARENA_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(AnswerChecker answerChecker) {
                            receiver.onSuccess(answerChecker);
                            Log.i(ARENA_SERVICE_TAG, "Chose answer request successfully!");
                        }
                    });
        }
    }

    @Override
    public void GetBattleResult(String user_id, int battle_id, final IServerResponse<MyAnswers> receiver) {
        IApiServices APIService = ApiUntils.getAPIService();
        if(APIService != null)
        {
            APIService.getBattleResult(user_id, battle_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<MyAnswers>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            receiver.onError(new NetworkError(e));
                            Log.e(ARENA_SERVICE_TAG, "Can not get battle result!");
                            Log.e(ARENA_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(MyAnswers myAnswers) {
                            receiver.onSuccess(myAnswers);
                            Log.i(ARENA_SERVICE_TAG, "Get battle result successfully!");
                        }
                    });
        }
    }
}

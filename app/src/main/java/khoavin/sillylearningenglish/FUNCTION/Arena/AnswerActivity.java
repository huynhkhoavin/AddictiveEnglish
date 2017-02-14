package khoavin.sillylearningenglish.FUNCTION.Arena;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import khoavin.sillylearningenglish.ENTITY_DATABASE.Commom;
import khoavin.sillylearningenglish.FUNCTION.Arena.ArenaTools.QuestionPageController;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/13/2017.
 */

public class AnswerActivity extends AppCompatActivity {

    ProgressBar totalTimeProgressBar;
    private QuestionPageController questionPageController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        setTitle(R.string.answer_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        //Init all controls
        InitAllControls();
    }

    private  void InitAllControls()
    {
        //Init progressbar
        this.totalTimeProgressBar = (ProgressBar)findViewById(R.id.total_time_progressbar);

        // Get the Drawable custom_progressbar
        Drawable draw= getResources().getDrawable(R.drawable.custom_progressbar);

        // set the drawable as progress drawable
        this.totalTimeProgressBar.setProgressDrawable(draw);
        this.totalTimeProgressBar.setProgress(50);

        //Question page controller
        this.questionPageController = new QuestionPageController(this);

        questionPageController.setValue(Commom.QuestionType.Reading, "A. Don't work", "B. aren't work", "Câu tuyên chiến", "They _______ because it is a national holiday.");
        questionPageController.showAnswer(Commom.AnswerKey.A);
        questionPageController.showRepeatImage();
    }
}

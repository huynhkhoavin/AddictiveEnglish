package khoavin.sillylearningenglish.Function.TrainingRoom.LessonInfo.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.LessonDetailActivity;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/15/2017.
 */

public class LessonInfoActivity extends AppCompatActivity {

    @BindView(R.id.lesson_image)
    ImageView lessonAvatar;
    @BindView(R.id.lesson_title)
    TextView lessonTitle;
    @BindView(R.id.lesson_price)
    TextView lessonPrice;
    @BindView(R.id.listen_button)
    Button buttonListen;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To retrieve object in second Activity

        setContentView(R.layout.activity_lession_info);
        ButterKnife.bind(this);
        bindingLesson();
    }
    void bindingLesson(){
        Lesson item = (Lesson)getIntent().getSerializableExtra("Lesson");
        Glide.with(this)
                .load(item.getLsAvatarUrl())
                .into(lessonAvatar);
        lessonTitle.setText(item.getLsTitle());
        lessonPrice.setText(item.getLsPrice());
        ratingBar.setRating(Float.parseFloat(item.getLsRate()));
        buttonListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),LessonDetailActivity.class);
                startActivity(it);
            }
        });
    }
}

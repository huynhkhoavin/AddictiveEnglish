package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.Reading.ReadActivity;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonReadFragment extends FragmentPattern {
    public Lesson lesson;
    @BindView(R.id.btn_Read)
    Button btnRead;

    @BindView(R.id.img_lesson_avatar)
    ImageView lessonAvatar;
    @BindView(R.id.tv_lessonTitle)
    TextView lessonTitle;
    @BindView(R.id.spinner)
    Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lesson_read,container,false);
        ButterKnife.bind(this,v);

        Glide.with(this)
                .load(lesson.getLsAvatarUrl())
                .into(lessonAvatar);
        lessonTitle.setText(lesson.getLsTitle());

        String[] arraySpinner = new String[] {
                "01", "02", "03", "04", "05"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, arraySpinner);
        spinner.setAdapter(adapter);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReadActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}

package khoavin.sillylearningenglish.Function.TrainingRoom.LessonInfo.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.LessonDetailActivity;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/15/2017.
 */

public class LessonInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private void initUI(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lession_info);
        initUI();
    }

    public void OpenLessonDetailClick(View v){
        Intent it = new Intent(LessonInfoActivity.this, LessonDetailActivity.class);
        startActivity(it);
    }
}

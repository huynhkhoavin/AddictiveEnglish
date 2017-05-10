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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.PlayActivity;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.CHECK_LESSON_WAS_BOUGHT;

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

    @Inject
    IVolleyService volleyService;

    @Inject
    IAuthenticationService authenticationService;
    Lesson item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To retrieve object in second Activity
        ((SillyApp) this.getApplication()).getDependencyComponent().inject(this);
        item = (Lesson)getIntent().getSerializableExtra("Lesson");
        setContentView(R.layout.activity_lession_info);
        ButterKnife.bind(this);
        bindingLesson();
        checkLessonInfo();
    }
    void checkLessonInfo(){
        ProgressAsyncTask
                        RequestQueue queue = volleyService.getRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_LESSON_WAS_BOUGHT,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                                        if (errorCodes[0].getCode().equals("205"))
                                        {
                                            buttonListen.setEnabled(true);
                                        }
                                        else
                                        {
                                            buttonListen.setEnabled(false);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Error");
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("user_id", authenticationService.getCurrentUser().getUid());
                                params.put("ls_id",item.getLsId());
                                return params;
                            }
                        };
                        queue.add(stringRequest);
    }
    void bindingLesson(){

        Glide.with(this)
                .load(item.getLsAvatarUrl())
                .into(lessonAvatar);
        lessonTitle.setText(item.getLsTitle());
        lessonPrice.setText(item.getLsPrice());
        ratingBar.setRating(Float.parseFloat(item.getLsRate()));
        buttonListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),PlayActivity.class);
                it.putExtra("Lesson", item);
                startActivity(it);
            }
        });
    }
}

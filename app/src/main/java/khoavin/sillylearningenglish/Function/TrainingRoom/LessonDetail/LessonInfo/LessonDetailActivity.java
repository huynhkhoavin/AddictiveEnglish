package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.PlayActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.Reading.FragmentA;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.ConnectDialog;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.Pattern.YesNoDialog;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.MUSIC_SERVICE_IS_RUNNING;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.*;

/**
 * Created by KhoaVin on 2/15/2017.
 */

public class LessonDetailActivity extends AppCompatActivity {

    @BindView(R.id.lesson_image)
    ImageView lessonAvatar;
    @BindView(R.id.lesson_title)
    TextView lessonTitle;
    @BindView(R.id.author_name)
    TextView tvAuthor;
    @BindView(R.id.lesson_price)
    TextView lessonPrice;
    @BindView(R.id.listen_button)
    Button buttonListen;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.titleBar)
    LinearLayout titleBar;

    @BindView(R.id.lessonTitle) TextView big_LessonTitle;
    @BindView(R.id.btnBack) ImageView btnBack;
    @Inject
    IAuthenticationService authenticationService;

    @Inject
    ITrainingService trainingService;

    /**
     * Shared Preferences
     */
    SharedPreferences sharedpreferences;

    Lesson item;
    boolean wasBought = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lession_info);
        ButterKnife.bind(this);
        ((SillyApp) getApplication()).getDependencyComponent().inject(this);
        item = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
        setTitle(item.getLsTitle());
        bindingLesson();
        checkLessonInfo();
        setRatingBar();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    void setRatingBar(){




        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                sharedpreferences = getSharedPreferences(Constants.SHARED_PREFERENCES.RATING_PREFERENCES, Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedpreferences.edit();

                if (sharedpreferences!=null){
                    String lessonIsRated = sharedpreferences.getString(Constants.SHARED_PREFERENCES.LESSON_ID_RATED + item.getLsId(),"false");
                    if (lessonIsRated.equals("true")){
                        ratingBar.setIsIndicator(true);
                        Toast.makeText(getApplicationContext(),"Can't rate a lesson again!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        trainingService.RatingLesson(Integer.parseInt(item.getLsId()), rating, LessonDetailActivity.this, new IVolleyResponse<ErrorCode>() {
                            @Override
                            public void onSuccess(ErrorCode responseObj) {
                                if (responseObj.getCode() == Common.ServiceCode.COMPLETED){
                                    Toast.makeText(getApplicationContext(),"Rate lesson complete!", Toast.LENGTH_SHORT).show();

                                    editor.putString(Constants.SHARED_PREFERENCES.LESSON_ID_RATED + item.getLsId(),"true");
                                    editor.commit();

                                    bindingLesson();
                                }
                            }

                            @Override
                            public void onError(ErrorCode errorCode) {

                            }
                        });
                    }


                }


            }
        });
    }
    void checkLessonInfo(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                if (errorCodes[0].getCode() == Common.ServiceCode.LESSON_WAS_BOUGHT)
                {
                    wasBought = true;
                    buttonListen.setText("Listen");
                }
                else
                {
                    wasBought = false;
                    buttonListen.setText("Buy");
                }
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", authenticationService.getCurrentUser().getUid());
                params.put("ls_id",item.getLsId());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return CHECK_LESSON_WAS_BOUGHT;
            }
        };
        networkAsyncTask.execute();
    }
    void bindingLesson(){
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvAuthor.setText(item.getLsAuthor());
        Glide.with(this)
                .load(item.getLsAvatarUrl())
                .into(lessonAvatar);
        big_LessonTitle.setText(item.getLsTitle());
        lessonTitle.setText(item.getLsTitle());
        lessonPrice.setText(item.getLsPrice());
        ratingBar.setRating(item.getRate());
        buttonListen.setOnClickListener(btnBuyOnClickListener);
    }
    //region Click
    View.OnClickListener btnBuyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (wasBought){
                if(Storage.getInstance().CheckNodeIsExist(MUSIC_SERVICE_IS_RUNNING)) {
                    Lesson currentLesson = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
                    if(item.getLsId().equals(currentLesson.getLsId())== false) {
                        EventBus.getDefault().post(new MessageEvent(Constants.ACTION.STOPFOREGROUND_ACTION));
                        showAlert();
                    }
                    else
                    {
                        Intent it = new Intent(LessonDetailActivity.this,PlayActivity.class);
                        startActivity(it);
                    }
                }
                else{
                    Intent it = new Intent(LessonDetailActivity.this,PlayActivity.class);
                    startActivity(it);
                }
            }
            else
            {
                buyLesson();
            }
        }
    };
    //endregion
    void showAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(LessonDetailActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Service is running, do you want to stop it?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent it = new Intent(LessonDetailActivity.this,PlayActivity.class);
                startActivity(it);
            }
        });
        alertDialog.show();
    }
    void buyLesson(){

        final NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                if (errorCodes[0].getCode()== Common.ServiceCode.COMPLETED)
                {
                    buttonListen.setText("Listen");
                    wasBought  = true;
                    Toast.makeText(getApplicationContext(),"Buy lesson success!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),errorCodes[0].getDetails(),Toast.LENGTH_LONG).show();
                    wasBought = false;
                    buttonListen.setText("Buy");
                }
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", authenticationService.getCurrentUser().getUid());
                params.put("ls_id",item.getLsId());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return BUY_LESSON;
            }
        };
        final YesNoDialog yesNoDialog = new YesNoDialog();
        yesNoDialog.show(((AppCompatActivity)this).getSupportFragmentManager(),"yes no dialog");
        yesNoDialog.setMessage("Are you sure to buy this lesson?");
        yesNoDialog.setOnPositiveListener(new ConnectDialog.Listener() {
            @Override
            public void onClick() {
                networkAsyncTask.execute();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

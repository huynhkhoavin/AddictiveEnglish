package khoavin.sillylearningenglish.Function.LuckySpinning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Reward;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.USER_DO_SPIN;

/**
 * Created by KhoaVin on 06/07/2017.
 */

public class ActivitySpinning extends BaseDetailActivity {
    @BindView(R.id.wheel)
    ImageView spinningWheel;

    @BindView(R.id.spinning_needle)
    ImageView spinningNeedle;

    @Inject
    IAuthenticationService authenticationService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinning);
        ButterKnife.bind(this);
        setupWindowAnimations();
        ((SillyApp) (this).getApplication())
                .getDependencyComponent()
                .inject(this);
//        Matrix matrix = new Matrix();
//        spinningWheel.setScaleType(ImageView.ScaleType.MATRIX);   //required
//        matrix.postRotate((float) 90, spinningWheel.getX()+spinningWheel.getWidth()/2, spinningWheel.getY()+spinningWheel.getHeight()/2);
//        spinningWheel.setImageMatrix(matrix);
        spinningNeedle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSpin();
            }
        });
    }
    private void rotate(float degree, final Reward reward) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnim.setDuration(5000);
        rotateAnim.setFillAfter(true);
        rotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(ActivitySpinning.this, reward.getRewardName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        spinningWheel.startAnimation(rotateAnim);
    }
    public void DoSpin(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                ArrayList<Reward> list = ArrayConvert.toArrayList(JsonConvert.getArray(response,Reward[].class));
                if (list.get(0).getRewardId().equals("6")){
                    Toast.makeText(ActivitySpinning.this,list.get(0).getRewardName(),Toast.LENGTH_SHORT).show();
                }
                else {
                    int angle = (Integer.parseInt(list.get(0).getRewardId()) - 1) * 36;
                    rotate(3600+angle, list.get(0));
                }
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",authenticationService.getCurrentUser().getUid());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return USER_DO_SPIN;
            }
        };
        networkAsyncTask.execute();
    }
    //region transition
    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }
    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        // This view will not be affected by enter transition animation
        //enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        return enterTransition;
    }
    //endregion
}

package khoavin.sillylearningenglish.Function.LuckySpinning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.R;
import android.graphics.Matrix;
import android.widget.ImageView;
/**
 * Created by KhoaVin on 06/07/2017.
 */

public class ActivitySpinning extends AppCompatActivity {
    @BindView(R.id.wheel)
    ImageView spinningWheel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinning);
        ButterKnife.bind(this);

//        Matrix matrix = new Matrix();
//        spinningWheel.setScaleType(ImageView.ScaleType.MATRIX);   //required
//        matrix.postRotate((float) 90, spinningWheel.getX()+spinningWheel.getWidth()/2, spinningWheel.getY()+spinningWheel.getHeight()/2);
//        spinningWheel.setImageMatrix(matrix);
        rotate(3600+10*36);
    }
    private void rotate(float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnim.setDuration(5000);
        rotateAnim.setFillAfter(true);

        spinningWheel.startAnimation(rotateAnim);
    }
}

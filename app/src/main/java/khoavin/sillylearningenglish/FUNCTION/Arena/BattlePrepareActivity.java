package khoavin.sillylearningenglish.FUNCTION.Arena;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/12/2017.
 */

public class BattlePrepareActivity extends AppCompatActivity {

    ImageView startBattleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_prepare);
        setTitle(R.string.challenge);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        //Init all controls
        InitAllControls();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    private void InitAllControls()
    {
        startBattleButton = (ImageView)findViewById(R.id.start_battle_button);
        startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(BattlePrepareActivity.this, AnswerActivity.class);
                startActivity(it);
            }
        });
    }
}

package khoavin.sillylearningenglish.FUNCTION.Arena.Views.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import khoavin.sillylearningenglish.FUNCTION.Arena.Views.IArenaView;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/12/2017.
 */

public class ArenaActivity extends AppCompatActivity implements IArenaView{

    private ImageView findBattleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle(R.string.arena_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        findBattleButton = (ImageView) findViewById(R.id.find_battle_button);
        findBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ArenaActivity.this, BattlePrepareActivity.class);
                startActivity(it);
            }
        });

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
}

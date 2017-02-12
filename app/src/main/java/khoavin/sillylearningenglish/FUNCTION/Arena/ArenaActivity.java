package khoavin.sillylearningenglish.FUNCTION.Arena;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/12/2017.
 */

public class ArenaActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle(R.string.arena_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
}

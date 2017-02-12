package khoavin.sillylearningenglish.FUNCTION.MailBox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class MailActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        setTitle(R.string.mail_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
}

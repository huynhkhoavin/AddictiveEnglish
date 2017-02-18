package khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxDetail.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class ActivityMailBoxDetail extends AppCompatActivity implements IMailBoxDetailView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_detail);
    }


    @Override
    public void ShowMailDetail(Object DataSource) {
        //Binding data here
    }
}

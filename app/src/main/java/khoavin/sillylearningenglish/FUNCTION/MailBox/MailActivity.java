package khoavin.sillylearningenglish.FUNCTION.MailBox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Mail;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class MailActivity extends AppCompatActivity {

    private MailBoxAdapter mailBoxAdapter;
    private RecyclerView listMail;
    private Mail[] mails= new Mail[]{
            new Mail(false,"Hệ Thống","Thách đấu từ",false),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true)
    };
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        setTitle(R.string.mail_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        mailBoxAdapter = new MailBoxAdapter(this,mails);
        listMail = (RecyclerView)findViewById(R.id.mailList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listMail.setLayoutManager(linearLayoutManager);
        listMail.setAdapter(mailBoxAdapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
        listMail.addItemDecoration(dividerItemDecoration);
    }
    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }
}

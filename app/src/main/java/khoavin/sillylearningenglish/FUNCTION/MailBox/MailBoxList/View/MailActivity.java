package khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxList.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxDetail.View.ActivityMailBoxDetail;
import khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxList.Model.IMailBoxModel;
import khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxList.Model.MailBoxModel;
import khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxList.Presenter.IMailBoxPresenter;
import khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxList.Presenter.MailBoxPresenter;
import khoavin.sillylearningenglish.PATTERN.RecyclerItemClickListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Mail;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class MailActivity extends AppCompatActivity implements IMailBoxView {

    private MailBoxAdapter mailBoxAdapter;
    private RecyclerView listMail;

    private IMailBoxPresenter mailBoxPresenter;
    private IMailBoxModel mailBoxModel;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        setTitle(R.string.mail_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        mailBoxModel = new MailBoxModel();
        mailBoxPresenter = new MailBoxPresenter(this,mailBoxModel);
        mailBoxPresenter.ShowMailList();
    }
    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    @Override
    public void ShowMailList(Mail[] mails) {


        listMail = (RecyclerView)findViewById(R.id.mailList);
        mailBoxAdapter = new MailBoxAdapter(this,mails);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listMail.setLayoutManager(linearLayoutManager);
        listMail.setAdapter(mailBoxAdapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
        listMail.addItemDecoration(dividerItemDecoration);
        listMail.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent it = new Intent(getApplicationContext(), ActivityMailBoxDetail.class);
                        startActivity(it);
                    }
                })
        );
    }

    @Override
    public void ShowMailDetail(int position) {

    }
}

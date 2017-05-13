package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View.ActivityMailBoxDetail;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Model.IMailBoxModel;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Model.MailBoxModel;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter.IMailBoxPresenter;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter.MailBoxPresenter;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inboxs;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
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
        mailBoxPresenter = new MailBoxPresenter(this);
        //mailBoxPresenter.ShowMailList();
    }
    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    @Override
    public void ShowMailList(Inboxs inboxs) {
        listMail = (RecyclerView)findViewById(R.id.mailList);
        mailBoxAdapter = new MailBoxAdapter(this, ArrayConvert.toObjectArray(inboxs.getData()));
        mailBoxAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {
                Intent it = new Intent(getApplicationContext(), ActivityMailBoxDetail.class);
                Inbox ibItem = (Inbox)ItemObject;
                it.putExtra("INBOX_ITEM", ibItem);
                startActivity(it);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listMail.setLayoutManager(linearLayoutManager);
        listMail.setAdapter(mailBoxAdapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
        listMail.addItemDecoration(dividerItemDecoration);
//        listMail.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        //Intent it = new Intent(getApplicationContext(), ActivityMailBoxDetail.class);
//                        //startActivity(it);
//                    }
//                })
//        );
    }

    @Override
    public void ShowMailList(Inbox[] items)
    {
        listMail = (RecyclerView)findViewById(R.id.mailList);
        mailBoxAdapter = new MailBoxAdapter(this, ArrayConvert.toObjectArray(items));
        mailBoxAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {
                Intent it = new Intent(getApplicationContext(), ActivityMailBoxDetail.class);
                Inbox ibItem = (Inbox)ItemObject;
                it.putExtra("INBOX_ITEM", ibItem);
                startActivity(it);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listMail.setLayoutManager(linearLayoutManager);
        listMail.setAdapter(mailBoxAdapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
        listMail.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void ShowMailDetail(int position) {

    }
}

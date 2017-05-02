package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.AnswerActivity;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.BattlePrepareActivity;
import khoavin.sillylearningenglish.NetworkService.Implementation.ArenaService;
import khoavin.sillylearningenglish.NetworkService.Implementation.PlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class ActivityMailBoxDetail extends AppCompatActivity implements IMailBoxDetailView {

    private Inbox currentItem = null;

    @BindView(R.id.mail_title)
    TextView mailTitle;

    @BindView(R.id.mail_content)
    TextView mailContent;

    @BindView(R.id.sender_name)
    TextView senderName;

    @BindView(R.id.addition_information)
    TextView additionInformation;

    @BindView(R.id.mail_button_action)
    Button actionButton;

    @Inject
    IArenaService arenaService;

    @Inject
    IPlayerService playerService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_detail);
        ButterKnife.bind(this);

        //inject arena service
        ((SillyApp) (this).getApplication())
                .getDependencyComponent()
                .inject(this);

        currentItem = null;
        currentItem = (Inbox)getIntent().getSerializableExtra("INBOX_ITEM");

        if(currentItem != null)
        {
            switch (currentItem.getMailType())
            {
                case BATTLE_CHALLENGE:
                    mailTitle.setText("Thư thách đấu");
                    break;
                case BATTLE_RESULT:
                    mailTitle.setText("Kết quả trận đấu");
                    break;
                case GIFT_COIN:
                    break;
                case NOT_FOUND:
                    break;
            }

            additionInformation.setText("Thông tin thêm");
            mailContent.setText(currentItem.getContent());
            senderName.setText(currentItem.getSenderName());
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentItem != null)
                    {
                        if(currentItem.getMailType() == Common.InBoxType.BATTLE_CHALLENGE)
                        {
                            if(arenaService != null)
                            {
                                arenaService.AcceptBattle(playerService.GetCurrentUser().getUserId(), Integer.valueOf(currentItem.getValue()), new IServerResponse<Questions>() {
                                    @Override
                                    public void onSuccess(Questions questions) {
                                        Intent it = new Intent(ActivityMailBoxDetail.this, AnswerActivity.class);
                                        startActivity(it);
                                        Log.e("PREPARE_ACTIVITY", "Prepare success!");
                                    }

                                    @Override
                                    public void onError(SillyError error) {

                                    }
                                });
                            }
                        }
                        else if(currentItem.getMailType() == Common.InBoxType.BATTLE_RESULT)
                        {

                        }
                    }
                }
            });

        }
        else
        {
            Log.e("Mail Details: ", "Can not receive INBOX_ITEM from Intent!");
        }
    }


    @Override
    public void ShowMailDetail(Object DataSource) {
        //Binding data here
    }
}

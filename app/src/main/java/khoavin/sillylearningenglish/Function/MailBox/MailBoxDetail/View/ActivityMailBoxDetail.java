package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.AnswerActivity;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
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

    //region view state
    /**
     * The view state control
     */
    private UIView itemState;
    private UIView buttonState;

    @BindView(R.id.mail_state_lost_coin_obj)
    LinearLayout mailStateLostCoinObj;

    @BindView(R.id.mail_state_won_coin_obj)
    LinearLayout mailStateWonCoinObj;

    @BindView(R.id.mail_state_lost_down_obj)
    LinearLayout mailStateLostDownObj;

    @BindView(R.id.mail_state_won_up_obj)
    LinearLayout mailStateWonUpObj;

    @BindView(R.id.mail_state_duel_obj)
    LinearLayout mailStateDuelObj;

    @BindView(R.id.mail_state_new_book_obj)
    LinearLayout mailStateNewBookObj;

    //endregion

    //region Binding controls
    @BindView(R.id.mail_title)
    TextView mailTitle;

    @BindView(R.id.mail_delete)
    ImageView mailDelete;

    @BindView(R.id.mail_ratting)
    ImageView mailRatting;

    @BindView(R.id.mail_banner)
    ImageView mailBanner;

    @BindView(R.id.mail_status)
    TextView mailStatus;

    @BindView(R.id.mail_time)
    TextView mailTime;

    @BindView(R.id.mail_lose_coin_number)
    TextView mailLostCoinNumber;

    @BindView(R.id.mail_won_coin_number)
    TextView mailWonCoinNumber;

    @BindView(R.id.mail_down_to_rank)
    TextView maillDownToRank;

    @BindView(R.id.mail_up_to_rank)
    TextView mailUpTorank;

    @BindView(R.id.mail_book_name)
    TextView mailBookName;

    @BindView(R.id.mail_message_content)
    TextView mailMessageContent;

    @BindView(R.id.mail_accept_button)
    Button mailAcceptButton;

    @BindView(R.id.mail_cancel_button)
    Button mailCancelButton;

    //endregion

    //region Dependency
    @Inject
    IArenaService arenaService;

    @Inject
    IPlayerService playerService;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_detail);
        ButterKnife.bind(this);

        //Initialize UIView and all control state
        itemState = new UIView();
        itemState.RegistryState("LostCoin", mailStateLostCoinObj);
        itemState.RegistryState("WonCoin", mailStateWonCoinObj);
        itemState.RegistryState("LostDown", mailStateLostDownObj);
        itemState.RegistryState("WonUp", mailStateWonUpObj);
        itemState.RegistryState("NewBook", mailStateNewBookObj);
        itemState.RegistryState("Duel", mailStateDuelObj);

        //Initialize button state
        buttonState = new UIView();
        buttonState.RegistryState("Accept", mailAcceptButton);
        buttonState.RegistryState("Cancel", mailCancelButton);

        //inject arena service
        ((SillyApp) (this).getApplication())
                .getDependencyComponent()
                .inject(this);

        mailAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAcceptButtonClick();
            }
        });

        mailCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCancelButtonClick();
            }
        });

        mailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DeleteMail();
            }
        });

        mailRatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RattingMail();
            }
        });

        //Get inbox item from intent
        currentItem = null;
        currentItem = (Inbox) getIntent().getSerializableExtra("INBOX_ITEM");
        InitializeView();
    }

    /**
     * Initialize view
     */
    private void InitializeView() {

        if(currentItem == null) return;

        //Normal properties
        mailTime.setText("22/12/1994 2:30 PM");
        mailMessageContent.setText(currentItem.getContent());
        itemState.DeactiveAllcontrol();

        //Special properties
        switch (currentItem.getMailType()) {
            case BATTLE_CHALLENGE:
                mailTitle.setText("Thư thách đấu");
                mailStatus.setText("Thông tin");
                itemState.ControlState("Duel");
                buttonState.ActiveAllControl();
                mailMessageContent.setText("Người chơi " + currentItem.getSenderName() + " muốn thách đấu với bạn với giá trị cược lên đến "
                        + Common.FormatBigNumber(currentItem.getValue()) + "\nBạn có giám!");
                break;
            case BATTLE_RESULT:
                mailTitle.setText("Kết quả trận đấu");
                mailStatus.setText("Chiến thắng");
                itemState.ActiveControl("WonCoin");
                itemState.ActiveControl("WonUp");
                mailWonCoinNumber.setText("+" + Common.FormatBigNumber(currentItem.getValue()));
                mailUpTorank.setText("BẠC I");
                buttonState.ControlState("Accept");
                break;
            case GIFT_COIN:
                break;
            case  SYSTEM_MESSAGE:
                break;
            case NOT_FOUND:
                break;
        }
    }

    /**
     * Accept battle
     * @param accept
     */
    private void AcceptBattle(boolean accept)
    {
        if(accept)
        {
            if(arenaService != null)
            {
                arenaService.AcceptBattle(playerService.GetCurrentUser().getUserId(), Integer.valueOf(currentItem.getValue()), new IServerResponse<Questions>() {
                    @Override
                    public void onSuccess(Questions questions) {
                        Intent it = new Intent(ActivityMailBoxDetail.this, AnswerActivity.class);
                        startActivity(it);
                        Log.i("PREPARE_ACTIVITY", "Prepare success!");
                    }

                    @Override
                    public void onError(SillyError sillyError) {

                    }
                });
            }
        }
        else
        {
            //Gui request dau hang len server
        }
    }

    private void ClaimReward()
    {
        if(currentItem == null) return;
        //Sent claim request to server
        Log.i("MAIL_DETAIL_ACTIVITY", "Sent claim request to server");
    }

    /**
     * Back to mail box
     */
    private void BackToMailBox()
    {
        //Do something to back to mail box
    }

    /**
     * Called when accept button clicked
     */
    private  void OnAcceptButtonClick()
    {
        if(currentItem == null) return;

        switch (currentItem.getMailType())
        {
            case BATTLE_CHALLENGE:
                AcceptBattle(true);
                break;
            case BATTLE_RESULT:
                ClaimReward();
                break;
            case GIFT_COIN:
                ClaimReward();
                break;
            case NOT_FOUND:
                //...
                break;
            case SYSTEM_MESSAGE:
                ClaimReward();
                break;
        }
    }

    /**
     * Called when cancel button clicked
     */
    private  void OnCancelButtonClick()
    {
        if(currentItem == null) return;
        if (currentItem.getMailType() == Common.InBoxType.BATTLE_CHALLENGE)
        {
            AcceptBattle(false);
        }
        else
        {
            BackToMailBox();
        }
    }

    /**
     * Ratting maill
     */
    private void RattingMail() {

    }

    /**
     * Delete mail
     */
    private  void DeleteMail()
    {

    }


    @Override
    public void ShowMailDetail(Object DataSource) {
        //Binding data here
    }
}

package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter.IMailBoxDetailPresenter;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter.MailBoxDetailPresenter;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class ActivityMailBoxDetail extends AppCompatActivity implements IMailBoxDetailView {

    private Inbox currentItem = null;
    private IMailBoxDetailPresenter presenter;

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

    @BindView(R.id.mail_delete_button)
    ImageView mailDeleteButton;

    @BindView(R.id.mail_ratting_button)
    ImageView mailRatingButton;

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

    @BindView(R.id.mail_accept_battle_button)
    Button acceptBattleButton;

    @BindView(R.id.mail_cancel_battle_button)
    Button cancelButton;

    @BindView(R.id.mail_claim_reward_button)
    Button mailClaimRewardButton;

    @BindView(R.id.mail_confirm_button)
    Button mailConfirmButton;

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
        buttonState.RegistryState("Accept", acceptBattleButton);
        buttonState.RegistryState("Cancel", cancelButton);
        buttonState.RegistryState("Claim", mailClaimRewardButton);
        buttonState.RegistryState("Confirm", mailConfirmButton);

        //Set button command
        acceptBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAcceptButtonClick();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCancelButtonClick();
            }
        });
        mailConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnConfirmButtonClick();
            }
        });
        mailClaimRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClaimRewardButtonClick();
            }
        });
        mailDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.DeleteMail();
            }
        });

        mailRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.RattingMail();
            }
        });

        //Get inbox item from intent
        currentItem = null;
        currentItem = (Inbox) getIntent().getSerializableExtra("INBOX_ITEM");

        presenter = new MailBoxDetailPresenter(this);
        presenter.SetDataContext(currentItem);
    }

    /**
     * Called when accept button clicked
     */
    private void OnAcceptButtonClick() {
        if (currentItem == null) return;

        switch (currentItem.getMailType()) {
            case BATTLE_CHALLENGE:
                presenter.AcceptBattle();
                break;
            case BATTLE_RESULT:
                presenter.ClaimReward();
                break;
            case GIFT_COIN:
                presenter.ClaimReward();
                break;
            case NOT_FOUND:
                //...
                break;
            case SYSTEM_MESSAGE:
                presenter.ClaimReward();
                break;
        }
    }

    /**
     * Called when cancel button clicked
     */
    private void OnCancelButtonClick() {
        if (currentItem == null) return;
        if (currentItem.getMailType() == Common.MailType.BATTLE_CHALLENGE) {
            presenter.CancelBattle();
        } else {
            Common.LogInfo("Cannot cancel battle, should not reach here!");
        }
    }

    /**
     * Called when confirm button click
     */
    private void OnConfirmButtonClick() {
        presenter.BackToInbox();
    }

    /**
     * Called when claim reward button click
     */
    private void OnClaimRewardButtonClick() {
        if (currentItem == null) return;
        if (currentItem.getMailType() == Common.MailType.BATTLE_RESULT ||
                currentItem.getMailType() == Common.MailType.GIFT_COIN) {
            presenter.ClaimReward();
        } else {
            Common.LogInfo("Can not claim reward, Shold not reach here!");
        }
    }

    //region Implementation

    @Override
    public void SetTitle(String title) {
        this.mailTitle.setText(title);
    }

    @Override
    public void SetStatus(String status) {
        this.mailStatus.setText(status);
    }

    @Override
    public void SetTime(Date time) {
        this.mailTime.setText("22/12/1994 12:30 PM");
    }

    @Override
    public void SetMessage(String message) {
        this.mailMessageContent.setText(message);
    }

    @Override
    public void SetImage(String url) {

    }

    @Override
    public void SetImage(int id) {

    }

    @Override
    public void SetCoins(int coins) {
        String coinNummber = "+" + Common.FormatBigNumber(coins);
        this.mailWonCoinNumber.setText(coinNummber);
        this.mailLostCoinNumber.setText(coinNummber);
    }

    @Override
    public void SetBookName(String bookName) {
        this.mailBookName.setText(bookName);
    }

    @Override
    public void RattingMail(boolean key) {

    }

    @Override
    public void SetUpDownRank(String newRank) {
        this.mailUpTorank.setText(newRank);
        this.maillDownToRank.setText(newRank);
    }

    @Override
    public void SetMailType(Common.MailType type) {
        itemState.DeactiveAllcontrol();
        buttonState.DeactiveAllcontrol();
        switch (type) {
            case BATTLE_CHALLENGE:
                itemState.ActiveControl("Duel");
                buttonState.ActiveControl("Accept");
                buttonState.ActiveControl("Cancel");
                break;
            case BATTLE_RESULT:
                buttonState.ActiveControl("Claim");

                if (true) {
                    itemState.ActiveControl("WonCoin");
                    itemState.ActiveControl("WonUp");
                } else {
                    itemState.ActiveControl("LostCoin");
                    itemState.ActiveControl("LoseDown");
                }

                break;
            case GIFT_COIN:
                buttonState.ActiveControl("Claim");
                itemState.ActiveControl("WonCoin");
                itemState.ActiveControl("NewBook");
                break;
            case NOT_FOUND:
                buttonState.ActiveControl("Confirm");
                break;
            case SYSTEM_MESSAGE:
                buttonState.ActiveControl("Confirm");
                break;
        }
    }

    @Override
    public void SetRatingState(boolean isRated) {
        if (isRated) {
            mailRatingButton.setBackground(getResources().getDrawable(R.drawable.rating_icon_deactive_3232));
        } else {
            mailRatingButton.setBackground(getResources().getDrawable(R.drawable.rating_icon_active_3232));
        }
    }

    //endregion
}

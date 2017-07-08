package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter.IMailBoxDetailPresenter;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter.MailBoxDetailPresenter;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.AttachItem;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class ActivityMailBoxDetail extends BaseDetailActivity implements IMailBoxDetailView {

    //define const for attach item.
    private final String ITEM_LOST_COINS = "LostCoins";
    private final String ITEM_ADD_COINS = "WonCoins";
    private final String ITEM_LEVEL_DOWN = "LevelDown";
    private final String ITEM_LEVEL_UP = "LevelUp";
    private final String ITEM_NEW_BOOK = "NewBook";
    private final String ITEM_BATTLE_CHALLENGE = "Battle";
    private final String ITEM_ADD_FRIEND = "AddFriend";

    //define const for button state
    private final String BUTTON_ACCEPT = "Accept";
    private final String BUTTON_CANCEL = "Cancel";
    private final String BUTTON_CLAIM = "Claim";
    private final String BUTTON_CONFIRM = "Confirm";

    private final String BUTTON_ACCEPT_FRIEND = "AcceptFriend";
    private final String BUTTON_CANCEL_FRIEND = "CancelFriend";

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

    @BindView(R.id.mail_state_add_friend_obj)
    LinearLayout mailStateAddFriendOnj;

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
    TextView mailDownToRank;

    @BindView(R.id.mail_up_to_rank)
    TextView mailUpToRank;

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

    @BindView(R.id.mail_accept_friend_button)
    Button mailAcceptFriendButton;

    @BindView(R.id.mail_cancel_friend_button)
    Button mailCancelFriendButton;

    @BindView(R.id.titleBar)
    LinearLayout titleBar;

    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_detail);
        ButterKnife.bind(this);
        setupWindowAnimations();

        //Initialize UIView and all control state
        itemState = new UIView();
        itemState.RegistryState(ITEM_LOST_COINS, mailStateLostCoinObj);
        itemState.RegistryState(ITEM_ADD_COINS, mailStateWonCoinObj);
        itemState.RegistryState(ITEM_LEVEL_DOWN, mailStateLostDownObj);
        itemState.RegistryState(ITEM_LEVEL_UP, mailStateWonUpObj);
        itemState.RegistryState(ITEM_NEW_BOOK, mailStateNewBookObj);
        itemState.RegistryState(ITEM_BATTLE_CHALLENGE, mailStateDuelObj);
        itemState.RegistryState(ITEM_ADD_FRIEND, mailStateAddFriendOnj);

        //Initialize button state
        buttonState = new UIView();
        buttonState.RegistryState(BUTTON_ACCEPT, acceptBattleButton);
        buttonState.RegistryState(BUTTON_CANCEL, cancelButton);
        buttonState.RegistryState(BUTTON_CLAIM, mailClaimRewardButton);
        buttonState.RegistryState(BUTTON_CONFIRM, mailConfirmButton);
        buttonState.RegistryState(BUTTON_ACCEPT_FRIEND, mailAcceptFriendButton);
        buttonState.RegistryState(BUTTON_CANCEL_FRIEND, mailCancelFriendButton);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

        mailAcceptFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAcceptFriendRequest();
            }
        });

        mailCancelFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCancelFriendRequest();
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
            case GIF_REWARD:
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
                currentItem.getMailType() == Common.MailType.GIF_REWARD) {
            presenter.ClaimReward();
        } else {
            Common.LogInfo("Can not claim reward, Shold not reach here!");
        }
    }

    /**
     * Called when click on accept friend request
     */
    private void OnAcceptFriendRequest() {
        presenter.AnswerFriendRequest();
    }

    /**
     * Called when click on cancel friend request
     */
    private void OnCancelFriendRequest() {
        presenter.CancelFriendRequest();
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
    public void SetTime(Date timeString) {
        this.mailTime.setText(Common.GetSillyDateFormat().FormatDateTimeString(timeString));
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
        String coinNummber = Common.FormatBigNumber(coins);
        String addCoinNumber = "+ " + coinNummber;
        String lostCoinNumber = "- " + coinNummber;
        this.mailWonCoinNumber.setText(addCoinNumber);
        this.mailLostCoinNumber.setText(lostCoinNumber);
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
        this.mailUpToRank.setText(newRank);
        this.mailDownToRank.setText(newRank);
    }

    @Override
    public void SetRatingState(boolean isRated) {
        if (isRated) {
            mailRatingButton.setImageResource(R.drawable.rating_icon_active_3232);
        } else {
            mailRatingButton.setImageResource(R.drawable.rating_icon_deactive_3232);

        }
    }

    /**
     * Set button state with mail type.
     *
     * @param type The mail type.
     */
    @Override
    public void SetButtonState(Common.MailType type, boolean isReceived) {
        buttonState.DeactiveAllcontrol();
        if (isReceived) {
            buttonState.ActiveControl(BUTTON_CONFIRM);
        } else {
            switch (type) {
                case BATTLE_CHALLENGE:
                    buttonState.ActiveControl(BUTTON_ACCEPT);
                    buttonState.ActiveControl(BUTTON_CANCEL);
                    break;
                case BATTLE_RESULT:
                    buttonState.ActiveControl(BUTTON_CONFIRM);
                    break;
                case GIF_REWARD:
                    buttonState.ActiveControl(BUTTON_CLAIM);
                    break;
                case SYSTEM_MESSAGE:
                    buttonState.ActiveControl(BUTTON_CONFIRM);
                    break;
                case NOT_FOUND:
                    buttonState.ActiveControl(BUTTON_CONFIRM);
                case FRIEND_REQUEST:
                    buttonState.ActiveControl(BUTTON_CANCEL_FRIEND);
                    buttonState.ActiveControl(BUTTON_ACCEPT_FRIEND);
                    break;
            }
        }
    }

    /**
     * Set the attach items state.
     *
     * @param attachItems
     */
    @Override
    public void SetItemState(ArrayList<AttachItem> attachItems, Inbox inboxItem) {
        itemState.DeactiveAllcontrol();
        if (attachItems == null || attachItems.size() == 0) return;
        boolean isCoinHasChanged = false;
        boolean rankHasChanged = false;

        for (int i = 0; i < attachItems.size(); i++) {
            AttachItem item = attachItems.get(i);
            switch (item.getGiftType()) {
                case ADD_FRIEND_REQUEST_FRIEND_ID:
                    itemState.ActiveControl(ITEM_ADD_FRIEND);
                    break;
                case BATTLE_CHALLENGE_ID:
                    itemState.ActiveControl(ITEM_BATTLE_CHALLENGE);
                    break;
                case BOOK_UNLOCKED:
                    itemState.ActiveControl(ITEM_NEW_BOOK);
                    break;
                case COINS:
                    isCoinHasChanged = true;
                    break;
                case LESSON_UNLOCKED:
                    //active lesson
                    break;
                case BATTLE_BET_VALUE:
                    break;
                case BATTLE_RANK_UP_DOWN:
                    rankHasChanged = true;
                    break;
            }
        }

        //Coins updated
        if (isCoinHasChanged) {
            if (inboxItem.getMailType() == Common.MailType.BATTLE_RESULT) {
                if (inboxItem.getValue() == 0) {
                    itemState.ActiveControl(ITEM_LOST_COINS);
                    SetMessage("Bạn đã thua người chơi khác trong một bài kiểm tra trước đó.");
                } else if (inboxItem.getValue() == 1) {
                    itemState.ActiveControl(ITEM_ADD_COINS);
                    SetMessage("Chúc mừng bạn đã thắng người chơi khác một bài kiểm tra trước đó.");
                } else {
                    itemState.ActiveControl(ITEM_ADD_COINS);
                    //SetMessage(");
                }
            } else {
                itemState.ActiveControl(ITEM_ADD_COINS);
            }
        }

        //Rank updated.
        if(rankHasChanged)
        {
            if (inboxItem.getValue() == 0) {
                itemState.ActiveControl(ITEM_LEVEL_DOWN);
            } else if (inboxItem.getValue() == 1) {
                itemState.ActiveControl(ITEM_LEVEL_UP);
            } else {
                itemState.ActiveControl(ITEM_LEVEL_DOWN);
            }
        }
    }

    //region transition
    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }

    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        // This view will not be affected by enter transition animation
        //enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        return enterTransition;
    }

    //endregion
}

package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.BattlePrepareActivity;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View.IMailBoxDetailView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.AttachItem;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.Pattern.IAlertBoxResponse;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SingleViewObject.Common.MailType.BATTLE_CHALLENGE;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxDetailPresenter implements IMailBoxDetailPresenter {

    //region properties
    /**
     * Inject the player service
     */
    @Inject
    IPlayerService playerService;

    /**
     * Inject the arena service
     */
    @Inject
    IArenaService arenaService;

    /**
     * Inject volley service
     */
    @Inject
    IVolleyService volleyService;

    /**
     * Inject inbox service
     */
    @Inject
    IInboxService inboxService;

    /**
     * Presenter for view
     */
    private IMailBoxDetailView theView;

    /**
     * The data context
     */
    private Inbox dataContext;

    /**
     * The duel enemy
     */
    private Enemy duelEnemy;

    /**
     * The battle identifier.
     */
    private int battleIdentifier = -1;
    private long battleBetValue = -1;

    //endregion

    //region Implementation

    /**
     * Set data context for mail detail presenter
     *
     * @param data The data context
     */
    @Override
    public void SetDataContext(Inbox data) {
        this.dataContext = data;
        battleIdentifier = -1;

        //Set rating and button state.
        theView.SetRatingState(data.getIsRated());
        theView.SetButtonState(data.getMailType(), dataContext.getIsReceived());
        theView.SetMessage(dataContext.getContent());

        //Get attach items and set item state.
        GetAttachItems();

        //Update opened state.
        MasAsOpened();
    }

    /**
     * Sent rating mail request to server
     */
    @Override
    public void RattingMail() {
        inboxService.RateMail(playerService.GetCurrentUser().getUserId(), dataContext.getId(), GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
            @Override
            public void onSuccess(ErrorCode responseObj) {
                dataContext.setRatingState();
                theView.SetRatingState(dataContext.getIsRated());
            }

            @Override
            public void onError(ErrorCode errorCode) {
                Toast.makeText(GetView(), errorCode.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sent delete mail request to server
     */
    @Override
    public void DeleteMail() {
        if (dataContext.getMailType() == Common.MailType.BATTLE_CHALLENGE) {
            if (dataContext.getIsReceived()) {
                CallDeleteMailService(playerService.GetCurrentUser().getUserId(), dataContext.getId());
            } else {
                CancelBattle();
            }
        } else {
            Common.ShowConfirmMessage(GetView().getResources().getString(R.string.mail_remove_inform),
                    GetView().getResources().getString(R.string.alert_title_confirm),
                    GetView().getResources().getString(R.string.alert_positive_yes),
                    GetView().getResources().getString(R.string.alert_negative_no),
                    GetView(),
                    new IAlertBoxResponse() {
                        @Override
                        public void OnPositive() {
                            CallDeleteMailService(playerService.GetCurrentUser().getUserId(), dataContext.getId());
                        }

                        @Override
                        public void OnNegative() {

                        }
                    }
            );
        }
    }

    /**
     * Do something to back to mailbox view
     */
    @Override
    public void BackToInbox() {
        GetView().finish();
    }

    /**
     * Sent accept battle request to server
     */
    @Override
    public void AcceptBattle() {
        if (dataContext.getMailType() != BATTLE_CHALLENGE) return;
        if (duelEnemy != null) {
            //Move to battle prepare
        } else {
            arenaService.GetEnemyDuel(playerService.GetCurrentUser().getUserId(), battleIdentifier, GetView(), volleyService, new IVolleyResponse<Enemy>() {
                @Override
                public void onSuccess(Enemy enemy) {
                    duelEnemy = enemy;
                    /**
                     * Start battle prepare activity.
                     */
                    arenaService.SetBattleCalledFrom(Common.BattleCalledFrom.FROM_INBOX);
                    Intent it = new Intent(GetView(), BattlePrepareActivity.class);
                    it.putExtra("BATTLE_IDENTIFIER", battleIdentifier);
                    it.putExtra("BATTLE_BET_VALUE", battleBetValue);
                    it.putExtra("BATTLE_MAIL_IDENTIFIER", (int)dataContext.getId());
                    GetView().startActivity(it);
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    Toast.makeText(GetView(), errorCode.getDetails(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Sent cancel battle request to server
     */
    @Override
    public void CancelBattle() {
        if (dataContext.getMailType() != BATTLE_CHALLENGE) return;
        //Show inform message
        Common.ShowConfirmMessage(GetView().getResources().getString(R.string.mail_inform_cancel_battle),
                GetView().getResources().getString(R.string.alert_title_confirm),
                GetView().getResources().getString(R.string.alert_positive_yes),
                GetView().getResources().getString(R.string.alert_negative_no),
                GetView(),
                new IAlertBoxResponse() {
                    @Override
                    public void OnPositive() {
                        //Sent cancel battle request to server
                        arenaService.CancelBattle(playerService.GetCurrentUser().getUserId(), dataContext.getValue(), GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
                            @Override
                            public void onSuccess(ErrorCode responseObj) {
                                //Remove this mail and back to inbox view
                                CallDeleteMailService(playerService.GetCurrentUser().getUserId(), dataContext.getId());
                            }

                            @Override
                            public void onError(ErrorCode errorCode) {
                                Toast.makeText(GetView(), errorCode.getDetails(), Toast.LENGTH_LONG).show();
                                CallDeleteMailService(playerService.GetCurrentUser().getUserId(), dataContext.getId());
                            }
                        });
                    }

                    @Override
                    public void OnNegative() {

                    }
                }
        );
    }

    /**
     * Sent claim reward request to server
     */
    @Override
    public void ClaimReward() {
        if (!dataContext.getIsReceived()) {
            inboxService.ClaimReward(playerService.GetCurrentUser().getUserId(), dataContext.getId(), GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
                @Override
                public void onSuccess(ErrorCode responseObj) {

                    if(responseObj.getCode() == Common.ServiceCode.COMPLETED)
                    {
                        Toast.makeText(GetView(), "Claim success", Toast.LENGTH_SHORT).show();
                    }
                    else if (responseObj.getCode() == Common.ServiceCode.INBOX_CLAIMED_REWARD)
                    {
                        Toast.makeText(GetView(), "Rewards has claimed before.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    Toast.makeText(GetView(), "Fails to claim reward", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //endregion

    //region private method

    /**
     * Get string from config file
     *
     * @param id The string identifier
     * @return The string result
     */
    private String GetString(int id) {
        return GetView().getResources().getString(id);
    }

    /**
     * Get the view
     *
     * @return The appcompat
     */
    private AppCompatActivity _getView = null;

    private AppCompatActivity GetView() {
        if (_getView == null)
            _getView = (AppCompatActivity) theView;
        return _getView;
    }

    /**
     * Call delete mail service
     */
    private void CallDeleteMailService(String user_id, int mail_id) {
        inboxService.RemoveMail(user_id, mail_id, GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
            @Override
            public void onSuccess(ErrorCode responseObj) {
                Toast.makeText(GetView(), "delete success", Toast.LENGTH_SHORT).show();
                GetView().finish();
            }

            @Override
            public void onError(ErrorCode errorCode) {
                //Inform error
                Toast.makeText(GetView(), errorCode.getDetails(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Gets attach items of current mail.
     */
    private void GetAttachItems() {
        inboxService.GetAttachItems(playerService.GetCurrentUser().getUserId(), dataContext.getId(), GetView(), volleyService, new IVolleyResponse<ArrayList<AttachItem>>() {
            @Override
            public void onSuccess(ArrayList<AttachItem> items) {
                SetViewState(items);
            }

            @Override
            public void onError(ErrorCode errorCode) {
                Toast.makeText(GetView(), errorCode.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Mas as opened
     */
    private void MasAsOpened() {
        if (dataContext != null) {
            inboxService.MaskAsOpened(playerService.GetCurrentUser().getUserId(), dataContext.getId(), GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
                @Override
                public void onSuccess(ErrorCode responseCode) {
                    Common.LogInfo("Masked as opened!");
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    Common.LogError("Can not mask as opened");
                }
            });
        }
    }

    /**
     * Set value to controls.
     */
    private void SetViewState(ArrayList<AttachItem> items) {
        int winLostBattleFlag = -1;
        battleIdentifier = -1;
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                AttachItem attach = items.get(i);
                switch (attach.getGiftType()) {
                    case COINS:
                        theView.SetCoins(attach.getValue());
                        break;
                    case LESSON_UNLOCKED:
                        //...
                        break;
                    case BATTLE_CHALLENGE_ID:
                        battleIdentifier = attach.getValue();
                        break;
                    case BATTLE_BET_VALUE:
                        battleBetValue = attach.getValue();
                        theView.SetMessage(
                                String.format(GetString(R.string.mail_content_battle_challenge),
                                        dataContext.getSenderName(),
                                        Common.FormatBigNumber(attach.getValue()),
                                        dataContext.getContent()));
                        break;
                    case BATTLE_RANK_UP_DOWN:
                        theView.SetUpDownRank(Common.GetMedalTitleFromLevel(attach.getValue()));
                        break;
                    case BATTLE_WIN_LOST_FLAG:
                        winLostBattleFlag = attach.getValue();
                        break;
                    case BOOK_UNLOCKED:
                        theView.SetBookName(attach.getDetail());
                }
            }
        }

        theView.SetItemState(items, winLostBattleFlag);

        //Find create date.
        theView.SetTime(dataContext.getDateCreate());

        //Set addition view information.
        //Like: title, status,...
        switch (dataContext.getMailType()) {
            case BATTLE_CHALLENGE:
                theView.SetTitle(GetString(R.string.mail_title_battle_challenge));
                theView.SetStatus(GetString(R.string.mail_status_information));
                break;
            case BATTLE_RESULT:
                theView.SetTitle(GetString(R.string.mail_title_battle_report));
                theView.SetStatus(GetString(R.string.mail_status_battle_won));
                break;
            case GIF_REWARD:
                theView.SetTitle(GetString(R.string.mail_title_system_reward));
                theView.SetStatus(GetString(R.string.mail_status_gif_info));
                break;
            case SYSTEM_MESSAGE:
                theView.SetTitle(GetString((R.string.mail_title_system_message)));
                theView.SetStatus(GetString(R.string.mail_status_information));
                break;
        }

    }
    //endregion

    /**
     * Initialize the mail box detail presenter
     *
     * @param theView presenter for view
     */
    public MailBoxDetailPresenter(IMailBoxDetailView theView) {
        //Initialize the view
        this.theView = theView;

        //Reset value of duel enemy
        this.duelEnemy = null;

        //Inject dependency
        ((SillyApp) GetView().getApplication())
                .getDependencyComponent()
                .inject(this);
    }
}

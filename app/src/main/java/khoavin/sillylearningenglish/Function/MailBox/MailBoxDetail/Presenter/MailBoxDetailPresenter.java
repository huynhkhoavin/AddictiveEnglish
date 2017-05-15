package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonParseException;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.AnswerActivity;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View.IMailBoxDetailView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.Pattern.IAlertBoxResponse;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_GET_ENEMY_DUEL;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.CHECK_LESSON_WAS_BOUGHT;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_CLAIM;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_DELETE;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_RATE;
import static khoavin.sillylearningenglish.SingleViewObject.Common.MailType.BATTLE_CHALLENGE;
import static khoavin.sillylearningenglish.SingleViewObject.Common.MailType.BATTLE_RESULT;
import static khoavin.sillylearningenglish.SingleViewObject.Common.MailType.GIFT_COIN;
import static khoavin.sillylearningenglish.SingleViewObject.Common.MailType.NOT_FOUND;
import static khoavin.sillylearningenglish.SingleViewObject.Common.MailType.SYSTEM_MESSAGE;

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
        Date d;
        try {
            d = dataContext.getDateCreate();
            theView.SetTime(d);
        } catch (ParseException e) {
            d = new Date(2017, 1, 1, 12, 0, 0);
            theView.SetTime(d);
            e.printStackTrace();
        }


        theView.SetMailType(dataContext.getMailType());
        switch (dataContext.getMailType()) {
            case BATTLE_CHALLENGE:
                theView.SetTitle(GetString(R.string.mail_title_battle_challenge));
                theView.SetStatus(GetString(R.string.mail_status_information));
                theView.SetMessage(
                        String.format(GetString(R.string.mail_content_battle_challenge),
                                dataContext.getSenderName(),
                                Common.FormatBigNumber(data.getValue()), data.getContent()));
                break;
            case BATTLE_RESULT:
                theView.SetTitle(GetString(R.string.mail_title_battle_report));
                theView.SetStatus(GetString(R.string.mail_status_battle_won));
                theView.SetCoins(dataContext.getValue());
                theView.SetUpDownRank("BẠC I");
                break;
            case GIFT_COIN:
                theView.SetCoins(dataContext.getValue());
                break;
            case SYSTEM_MESSAGE:
                break;
            case NOT_FOUND:
                break;
        }
    }

    /**
     * Sent rating mail request to server
     */
    @Override
    public void RattingMail() {
        final boolean currentRatingState = dataContext.getIsRated();
        inboxService.RateMail(playerService.GetCurrentUser().getUserId(), dataContext.getId(), GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
            @Override
            public void onSuccess(ErrorCode responseObj) {
                /**
                 * Must update mail box adapter data context
                 */
                dataContext.setIsRated(!currentRatingState);
                theView.SetRatingState(!currentRatingState);
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
            if (dataContext.getIsReceived())
                CallDeleteMailService(playerService.GetCurrentUser().getUserId(), dataContext.getId());
            else
                CancelBattle();
        } else {
            Common.ShowInformMessage(GetView().getResources().getString(R.string.mail_remove_inform),
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
            arenaService.GetEnemyDuel(playerService.GetCurrentUser().getUserId(), 102, GetView(), volleyService, new IVolleyResponse<Enemy>() {
                @Override
                public void onSuccess(Enemy responseObj) {
                    duelEnemy = responseObj;
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
        Common.ShowInformMessage(GetView().getResources().getString(R.string.mail_inform_cancel_battle),
                GetView().getResources().getString(R.string.alert_title_confirm),
                GetView().getResources().getString(R.string.alert_positive_yes),
                GetView().getResources().getString(R.string.alert_negative_no),
                GetView(),
                new IAlertBoxResponse() {
                    @Override
                    public void OnPositive() {
                        //Sent cancel battle request to server
                        arenaService.CancelBattle(playerService.GetCurrentUser().getUserId(), 101, GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
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
        inboxService.ClaimReward(playerService.GetCurrentUser().getUserId(), dataContext.getId(), GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
            @Override
            public void onSuccess(ErrorCode responseObj) {
                Toast.makeText(GetView(), "Claim success", Toast.LENGTH_SHORT).show();
                //Do something to update view
            }

            @Override
            public void onError(ErrorCode errorCode) {
                Toast.makeText(GetView(), "Fails to claim reward", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region private method

    /**
     * Get string from config file
     *
     * @param id The string indentifier
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
     * Move to battle prepare
     *
     * @param duelEnemy The duel enemy
     */
    private void MoveToBattlePrepare(Enemy duelEnemy) {

    }

    /**
     * Call delete mail service
     */
    private void CallDeleteMailService(String user_id, int mail_id) {
        inboxService.RemoveMail(user_id, mail_id, GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
            @Override
            public void onSuccess(ErrorCode responseObj) {
                Toast.makeText(GetView(), "delete success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ErrorCode errorCode) {
                //Inform error
                Toast.makeText(GetView(), errorCode.getDetails(), Toast.LENGTH_LONG).show();
            }
        });
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

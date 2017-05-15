package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;

public interface IInboxService {

    /**
     * Get the inbox items
     *
     * @param user_id The user's identifier
     */
    void GetInboxItems(String user_id, Context context, IVolleyService volleyService, IVolleyResponse<ArrayList<Inbox>> receiver);

    /**
     * Rate mail
     *
     * @param user_id The user's identifier
     * @param mail_id The mail's identifier
     */
    void RateMail(String user_id, int mail_id, Context context, IVolleyService volleyService, IVolleyResponse<ErrorCode> receiver);

    /**
     * Rate mail
     *
     * @param user_id The user's identifier
     * @param mail_id The mail's identifier
     */
    void RemoveMail(String user_id, int mail_id, Context context, IVolleyService volleyService, IVolleyResponse<ErrorCode> receiver);

    /**
     * Mask mail as opened
     *
     * @param user_id The user's Identifier
     * @param mail_id The mail's Identifier
     */
    void MaskAsOpened(String user_id, int mail_id, Context context, IVolleyService volleyService, IVolleyResponse<ErrorCode> receiver);

    /**
     * Claim mail's reward
     *
     * @param user_id The user's identifier
     * @param mail_id The mail's identifier
     */
    void ClaimReward(String user_id, int mail_id, Context context, IVolleyService volleyService, IVolleyResponse<ErrorCode> receiver);

    /**
     * Get current inbox items
     *
     * @return
     */
    ArrayList<Inbox> GetCurrentInboxItem();

    /**
     * Remove item , this function just remove item from local inbox items
     */
    ArrayList<Inbox> RemoveItem(int mail_identifier);

    /**
     * Remove item, this function just remove item from local inbox items
     *
     * @param mail_item
     */
    ArrayList<Inbox> RemoveItem(Inbox mail_item);

}

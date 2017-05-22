package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import khoavin.sillylearningenglish.SingleViewObject.Common;

public class Inbox implements Serializable {

    /**
     * The mail identifier
     */
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * The receiver identifier
     */
    @SerializedName("receiver")
    @Expose
    private String receiver;

    /**
     * The sender identifier
     */
    @SerializedName("sender")
    @Expose
    private String sender;

    /**
     * The mail content
     */
    @SerializedName("content")
    @Expose
    private String content;

    /**
     * The date created
     */
    @SerializedName("date_create")
    @Expose
    private String dateCreate;

    /**
     * The mail type
     */
    @SerializedName("mail_type")
    @Expose
    private String mailType;

    /**
     * The value
     */
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * Check if mail has read
     */
    @SerializedName("is_read")
    @Expose
    private String isRead;

    /**
     * The sender name
     */
    @SerializedName("sender_name")
    @Expose
    private String senderName;

    /**
     * Check if mail is rated
     */
    @SerializedName("is_rated")
    @Expose
    private String isRated;

    /**
     * Check if claimed reward or answer the duel
     */
    @SerializedName("is_received")
    @Expose
    private String isReceived;

    //region addition properties
    /**
     * The attach items of this mail
     */

    //endregion

    /**
     * Get mails's identifier
     * @return
     * The mail's identifier
     */
    public Integer getId() {
        return Integer.valueOf(id);
    }

    /**
     * Get the receiver's identifier
     * @return
     * The receiver's identifier
     */
    public Integer getReceiver() {
        return Integer.valueOf(receiver);
    }

    /**
     * Get sender's identifier
     * @return
     * The sender's identifier
     */
    public Integer getSender() {
        return Integer.valueOf(sender);
    }

    /**
     * Get the mail's content
     * @return
     * The message
     */
    public String getContent() {
        return content;
    }

    public String getTitle()
    {
        switch (getMailType())
        {
            case BATTLE_CHALLENGE:
                return "Thư thách đấu.";
            case BATTLE_RESULT:
                return "Kết quả trận đấu.";
            case GIF_REWARD:
                return "Quà hệ thống.";
            case SYSTEM_MESSAGE:
                return "Thông báo.";
        }
        return "Hệ thống.";
    }

    /**
     * Get the date created
     */
    public Date getDateCreate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = dateFormat.parse(dateCreate);
        return d;
    }

    /**
     * Get mail type
     * @return
     * The type of this mail
     */
    public Common.MailType getMailType() {
        if(mailType == null || mailType == "") return Common.MailType.NOT_FOUND;
        return Common.MailType.fromInt(Integer.valueOf(mailType));
    }

    /**
     * Get the value
     * @return
     * The value
     */
    public Integer getValue() {
        return Integer.valueOf(value);
    }

    /**
     *
     * @return Return true if mail has read
     */
    public boolean IsRead()
    {
        if(isRead.equals("1"))
            return true;
        return false;
    }

    /**
     * Set read state
     */
    public void setMailStateToJustRead()
    {
        if(isRead.equals("0"))
            isRead = "1";
    }

    /**
     * Get the sender's name
     * @return
     * The sender's name
     */
    public String getSenderName() {return senderName;}

    /**
     * Get is rated
     * @return
     * The rated state
     */
    public boolean getIsRated()
    {
        if(isRated.equals("0"))
            return false;
        else
            return true;
    }

    /**
     * set rating state. if current state is rated -> change to not yet rated.
     * otherwise change to rated.
     */
    public void setRatingState()
    {
        if(isRated.equals("0"))
            isRated = "1";
        else
            isRated = "0";
    }

    /**
     * Get the received state
     * @return
     * The received state
     */
    public boolean getIsReceived()
    {
        if(isReceived.equals("1"))
            return true;
        else
            return false;
    }

    /**
     * Set received state
     */
    public void setReceivedState()
    {
        if (isReceived.equals("0"))
        {
            isReceived = "1";
        }

    }
}
package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inbox {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("receiver")
    @Expose
    private String receiver;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("date_create")
    @Expose
    private String dateCreate;
    @SerializedName("mail_type")
    @Expose
    private String mailType;
    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("is_read")
    @Expose
    private String isRead;

    @SerializedName("sender_name")
    @Expose
    private String senderName;

    public Integer getId() {
        return Integer.valueOf(id);
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Date getDateCreate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = dateFormat.parse(dateCreate);
        return d;
    }

    public String getMailType() {
        return mailType;
    }

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

    public String getSenderName() {return senderName;}
}
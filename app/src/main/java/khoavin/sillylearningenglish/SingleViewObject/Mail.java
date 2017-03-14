package khoavin.sillylearningenglish.SingleViewObject;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class Mail {
    private boolean Checked;
    private String Sender;
    private String BriefContent;
    private boolean ReadStatus;

    public Mail(boolean checked, String sender, String briefContent, boolean readStatus) {
        Checked = checked;
        Sender = sender;
        BriefContent = briefContent;
        ReadStatus = readStatus;
    }

    public boolean isReadStatus() {
        return ReadStatus;
    }

    public void setReadStatus(boolean readStatus) {
        ReadStatus = readStatus;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getBriefContent() {
        return BriefContent;
    }

    public void setBriefContent(String briefContent) {
        BriefContent = briefContent;
    }


}

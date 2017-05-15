package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import khoavin.sillylearningenglish.SingleViewObject.Common;

public class AttatchItem
{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("mail_id")
    @Expose
    private String mailId;

    @SerializedName("gift_type")
    @Expose
    private String giftType;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("detail")
    @Expose
    private String detail;

    public String getId() {
        return id;
    }

    public String getMailId() {
        return mailId;
    }

    public Common.AttachType getGiftType() {
        if(giftType == null || giftType == "")
            return Common.AttachType.NOT_FOUND;
        else
            return Common.AttachType.fromInt(Integer.valueOf(giftType));
    }

    public Integer getValue() {
        if(value == null || value == "") return 0;
        return Integer.valueOf(value);
    }

    public Integer getQuantity() {
        if(quantity == null || quantity == "") return 0;
        return Integer.valueOf(quantity);
    }

    public String getDetail() {
        return detail;
    }
}

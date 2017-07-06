package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OatOal on 7/6/2017.
 */

public class NewMailCounter {
    @SerializedName("unbox_mail")
    @Expose
    private String unboxMail;

    public Integer getUnboxMail() {
        if(unboxMail == null || unboxMail.equals(""))
            return 0;
        return Integer.valueOf(unboxMail);
    }
}

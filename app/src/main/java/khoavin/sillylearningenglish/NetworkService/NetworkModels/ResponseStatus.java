package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseStatus {

    //region Properties

    //The response code
    @SerializedName("code")
    @Expose
    private Integer code;

    //The response message
    @SerializedName("message")
    @Expose
    private String message;

    //endregion

    //region Get or set Properties

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //endregion

}
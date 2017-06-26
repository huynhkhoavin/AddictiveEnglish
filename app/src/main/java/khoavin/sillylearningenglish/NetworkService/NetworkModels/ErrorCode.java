package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by KhoaVin on 09/05/2017.
 */
public class ErrorCode implements Serializable {
    /**
     * The code
     */
    @SerializedName("code")
    @Expose
    private String code;

    /**
     * The details
     */
    @SerializedName("details")
    @Expose
    private String details;

    /**
     * Storage Service code
     */
    private Common.ServiceCode serviceCode;

    /**
     * Check if initialize
     */
    private boolean isInitialize = false;

    /**
     * Get the code
     * @return
     */
    public Common.ServiceCode getCode() {
        if(!isInitialize)
        {
            Integer i = Integer.valueOf(code);
            if(i != null)
            {
                this.serviceCode = Common.ServiceCode.FromInt(i);
            }
            else
            {
                this.serviceCode = Common.ServiceCode.NOT_FOUND;
            }
        }

        return serviceCode;
    }

    /**
     * Set the code
     * @param code
     * The error code
     */
    public void setCode(Common.ServiceCode code) {
        this.serviceCode = code;
        this.code = String.valueOf(code.getValue());
    }

    /**
     * Get the detail text
     * @return
     * The detail of error code
     */
    public String getDetails() {
        return details;
    }

    /**
     * Set the detail text
     * @param details
     * The detail of error code
     */
    public void setDetails(String details) {
        this.details = details;
    }

    public ErrorCode(String code, String details) {
        this.code = code;
        this.details = details;
    }

    public ErrorCode() {

    }
}
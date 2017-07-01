package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 6/30/2017.
 */

public class AppParam {
    @SerializedName("param_id")
    @Expose
    private String paramId;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("enum_type")
    @Expose
    private String enumType;

    public String getParamId() {
        return paramId;
    }

    public Integer getValue() {
        return Integer.valueOf(value);
    }

    public Common.ParamType getParamType() {
        if(enumType == null || enumType == "")
            return Common.ParamType.UNKNOWN;
        return Common.ParamType.fromInt(Integer.valueOf(enumType));
    }
}

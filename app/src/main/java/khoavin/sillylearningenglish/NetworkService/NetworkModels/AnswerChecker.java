package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerChecker {

    @SerializedName("checker")
    @Expose
    private String checker;

    public Integer getChecker() {
        return Integer.valueOf(checker);
    }

    public void setChecker(Integer checker) {
        this.checker = String.valueOf(checker);
    }

}
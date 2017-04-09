package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerChecker {

    @SerializedName("checker")
    @Expose
    private String checker;

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public boolean getCheckerTrueFalse() {
        if(checker.equals("1"))
            return  true;
        return  false;
    }

}
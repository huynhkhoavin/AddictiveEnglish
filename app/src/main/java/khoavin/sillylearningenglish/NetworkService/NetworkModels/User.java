package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    //the user's id
    @SerializedName("user_id")
    @Expose
    private String userId;

    //the user's name
    @SerializedName("name")
    @Expose
    private String name;

    //the user remain coins
    @SerializedName("coin")
    @Expose
    private String coin;

    //the user's rank
    @SerializedName("rank")
    @Expose
    private String rank;

    //the user's level
    @SerializedName("level")
    @Expose
    private String level;

    //the win match
    @SerializedName("win_match")
    @Expose
    private String winMatch;

    //the total match
    @SerializedName("total_match")
    @Expose
    private String totalMatch;

    //the avatar's url
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getCoin() {
        return Integer.valueOf(coin);
    }

    public int getRank() {
        return Integer.valueOf(rank);
    }

    public int getLevel() {
        return Integer.valueOf(level);
    }

    public int getWinMatch() {
        return Integer.valueOf(winMatch);
    }

    public int getTotalMatch() {
        return Integer.valueOf(totalMatch);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}
package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ranking {

    //region Ranking WebModel properties

    /**
     * The user identifier on our server.
     */
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * The user identifier with facebook or google account.
     */
    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * The user's name
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * The user's rank position
     */
    @SerializedName("rank")
    @Expose
    private String rank;

    /**
     * The user's level.
     */
    @SerializedName("level")
    @Expose
    private String level;

    /**
     * The user's total win match
     */
    @SerializedName("win_match")
    @Expose
    private String winMatch;

    /**
     * The user's total match
     */
    @SerializedName("total_match")
    @Expose
    private String totalMatch;

    /**
     * The user's avatar url
     */
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    /**
     * Gets the value indicate if current ranking item is current user's friend.
     */
    @SerializedName("is_user_friend")
    @Expose
    private String isUserFriend;

    /**
     * Gets the rank position.
     *
     * @return The rank position.
     */
    public Integer getRankPosition() {
        return Integer.valueOf(rank);
    }

    /**
     * Gets the rank level.
     *
     * @return The rank level.
     */
    public Integer getLevel() {
        return Integer.valueOf(level);
    }

    /**
     * Gets the user's name.
     *
     * @return The user's name.
     */
    public String getUserName() {
        return name;
    }

    /**
     * Gets the total battles.
     *
     * @return The total battles.
     */
    public Integer getTotalBattle() {
        return Integer.valueOf(totalMatch);
    }

    /**
     * Gets the win battles
     *
     * @return The win battles.
     */
    public Integer getWinBattle() {
        return Integer.valueOf(winMatch);
    }

    /**
     * Gets the user's avatar url.
     *
     * @return The user's avatar url.
     */
    public String getUserAvatar() {
        return avatarUrl;
    }

    /**
     * Gets the user's identifier.
     * @return The user's identifier
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set rank position.
     */
    public void setRank(int value)
    {
        this.rank = String.valueOf(value);
    }

    /**
     * Gets a value indicate this user with current logged user communication
     * if they are friends return true, otherwise return false.
     * @return
     */
    public boolean getIsUserFriend()
    {
        if(isUserFriend.equals("1"))
            return true;
        return false;
    }
}
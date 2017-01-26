package khoavin.sillylearningenglish.EntityDatabase.Silly_english;

/**
 * Created by KhoaVin on 1/22/2017.
 */

public class User {
    private String us_username;
    private String us_facebook_id;
    private int us_coin;
    private int us_exp_point;
    private int us_level_id;
    private int us_win_math;
    private int us_total_match;

    public String getUs_username(){
        return us_username;
    }

    public void setUs_username(String us_username){
        this.us_username=us_username;
    }

    public String getUs_facebook_id(){
        return us_facebook_id;
    }

    public void setUs_facebook_id(String us_facebook_id){
        this.us_facebook_id=us_facebook_id;
    }

    public int getUs_coin(){
        return us_coin;
    }

    public void setUs_coin(int us_coin){
        this.us_coin=us_coin;
    }

    public int getUs_exp_point(){
        return us_exp_point;
    }

    public void setUs_exp_point(int us_exp_point){
        this.us_exp_point=us_exp_point;
    }

    public int getUs_level_id(){
        return us_level_id;
    }

    public void setUs_level_id(int us_level_id){
        this.us_level_id=us_level_id;
    }

    public int getUs_win_math(){
        return us_win_math;
    }

    public void setUs_win_math(int us_win_math){
        this.us_win_math=us_win_math;
    }

    public int getUs_total_match(){
        return us_total_match;
    }

    public void setUs_total_match(int us_total_match){
        this.us_total_match=us_total_match;
    }
}

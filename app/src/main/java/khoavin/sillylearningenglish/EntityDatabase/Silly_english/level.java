package khoavin.sillylearningenglish.EntityDatabase.Silly_english;

/**
 * Created by KhoaVin on 1/22/2017.
 */

public class Level {
    private int lv_exp_point;
    private char lv_leter;
    private int lv_metal_kind;

    public int getLv_exp_point(){
        return lv_exp_point;
    }

    public void setLv_exp_point(int lv_exp_point){
        this.lv_exp_point=lv_exp_point;
    }

    public char getLv_leter(){
        return lv_leter;
    }

    public void setLv_leter(char lv_leter){
        this.lv_leter=lv_leter;
    }

    public int getLv_metal_kind(){
        return lv_metal_kind;
    }

    public void setLv_metal_kind(int lv_metal_kind){
        this.lv_metal_kind=lv_metal_kind;
    }
}

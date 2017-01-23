package khoavin.sillylearningenglish.EntityDatabase.Silly_english;

/**
 * Created by KhoaVin on 1/22/2017.
 */

public class ReadingUnit {
    private String re_source;
    private int re_price;
    private int re_max_read_times;
    private String re_url;

    public String getRe_source(){
        return re_source;
    }

    public void setRe_source(String re_source){
        this.re_source=re_source;
    }

    public int getRe_price(){
        return re_price;
    }

    public void setRe_price(int re_price){
        this.re_price=re_price;
    }

    public int getRe_max_read_times(){
        return re_max_read_times;
    }

    public void setRe_max_read_times(int re_max_read_times){
        this.re_max_read_times=re_max_read_times;
    }

    public String getRe_url(){
        return re_url;
    }

    public void setRe_url(String re_url){
        this.re_url=re_url;
    }
}

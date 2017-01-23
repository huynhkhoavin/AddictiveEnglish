package khoavin.sillylearningenglish.EntityDatabase.Silly_english;

/**
 * Created by KhoaVin on 1/22/2017.
 */

public class ListeningUnit {
    private String lu_source;
    private int lu_price;
    private int lu_max_hear_times;
    private String lu_url;
    private String lu_note;

    public String getLu_source(){
        return lu_source;
    }

    public void setLu_source(String lu_source){
        this.lu_source=lu_source;
    }

    public int getLu_price(){
        return lu_price;
    }

    public void setLu_price(int lu_price){
        this.lu_price=lu_price;
    }

    public int getLu_max_hear_times(){
        return lu_max_hear_times;
    }

    public void setLu_max_hear_times(int lu_max_hear_times){
        this.lu_max_hear_times=lu_max_hear_times;
    }

    public String getLu_url(){
        return lu_url;
    }

    public void setLu_url(String lu_url){
        this.lu_url=lu_url;
    }

    public String getLu_note(){
        return lu_note;
    }

    public void setLu_note(String lu_note){
        this.lu_note=lu_note;
    }
}

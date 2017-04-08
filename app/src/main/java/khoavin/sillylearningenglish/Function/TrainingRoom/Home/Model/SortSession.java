package khoavin.sillylearningenglish.Function.TrainingRoom.Home.Model;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class SortSession {
    private String headerTitle;
    private ArrayList<Lesson> allItemsInSection;


    public SortSession() {

    }
    public SortSession(String headerTitle, ArrayList<Lesson> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Lesson> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Lesson> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}

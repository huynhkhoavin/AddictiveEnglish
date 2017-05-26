package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Model;

import java.io.Serializable;
import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class GroupItem implements Serializable {
    private String headerTitle;
    private ArrayList<Lesson> allItemsInSection;


    public GroupItem() {

    }
    public GroupItem(String headerTitle, ArrayList<Lesson> allItemsInSection) {
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

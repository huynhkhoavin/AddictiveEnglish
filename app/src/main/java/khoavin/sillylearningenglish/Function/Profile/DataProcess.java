package khoavin.sillylearningenglish.Function.Profile;

import java.util.ArrayList;
import java.util.HashMap;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.ProgressChart;

/**
 * Created by KhoaVin on 10/07/2017.
 */

public class DataProcess {
    public ProgressChart[] progressCharts;



    public ProgressChart[] getProgressCharts() {
        return progressCharts;
    }

    public void setProgressCharts(ProgressChart[] progressCharts) {
        this.progressCharts = progressCharts;
    }

    public ArrayList<String> getLabels(){
        HashMap<Integer,String> mapWeekDay = new HashMap<>();
        mapWeekDay.put(2,"Monday");
        mapWeekDay.put(3,"Tuesday");
        mapWeekDay.put(4,"Wednesday");
        mapWeekDay.put(5,"Thursday");
        mapWeekDay.put(6,"Friday");
        mapWeekDay.put(7,"Saturday");
        mapWeekDay.put(8,"Sunday");


        ArrayList<String> labels = new ArrayList<String>();
        int startDay = progressCharts[0].getDay();
        for (int i  = 0;i<7;i++){
            labels.add(mapWeekDay.get(getDay(startDay,i-1)));
        }
        return labels;
    }
    public int getDay(int startDay, int duration){
        return (((startDay-2)+ duration%6)+2);
    }
}

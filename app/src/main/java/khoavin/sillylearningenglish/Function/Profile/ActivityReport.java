package khoavin.sillylearningenglish.Function.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ProgressChart;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_CHART_PROGRESS;

/**
 * Created by KhoaVin on 10/07/2017.
 */

public class ActivityReport extends BaseDetailActivity {
    @Inject
    IAuthenticationService authenticationService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_report);
        ((SillyApp) getApplication()).getDependencyComponent().inject(this);
        getChartData();
    }

    public void getChartData(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                ProgressChart[] listProgress = JsonConvert.getArray(response,ProgressChart[].class);
                getEntries(listProgress);
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", authenticationService.getCurrentUser().getUid());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_CHART_PROGRESS;
            }
        };
        networkAsyncTask.execute();
    }

    public ArrayList<String> getLabels(ProgressChart[] listProgress){
        HashMap<Integer,String> mapWeekDay = new HashMap<>();
        mapWeekDay.put(2,"Monday");
        mapWeekDay.put(3,"Tuesday");
        mapWeekDay.put(4,"Wednesday");
        mapWeekDay.put(5,"Thursday");
        mapWeekDay.put(6,"Friday");
        mapWeekDay.put(7,"Saturday");
        mapWeekDay.put(8,"Sunday");


        ArrayList<String> labels = new ArrayList<String>();
        for (int i  = 1;i<=7;i++){
            labels.add(mapWeekDay.get(getDay(listProgress[0].getDay(),i-1)));
        }
        return labels;
    }
    public void getEntries(ProgressChart[] listProgress){
        HashMap<Integer,String> mapWeekDay = new HashMap<>();
        mapWeekDay.put(2,"Monday");
        mapWeekDay.put(3,"Tuesday");
        mapWeekDay.put(4,"Wednesday");
        mapWeekDay.put(5,"Thursday");
        mapWeekDay.put(6,"Friday");
        mapWeekDay.put(7,"Saturday");
        mapWeekDay.put(8,"Sunday");

        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        HashMap<Integer,Integer> mapConvert = new HashMap<>();
        for (ProgressChart pc : listProgress){
            mapConvert.put(pc.getDay(),pc.getUnitCount());
        }
        int startDay = listProgress[0].getDay();
        int index = 0;
        for (int i = 0;i<=6;i++){
            int realday = getDay(startDay,i);
            if (mapConvert.containsKey(realday)){
                labels.add(mapWeekDay.get(realday));
                entries.add(new BarEntry(mapConvert.get(realday),index));
            }
            else
            {
                labels.add(mapWeekDay.get(realday));
                entries.add(new BarEntry(0,index));
            }
            index++;
        }

        BarDataSet dataset = new BarDataSet(entries, "Number of unit you learn 7 day newest");

        BarChart chart = new BarChart(this);

        chart.animateY(1000);

        chart.setTouchEnabled(true);

        setContentView(chart);

        BarData data = new BarData(labels,dataset);

        chart.setData(data);

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
    }

    public int getDay(int startDay, int duration){
        if ((startDay+duration)>8){
            return (startDay+duration-7);
        }else
            return (startDay+duration);
    }
    public int searchDay(ProgressChart[] list,int day){
        for (int i = 0; i< list.length;i++){
            if (list[i].getDay() == day){
                return i;
            }
        }
        return  -1;
    }
}

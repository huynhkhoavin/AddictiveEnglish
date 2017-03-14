package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Model;

import java.util.ArrayList;

import khoavin.sillylearningenglish.SingleViewObject.ProgressUnit;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonDetailModel implements ILessonDetailModel {
    ProgressUnit[] progressUnits;


    public LessonDetailModel() {
        FakeData();
    }

    @Override
    public ArrayList<ProgressUnit> getProgress() {
        ArrayList<ProgressUnit> progressUnits = new ArrayList<>();
        for (ProgressUnit prg:progressUnits)
        {
            progressUnits.add(prg);
        }
        return progressUnits;
    }
    private void FakeData(){
        if (progressUnits==null)
            progressUnits = new ProgressUnit[]{
                    new ProgressUnit(1,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",true),
                    new ProgressUnit(0,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",true),
                    new ProgressUnit(0,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",true),
                    new ProgressUnit(0,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",true),
                    new ProgressUnit(0,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",false),
                    new ProgressUnit(0,"Chap 01","05:30",false)
            };
    }
}

package khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.Model;

import khoavin.sillylearningenglish.SINGLE_OBJECT.ProgressUnit;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonDetailModel implements ILessonDetailModel {
    ProgressUnit[] progressUnits;


    public LessonDetailModel() {
        FakeData();
    }

    @Override
    public ProgressUnit[] getProgress() {

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

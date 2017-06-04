package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Object;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonTracker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;

/**
 * Created by KhoaVin on 27/05/2017.
 */

public class LessonProgress {
    ArrayList<LessonUnit> lessonUnitArrayList;
    LessonTracker lessonTracker;

    public LessonProgress(ArrayList<LessonUnit> lessonUnitArrayList, LessonTracker lessonTracker) {
        this.lessonUnitArrayList = lessonUnitArrayList;
        this.lessonTracker = lessonTracker;
    }

    public ArrayList<LessonUnit> getLessonUnitArrayList() {
        return lessonUnitArrayList;
    }

    public void setLessonUnitArrayList(ArrayList<LessonUnit> lessonUnitArrayList) {
        this.lessonUnitArrayList = lessonUnitArrayList;
    }

    public LessonTracker getLessonTracker() {
        return lessonTracker;
    }

    public void setLessonTracker(LessonTracker lessonTracker) {
        this.lessonTracker = lessonTracker;
    }
    public int getCurrentProgressUnitId(){
        int id  = lessonUnitArrayList.get(0).getLuId()+(lessonTracker.getProgress()/(lessonUnitArrayList.size()*5));
        return id;
    }

}

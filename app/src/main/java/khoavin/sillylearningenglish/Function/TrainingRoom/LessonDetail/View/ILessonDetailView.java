package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public interface ILessonDetailView {
    void ShowProgress(ArrayList<LessonUnit> progressUnits, int progress);
}

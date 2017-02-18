package khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.Presenter;

import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.Model.ILessonDetailModel;
import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.View.ILessonDetailView;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonDetailPresenter implements ILessonDetailPresenter {

    ILessonDetailView lessonDetailView;
    ILessonDetailModel lessonDetailModel;

    public LessonDetailPresenter(ILessonDetailView lessonDetailView, ILessonDetailModel lessonDetailModel) {
        this.lessonDetailView = lessonDetailView;
        this.lessonDetailModel = lessonDetailModel;
    }

    @Override
    public void ShowProgressList() {
        lessonDetailView.ShowProgress(lessonDetailModel.getProgress());
    }
}

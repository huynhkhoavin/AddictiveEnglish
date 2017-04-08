package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;


import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Function.Arena.Presenters.IResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IResultView;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswers;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ResultPresenter implements IResultPresenter {

    private static final String RESULT_VIEW_TAG = "RESULT_VIEW: ";

    //The answer reuslts
    private MyAnswers myAnswers;

    //The view
    private IResultView resultView;

    //The service
    @Inject
    IArenaService arenaService;

    public ResultPresenter(final IResultView theView)
    {
        //Set view
        this.resultView = theView;

        //Inject service
        ((SillyApp) ((AppCompatActivity) resultView).getApplication())
                .getDependencyComponent()
                .inject(this);
    }
}

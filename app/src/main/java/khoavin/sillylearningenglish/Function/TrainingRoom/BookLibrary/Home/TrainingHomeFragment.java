package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.HomeMenu.HomePage.HomePageFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter.GroupViewAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Listener.ItemClickPosition;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Listener.SortListener;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Model.SortSession;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Presenter.TrainingPresenter;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonInfoFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.*;
import static khoavin.sillylearningenglish.SYSTEM.Service.Constants.ACTION.UPDATE_PROGRESS_SUCCESS;

/**
 * Created by KhoaVin on 2/13/2017.
 */

public class TrainingHomeFragment extends FragmentPattern {
    static String TAG = "TrainingHomeFragment";
    TrainingPresenter trainingPresenter;
    ArrayList<SortSession> allSortSession = new ArrayList<SortSession>();

    @BindView(R.id.my_recycler_view)
    RecyclerView my_recycler_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_traning_room,container,false);
        ButterKnife.bind(this,v);

        trainingPresenter = new TrainingPresenter(getActivity());
        if (savedInstanceState!=null){
            allSortSession = (ArrayList<SortSession>)savedInstanceState.getSerializable("list");
        }else{
            ProgressAsyncTask progressAsynctask = new ProgressAsyncTask(getContext()) {
                @Override
                public void onDoing() {
                    trainingPresenter.GetPopularLesson(new SortListener() {
                        @Override
                        public void PopularSort(ArrayList<Lesson> lessons) {
                            SortSession popularSort = new SortSession();
                            allSortSession.clear();
                            popularSort.setHeaderTitle("Most Popular");
                            popularSort.setAllItemsInSection(lessons);
                            allSortSession.add(popularSort);
                            my_recycler_view.setHasFixedSize(true);
                            final GroupViewAdapter adapter = new GroupViewAdapter(getContext(), ArrayConvert.toObjectArray(allSortSession));
                            adapter.setItemClickPosition(new ItemClickPosition() {
                                @Override
                                public void OnClick(int Row, int Column) {
                                    Log.e(TAG,String.valueOf(Row) +":" + String.valueOf(Column) );
                                    //Intent it = new Intent(getContext(), LessonInfoFragment.class);
                                    //To pass:
                                    Storage.getInstance().addValue(CURRENT_LESSON, adapter.getItem(Row,Column));

                                    EventBus.getDefault().post("Training");
                                }
                            });
                            my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            my_recycler_view.setAdapter(adapter);
                        }
                    });
                }
                @Override
                public void onTaskComplete(Void aVoid) {

                }
            };
            progressAsynctask.execute();
        }

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("list",allSortSession);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            allSortSession = (ArrayList<SortSession>)savedInstanceState.getSerializable("list");
        }
    }
}

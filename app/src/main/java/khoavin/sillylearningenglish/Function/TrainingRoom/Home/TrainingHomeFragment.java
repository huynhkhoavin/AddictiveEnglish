package khoavin.sillylearningenglish.Function.TrainingRoom.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Adapter.RecyclerViewDataAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Listener.ItemClickPosition;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Listener.SortListener;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Model.SortSession;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Presenter.TrainingPresenter;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonInfo.View.LessonInfoActivity;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ProgressAsynctask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

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
        ProgressAsynctask progressAsynctask = new ProgressAsynctask() {
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
                        final RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), ArrayConvert.toObjectArray(allSortSession));
                        adapter.setItemClickPosition(new ItemClickPosition() {
                            @Override
                            public void OnClick(int Row, int Column) {
                                Log.e(TAG,String.valueOf(Row) +":" + String.valueOf(Column) );
                                Intent it = new Intent(getContext(), LessonInfoActivity.class);
                                //To pass:
                                it.putExtra("Lesson", adapter.getItem(Row,Column));
                                startActivity(it);
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
        progressAsynctask.setContext(getActivity());
        progressAsynctask.execute();
        return v;
    }
}

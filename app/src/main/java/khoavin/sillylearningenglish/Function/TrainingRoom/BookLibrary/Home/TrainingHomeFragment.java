package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter.GroupViewAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Listener.ItemClickPosition;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Listener.SortListener;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Model.GroupItem;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Presenter.TrainingPresenter;
import khoavin.sillylearningenglish.Function.TrainingRoom.ListLessonExtend.ActivityListLesson;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.Pattern.RecyclerItemClickListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.*;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_POPULAR_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_RATING_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_USER_NOTIFICATION;

/**
 * Created by KhoaVin on 2/13/2017.
 */

public class TrainingHomeFragment extends FragmentPattern {
    @Inject
    IVolleyService volleyService;
    static String TAG = "TrainingHomeFragment";
    TrainingPresenter trainingPresenter;
    ArrayList<GroupItem> allSortSession = new ArrayList<GroupItem>();

    @BindView(R.id.my_recycler_view)
    RecyclerView my_recycler_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_traning_room,container,false);
        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);

        ButterKnife.bind(this,v);

        trainingPresenter = new TrainingPresenter(getActivity());

        getPopularLesson();

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("list",allSortSession);
    }

    public void getPopularLesson(){
        allSortSession.clear();

        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                Lesson[] lessons = JsonConvert.getArray(response,Lesson[].class);
                GroupItem popularSort = new GroupItem();
                //allSortSession.clear();
                popularSort.setHeaderTitle("Most Popular");
                popularSort.setAllItemsInSection(ArrayConvert.toArrayList(lessons));
                allSortSession.add(popularSort);
                getRatingLesson();
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("limit_amount","10");
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_POPULAR_LESSON;
            }
        };
        networkAsyncTask.execute();
    }
    public void getRatingLesson(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                final Lesson[] lessons = JsonConvert.getArray(response,Lesson[].class);
                GroupItem popularSort = new GroupItem();
                //allSortSession.clear();
                popularSort.setHeaderTitle("Top Rate");
                popularSort.setAllItemsInSection(ArrayConvert.toArrayList(lessons));
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

                        EventBus.getDefault().post(Constants.ACTION.GO_TO_DETAIL);
                    }
                });
                adapter.setBtnMoreClickListener(new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), ActivityListLesson.class);
                        Storage.getInstance().addValue(CURRENT_MORE_LESSON,position);

                        intent.putExtra(CURRENT_MORE_LESSON,position);
                        startActivity(intent);
                    }
                });


                my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                my_recycler_view.setAdapter(adapter);
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("limit_amount","10");
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_RATING_LESSON;
            }
        };
        networkAsyncTask.execute();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            allSortSession = (ArrayList<GroupItem>)savedInstanceState.getSerializable("list");
        }
    }
}

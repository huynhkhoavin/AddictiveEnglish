package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Storage;

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
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter.SingleViewAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Listener.ItemClickPosition;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.Pattern.RecyclerItemClickListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_USER_LESSON;

/**
 * Created by KhoaVin on 03/06/2017.
 */

public class UserStorageFragment extends FragmentPattern {
    @Inject
    public IVolleyService volleyService;
    @Inject
    public IAuthenticationService authenticationService;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public SingleViewAdapter singleViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_storage,container,false);
        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);
        ButterKnife.bind(this,v);
        setupAdapter();
        getUserLesson();
        return v;
    }

    public void getUserLesson(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                Lesson[] lessons = JsonConvert.getArray(response,Lesson[].class);
                singleViewAdapter.setDataSource(ArrayConvert.toObjectArray(lessons));
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",authenticationService.getCurrentUser().getUid());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_USER_LESSON;
            }
        };
        networkAsyncTask.execute();
    }
    public void setupAdapter(){
        singleViewAdapter = new SingleViewAdapter(getContext(),new ArrayList<>());
        singleViewAdapter.setOnItemClickListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                        //Intent it = new Intent(getContext(), LessonInfoFragment.class);
                        //To pass:
                        Storage.getInstance().addValue(CURRENT_LESSON, singleViewAdapter.getDataSource().get(position));

                        EventBus.getDefault().post(Constants.ACTION.GO_TO_DETAIL);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(singleViewAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }
}

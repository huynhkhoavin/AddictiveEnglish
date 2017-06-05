package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Storage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter.SingleViewAdapter;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

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
        ProgressAsyncTask progressThreadTask = new ProgressAsyncTask(getContext()) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USER_LESSON,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Lesson[] lessons = JsonConvert.getArray(response,Lesson[].class);
                                singleViewAdapter.setDataSource(ArrayConvert.toObjectArray(lessons));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id",authenticationService.getCurrentUser().getUid());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };
        progressThreadTask.execute();
    }
    public void setupAdapter(){
        singleViewAdapter = new SingleViewAdapter(getContext(),new ArrayList<>());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(singleViewAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }
}

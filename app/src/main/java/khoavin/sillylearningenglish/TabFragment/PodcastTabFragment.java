package khoavin.sillylearningenglish.TabFragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import khoavin.sillylearningenglish.Adapter.Listening.PodcastListAdapter;
import khoavin.sillylearningenglish.EntityDatabase.Silly_english.ListeningUnit;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.ToolFactory.DividerItemDecoration;
import khoavin.sillylearningenglish.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.ToolFactory.VolleySingleton;
import khoavin.sillylearningenglish.ViewHolder.PodcastItemViewHolder;

import static khoavin.sillylearningenglish.Constant.RequestCode.ACTION;
import static khoavin.sillylearningenglish.Constant.RequestCode.LISTENING_PODCAST;
import static khoavin.sillylearningenglish.Constant.WebConstant.WEBSERVICE_ADDRESS;

/**
 * Created by KhoaVin on 1/23/2017.
 */

public class PodcastTabFragment extends TabFragment{
    RecyclerView recyclerView;
    PodcastListAdapter podcastListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.listening_podcast_tab,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.podcast_recycleView);
        ConnectToServer();
        return v;
    }
    public void ConnectToServer(){
        RequestQueue queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        String parameterGet = "";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,WEBSERVICE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ListeningUnit[] listeningUnits = JsonConvert.getArray(response,ListeningUnit[].class);
                        fillListView(listeningUnits);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action",LISTENING_PODCAST);
                return params;
            }
        };
        queue.add(stringRequest);
    }
    public void fillListView(ListeningUnit[] dataSource){
        podcastListAdapter = new PodcastListAdapter(getContext(),dataSource);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(podcastListAdapter);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}

package khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.YoutubeChanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubePlayer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.ProgressListAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Recycler.YoutubeChannelAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.YoutubePlayer.YoutubeActivity;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.YoutubeChannel;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_CHANNEL;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_LIST_YOUTUBE_CHANNEL;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.SERVER_URL;

/**
 * Created by KhoaVin on 08/07/2017.
 */

public class ChannelFragment extends FragmentPattern {
    YoutubeChannelAdapter youtubeChannelAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_channel_list,container,false);
        ButterKnife.bind(this,v);

        setUpAdapter();

        getListChanel();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListChanel();
            }
        });

        return v;
    }

    public void getListChanel(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                YoutubeChannel[] youtubeChannels = JsonConvert.getArray(response,YoutubeChannel[].class);
                youtubeChannelAdapter.setDataSource(ArrayConvert.toObjectArray(youtubeChannels));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public Map<String, String> getListParams() {
                return null;
            }

            @Override
            public String getAPI_URL() {
                return GET_LIST_YOUTUBE_CHANNEL;
            }
        };
        networkAsyncTask.execute();
    }

        public void setUpAdapter(){
            youtubeChannelAdapter = new YoutubeChannelAdapter(getActivity(), new ArrayList<>());
            youtubeChannelAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
                @Override
                public void OnClick(int ItemPosition, Object ItemObject) {
                    Storage.getInstance().addValue(CURRENT_CHANNEL,(YoutubeChannel)ItemObject);
                    Intent it = new Intent(getActivity(),YoutubeActivity.class);
                    ((BaseDetailActivity)getActivity()).transitionTo(it);
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(youtubeChannelAdapter);
        }
}

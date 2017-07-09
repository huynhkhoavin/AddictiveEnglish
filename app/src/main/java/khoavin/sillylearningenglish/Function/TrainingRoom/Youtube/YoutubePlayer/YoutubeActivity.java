package khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.YoutubePlayer;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Model.Channel;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Model.Item;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Model.Statistics;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Model.VideoDetail;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.YoutubeChannel;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_CHANNEL;
import static khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert.gson;

public class YoutubeActivity extends YouTubeBaseActivity {

    private final String GoogleAPI = "https://www.googleapis.com/youtube/v3/search";
    //?key=AIzaSyCZmWOPO3A6PUcJarcZ5FXJ-kYG25WtWdU&channelId=UCKYjNmDm4z4r7zgxSADXWow&part=snippet,id&order=date&maxResults=20"


    @Inject
    IVolleyService volleyService;
    private final String APIKey = "AIzaSyCZmWOPO3A6PUcJarcZ5FXJ-kYG25WtWdU";

    @BindView(R.id.youtubeView)
    YouTubePlayerView youTubePlayerView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.videoName)
    TextView videoName;

    @BindView(R.id.videoViews)
    TextView videoViews;

    VideoAdapter videoAdapter;
    
    public String VideoCode;

    public YoutubeChannel youtubeChannel;

    YouTubePlayer.OnInitializedListener initializedListener;

    YouTubePlayer.PlaybackEventListener playbackEventListener;

    YouTubePlayer mYouTubePlayer;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_youtube);
        ButterKnife.bind(this);
        ((SillyApp) getApplication()).getDependencyComponent().inject(this);

        initializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                    mYouTubePlayer = youTubePlayer;
                mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                getChannelVideoList();
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize(APIKey,initializedListener);
        SetUpAdapter();
        youtubeChannel = (YoutubeChannel) Storage.getInstance().getValue(CURRENT_CHANNEL);

    }
    public void SetUpAdapter(){
        videoAdapter = new VideoAdapter(this,new ArrayList<>());
        videoAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, final Object ItemObject) {
                VideoCode = ((Item)ItemObject).getId().getVideoId();
                if (mYouTubePlayer!=null){
                    mYouTubePlayer.loadVideo(VideoCode);
                    videoName.setText(((Item)ItemObject).getSnippet().getTitle());
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videoAdapter);
    }
    public void getChannelVideoList(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                Channel channel = gson.fromJson(response, Channel.class);
                videoAdapter.setDataSource(ArrayConvert.toObjectArray(channel.getItems()));
                VideoCode  = channel.getItems().get(0).getId().getVideoId();
                mYouTubePlayer.loadVideo(VideoCode);
                videoName.setText(channel.getItems().get(0).getSnippet().getTitle());

            }

            @Override
            public Map<String, String> getListParams() {
                return null;
            }

            @Override
            public String getAPI_URL() {
                return GoogleAPI + "?key="+APIKey+"&channelId="+youtubeChannel.getYcCode()+"&part=snippet,id&order=date&maxResults=50";
            }
        };
        networkAsyncTask.setHttpMethod(Request.Method.GET);
        networkAsyncTask.execute();
    }
}

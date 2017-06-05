package khoavin.sillylearningenglish.Function.Social.SocialFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonDetailActivity;
import khoavin.sillylearningenglish.NetworkService.EventListener.FetchNotifyListener;
import khoavin.sillylearningenglish.Function.Social.View.NotificationAdapter;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ISocialNetworkService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by Dev02 on 5/29/2017.
 */

public class SocialHomeFragment extends FragmentPattern {
    @Inject
    ISocialNetworkService socialNetworkService;
    @BindView(R.id.socialHomeRecyclerView)
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_social_home,container,false);
        ButterKnife.bind(this,v);
        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);
        socialNetworkService.Init(getActivity());
        setUpAdapter();
        getNotification();
        EventBus.getDefault().register(this);
        return v;
    }

    public void getNotification(){
        socialNetworkService.getHomeNotification(new FetchNotifyListener() {
            @Override
            public void onFetchSuccess(ArrayList<Notification> listNotify) {
                showNotify(listNotify);
            }

            @Override
            public void onFetchFailed(String errorMessage) {

            }
        });
    }
    public void showNotify(ArrayList<Notification> list){
        notificationAdapter.setDataSource(ArrayConvert.toObjectArray(list));
    }
    public void setUpAdapter(){
        notificationAdapter = new NotificationAdapter(getActivity(), new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        notificationAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {
                FragmentManager fm = getFragmentManager();
                NotifyDetailFragment dialogFragment = new NotifyDetailFragment((Notification)ItemObject);
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notificationAdapter);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(MessageEvent messageEvent){
        if (messageEvent.getMessage().equals("POST_SUCCESS")) {
            getNotification();
        }
    }
}

package khoavin.sillylearningenglish.Function.Social.SocialFragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Social.Event.FetchNotifyListener;
import khoavin.sillylearningenglish.Function.Social.View.NotificationAdapter;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ISocialNetworkService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;
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
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notificationAdapter);
    }
}

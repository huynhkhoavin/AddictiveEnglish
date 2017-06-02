package khoavin.sillylearningenglish.Function.Social.SocialFragment;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.EventListener.FetchNotifyListener;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;

/**
 * Created by Dev02 on 5/29/2017.
 */

public class SocialProfileFragment extends SocialHomeFragment {
    @Override
    public void getNotification() {
        socialNetworkService.getProfileNotification(new FetchNotifyListener() {
            @Override
            public void onFetchSuccess(ArrayList<Notification> listNotify) {
                showNotify(listNotify);
            }

            @Override
            public void onFetchFailed(String errorMessage) {

            }
        });
    }
}

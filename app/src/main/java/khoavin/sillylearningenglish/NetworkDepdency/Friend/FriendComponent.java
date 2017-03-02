package khoavin.sillylearningenglish.NetworkDepdency.Friend;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.FUNCTION.Friend.Presenter.FriendPresenter;

/**
 * Created by KhoaVin on 2/23/2017.
 */
@Singleton
@Component(modules = {FriendModule.class})
public interface FriendComponent {
    void inject(FriendPresenter friendPresenter);
}

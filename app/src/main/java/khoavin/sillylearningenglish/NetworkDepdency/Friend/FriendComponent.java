package khoavin.sillylearningenglish.NetworkDepdency.Friend;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.FUNCTION.Friend.Presenter.FriendPresenter;
import khoavin.sillylearningenglish.FUNCTION.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;

/**
 * Created by KhoaVin on 2/23/2017.
 */
@Singleton
@Component(modules = {FriendModule.class})
public interface FriendComponent {
    void inject(FriendPresenter friendPresenter);
    void inject(IFriendService friendService);
    void inject(HomeActivity homeActivity);
}

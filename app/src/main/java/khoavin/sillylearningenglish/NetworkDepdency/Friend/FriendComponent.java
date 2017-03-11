package khoavin.sillylearningenglish.NetworkDepdency.Friend;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.Function.Friend.Presenter.FriendPresenter;
import khoavin.sillylearningenglish.NetworkService.Implementation.UserService;

/**
 * Created by KhoaVin on 2/23/2017.
 */
@Singleton
@Component(modules = {FriendModule.class})
public interface FriendComponent {
    void inject(FriendPresenter friendPresenter);
    void inject(UserService userService);
}

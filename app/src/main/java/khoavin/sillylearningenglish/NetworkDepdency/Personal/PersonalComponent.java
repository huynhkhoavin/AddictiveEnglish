package khoavin.sillylearningenglish.NetworkDepdency.Personal;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by KhoaVin on 3/3/2017.
 */
@Singleton
@Component(modules = {PersonalModule.class})
public interface PersonalComponent {
    //void inject(FriendPresenter friendPresenter);
}

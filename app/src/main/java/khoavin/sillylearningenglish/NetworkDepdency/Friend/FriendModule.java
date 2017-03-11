package khoavin.sillylearningenglish.NetworkDepdency.Friend;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import khoavin.sillylearningenglish.NetworkService.Implementation.AuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Implementation.ChatService;
import khoavin.sillylearningenglish.NetworkService.Implementation.FriendService;
import khoavin.sillylearningenglish.NetworkService.Implementation.UserService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IChatService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IUserService;

/**
 * Created by KhoaVin on 2/23/2017.
 */

@Module
public class FriendModule {
    @Provides
    @Singleton
    IFriendService providesFriendService(){
        return new FriendService();
    }

    @Provides
    @Singleton
    IChatService providesChatService(){
        return new ChatService();
    }

    @Provides
    @Singleton
    IUserService providesUserService(){
        return new UserService();
    }

    @Provides
    @Singleton
    IAuthenticationService provicesAuthenticationService(){
        return new AuthenticationService();
    }
}

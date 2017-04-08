package khoavin.sillylearningenglish.Depdency;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import khoavin.sillylearningenglish.Function.Friend.ChatObject.ManyChatRoom;
import khoavin.sillylearningenglish.NetworkService.Implementation.AuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Implementation.ChatService;
import khoavin.sillylearningenglish.NetworkService.Implementation.FriendService;
import khoavin.sillylearningenglish.NetworkService.Implementation.TrainingService;
import khoavin.sillylearningenglish.NetworkService.Implementation.UserService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IChatService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IUserService;
import khoavin.sillylearningenglish.NetworkService.Implementation.ArenaService;
import khoavin.sillylearningenglish.NetworkService.Implementation.PlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;

@Module
public class DependencyModule {
    @Provides
    @Singleton
    IPlayerService provideIUserService() {
        return new PlayerService();
    }

    @Provides
    @Singleton
    IArenaService provideIArenaService()
    {
        return new ArenaService();
    }

    @Provides
    @Singleton
    ITrainingService provideITrainingService() { return new TrainingService();}

    //region Firebase Service
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

    @Provides
    @Singleton
    ManyChatRoom providesManyChatRoom(){
        return new ManyChatRoom();
    }
    //endregion
}
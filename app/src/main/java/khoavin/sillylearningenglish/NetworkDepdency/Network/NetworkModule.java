package khoavin.sillylearningenglish.NetworkDepdency.Network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Implementation.ArenaService;
import khoavin.sillylearningenglish.NetworkService.Implementation.PlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private String mBaseUrl;

    public NetworkModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }


    @Provides
    @Singleton
    IApiServices provideIApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IApiServices.class);
    }

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
}
package khoavin.sillylearningenglish.SERVICE.DIDagger;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import khoavin.sillylearningenglish.SERVICE.IAPIServices;
import khoavin.sillylearningenglish.SERVICE.Implementation.UserService;
import khoavin.sillylearningenglish.SERVICE.Interfaces.IUserService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    String mBaseUrl;

    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }


    @Provides
    @Singleton
    IAPIServices provideIAPService()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IAPIServices.class);
    }

    @Provides
    @Singleton
    IUserService provideIUsreService()
    {
        return new UserService();
    }
}


package khoavin.sillylearningenglish.NetworkDepdency.Authentication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import khoavin.sillylearningenglish.NetworkService.Implementation.AuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;

/**
 * Created by KhoaVin on 2/21/2017.
 */
@Module
public class AuthenticationModule {
    public AuthenticationModule(){

    }

    @Provides
    @Singleton
    IAuthenticationService provideAuthenticationService(){
        return new AuthenticationService();
    }

}

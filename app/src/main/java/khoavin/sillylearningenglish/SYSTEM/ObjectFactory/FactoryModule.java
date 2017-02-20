package khoavin.sillylearningenglish.SYSTEM.ObjectFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import khoavin.sillylearningenglish.SERVICE.Implementation.UserService;
import khoavin.sillylearningenglish.SERVICE.Interfaces.IUserService;

/**
 * Created by KhoaVin on 1/22/2017.
 */
@Module
public class FactoryModule {
    @Provides
    @Singleton
    IUserService provideUserService(){
        return new UserService();
    }
}

package khoavin.sillylearningenglish.NetworkDepdency.Personal;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import khoavin.sillylearningenglish.NetworkService.Implementation.PersonalService;

/**
 * Created by KhoaVin on 3/3/2017.
 */
@Module
public class PersonalModule {

    @Singleton
    @Provides
    public PersonalService providesPersonalService(){
        return new PersonalService();
    }
}

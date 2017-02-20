package khoavin.sillylearningenglish.SYSTEM.ObjectFactory;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.SERVICE.Implementation.UserService;

/**
 * Created by KhoaVin on 2/19/2017.
 */
@Singleton
@Component(modules = {DaggerFactory.class})
public interface ServiceComponent {
    UserService providesUserService();

}

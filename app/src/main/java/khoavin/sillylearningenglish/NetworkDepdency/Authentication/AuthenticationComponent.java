package khoavin.sillylearningenglish.NetworkDepdency.Authentication;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.FUNCTION.Authentication.Login.LoginPresenter;

/**
 * Created by KhoaVin on 2/21/2017.
 */
@Singleton
@Component(modules = {AuthenticationModule.class})
public interface AuthenticationComponent {
    void inject(LoginPresenter loginPresenter);
}

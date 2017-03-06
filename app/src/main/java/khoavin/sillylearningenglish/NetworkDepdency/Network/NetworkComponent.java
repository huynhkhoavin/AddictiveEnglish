package khoavin.sillylearningenglish.NetworkDepdency.Network;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation.AnswerPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation.ArenaPresenter;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(AnswerPresenter presenter);
    void inject(ArenaPresenter presenter);
}

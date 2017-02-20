package khoavin.sillylearningenglish.NetworkDepdency;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation.AnswerPresenter;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(AnswerPresenter presenter);
}

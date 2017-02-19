package khoavin.sillylearningenglish.SERVICE.DIDagger;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation.AnswerPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Views.Implementation.AnswerActivity;

@Singleton
@Component(modules={NetModule.class})
public interface NetComponent
{
    void inject(AnswerActivity activity);
    void inject(AnswerPresenter presenter);
}
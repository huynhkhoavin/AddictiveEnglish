package khoavin.sillylearningenglish.Depdency;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.AnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ArenaPresenter;
import khoavin.sillylearningenglish.Function.Authentication.Login.LoginPresenter;
import khoavin.sillylearningenglish.Function.Friend.Presenter.FriendPresenter;
import khoavin.sillylearningenglish.Function.Friend.View.ChatDialog;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Presenter.TrainingPresenter;
import khoavin.sillylearningenglish.NetworkService.Implementation.UserService;

@Singleton
@Component(modules = {DependencyModule.class})
public interface DependencyComponent {
    void inject(AnswerPresenter presenter);
    void inject(ArenaPresenter presenter);
    void inject(TrainingPresenter presenter);

    void inject(FriendPresenter friendPresenter);
    void inject(UserService userService);
    void inject(ChatDialog chatDialog);
    void inject(LoginPresenter loginPresenter);

}

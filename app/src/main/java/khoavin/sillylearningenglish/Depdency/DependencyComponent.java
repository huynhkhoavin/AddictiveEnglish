package khoavin.sillylearningenglish.Depdency;

import javax.inject.Singleton;

import dagger.Component;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.AnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ArenaPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.BattleHistoryPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.BattlePreparePresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ResultPresenter;
import khoavin.sillylearningenglish.Function.Authentication.Login.LoginPresenter;
import khoavin.sillylearningenglish.Function.Authentication.LoginActivity;
import khoavin.sillylearningenglish.Function.FindNewFriends.FindFriendDialog;
import khoavin.sillylearningenglish.Function.Friend.Presenter.FriendPresenter;
import khoavin.sillylearningenglish.Function.Friend.View.ChatDialog;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.Function.LuckySpinning.ActivitySpinning;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter.MailBoxDetailPresenter;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter.MailBoxPresenter;
import khoavin.sillylearningenglish.Function.Ranking.Presenter.RankingPresenter;
import khoavin.sillylearningenglish.Function.Social.SocialFragment.NotifyDetailFragment;
import khoavin.sillylearningenglish.Function.Social.SocialFragment.PostNotifyFragment;
import khoavin.sillylearningenglish.Function.Social.SocialFragment.SocialHomeFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Presenter.TrainingPresenter;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Storage.UserStorageFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment.LessonPlayFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonDetailActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment.LessonProgressFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.PlayActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.YoutubePlayer.YoutubeActivity;
import khoavin.sillylearningenglish.NetworkService.Implementation.SocialNetworkService;
import khoavin.sillylearningenglish.NetworkService.Implementation.UserService;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.NetworkThreadTask;
import khoavin.sillylearningenglish.SYSTEM.Service.BackgroundMusicService;

@Singleton
@Component(modules = {DependencyModule.class})
public interface DependencyComponent {
    void inject(AnswerPresenter presenter);
    void inject(ArenaPresenter presenter);
    void inject(ResultPresenter presenter);
    void inject(BattlePreparePresenter presenter);
    void inject(MailBoxPresenter presenter);
    void inject(LoginActivity activity);
    void inject(MailBoxDetailPresenter presenter);
    void inject(RankingPresenter presenter);
    void inject(BattleHistoryPresenter presenter);


    void inject(TrainingPresenter presenter);
    void inject(FriendPresenter friendPresenter);
    void inject(UserService userService);
    void inject(ChatDialog chatDialog);
    void inject(LoginPresenter loginPresenter);
    void inject(LessonProgressFragment lessonProgressFragment);
    void inject(LessonDetailActivity lessonDetailActivity);
    void inject(BackgroundMusicService backgroundMusicService);
    void inject(SocialNetworkService socialNetworkService);
    void inject(SocialHomeFragment socialHomeFragment);
    void inject(NotifyDetailFragment notifyDetailFragment);
    void inject(TrainingHomeFragment trainingHomeFragment);
    void inject(UserStorageFragment userStorageFragment);
    void inject(PostNotifyFragment userStorageFragment);
    void inject(HomeActivity homeActivity);
    void inject(PlayActivity playActivity);
    void inject(LessonPlayFragment lessonPlayFragment);
    void inject(FindFriendDialog findFriendActivity);

    void inject(NetworkAsyncTask networkAsyncTask);
    void inject(NetworkThreadTask networkThreadTask);
    void inject(ActivitySpinning activitySpinning);
    void inject(YoutubeActivity youtubeActivity);
}

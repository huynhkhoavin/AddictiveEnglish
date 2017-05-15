package khoavin.sillylearningenglish.NetworkService.Retrofit;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inboxs;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Leaderboards;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lessons;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswers;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IApiServices {

    //Get user information
    @FormUrlEncoded
    @POST("/sillyenglish-web-service/index.php/user/getinfo/")
    Observable<User> getUserInformation(@Field("user_id") String id,
                                        @Field("user_name") String user_name,
                                        @Field("user_avatar") String user_avatar);

    //Find battle
    @FormUrlEncoded
    @POST("/sillyenglish-web-service/index.php/arena/find_battle/")
    Observable<Enemy> findBattle(@Field("user_id") String user_id);

    //Create battle
    @FormUrlEncoded
    @POST("/sillyenglish-web-service/index.php/arena/create_battle/")
    Observable<Questions> createBattle(
                                        @Field("attacker_id") String attacker_id,
                                        @Field("defender_id")String defender_id,
                                        @Field("bet_value")long bet_value,
                                        @Field("message")String message);

    //Accept battle
    @FormUrlEncoded
    @POST("/sillyenglish-web-service/index.php/arena/accept_battle/")
    Observable<Questions> acceptBattle(@Field("defender_id") String defender_id, @Field("battle_id") Integer battle_id);

    //Chose answer
    @FormUrlEncoded
    @POST("/sillyenglish-web-service/index.php/arena/chose_answer/")
    Observable<AnswerChecker> choseAnswer(@Field("user_id") String user_id,
                                          @Field("battle_id")Integer battle_id,
                                          @Field("question_id")Integer question_id,
                                          @Field("chose_answer")Integer chose_answer);

    //Get battle chain
    @FormUrlEncoded
    @POST("/sillyenglish-web-service/index.php/arena/get_battle_chains/")
    Observable<User> getBattleChains(@Query("user_id") String user_id);

    //Get battle result
    @FormUrlEncoded
    @POST("/sillyenglish-web-service/index.php/arena/get_battle_result/")
    Observable<MyAnswers> getBattleResult(@Field("user_id") String user_id, @Field("battle_id")Integer battle_id);

    //Get leaderboard
    @GET("/sillyenglish-web-service/index.php/leaderboard/global_ranking/")
    Observable<Leaderboards> getGlobalRanking(@Field("user_id") String user_id);

    //Get friend leaderboard
    @GET("/sillyenglish-web-service/index.php/leaderboard/global_ranking/")
    Observable<Leaderboards> getFriendRanking(@Field("user_id") String user_id);

    //Get inbox information
    @FormUrlEncoded
    @POST("/sillyenglish-web-service/index.php/inbox/get_inbox/")
    Observable<Inboxs> getInboxItems(@Field("user_id") String id);

    //Get Lesson
    @GET("/sillyenglish-web-service/index.php/training/getpopolarlesson/")
    Observable<Lessons> getPopularLesson();

}

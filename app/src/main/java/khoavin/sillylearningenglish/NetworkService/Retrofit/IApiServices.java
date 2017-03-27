package khoavin.sillylearningenglish.NetworkService.Retrofit;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Leaderboards;
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
    @POST("/sillyenglish/index.php/user/getinfo/")
    Observable<User> getUserInformation(@Field("user_id") String id);

    //Find battle
    @FormUrlEncoded
    @POST("/sillyenglish/index.php/arena/find_battle/")
    Observable<Enemy> findBattle(@Field("user_id") String user_id);

    //Create battle
    @FormUrlEncoded
    @POST("/sillyenglish/index.php/arena/create_battle/")
    Observable<Questions> createBattle(@Field("attacker_id") String attacker_id, @Field("defender_id")String defender_id);

    //Accept battle
    @FormUrlEncoded
    @POST("/sillyenglish/index.php/arena/accept_battle/")
    Observable<Questions> acceptBattle(@Field("defender_id") String defender_id, @Field("battle_id") Integer battle_id);

    //Chose answer
    @FormUrlEncoded
    @POST("/sillyenglish/index.php/arena/chose_answer/")
    Observable<AnswerChecker> choseAnswer(@Field("attacker_id") String user_id,
                                          @Field("battle_id")Integer battle_id,
                                          @Field("question_id")Integer question_id,
                                          @Field("chose_answer")Integer chose_answer);

    //Get battle chain
    @FormUrlEncoded
    @POST("/sillyenglish/index.php/arena/get_battle_chains/")
    Observable<User> getBattleChains(@Query("user_id") String user_id);

    //Get battle result
    @FormUrlEncoded
    @POST("/sillyenglish/index.php/arena/get_battle_result/")
    Observable<MyAnswers> getBattleResult(@Query("user_id") String user_id, @Field("battle_id")Integer battle_id);

    //Get leaderboard
    @GET("/sillyenglish/index.php/leaderboard/global_ranking/")
    Observable<Leaderboards> getGlobalRanking(@Field("user_id") String user_id);

    //Get friend leaderboard
    @GET("/sillyenglish/index.php/leaderboard/global_ranking/")
    Observable<Leaderboards> getFriendRanking(@Field("user_id") String user_id);
}

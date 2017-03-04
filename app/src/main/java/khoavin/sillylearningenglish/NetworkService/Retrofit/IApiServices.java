package khoavin.sillylearningenglish.NetworkService.Retrofit;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleQuestions;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface IApiServices {

    @GET("/sillyenglish/index.php/user/getinfo/")
    Observable<User> getUserInformation(@Query("user_id") String id);

    @FormUrlEncoded
    @POST("/arena/create_battle")
    Observable<BattleQuestions> createBattle(@Field("attacker_id") String attacker_id, @Field("defender_id")String defender_id);
}

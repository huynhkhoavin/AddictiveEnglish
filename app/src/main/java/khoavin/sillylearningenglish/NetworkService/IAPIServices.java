package khoavin.sillylearningenglish.NetworkService;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleInformation;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface IAPIServices {

    @GET("/index.php/{user_id}")
    Observable<User> getUserInformation(@Path("user_id") String id);

    @GET("/arena.php")
    Observable<BattleInformation> createBattle(@Query("user_id") String id, @Query("enemy_id")String enemy_id);
}

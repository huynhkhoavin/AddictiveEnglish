package khoavin.sillylearningenglish.SERVICE;

import khoavin.sillylearningenglish.SERVICE.WebModels.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by OatOal on 2/19/2017.
 */

public interface IAPIServices {

    @GET("/index.php/{user_id}")
    Observable<User> getUserInformation(@Path("user_id") String id);
}

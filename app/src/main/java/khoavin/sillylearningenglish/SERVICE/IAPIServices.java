package khoavin.sillylearningenglish.SERVICE;

import khoavin.sillylearningenglish.SERVICE.WebModels.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.functions.Func2;

/**
 * Created by OatOal on 2/19/2017.
 */

public interface IAPIServices {

    @GET("/index.php")
    //@FormUrlEncoded
    Observable<User> getUserInformation(@Path("user_id") String id, Func2<User, Exception, Void> receiver);


}

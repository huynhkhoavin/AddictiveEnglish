package khoavin.sillylearningenglish.SERVICE;

/**
 * Created by OatOal on 2/19/2017.
 */

public class APIUntils {

    private APIUntils() {}

    //public static final String BASE_URL = "http://192.168.1.106:8080/englishproject/";
    public static final String BASE_URL = "http://192.168.1.108:80/";

    public static IAPIServices getAPIService() {

        return RetrofitFactory.getClient(BASE_URL).create(IAPIServices.class);
    }
}

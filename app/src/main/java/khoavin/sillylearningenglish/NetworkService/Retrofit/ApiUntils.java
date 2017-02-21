package khoavin.sillylearningenglish.NetworkService.Retrofit;

public class ApiUntils {

    private ApiUntils() {}

    //public static final String BASE_URL = "http://192.168.1.106:8080/englishproject/";
    public static final String BASE_URL = "http://192.168.1.108/";

    public static IApiServices getAPIService() {

        return RetrofitFactory.getClient(BASE_URL).create(IApiServices.class);
    }
}

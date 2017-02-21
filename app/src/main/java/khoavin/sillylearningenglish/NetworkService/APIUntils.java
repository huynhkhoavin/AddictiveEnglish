package khoavin.sillylearningenglish.NetworkService;

public class ApiUntils {

    private ApiUntils() {}

    //public static final String BASE_URL = "http://192.168.1.106:8080/englishproject/";
    public static final String BASE_URL = "http://192.168.1.108/";

    public static IAPIServices getAPIService() {

        return RetrofitFactory.getClient(BASE_URL).create(IAPIServices.class);
    }
}

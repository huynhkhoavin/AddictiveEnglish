package khoavin.sillylearningenglish.NetworkService.Retrofit;

public class ApiUntils {

    private ApiUntils() {}

    //public static final String BASE_URL = "http://192.168.1.101/englishproject/";
    public static final String BASE_URL = "http://192.168.1.105/";

    /// <sumary>
    /// Get the api service
    /// </sumary>
    public static IApiServices getAPIService() {
        return RetrofitFactory.getClient(BASE_URL).create(IApiServices.class);
    }

    /// <sumary>
    /// Get the error converter
    /// </sumary>
    public static ErrorConverter getErrorConverter()
    {
        return RetrofitFactory.getErrorConverter();
    }
}

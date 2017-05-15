package khoavin.sillylearningenglish.NetworkService.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    //The retrofit client
    private static Retrofit retrofit = null;

    //The error converter
    private static ErrorConverter errorConverter = null;

    //Gets the retrofit client
    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            errorConverter = new ErrorConverter();
            errorConverter.Initialize(retrofit);
        }
        return retrofit;
    }

    /// <sumary>
    /// Get the error converter
    /// </sumary>
    public static ErrorConverter getErrorConverter()
    {
        return errorConverter;
    }
}

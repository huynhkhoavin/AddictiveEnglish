package khoavin.sillylearningenglish.NetworkService.Retrofit;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by OatOal on 4/1/2017.
 */

public class ErrorConverter {

    private Retrofit retrofitInstance;
    private SillyError networkError;
    private SillyError retrofitNotInitializeError;
    private SillyError unknowError;
    private SillyError ioError;
    private SillyError ioConvertNetworkError;

    public void Initialize(Retrofit retrofit)
    {
        this.retrofitInstance = retrofit;

        networkError = new SillyError();
        networkError.code = "-1";
        networkError.message = "Network error";

        retrofitNotInitializeError = new SillyError();
        retrofitNotInitializeError.code = "-2";
        retrofitNotInitializeError.message = "Retrofit not initialized";

        unknowError = new SillyError();
        unknowError.code = "-3";
        unknowError.message = "NOT_FOUND error";

        ioError = new SillyError();
        ioError.code = "-4";
        ioError.message = "IO parse error";

        ioConvertNetworkError = new SillyError();
        ioConvertNetworkError.code = "-5";
        ioConvertNetworkError.message = "Error when parse error message from server";
    }

    /// <sumary>
    /// The error converter
    /// </sumary>
    public SillyError ConvertThrowable(Throwable e)
    {
        Log.e("Error Handler: ", e.toString());
        if(retrofitInstance != null)
        {
            if (e instanceof HttpException)
            {
                ResponseBody body = ((HttpException) e).response().errorBody();
                Converter<ResponseBody, SillyError> errorConverter = retrofitInstance.responseBodyConverter(SillyError.class, new Annotation[0]);
                try
                {
                    SillyError error = errorConverter.convert(body);
                    Log.i("ErrorHandler: ", "ERROR: " + error.message);
                    Log.i("ErrorHandler: ", "CODE: " + error.code);
                    return error;

                } catch (IOException e1)
                {
                    e1.printStackTrace();
                    return  ioConvertNetworkError;
                }
            }
            else if(e instanceof IOException)
            {
                return ioError;
            }
            else
            {
                return unknowError;
            }
        }
        else
        {
            return retrofitNotInitializeError;
        }
    }

    /// <sumary>
    /// Get the not initialize converter error
    /// </sumary>
    public static SillyError NotInitializeErrorConverter()
    {
        SillyError error = new SillyError();
        error.code = "-6";
        error.message = "Not initialize error converter";
        return error;
    }
}


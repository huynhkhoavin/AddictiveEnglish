package khoavin.sillylearningenglish.NetworkService.Retrofit;

/**
 * Created by OatOal on 4/1/2017.
 */

public class SillyError {
    String message;
    String code;

    /// <sumary>
    /// Get error message
    /// </sumary>
    public String getMessage()
    {
        return message;
    }

    /// <sumary>
    /// Get the error code
    /// </sumary>
    public String getErrorCode()
    {
        return code;
    }
}

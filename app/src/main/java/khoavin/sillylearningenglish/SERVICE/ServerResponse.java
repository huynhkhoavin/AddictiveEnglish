package khoavin.sillylearningenglish.SERVICE;

import rx.functions.Func2;

/**
 * Created by OatOal on 2/19/2017.
 */

public class ServerResponse<T> {

    private Func2<T, Exception, Void> _serverCallback;

    public ServerResponse()
    {
        _serverCallback = new Func2<T, Exception, Void>() {
            @Override
            public Void call(T t, Exception e) {
                return null;
            }
        };
    }

    public void Extractor(T response, Exception e)
    {
        this._serverCallback.call(response, e);
    }
}

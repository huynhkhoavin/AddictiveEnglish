package khoavin.sillylearningenglish.SERVICE.DIDagger;

import android.app.Application;

public class MyApp extends Application {

    private NetComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerNetComponent.builder()
                .netModule(new NetModule("http://192.168.1.108:80/"))
                .build();
    }

    public NetComponent getNetComponent()
    {
        return this.mComponent;
    }
}

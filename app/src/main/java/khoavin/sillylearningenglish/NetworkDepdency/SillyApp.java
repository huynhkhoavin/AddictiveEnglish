package khoavin.sillylearningenglish.NetworkDepdency;

import android.app.Application;

public class SillyApp extends Application {

    private NetworkComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://192.168.1.108:80/"))
                .build();
    }

    public NetworkComponent getNetComponent() {
        return this.mComponent;
    }
}

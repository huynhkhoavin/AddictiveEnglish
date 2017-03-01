package khoavin.sillylearningenglish.EventListener.DI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import khoavin.sillylearningenglish.EventListener.GlobalEvent.GlobalEvent;

/**
 * Created by Dev02 on 3/1/2017.
 */
@Module
public class EventModule {
    public EventModule(){

    }
    @Provides
    @Singleton
    GlobalEvent providesGlobalEvent(){
        return new GlobalEvent();
    }
}

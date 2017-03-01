package khoavin.sillylearningenglish.EventListener.DI;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dev02 on 3/1/2017.
 */

@Singleton
@Component(modules = {EventModule.class})
public interface EventComponent {

}

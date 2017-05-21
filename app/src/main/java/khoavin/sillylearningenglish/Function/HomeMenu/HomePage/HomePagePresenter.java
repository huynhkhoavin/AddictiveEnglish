package khoavin.sillylearningenglish.Function.HomeMenu.HomePage;

import android.app.Activity;

/**
 * Created by KhoaVin on 20/05/2017.
 */

public class HomePagePresenter implements IHomePagePresenter {
    HomePageView homePageView;
    Activity activity;
    public HomePagePresenter(Activity activity){
        this.activity = activity;
        homePageView = new HomePageView(activity);
    }
}

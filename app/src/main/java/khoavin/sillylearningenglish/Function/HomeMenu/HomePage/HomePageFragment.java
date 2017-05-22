package khoavin.sillylearningenglish.Function.HomeMenu.HomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 21/05/2017.
 */

public class HomePageFragment extends FragmentPattern {
    IHomePagePresenter homePagePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_home_page,container,false);
        homePagePresenter = new HomePagePresenter(getActivity(),v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

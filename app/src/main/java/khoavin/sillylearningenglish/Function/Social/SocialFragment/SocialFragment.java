package khoavin.sillylearningenglish.Function.Social.SocialFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.TabIconPagerAdapter;
import khoavin.sillylearningenglish.R;

/**
 * Created by Dev02 on 5/29/2017.
 */

public class SocialFragment extends FragmentPattern {
    TabIconPagerAdapter tabPagerAdapter;
    @BindView(R.id.social_TabLayout)
    TabLayout socialTabLayout;
    @BindView(R.id.social_ViewPager)
    ViewPager socialViewPager;

    @BindView(R.id.nested_scroll)
    NestedScrollView nestedScrollView;
    @BindView(R.id.bmb) BoomMenuButton bmb;
    private ArrayList<Pair> piecesAndButtons = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_social,container,false);
        ButterKnife.bind(this,v);
        setupBoomButton();
        setUpTabAdapter();
        nestedScrollView.setFillViewport(true);
        return v;
    }
    private void setUpTabAdapter(){
        String[] TabTitle = {"Home","Profile"};
        FragmentPattern[] FragmentList = {new SocialHomeFragment(),new SocialProfileFragment()};
        //TabIconPagerAdapter(FragmentManager fm, Context context, FragmentPattern[] listFragment,int[] imageResId,String[] tabTitles) {
        tabPagerAdapter = new TabIconPagerAdapter(getActivity().getSupportFragmentManager(),getContext(),FragmentList,new int[]{R.drawable.ic_home,R.drawable.ic_profile},new String[]{"",""});
        socialViewPager.setAdapter(tabPagerAdapter);
        socialTabLayout.setupWithViewPager(socialViewPager);
        socialTabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
    public void setupBoomButton(){
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.SimpleCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_2_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_2_1);
        bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());
        BuilderManager.getCircleButtonData(piecesAndButtons);

        PiecePlaceEnum piecePlaceEnum = (PiecePlaceEnum)piecesAndButtons.get(5).first;
        bmb.setPiecePlaceEnum(piecePlaceEnum);

        ButtonPlaceEnum buttonPlaceEnum = (ButtonPlaceEnum)piecesAndButtons.get(5).second;
        bmb.setButtonPlaceEnum(buttonPlaceEnum);

        bmb.clearBuilders();
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder().listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    Toast.makeText(getContext(), "Clicked " + index, Toast.LENGTH_SHORT).show();
                }
            }));

    }
}

package khoavin.sillylearningenglish.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class HomeFragment extends TabFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment,container,false);
        return v;
    }
}

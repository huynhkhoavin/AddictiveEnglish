package khoavin.sillylearningenglish.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 1/23/2017.
 */

//Tab Fragment Pattern
public class TabFragment extends Fragment implements ITabFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v =  inflater.inflate(R.layout.default_fragment,container,false);
        return v;
    }
}

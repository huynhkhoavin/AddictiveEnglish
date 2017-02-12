package khoavin.sillylearningenglish.Fragment.Battle;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.Fragment.TabFragment;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/12/2017.
 */

public class BattleFragment extends TabFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.arena_fragment,container,false);
        getActivity().setTitle(R.string.arena_title);
        return v;
    }
}

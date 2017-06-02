package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by Khoavin on 4/2/2017.
 */

public class GroupViewHolder extends ViewHolderPattern {
    @BindView(R.id.itemTitle) TextView itemTitle;

    @BindView(R.id.recycler_view_list) RecyclerView recycler_view_list;

    @BindView(R.id.btnMore)
    protected Button btnMore;

    public GroupViewHolder(View view) {
        super(view);
    }
}

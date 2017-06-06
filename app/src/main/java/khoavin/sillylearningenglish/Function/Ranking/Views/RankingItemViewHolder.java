package khoavin.sillylearningenglish.Function.Ranking.Views;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 5/24/2017.
 */

public class RankingItemViewHolder extends ViewHolderPattern{

    /**
     * The item.
     */
    public CardView singleRank;

    /**
     * The user's rank text.
     */
    public TextView userRank;

    /**
     * The user's name text.
     */
    public TextView userName;

    /**
     * The user's avatar.
     */
    public ImageView userAvatar;

    /**
     * The user's win rate.
     */
    public TextView winRate;

    /**
     * The user's total battle.
     */
    public TextView totalBattle;

    /**
     * The user's medal Img.
     */
    public ImageView medalImage;

    /**
     * The user's medal title.
     */
    public TextView medalTitle;

    /**
     * Initialize the ranking item view holder.
     * @param itemView The view.
     */
    public RankingItemViewHolder(View itemView) {
        super(itemView);
        this.singleRank = (CardView) itemView.findViewById(R.id.single_rank);
        this.userRank = (TextView) itemView.findViewById(R.id.rank_item_position);
        this.userName = (TextView) itemView.findViewById(R.id.rank_item_name);
        this.userAvatar = (ImageView) itemView.findViewById(R.id.rank_item_avatar);
        this.winRate = (TextView) itemView.findViewById(R.id.rank_item_win_rate);
        this.totalBattle = (TextView) itemView.findViewById(R.id.rank_item_total_battle);
        this.medalImage = (ImageView) itemView.findViewById(R.id.rank_item_medal_image);
        this.medalTitle = (TextView) itemView.findViewById(R.id.rank_item_medal_title);
    }

}

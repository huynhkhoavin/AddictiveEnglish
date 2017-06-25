package khoavin.sillylearningenglish.Function.Arena.Views;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 6/20/2017.
 */

public class BattleHistoryItemViewHolder extends ViewHolderPattern {

    public CardView singleHistoryItem;

    public ImageView userAvatar;
    public TextView userName;
    public TextView userTotalTime;
    public TextView userTrueAnswer;
    public ImageView enemyAvatar;
    public TextView enemyName;
    public TextView enemyTotalTime;
    public TextView enemyTrueAnswer;
    public View winItem;
    public View lostItem;
    public TextView dateCreate;

    /**
     * Initialize the history view holder item.
     * @param itemView the view.
     */
    public BattleHistoryItemViewHolder(View itemView) {
        super(itemView);
        singleHistoryItem = (CardView)itemView.findViewById(R.id.single_history);

        userAvatar = (ImageView) itemView.findViewById(R.id.history_user_avatar);
        userName = (TextView) itemView.findViewById(R.id.history_user_name);
        userTotalTime = (TextView) itemView.findViewById(R.id.history_user_total_time);
        userTrueAnswer = (TextView) itemView.findViewById(R.id.history_user_true_answer);

        enemyAvatar = (ImageView) itemView.findViewById(R.id.history_enemy_avatar);
        enemyName = (TextView) itemView.findViewById(R.id.history_enemy_name);
        enemyTotalTime = (TextView) itemView.findViewById(R.id.history_enemy_total_time);
        enemyTrueAnswer = (TextView) itemView.findViewById(R.id.history_enemy_true_answer);

        winItem = (Button) itemView.findViewById(R.id.history_victory_state);
        lostItem = (Button) itemView.findViewById(R.id.history_failure_state);

        dateCreate = (TextView)itemView.findViewById(R.id.history_date_create);

    }
}

package khoavin.sillylearningenglish.FUNCTION.Arena.ArenaTools;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/15/2017.
 * List view not init yet. Mus refactor and init reuseable collection view to world ranking list and friend ranking list
 */

public class RankingController {

    //region Controls

    private TextView rankPositionText;
    private ImageView userAvatarImage;
    private TextView userNameText;
    private TextView winRateText;
    private ImageView userMedalImage;
    private TextView userRankText;
    private Button addFriendButton;
    private Button challengeButton;

    private Button worldRankingButton;
    private Button friendRankingButton;
    private ListView worldRankingList;
    private ListView friendRankingList;

    private AppCompatActivity context;

    //endregion

    //region Controls Models

    private String userName, rankName;
    private int rankPosition;
    private float winRate;

    //endregion

    //region Private Method

    //Init style after set controls value
    private void InitStyle()
    {
        rankPositionText.setText(rankPosition + ".");
        userNameText.setText(userName);
        winRateText.setText(Float.toString(winRate) + "%");
        userRankText.setText(rankName);

        InitCollectionView();
        showWorldTab();

        //Add listener to add friend button
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IsUserItSelf())
                {
                    //Do something here
                    //Sent add friend request to server
                }
            }
        });


        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IsUserItSelf())
                {
                    //Do something here
                    //Move to battle prepare screen
                }

            }
        });

        friendRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call show friend ranking view
                showFriendTab();

            }
        });

        worldRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call show world ranking view
                showWorldTab();
            }
        });
    }

    private void showFriendTab()
    {
        this.worldRankingButton.setBackground(context.getResources().getDrawable(R.drawable.tab_left_deactive));
        this.friendRankingButton.setBackground(context.getResources().getDrawable(R.drawable.tab_right_active));

        this.friendRankingList.setVisibility(View.VISIBLE);
        this.worldRankingList.setVisibility(View.GONE);
    }

    private void showWorldTab()
    {
        this.worldRankingButton.setBackground(context.getResources().getDrawable(R.drawable.tab_left_active));
        this.friendRankingButton.setBackground(context.getResources().getDrawable(R.drawable.tab_right_deactive));

        this.friendRankingList.setVisibility(View.GONE);
        this.worldRankingList.setVisibility(View.VISIBLE);
    }

    private void InitCollectionView()
    {
        //Init collection view here
    }

    private boolean IsUserItSelf()
    {
        //Do something to define if current showed information are user it self
        return false;
    }

    //endregion

    //region Constructor and Public Method

    //Init all controls
    public RankingController(AppCompatActivity context)
    {
        this.context = context;
        rankPositionText = (TextView)context.findViewById(R.id.rank_position);
        userAvatarImage = (ImageView)context.findViewById(R.id.user_avatar);
        userNameText = (TextView)context.findViewById(R.id.rank_position);
        winRateText = (TextView)context.findViewById(R.id.rank_position);
        userMedalImage = (ImageView) context.findViewById(R.id.user_rank);
        userRankText = (TextView)context.findViewById(R.id.rank_position);
        addFriendButton = (Button) context.findViewById(R.id.button_add_friend);
        challengeButton = (Button)context.findViewById(R.id.button_challenge);
        worldRankingButton = (Button)context.findViewById(R.id.world_ranking);
        friendRankingButton = (Button)context.findViewById(R.id.friend_ranking);
        worldRankingList = (ListView) context.findViewById(R.id.world_ranking_list);
        friendRankingList = (ListView)context.findViewById(R.id.friend_ranking_list);
    }

    //Set value for controls


    public RankingController(float winRate, String userName, String rankName, int rankPosition) {
        this.winRate = winRate;
        this.userName = userName;
        this.rankName = rankName;
        this.rankPosition = rankPosition;

        InitStyle();
    }

    //endregion
}

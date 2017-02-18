package khoavin.sillylearningenglish.SINGLE_OBJECT;

/**
 * Created by OatOal on 2/18/2017.
 */

public class User {

    //The user's name
    private String userName;

    //The user's win rate
    private float winRate;

    //The user's total battle
    private int totalBattle;

    //The user's avatar image url
    private String avatarUrl;

    //The user's coin number
    private long coin;

    //The user's battle chain state
    private Common.ChainState[] battleChains;

    //The ranking information
    private Ranking rankingInfo;

    //User information builder
    private User(UserInfoBuilder builder)
    {
        this.battleChains = builder.battleChains;
        this.avatarUrl = builder.avatarUrl;
        this.coin = builder.coin;
        this.userName = builder.userName;
        this.winRate = builder.winRate;
        this.totalBattle = builder.totalBattle;
        this.rankingInfo = builder.rankingInfo;
    }

    //region Builder

    public static class UserInfoBuilder
    {
        private String userName;
        private float winRate;
        private int totalBattle;
        private String avatarUrl;
        private long coin;
        private Common.ChainState[] battleChains;
        private Ranking rankingInfo;

        public UserInfoBuilder Name(String userName) {
            this.userName = userName;
            return this;
        }

        public UserInfoBuilder WinRate(float winRate) {
            this.winRate = winRate;
            return this;
        }

        public UserInfoBuilder TotalBattle(int totalBattle) {
            this.totalBattle = totalBattle;
            return this;
        }

        public UserInfoBuilder Avatar(String avatarUrl)
        {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public UserInfoBuilder Coin(long coin)
        {
            this.coin = coin;
            return this;
        }

        public UserInfoBuilder BattleChains(Common.ChainState[] battleChains)
        {
            this.battleChains = battleChains;
            return this;
        }

        public UserInfoBuilder Ranking(Ranking rankingInfo)
        {
            this.rankingInfo = rankingInfo;
            return this;
        }

        public User Build()
        {
            return new User(this);
        }

        //endregion

    }
}



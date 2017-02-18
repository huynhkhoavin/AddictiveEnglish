package khoavin.sillylearningenglish.SINGLE_OBJECT;

/**
 * Created by OatOal on 2/18/2017.
 */

public class Ranking {

    //region Properties

    //The rank's medal
    private Common.RankMedal medal;

    //The rank's title
    private String rankTitle;

    //The rank's position (position on leader board)
    private int rankPosition;

    //The rank's level (1 -> 78)
    private int rankLevel;

    //endregion

    //region Ranking builder

    private Ranking(RankingInfoBuilder builder)
    {
        this.medal = builder.medal;
        this.rankLevel = builder.rankLevel;
        this.rankPosition = builder.rankPosition;
        this.rankTitle = builder.rankTitle;
    }

    public static class RankingInfoBuilder
    {
        private Common.RankMedal medal;
        private String rankTitle;
        private int rankPosition;
        private int rankLevel;

        public RankingInfoBuilder Medal(Common.RankMedal medal)
        {
            this.medal = medal;
            return this;
        }

        public RankingInfoBuilder RankTitle(String rankTitle)
        {
            this.rankTitle = rankTitle;
            return this;
        }

        public RankingInfoBuilder RankPosition(int rankPosition)
        {
            this.rankPosition = rankPosition;
            return this;
        }

        public RankingInfoBuilder RankLevel(int rankLevel)
        {
            this.rankLevel = rankLevel;
            return this;
        }

        public Ranking Build()
        {
            return new Ranking(this);
        }
    }

    //endregion
}

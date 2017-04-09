package khoavin.sillylearningenglish.SingleViewObject;

/**
 * Created by OatOal on 2/18/2017.
 */

public class Common {

    //The question type
    public enum QuestionType {
        Reading,
        Listening,
        Unknow
    }

    //The answer key
    public enum AnswerKey {
        A,
        B,
        C,
        D,
        U
    }

    //The ranking medal
    public enum RankMedal
    {
        Bronze,
        Sliver,
        Gold
    }

    public enum ChainState
    {
        Win,
        Failure,
        Waiting,
        Locked
    }

    /// <summary>
    /// Specifies the state of a promise.
    /// </summary>
    public enum PromiseState
    {
        Pending,    // The promise is in-flight.
        Rejected,   // The promise has been rejected.
        Resolved    // The promise has been resolved.
    };

    public static String SERVICE_BASE_URL = "http://192.168.1.106:8080/englishproject/";

    //Get rank medal
    public static RankMedal GetMedalFromLevel(int level)
    {
        if(level >= 1 && level <= 26) return RankMedal.Bronze;
        if(level >= 27 && level <= 53) return RankMedal.Sliver;
        if(level >= 54 && level <= 78) return RankMedal.Gold;
        return RankMedal.Bronze;
    }

    //Get rank title
    public static String GetMedalTitleFromLevel(int level)
    {
        if(level < 1 || level > 78) return "Unknow";
        if(level >= 1 && level <= 26) return "ĐỒNG " +  (26 - level % 26);
        if(level >= 27 && level <= 53) return "BẠC " +  (26 - level % 26);
        if(level >= 54 && level <= 78) return "VÀNG " +  (26 - level % 26);
        return "";
    }

    //Get win rate
    public static String GetWinRate(int total_match, int win_match)
    {
        float winRate = 1.0f * win_match / total_match;
        winRate *= 100;
        return (String.format(java.util.Locale.US,"%.2f", winRate) + "%");
    }

    //Convert milisecond to string
    public static String MillisecondToString(long milliSecond)
    {
        long seconds, mins, hours;
        seconds = milliSecond / 1000;
        if(seconds < 60)
        {
            return "0:" + seconds;
        }
        else if(seconds > 60 && seconds < 3600)
        {
            mins = seconds / 60;
            seconds = seconds % 60;
            return mins + ":" + seconds;
        }
        else
        {
            hours = seconds / 3600;
            seconds = seconds % 3600;
            mins = seconds / 60;
            seconds = seconds % 60;

            return hours + ":" + mins + ":" + seconds;
        }

    }


}
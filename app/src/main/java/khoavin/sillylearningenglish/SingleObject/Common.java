package khoavin.sillylearningenglish.SingleObject;

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
        D
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
    public RankMedal GetMedalFromLevel(int level)
    {
        if(level >= 1 && level <= 26) return RankMedal.Bronze;
        if(level >= 27 && level <= 53) return RankMedal.Sliver;
        if(level >= 54 && level <= 78) return RankMedal.Gold;
        return RankMedal.Bronze;
    }

    //Get rank title
    public String GetMedalTitleFromLevel(int level)
    {
        if(level < 1 || level > 78) return "Unknow";
        if(level >= 1 && level <= 26) return "ĐỒNG " +  (26 - level % 26);
        if(level >= 27 && level <= 53) return "BẠC " +  (26 - level % 26);
        if(level >= 54 && level <= 78) return "VÀNG " +  (26 - level % 26);
        return "";
    }


}
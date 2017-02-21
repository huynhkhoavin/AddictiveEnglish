package khoavin.sillylearningenglish.SINGLE_OBJECT;

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
}
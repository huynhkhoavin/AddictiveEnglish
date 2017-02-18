package khoavin.sillylearningenglish.SINGLE_OBJECT;

/**
 * Created by OatOal on 2/18/2017.
 */

public class Common {

    //The question type
    public enum QuestionType {
        Reading,
        Listening,
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
}
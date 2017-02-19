package khoavin.sillylearningenglish.TEST_DATA;

import khoavin.sillylearningenglish.SINGLE_OBJECT.Result;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Common;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Question;

/**
 * Created by OatOal on 2/18/2017.
 */
public class TestData {
    private static TestData ourInstance = new TestData();

    public static TestData getInstance() {
        return ourInstance;
    }

    private static Question[] questionList;
    private static Result result;

    private TestData() {

        //Test data for question list
        questionList = new Question[5];
        questionList[0] = new Question.QuestionBuilder()
                .QuestionTitle("Câu tuyên chiến:")
                .QuestionType(Common.QuestionType.Listening)
                .QuestionContent("They_____ because it is a national holiday.")
                .AudioSource("")
                .AnswerA("A. Don't work")
                .AnswerB("B. Aren't working")
                .Build();

        questionList[1] = new Question.QuestionBuilder()
                .QuestionTitle("Câu phá thành:")
                .QuestionType(Common.QuestionType.Reading)
                .QuestionContent("What are they taking about?")
                .AudioSource("http://download.a2.nixcdn.com/7cfa7c5ab39ad2526e68ce803ce6144f/58a822f9/NhacCuaTui162/RolyPoly-DangCapNhat_37827.mp3")
                .AnswerA("A. A music")
                .AnswerB("B. A film")
                .Build();

        questionList[2] = new Question.QuestionBuilder()
                .QuestionTitle("Câu khô máu:")
                .QuestionType(Common.QuestionType.Reading)
                .QuestionContent("They_____ because it is a national holiday.")
                .AudioSource("")
                .AnswerA("A. Don't work")
                .AnswerB("B. Aren't working")
                .Build();

        questionList[3] = new Question.QuestionBuilder()
                .QuestionTitle("Câu ấn định:")
                .QuestionType(Common.QuestionType.Reading)
                .QuestionContent("What are they taking about?")
                .AudioSource("http://download.a2.nixcdn.com/7cfa7c5ab39ad2526e68ce803ce6144f/58a822f9/NhacCuaTui162/RolyPoly-DangCapNhat_37827.mp3")
                .AnswerA("A. A music")
                .AnswerB("B. A film")
                .Build();

        questionList[4] = new Question.QuestionBuilder()
                .QuestionTitle("Câu ăn mừng:")
                .QuestionType(Common.QuestionType.Reading)
                .QuestionContent("They_____ because it is a national holiday.")
                .AudioSource("")
                .AnswerA("A. Don't work")
                .AnswerB("B. Aren't working")
                .Build();

        //Test data for answer
        this.result = new Result(questionList, 243, new boolean[]{true, true, false, false, true});

    }

    public static Question[] getQuestionList() {
        return questionList;
    }

    public static Result getResult() {
        return result;
    }
}

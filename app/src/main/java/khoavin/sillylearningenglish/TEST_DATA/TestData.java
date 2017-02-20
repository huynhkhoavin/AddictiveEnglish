package khoavin.sillylearningenglish.TEST_DATA;

import khoavin.sillylearningenglish.SINGLE_OBJECT.Result;

public class TestData {
    private static TestData ourInstance = new TestData();

    public static TestData getInstance() {
        return ourInstance;
    }

    private static Result result;

    private TestData() {
    }

    public static Result getResult() {
        return result;
    }
}

//package khoavin.sillylearningenglish.SINGLE_OBJECT;
//
//
//public class Question {
//
//    private Common.QuestionType questionType;
//    private String questionTitle;
//    private String questionContent;
//    private String answerA;
//    private String answerB;
//    private String audioSource;
//
//    private Question(QuestionBuilder builder)
//    {
//        this.answerA = builder.answerA;
//        this.answerB = builder.answerB;
//        this.audioSource = builder.audioSource;
//        this.questionContent = builder.questionContent;
//        this.questionTitle = builder.questionTitle;
//        this.questionType = builder.questionType;
//    }
//
//    public Common.QuestionType getQuestionType() {
//        return questionType;
//    }
//
//    public String getQuestionTitle() {
//        return questionTitle;
//    }
//
//    public String getQuestionContent() {
//        return questionContent;
//    }
//
//    public String getAnswerA() {
//        return answerA;
//    }
//
//    public String getAnswerB() {
//        return answerB;
//    }
//
//    public String getAudioSource() {
//        return audioSource;
//    }
//
//    public static class QuestionBuilder
//    {
//        private Common.QuestionType questionType;
//        private String questionTitle;
//        private String questionContent;
//        private String answerA;
//        private String answerB;
//        private String audioSource;
//
//        public Question Build()
//        {
//            return new Question(this);
//        }
//
//        public QuestionBuilder QuestionType(Common.QuestionType questionType) {
//            this.questionType = questionType;
//            return this;
//        }
//
//        public QuestionBuilder QuestionTitle (String questionTitle) {
//            this.questionTitle = questionTitle;
//            return this;
//        }
//
//        public QuestionBuilder QuestionContent(String questionContent) {
//            this.questionContent = questionContent;
//            return this;
//        }
//
//        public QuestionBuilder AnswerA(String answerA) {
//            this.answerA = answerA;
//            return this;
//        }
//
//        public QuestionBuilder AnswerB(String answerB) {
//            this.answerB = answerB;
//            return this;
//        }
//
//        public QuestionBuilder AudioSource(String audioSource) {
//            this.audioSource = audioSource;
//            return this;
//        }
//    }
//}

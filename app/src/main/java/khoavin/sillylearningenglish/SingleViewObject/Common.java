package khoavin.sillylearningenglish.SingleViewObject;

import android.util.Log;

import com.android.volley.VolleyError;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;

/**
 * Created by OatOal on 2/18/2017.
 */

public class Common {

    //region Enum

    /**
     * The Attach Gif Type
     */
    public enum AttachType {
        NOT_FOUND(0),
        COINS(1),
        BOOK(2),
        LESSON(3),
        BATTLE_CHALLENGE_ID(4),
        BATTLE_RESULT_RANK_UPGRADE(5);

        /**
         * Storage the Attach type value
         */
        private final int value;

        private AttachType(int value) {
            this.value = value;
        }

        /**
         * Get attach type from integer
         * @param i
         * The integer value
         * @return
         * The attach type
         */
        public static AttachType fromInt(int i) {
            for (AttachType b : AttachType.values()) {
                if (b.getValue() == i) { return b; }
            }
            return null;
        }

        /**
         * Get AttachType as Integer
         * @return
         */
        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * The Mail type
     */
    public enum MailType {
        NOT_FOUND(0),
        GIFT_COIN(1),
        BATTLE_CHALLENGE(2),
        BATTLE_RESULT(3),
        SYSTEM_MESSAGE(4);

        /**
         * Storage value
         */
        private final int value;

        /**
         * Initialize
         * @param value
         */
        private MailType(int value)
        {
            this.value = value;
        }

        /**
         * Get inbox type from integer
         * @param i
         * The value
         * @return
         * The InboxType result
         */
        public static MailType fromInt(int i)
        {
            for(MailType b : MailType.values())
            {
                if(b.getValue() == i)
                    return b;
            }
            return NOT_FOUND;
        }

        /**
         * Get Inbox type value as integer
         * @return
         */
        public int getValue() { return value; }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * The question type
     */
    public enum QuestionType {
        NOT_FOUND(0),
        READING(1),
        LISTENING(2);

        /**
         * Storage value of question type
         */
        private final int value;

        /**
         * Initialize
         * @param value
         */
        private QuestionType(int value)
        {
            this.value = value;
        }

        /**
         * Get the QuestionType value
         * @return
         */
        public int getValue(){return value;}

        /**
         * Get question type from integer
         * @param i
         * @return
         */
        public static QuestionType fromInt(int i)
        {
            for(QuestionType q : QuestionType.values())
            {
                if(q.getValue() == i) return q;
            }
            return NOT_FOUND;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * The answer key
     */
    public enum AnswerKey {
        NOT_FOUND(0),
        A(1),
        B(2),
        C(3),
        D(4),
        DONE(5),
        OVER(6);

        /**
         * Storage AnswerKey value
         */
        private final int value;

        /**
         * Initialize
         * @param value
         */
        private AnswerKey(int value){this.value = value;}

        /**
         * Get AnswerKey value
         * @return
         * The integer of AnswerKey
         */
        public int getValue(){return value;}

        /**
         * Get answerkey from integer
         * @param i
         * @return
         */
        public static AnswerKey fromInt(int i)
        {
            for(AnswerKey k : AnswerKey.values())
            {
                if(k.getValue() == i) return k;
            }
            return NOT_FOUND;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * The rank medal
     */
    public enum RankMedal {
        Bronze,
        Sliver,
        Gold
    }

    public enum BattleFrom
    {
        UNKNOW(0),
        ARENA(1),
        MAIL_DETAIL(2);

        private final int value;

        private  BattleFrom(int value)
        {
            this.value = value;
        }

        public int getValue(){ return value;}

        public static BattleFrom FromInt(int i)
        {
            for (BattleFrom b : BattleFrom.values())
            {
                if(b.getValue() == i) return b;
            }
            return UNKNOW;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    //endregion

    //region Utils method

    public static String SERVICE_BASE_URL = "http://192.168.1.106:8080/englishproject/";

    /**
     * Get rank medal from level
     * @param level
     * The level
     * @return
     * The Medal with level
     */
    public static RankMedal GetMedalFromLevel(int level) {
        if (level >= 1 && level <= 26) return RankMedal.Bronze;
        if (level >= 27 && level <= 53) return RankMedal.Sliver;
        if (level >= 54 && level <= 78) return RankMedal.Gold;
        return RankMedal.Bronze;
    }

    /**
     * Get Medal title from level
     * @param level
     * The level
     * @return
     * The Medal title
     */
    public static String GetMedalTitleFromLevel(int level) {
        if (level < 1 || level > 78) return "NOT_FOUND";
        if (level >= 1 && level <= 26) return "ĐỒNG " + (26 - level % 26);
        if (level >= 27 && level <= 53) return "BẠC " + (26 - level % 26);
        if (level >= 54 && level <= 78) return "VÀNG " + (26 - level % 26);
        return "";
    }

    /**
     * Find win rate as String
     * @param total_match
     * The total match
     * @param win_match
     * The win match
     * @return
     * The win rate as string
     */
    public static String GetWinRate(int total_match, int win_match) {
        if (total_match == 0)
            return "0%";
        float winRate = 1.0f * win_match / total_match;
        winRate *= 100;
        return (String.format(java.util.Locale.US, "%.2f", winRate) + "%");
    }

    /**
     * Convert milisecond to string
     * @param milliSecond
     * The milisecond value
     * @return
     * The string value with milisecond
     */
    public static String MillisecondToString(long milliSecond) {
        long seconds, mins, hours;
        seconds = milliSecond / 1000;
        if (seconds < 60) {
            return "0:" + seconds;
        } else if (seconds > 60 && seconds < 3600) {
            mins = seconds / 60;
            seconds = seconds % 60;
            return mins + ":" + seconds;
        } else {
            hours = seconds / 3600;
            seconds = seconds % 3600;
            mins = seconds / 60;
            seconds = seconds % 60;

            return hours + ":" + mins + ":" + seconds;
        }

    }

    /**
     * Format big number as string (99999 -> 99,999)
     * @param value
     * The value
     * @return
     * The string after formated
     */
    public static String FormatBigNumber(long value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }

    /**
     * The currency format properties
     */
    private static String[] suffix = new String[]{"","k", "m", "b", "t"};
    private static int MAX_LENGTH = 4;
    /**
     * For mat number as currency value (2000 -> 2k)
     * @param value
     * The currency value
     * @return
     * The string after formated
     */
    public  static String FormatCurrencyValue(long value)
    {
        String r = new DecimalFormat("##0E0").format(value);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while(r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")){
            r = r.substring(0, r.length()-2) + r.substring(r.length() - 1);
        }
        return r;
    }

    /**
     * Log information to console
     *
     * @param info the information
     */
    public static void LogInfo(String info) {
        Log.i("##--INFO--##", info);
    }

    /**
     * Log an error to console
     *
     * @param error the error
     */
    public static void LogError(String error) {
        Log.e("##--ERROR--##", error);
    }

    //region Error code
    /**
     * Gets the not found error code
     */
    private static ErrorCode notFoundErrorCode;
    public static ErrorCode getNotFoundErrorCode()
    {
        if(notFoundErrorCode != null)
            return notFoundErrorCode;
        else
        {
            notFoundErrorCode = new ErrorCode();
            notFoundErrorCode.setCode("999");
            notFoundErrorCode.setDetails("Unknow error");
            return notFoundErrorCode;
        }
    }

    /**
     * Gets the parse json error code
     */
    private  static ErrorCode parseJsonErrorCode;
    public static ErrorCode getParseJsonErrorCode()
    {
        if(parseJsonErrorCode != null)
            return parseJsonErrorCode;

        else
        {
            parseJsonErrorCode = new ErrorCode();
            parseJsonErrorCode.setCode("998");
            parseJsonErrorCode.setDetails("Fails to parse json");
            return parseJsonErrorCode;
        }
    }

    /**
     * Gets the array null or zero size
     */
    private  static ErrorCode responseNullOrZeroSizeErrorCode;
    public static ErrorCode getResponseNullOrZeroSizeErrorCode()
    {
        if(responseNullOrZeroSizeErrorCode != null)
            return responseNullOrZeroSizeErrorCode;

        else
        {
            responseNullOrZeroSizeErrorCode = new ErrorCode();
            responseNullOrZeroSizeErrorCode.setCode("997");
            responseNullOrZeroSizeErrorCode.setDetails("The response array null or size equal zero");
            return responseNullOrZeroSizeErrorCode;
        }
    }

    /**
     * Gets the internal error code
     */
    private static ErrorCode internalServerErrorCode;
    public static ErrorCode getInternalServerErrorCode(VolleyError error)
    {
        if(internalServerErrorCode != null)
        {
            internalServerErrorCode.setDetails(error.toString());
            return internalServerErrorCode;
        }
        else
        {
            internalServerErrorCode = new ErrorCode();
            internalServerErrorCode.setCode("996");
            internalServerErrorCode.setDetails(error.toString());
            return internalServerErrorCode;
        }
    }
    //endregion
}
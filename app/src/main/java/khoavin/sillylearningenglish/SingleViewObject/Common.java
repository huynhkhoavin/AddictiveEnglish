package khoavin.sillylearningenglish.SingleViewObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.VolleyError;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.Pattern.IAlertBoxResponse;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SillyDateFormat;

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
        LESSON_UNLOCKED(2),
        BATTLE_CHALLENGE_ID(3),
        BATTLE_RANK_UP_DOWN(4),
        BATTLE_BET_VALUE(5),
        BOOK_UNLOCKED(6),
        BATTLE_WIN_LOST_FLAG(7),
        ADD_FRIEND_REQUEST_FRIEND_ID(8);

        /**
         * Storage the Attach type value
         */
        private final int value;

        private AttachType(int value) {
            this.value = value;
        }

        /**
         * Get attach type from integer
         *
         * @param i The integer value
         * @return The attach type
         */
        public static AttachType fromInt(int i) {
            for (AttachType b : AttachType.values()) {
                if (b.getValue() == i) {
                    return b;
                }
            }
            return null;
        }

        /**
         * Get AttachType as Integer
         *
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
        GIF_REWARD(1),
        BATTLE_CHALLENGE(2),
        BATTLE_RESULT(3),
        SYSTEM_MESSAGE(4),
        FRIEND_REQUEST(5);

        /**
         * Storage value
         */
        private final int value;

        /**
         * Initialize
         *
         * @param value
         */
        private MailType(int value) {
            this.value = value;
        }

        /**
         * Get inbox type from integer
         *
         * @param i The value
         * @return The InboxType result
         */
        public static MailType fromInt(int i) {
            for (MailType b : MailType.values()) {
                if (b.getValue() == i)
                    return b;
            }
            return NOT_FOUND;
        }

        /**
         * Get Inbox type value as integer
         *
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
     * The question type
     */
    public enum QuestionType {
        NOT_FOUND(0),
        READING(1),
        LISTENING(2),
        WRITING(3);

        /**
         * Storage value of question type
         */
        private final int value;

        /**
         * Initialize
         *
         * @param value
         */
        private QuestionType(int value) {
            this.value = value;
        }

        /**
         * Get the QuestionType value
         *
         * @return
         */
        public int getValue() {
            return value;
        }

        /**
         * Get question type from integer
         *
         * @param i
         * @return
         */
        public static QuestionType fromInt(int i) {
            for (QuestionType q : QuestionType.values()) {
                if (q.getValue() == i) return q;
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
         *
         * @param value
         */
        private AnswerKey(int value) {
            this.value = value;
        }

        /**
         * Get AnswerKey value
         *
         * @return The integer of AnswerKey
         */
        public int getValue() {
            return value;
        }

        /**
         * Get answerkey from integer
         *
         * @param i
         * @return
         */
        public static AnswerKey fromInt(int i) {
            for (AnswerKey k : AnswerKey.values()) {
                if (k.getValue() == i) return k;
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

    /**
     * Define service response code
     */
    public enum ServiceCode {
        //Addition error code
        NOT_FOUND(0),
        JSON_PARSE_ERROR(999),
        RESPONSE_NULL_OR_ZERO_SIZE(998),
        INTERNAL_SERVER_ERROR(997),

        //Database define error code
        COMPLETED(200),
        NOT_COMPLETED(201),
        BATTLE_CLOSED(202),
        BATTLE_NOT_FOUND(203),
        NOT_ENOUGH_COINS_TO_BUY(204),
        LESSON_WAS_BOUGHT(205),
        NOT_ENOUGH_MONEY(206),
        LESSON_WAS_NOT_BOUGHT(207),
        INBOX_ITEM_NOT_FOUND(208),
        INSERT_AND_UPDATE_PROGRESS_SUCCESS(209),
        UPDATE_PROGRESS_SUCCESS(210),
        LESSON_UNIT_MUST_HIGHER_THAN_CURRENT_UNIT(211),
        INBOX_CLAIMED_REWARD(300),
        USER_NOT_FOUND(301),
        ALREADY_FRIEND(302),
        FRIEND_NOT_FOUND(303);

        /**
         * Storage value
         */
        private final int value;

        /**
         * Initialize
         *
         * @param value The value
         */
        private ServiceCode(int value) {
            this.value = value;
        }

        /**
         * Get Service code value
         *
         * @return
         */
        public int getValue() {
            return value;
        }

        /**
         * Get service code from integer
         *
         * @param i The value
         * @return The service code
         */
        public static ServiceCode FromInt(int i) {
            for (ServiceCode b : ServiceCode.values()) {
                if (b.getValue() == i) return b;
            }
            return NOT_FOUND;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

    }

    /**
     * ...
     */
    public enum BattleCalledFrom {
        NOT_FOUND(0),
        FROM_INBOX(1),
        FROM_ARENA(2),
        FROM_RANKING(3);

        private final int value;

        private BattleCalledFrom(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static BattleCalledFrom FromInt(int i) {
            for (BattleCalledFrom b : BattleCalledFrom.values()) {
                if (b.getValue() == i) return b;
            }
            return NOT_FOUND;
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
     *
     * @param level The level
     * @return The Medal with level
     */
    public static RankMedal GetMedalFromLevel(int level) {
        if (level >= 1 && level <= 26) return RankMedal.Bronze;
        if (level >= 27 && level <= 53) return RankMedal.Sliver;
        if (level >= 54 && level <= 78) return RankMedal.Gold;
        return RankMedal.Bronze;
    }

    /**
     * Get Medal title from level
     *
     * @param level The level
     * @return The Medal title
     */
    public static String GetMedalTitleFromLevel(int level, Context context) {
        if (level < 1 || level > 78) return String.format(context.getResources().getString(R.string.ranking_bronze_title), "0");
        if (level >= 1 && level <= 26) return String.format(context.getResources().getString(R.string.ranking_bronze_title), String.valueOf(26 - level % 26));;
        if (level >= 27 && level <= 53) return String.format(context.getResources().getString(R.string.ranking_sliver_title), String.valueOf(26 - level % 26));
        if (level >= 54 && level <= 78) return String.format(context.getResources().getString(R.string.ranking_gold_title), String.valueOf(26 - level % 26));
        return "";
    }

    /**
     * Find win rate as String
     *
     * @param total_match The total match
     * @param win_match   The win match
     * @return The win rate as string
     */
    public static String GetWinRate(int total_match, int win_match) {
        if (total_match == 0)
            return "0%";
        float winRate = 1.0f * win_match / total_match;
        winRate *= 100;
        return (String.format(java.util.Locale.US, "%.2f", winRate) + "%");
    }

    public static float GetWinRateAsFloat(int total_match, int win_match) {
        if (total_match == 0)
            return 0;
        float winRate = 1.0f * win_match / total_match;
        winRate *= 100;
        double roundOff = Math.round(winRate * 100.0) / 100.0;
        return (float)roundOff;
    }

    /**
     * Gets the user's rank position text.
     * @param rankPosition The user's position on ranking table.
     * @return The ranking text.
     */
    public static String getUserRankPositionText(int rankPosition)
    {
        if(rankPosition == 1 || rankPosition == 2 || rankPosition == 3)
        {
            return "No." + String.valueOf(rankPosition);
        }
        else if(rankPosition > 3)
        {
            return String.valueOf(rankPosition);
        }
        else
        {
            return "-";
        }
    }

    /**
     * Gets the rank medal image.
     * @param level The level.
     * @return The medal identifier.
     */
    public static int getRankMedalImage(int level)
    {
        RankMedal medal = GetMedalFromLevel(level);
        switch (medal)
        {
            case Bronze:
                return R.drawable.medal_bronze;
            case Sliver:
                return R.drawable.medal_sliver;
            case Gold:
                return R.drawable.medal_gold;
            default:
                return R.drawable.medal_bronze;
        }
    }

    /**
     * Format big number as string (99999 -> 99,999)
     *
     * @param value The value
     * @return The string after formated
     */
    public static String FormatBigNumber(long value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }

    /**
     * The currency format properties
     */
    private static String[] suffix = new String[]{"", "k", "m", "b", "t"};
    private static int MAX_LENGTH = 4;

    /**
     * For mat number as currency value (2000 -> 2k)
     *
     * @param value The currency value
     * @return The string after formated
     */
    public static String FormatCurrencyValue(long value) {
        String r = new DecimalFormat("##0E0").format(value);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return r;
    }

    /**
     * Gets the silly date format.
     */
    private static SillyDateFormat _sillyDateFormat;

    public static SillyDateFormat GetSillyDateFormat() {
        if (_sillyDateFormat == null) _sillyDateFormat = new SillyDateFormat();
        return _sillyDateFormat;
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

    public static ErrorCode getNotFoundErrorCode() {
        if (notFoundErrorCode != null)
            return notFoundErrorCode;
        else {
            notFoundErrorCode = new ErrorCode();
            notFoundErrorCode.setCode(ServiceCode.NOT_FOUND);
            notFoundErrorCode.setDetails("Error not found");
            return notFoundErrorCode;
        }
    }

    /**
     * Gets the parse json error code
     */
    private static ErrorCode parseJsonErrorCode;

    public static ErrorCode getParseJsonErrorCode() {
        if (parseJsonErrorCode != null)
            return parseJsonErrorCode;

        else {
            parseJsonErrorCode = new ErrorCode();
            parseJsonErrorCode.setCode(ServiceCode.JSON_PARSE_ERROR);
            parseJsonErrorCode.setDetails("Fails to parse json");
            return parseJsonErrorCode;
        }
    }

    /**
     * Gets the array null or zero size
     */
    private static ErrorCode responseNullOrZeroSizeErrorCode;

    public static ErrorCode getResponseNullOrZeroSizeErrorCode() {
        if (responseNullOrZeroSizeErrorCode != null)
            return responseNullOrZeroSizeErrorCode;

        else {
            responseNullOrZeroSizeErrorCode = new ErrorCode();
            responseNullOrZeroSizeErrorCode.setCode(ServiceCode.RESPONSE_NULL_OR_ZERO_SIZE);
            responseNullOrZeroSizeErrorCode.setDetails("The response array null or size equal zero");
            return responseNullOrZeroSizeErrorCode;
        }
    }

    private static ErrorCode success200ErrorCode;

    public static ErrorCode getSuccess200ErrorCode() {
        if (success200ErrorCode != null)
            return success200ErrorCode;

        else {
            success200ErrorCode = new ErrorCode();
            success200ErrorCode.setCode(ServiceCode.COMPLETED);
            success200ErrorCode.setDetails("Success");
            return success200ErrorCode;
        }
    }

    /**
     * Gets the internal error code
     */
    private static ErrorCode internalServerErrorCode;

    public static ErrorCode getInternalServerErrorCode(VolleyError error) {
        if (internalServerErrorCode != null) {
            internalServerErrorCode.setDetails(error.toString());
            return internalServerErrorCode;
        } else {
            internalServerErrorCode = new ErrorCode();
            internalServerErrorCode.setCode(ServiceCode.INTERNAL_SERVER_ERROR);
            internalServerErrorCode.setDetails(error.toString());
            return internalServerErrorCode;
        }
    }
    //endregion

    //region Alert box

    /**
     * Show confirm message box.
     *
     * @param message       The message.
     * @param title         The title.
     * @param positiveTitle The title for positive button.
     * @param negativeTitle The title for negative button.
     * @param context       The context.
     * @param alertResponse The response for select.
     */
    public static void ShowConfirmMessage(String message, String title, String positiveTitle, String negativeTitle, Context context, final IAlertBoxResponse alertResponse) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveTitle, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (alertResponse != null)
                            alertResponse.OnPositive();
                    }
                })
                .setNegativeButton(negativeTitle, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (alertResponse != null)
                            alertResponse.OnNegative();
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Show confirm message box.
     *
     * @param message       The message.
     * @param title         The title.
     * @param positiveTitle The title for positive button.
     * @param context       The context
     * @param alertResponse The response for select.
     */
    public static void ShowInformMessage(String message, String title, String positiveTitle, Context context, final IAlertBoxResponse alertResponse) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveTitle, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (alertResponse != null)
                            alertResponse.OnPositive();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    //endregion
}
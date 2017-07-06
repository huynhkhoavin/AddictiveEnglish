package khoavin.sillylearningenglish.SYSTEM.Constant;


/**
 * Created by KhoaVin on 1/19/2017.
 */

public class WebAddress {
    public static final String SERVER_URL = "http://10.80.23.159/";
    public static final String BASE_URL = SERVER_URL+"sillyenglish-web-service/index.php/";

    //region Training
    public static final String GET_POPULAR_LESSON = BASE_URL + "training/get_popular_lesson";
    public static final String CHECK_LESSON_WAS_BOUGHT = BASE_URL + "training/check_lesson_was_bought";
    public static final String BUY_LESSON = BASE_URL + "training/buylesson";
    public static final String UPDATE_LESSON_UNIT = BASE_URL + "training/update_lesson_progress";
    public static final String GET_LESSON_UNIT = BASE_URL + "training/get_lesson_unit";
    public static final String GET_LESSON_TRACKER = BASE_URL + "training/get_lesson_tracker";
    public static final String GET_RATING_LESSON = BASE_URL + "training/get_rating_lesson";
    public static final String GET_DAILY_LESSON = BASE_URL + "training/get_daily_lesson";
    public static final String GET_USER_LESSON = BASE_URL + "training/get_user_lesson";
    public static final String RATING_LESSON = BASE_URL + "training/rating_lesson";
    //endregion

    //region inbox
    public static final String MAIL_GET_ITEMS = BASE_URL + "inbox/get_inbox";
    public static final String MAIL_DELETE = BASE_URL + "inbox/remove_mail";
    public static final String MAIL_DELETE_SELECTED = BASE_URL + "inbox/delete_selected_mails";
    public static final String MAIL_RATE = BASE_URL + "inbox/rate";
    public static final String MAIL_CLAIM = BASE_URL + "inbox/inbox_claim_rewards";
    public static final String MAIL_MASK_OPENED = BASE_URL + "inbox/mask_as_opened";
    public static final String MAIL_GET_ATTACH_ITEMS = BASE_URL + "inbox/get_attach_item";
    public static final String MAIL_ACCEPT_FRIEND = BASE_URL + "inbox/accept_friend_request";
    public static final String MAIL_NEW_MAIL_CHECKER = BASE_URL + "inbox/new_mail_checking";
    //endregion

    //region battle
    public static final String BATTLE_GET_ENEMY_DUEL = BASE_URL + "arena/get_enemy_duel";
    public static final String BATTLE_RESULT = BASE_URL + "arena/get_battle_result";
    public static final String BATTLE_FIND = BASE_URL + "arena/find_battle";
    public static final String BATTLE_FIND_ENEMY = BASE_URL + "arena/find_enemy_with_identifier";
    public static final String BATTLE_GET_CHAINS = BASE_URL + "arena/get_battle_chains";
    public static final String BATTLE_ACCEPT = BASE_URL + "arena/accept_battle";
    public static final String BATTLE_CHOSE_ANSWER = BASE_URL + "arena/chose_answer";
    public static final String BATTLE_CANCEL = BASE_URL + "arena/cancel_battle";
    public static final String BATTLE_CREATE = BASE_URL + "arena/create_battle";
    public static final String BATTLE_GET_HISTORY = BASE_URL + "arena/get_battle_history";

    //Ranking
    public static final String RANKING_GET_GLOBAL_RANKING = BASE_URL + "leaderboard/global_ranking";
    public static final String RANKING_GET_FRIEND_RANKING = BASE_URL + "leaderboard/friend_ranking";


    //endregion

    //region user
    public static final String USER_GET_OR_CREATE = BASE_URL + "user/getinfo";
    public static final String USER_GET_APP_PARAMS = BASE_URL + "user/get_app_params";
    //endregion

    //region Friends
    public static final String FIND_FRIEND_BY_NAME = BASE_URL+ "friend/find_friends";
    public static final String REQUEST_ADD_FRIEND = BASE_URL + "leaderboard/add_friend_request";
    public static final String UNFRIEND_REQUEST = BASE_URL + "friend/unfriend";
    public static final String GET_LIST_FRIEND = BASE_URL + "friend/get_list_friends";
    //region

    //region Social Network
    public static final String POST_NOTIFICATION = BASE_URL + "social/post_new_notification";
    public static final String GET_USER_NOTIFICATION = BASE_URL + "social/get_user_notification";
    public static final String GET_USER_PROFILE = BASE_URL + "social/get_user_notification_profile";
    public static final String COMMENT_NOTIFY = BASE_URL + "social/comment_notify";
    public static final String GET_NOTIFY_COMMENTS = BASE_URL + "social/get_notify_comments";
    //endregion
}


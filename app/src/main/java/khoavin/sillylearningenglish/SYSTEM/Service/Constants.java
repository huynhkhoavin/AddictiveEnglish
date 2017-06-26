package khoavin.sillylearningenglish.SYSTEM.Service;

/**
 * Created by Khoavin on 4/9/2017.
 */

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "main";
        public static String INIT_ACTION = "init";
        public static String PLAY_BEGIN_ACTION = "begin";
        public static String PREV_ACTION = "prev";
        public static String PLAY_ACTION = "play";
        public static String PAUSE_ACTION = "pause";
        public static String NEXT_ACTION = "next";
        public static String STARTFOREGROUND_ACTION = "startforeground";
        public static String STOPFOREGROUND_ACTION = "stopforeground";
        public static String ADD_URL = "add_url";
        public static String UPDATE_PROGRESS_SUCCESS = "update_progress_success";
        public static String INIT_NEW_LESSON = "init_new_lesson";
        public static String FRIEND_REQUEST_ACCEPTED = "friend_request_accepted";
    }
    public interface MUSIC_STATE{
        String IS_PLAYING = "is_playing";
        String IS_PAUSE = "is_pause";
        String IS_STOP = "is_stop";
    }
    public interface MESSAGE_EVENT{
        public static String UPDATE_PROGRESS = "UPDATE_PROGRESS";
    }
    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
    public interface SHARED_PREFERENCES{
        public static String MUSIC_PREFERENCES = "MUSIC_PREFERENCES";
        public static String PROGRESS = "PROGRESS";
    }
}
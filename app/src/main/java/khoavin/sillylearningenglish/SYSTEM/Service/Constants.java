package khoavin.sillylearningenglish.SYSTEM.Service;

/**
 * Created by Khoavin on 4/9/2017.
 */

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "main";
        public static String INIT_ACTION = "init";
        public static String PREV_ACTION = "prev";
        public static String PLAY_ACTION = "play";
        public static String PAUSE_ACTION = "pause";
        public static String NEXT_ACTION = "next";
        public static String STARTFOREGROUND_ACTION = "startforeground";
        public static String STOPFOREGROUND_ACTION = "stopforeground";
        public static String ADD_URL = "add_url";
        public static String UPDATE_PROGRESS_SUCCESS = "update_progress_success";
    }
    public interface MESSAGE_EVENT{
        public static String UPDATE_PROGRESS = "UPDATE_PROGRESS";
    }
    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}
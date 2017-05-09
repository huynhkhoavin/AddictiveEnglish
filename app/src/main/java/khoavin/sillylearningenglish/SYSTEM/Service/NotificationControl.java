package khoavin.sillylearningenglish.SYSTEM.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.RemoteViews;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.PlayActivity;
import khoavin.sillylearningenglish.R;

/**
 * Created by Dev02 on 3/10/2017.
 */

public class NotificationControl {
    static String TAG = "NotificationControl";
    private NotificationManager mNotificationManager;
    private RemoteViews collapseViews;
    private RemoteViews expandViews;
    private Notification notify;
    private Context mContext;
    PLAYSTATE playstate;
    public NotificationControl(Context context) {
        mContext = context;
        EventBus.getDefault().register(this);
        playstate = PLAYSTATE.IS_PAUSE;
    }
    public void showNotification() {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
// Using RemoteViews to bind custom layouts into Notification
        collapseViews = new RemoteViews(mContext.getPackageName(),
                R.layout.music_collapse_bar);

        expandViews = new RemoteViews(mContext.getPackageName(),
                R.layout.music_expand_bar);
        BindingView();
        mNotificationManager.notify(TAG, Notification.FLAG_ONGOING_EVENT,notify);
    }

    public void BindingView(){
        //Click vao Notification se di vao PlayActivity
        Intent notificationIntent = new Intent(mContext, PlayActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
                notificationIntent, 0);
        // Click vao nut Prev
        Intent previousIntent = new Intent(mContext, BackgroundMusicService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(mContext, 0,
                previousIntent, 0);
        expandViews.setOnClickPendingIntent(R.id.btn_Prev, ppreviousIntent);

        //Click vao nut Play
        Intent playIntent = new Intent(mContext, BackgroundMusicService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(mContext, 0,
                playIntent, 0);
        expandViews.setOnClickPendingIntent(R.id.btn_Play,pplayIntent);
        collapseViews.setOnClickPendingIntent(R.id.btn_Play,pplayIntent);

        // Click vao nut Next
        Intent nextIntent = new Intent(mContext, BackgroundMusicService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(mContext, 0,
                nextIntent, 0);
        expandViews.setOnClickPendingIntent(R.id.btn_Next,pnextIntent);
        //Click vao nut // Stop
        Intent closeIntent = new Intent(mContext, BackgroundMusicService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(mContext, 0,
                closeIntent, 0);

        //notify = new Notification.Builder(mContext).build();
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setOngoing(false);
        notify = builder.build();
        notify.contentView = collapseViews;
        notify.bigContentView = expandViews;
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        notify.icon = R.drawable.my_listening;
        notify.contentIntent = pendingIntent;
    }
    @Subscribe
    public void onEvent(MediaPlayer mediaPlayer) {

        // Click vao nut Play
        Intent playIntent = new Intent(mContext, BackgroundMusicService.class);

        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(mContext, 0,
                playIntent, 0);

        Intent pauseIntent = new Intent(mContext, BackgroundMusicService.class);

        pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
        PendingIntent ppauseIntent = PendingIntent.getService(mContext, 0,
                pauseIntent, 0);
        if(mediaPlayer.isPlaying()){
                collapseViews.setImageViewResource(R.id.btn_Play, R.drawable.pause);
                expandViews.setImageViewResource(R.id.btn_Play, R.drawable.pause);
                expandViews.setOnClickPendingIntent(R.id.btn_Play,ppauseIntent);
                collapseViews.setOnClickPendingIntent(R.id.btn_Play,ppauseIntent);
            }
        else{
            collapseViews.setImageViewResource(R.id.btn_Play, R.drawable.play);
            expandViews.setImageViewResource(R.id.btn_Play, R.drawable.play);
            expandViews.setOnClickPendingIntent(R.id.btn_Play,pplayIntent);
            collapseViews.setOnClickPendingIntent(R.id.btn_Play,pplayIntent);
        }
        Notify();
    }
    public void CancelAll(){
        mNotificationManager.cancelAll();
        EventBus.getDefault().unregister(this);
    }
    public void Notify(){
        mNotificationManager.notify(TAG, Notification.FLAG_ONGOING_EVENT,notify);
    }
}

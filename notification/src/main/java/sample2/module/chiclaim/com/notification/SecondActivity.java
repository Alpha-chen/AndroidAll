package sample2.module.chiclaim.com.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/12.
 */

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_layout);
    }

    public void startSelf(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void sendNotification(View view) {
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setContentIntent(contentIntent);
        mNotifyMgr.notify(1, mBuilder.build());
    }

    /**
     * 实现  弹出notification然后把界面全部关掉，在点击通知栏的通知 进入SecondActivity ，然后按返回键回到MainActivity 的功能
     */
    public void sendNotificationForBack(View view) {
        Toast.makeText(this, "关闭所有的界面，然后点击通知栏的通知,再按返回键", Toast.LENGTH_SHORT).show();
        //sendNotificationForBack1();
        //or
        sendNotificationForBack2();
    }


    public void sendNotificationForBack1() {
        Intent resultIntent = new Intent(this, SecondActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // 添加返回栈
        stackBuilder.addParentStack(SecondActivity.class);
        // 添加Intent到栈顶
        stackBuilder.addNextIntent(resultIntent);
        // 创建包含返回栈的pendingIntent
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("SecondActivity");
        builder.setContentText("SecondActivity");
        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, builder.build());
    }

    public void sendNotificationForBack2() {
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.putExtra("from", "sendNotificationForBack2");
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent = new Intent(this, SecondActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{backIntent, intent}, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("SecondActivity");
        builder.setContentText("SecondActivity");
        builder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, builder.build());
    }

}
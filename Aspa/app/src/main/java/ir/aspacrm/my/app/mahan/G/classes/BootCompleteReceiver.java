package ir.aspacrm.my.app.mahan.G.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ir.aspacrm.my.app.mahan.G.G;

import java.util.Calendar;


public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d("BootCompleteReceiver : onReceive");
        G.checkNotification = new CheckNotification();
        G.checkNotification.SetRepeatAlarm(69, Calendar.getInstance().getTimeInMillis() + G.NOTIFICATION_CHECKER_TIME, G.NOTIFICATION_CHECKER_TIME);
    }
}

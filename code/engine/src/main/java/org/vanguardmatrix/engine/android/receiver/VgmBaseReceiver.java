package org.vanguardmatrix.engine.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.vanguardmatrix.engine.android.Application;
import org.vanguardmatrix.engine.android.data.notification.OnZaairListener;
import org.vanguardmatrix.engine.utils.NetworkChangeUtil;

/**
 * Created by Abdul Wahab on 10/2/2015.
 */
public class VgmBaseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkChangeUtil.getConnectivityStatusString(context);
        Log.e("VgmBaseReceiver", "intent.getAction():" + intent.getAction() + " int");
        if (!"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            if (status == NetworkChangeUtil.NETWORK_STATUS_NOT_CONNECTED) {
                //Application.getInstance().onNetworkChange(true);
                Toast.makeText(context, "OFF " + status, Toast.LENGTH_LONG).show();
                //new ForceExitPause(context).execute();
                for (OnZaairListener onZaairListener : Application.getInstance().getUIListeners(OnZaairListener.class))
                    onZaairListener.onNetStatusChange(false);
            } else {
                //Application.getInstance().onNetworkChange(false);
                Toast.makeText(context, "ON " + status, Toast.LENGTH_LONG).show();
                //new ResumeForceExitPause(context).execute();
                for (OnZaairListener onZaairListener : Application.getInstance().getUIListeners(OnZaairListener.class))
                    onZaairListener.onNetStatusChange(true);
            }

        }
    }
}
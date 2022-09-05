package com.slavik.tdam.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.slavik.tdam.util.Network;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private INetworkObserver mObserver;

    public void observe(INetworkObserver observer) {
        mObserver = observer;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = Network.getConnectivityStatusString(context);
        mObserver.onChange(status);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            System.out.println("cambió algo xd");
        }
    }

    public interface INetworkObserver {
        void onChange(int state);
    }
}

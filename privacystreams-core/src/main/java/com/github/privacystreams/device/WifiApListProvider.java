package com.github.privacystreams.device;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;

import com.github.privacystreams.core.UQI;
import com.github.privacystreams.core.providers.MultiItemStreamProvider;


/**
 * Provide a stream of current WIFI AP list.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
class WifiApListProvider extends MultiItemStreamProvider {

    class WifiReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            String name = wifiInfo.getSSID();
            for(ScanResult result: wifiMgr.getScanResults()){
                output(new WifiAp(result, name.equals(result.SSID)));
            }
        }
    }


    @Override
    protected void onCancelled(UQI uqi) {
        super.onCancelled(uqi);
        getContext().unregisterReceiver(wifiReceiver);
    }

    private transient WifiReceiver wifiReceiver;

    WifiApListProvider() {
        this.addRequiredPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE);
        this.wifiReceiver = new WifiReceiver();
    }


    @Override
    protected void provide() {

        WifiManager wifiMgr = (WifiManager) this.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        Looper.prepare();

        if(wifiMgr.isWifiEnabled()) {
            this.getContext().registerReceiver(this.wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiMgr.startScan();
        }

    }

}
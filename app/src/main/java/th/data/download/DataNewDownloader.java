package th.data.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import th.data.dynamic.Source;
import th.data.dynamic.source._24h.group.category.Category;
import th.tintuchangngay.DailyNewsActivity;
import th.tintuchangngay.listener.ActiveNetWorkListener;

/**
 * Created by The on 5/1/2017.
 */
public class DataNewDownloader {

    private Source source;
    private Category category;
    private DownloadedListener mDownloadedListener;
    private DownloadingListener mDownloadingListener;
    private IErrorListener onErr;
    private ActiveNetWorkListener onNetworkActive;
    private Context dailyNewsActivity;
    private NetWorkActiveBroadCastReceiver networkActive;


    public DataNewDownloader() {

    }


    public void onDownloaded(DownloadedListener downloadedListener) {
        this.mDownloadedListener = downloadedListener;
    }

    public void onDownloading(DownloadingListener downloadingListener) {
        this.mDownloadingListener = downloadingListener;
    }

    public void setSource(Source source) {
        this.source = source;

    }


    public void setCategory(Category category) {
        this.category = category;
    }

    public void downloadNews() {
        DownloadContentTask downloadContentTask = new DownloadContentTask();
        downloadContentTask.setmSource(this.source);
        downloadContentTask.setmCategory(this.category);
        downloadContentTask.setOnDownloadingListener(mDownloadingListener);
        downloadContentTask.setOnErr(this.onErr);
        downloadContentTask.setDownloadedListener(mDownloadedListener);
        downloadContentTask.execute();
    }


    public void setOnErr(IErrorListener onErr) {
        this.onErr = onErr;
    }


    public void setOnNetworkActive(ActiveNetWorkListener onNetworkActive, Context dailyNewsActivity) {
        this.onNetworkActive = onNetworkActive;
        this.dailyNewsActivity = dailyNewsActivity;
        networkActive = new NetWorkActiveBroadCastReceiver();
        IntentFilter connectivityAction = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        dailyNewsActivity.registerReceiver(networkActive, connectivityAction);

    }

    public void unRegisterBroadCast(DailyNewsActivity dailyNewsActivity) {
        if (networkActive != null) {
            dailyNewsActivity.unregisterReceiver(networkActive);
        }
    }


    class NetWorkActiveBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            onNetworkActive.active();
        }
    }

}

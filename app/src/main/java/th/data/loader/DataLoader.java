package th.data.loader;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import th.data.download.DataDownloaded;
import th.data.download.DownloadedListener;
import th.data.download.DownloadingListener;
import th.data.download.IErrorListener;
import th.data.dynamic.Source;
import th.data.dynamic.source._24h.group.category.Category;
import th.data.statik.Item;
import th.tintuchangngay.listener.IBeforeLoadItem;

/**
 * Created by The on 5/6/2017.
 */
public class DataLoader {
    private IErrorListener onErr;
    private DownloadedListener onDownloaded;
    private DownloadingListener onDownloading;
    private IBeforeLoadItem onBeforeLoad;

    public void setOnErr(IErrorListener onErr) {
        this.onErr = onErr;
    }

    public void onDownloaded(DownloadedListener downloadedListener) {
    }

    public void setOnDownloaded(DownloadedListener onDownloaded) {
        this.onDownloaded = onDownloaded;
    }

    public void setOnDownloading(DownloadingListener onDownloading) {
        this.onDownloading = onDownloading;
    }

    public void loadNews(Source source, Category cate) {
        new ThreadLoader(source, cate).start();

    }

    public void setOnBeforeLoad(IBeforeLoadItem onBeforeLoad) {
        this.onBeforeLoad = onBeforeLoad;
    }

    class ThreadLoader extends Thread {
        private Source mSource;
        private Category mCategory;

        ThreadLoader(Source src, Category cate) {
            this.mSource = src;
            mCategory = cate;
        }

        public void run() {
            try {
                if (onBeforeLoad != null) {
                    onBeforeLoad.beforeLoad();
                }
                List<Item> listItems = DataDownloaded.getInstance().getItems(mSource.name() + mCategory.name());
                Log.d("the.dv", "DataLoader.ThreadLoader.run()::  List<Item> listItems = DataDownloaded.getIns... [" + listItems.size() + "]");
                for (Item item : listItems) {
                    onDownloading.get(item);
                }
                if (onDownloaded != null) {
                    onDownloaded.get(new ArrayList<Item>(listItems));
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (onErr != null) {
                    onErr.onError();
                }
            }
        }
    }
}

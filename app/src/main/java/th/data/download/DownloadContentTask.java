package th.data.download;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import th.data.dynamic.News;
import th.data.dynamic.Source;
import th.data.dynamic.source._24h._24h;
import th.data.dynamic.source._24h.group.category.Category;
import th.data.statik.Item;

/**
 * Created by The on 5/1/2017.
 */
public class DownloadContentTask extends AsyncTask<DownloadingListener, Item, DownloadedListener> {

    private static News news;
    private String mRss;
    private DownloadedListener mDownloadedListener;
    private IErrorListener onErr;

    public Source getmSource() {
        return mSource;
    }


    public void setmSource(Source mSource) {
        this.mSource = mSource;
    }

    public Category getmCategory() {
        return mCategory;
    }

    public void setmCategory(Category mCategory) {
        this.mCategory = mCategory;
    }

    public void setDownloadedListener(DownloadedListener mDownloadedListener) {
        this.mDownloadedListener = mDownloadedListener;
    }


    private Source mSource;
    private Category mCategory;
    private DownloadingListener mDownloadingListener;

    private List<Item> items;

    public void setOnDownloadingListener(DownloadingListener mDownloadingListener) {
        this.mDownloadingListener = mDownloadingListener;
    }


    @Override
    protected DownloadedListener doInBackground(DownloadingListener... params) {
        Log.d("the.dv", "DownloadContentTask.doInBackGround");
        if (mSource == Source._24H) {
            if (news == null) {
                news = new _24h();
            }
            news.setOnErr(this.onErr);
//            news.setOnGetItems(new GetItemListener() {
//                @Override
//                public void get(Item item) {
//                    publishProgress(item);
//                }
//            });
            items = news.getNewByCategory(mCategory);

            if (mDownloadedListener != null && items != null) {
                mDownloadedListener.get(new ArrayList<Item>(items));
            }
        }

        return mDownloadedListener;
    }


    @Override
    protected void onProgressUpdate(Item... values) {
        if (mDownloadingListener != null) {
            mDownloadingListener.get(values[0]);
        }
        super.onProgressUpdate(values);
    }

   /* @Override
    protected void onPostExecute(DownloadedListener downloadedListener) {
        if (downloadedListener != null && items != null) {
            Log.d("the.dv", "DownloadContentTask.onPostExecute");
            downloadedListener.get(new ArrayList<Item>(items));
        }
        //  super.onPostExecute(downloadedListener);
    }*/

    public void setOnErr(IErrorListener onErr) {
        this.onErr = onErr;
    }
}

package th.data;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.List;

import th.data.download.DataNewDownloader;
import th.data.download.DownloadedListener;
import th.data.download.DownloadingListener;
import th.data.download.IErrorListener;
import th.data.dynamic.Source;
import th.data.dynamic.source._24h.group.category.Category;
import th.data.listener.IBeforeLoadItem;
import th.data.loader.DataLoader;
import th.data.statik.Item;

/**
 * Created by The on 5/13/2017.
 */

public class ContentAdapter extends AsyncTask<List<Item>, Item, List<Item>> {


    private Context mContext;
    private DataNewDownloader newDownloader;
    private DataLoader newLoaded;

    public ContentAdapter(Context context) {
        this.mContext = context;
        mainHandler = new Handler(mContext.getMainLooper());
    }

    //region Handler thread on UI thread
    // Get a handler that can be used to post to the main thread
    private Handler mainHandler ;

    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            if (mDLoaded != null) {
                mDLoaded.onDownloaded();
            }
        }
    };
    //endregion


    @Override
    protected List<Item> doInBackground(List<Item>... params) {
        Log.d("the.dv", "ContentAdapter.diInBackground()..");

        final List<Item> mListOfNews = params[0];
        mListOfNews.clear();
        newDownloader = new DataNewDownloader();
        newLoaded = new DataLoader();


        newDownloader.setSource(mSource);
        newDownloader.setCategory(mCategory);
//        newDownloader.setOnNetworkActive(new ActiveNetWorkListener() {
//            @Override
//            public void active() {
//                newDownloader.downloadNews();
//            }
//        }, mContext);


        newLoaded.setOnDownloading(new DownloadingListener() {
            @Override
            public void get(final Item item) {
                Log.d("the.dv", "setOnDownloading( ).. mListOfNews.add(item);");
                publishProgress(item);
                mListOfNews.add(item);
                if (mDLoading != null) {
                    mDLoading.onDownload();
                }
            }
        });
        newLoaded.setOnBeforeLoad(new IBeforeLoadItem() {
            @Override
            public void beforeLoad() {
                mListOfNews.clear();
                Log.d("the.dv", "BEFORE LOAD");
            }
        });
        newLoaded.setOnDownloaded(new DownloadedListener() {
            @Override
            public void get(List<Item> items) {
                Log.d("the.dv", "End LOAD : " + items.size() + "(items)");
                mainHandler.post(myRunnable);
            }
        });

        newLoaded.setOnErr(new IErrorListener() {
            @Override
            public void onError() {
                newLoaded.loadNews(mSource, mCategory);
            }
        });
        newDownloader.onDownloaded(new DownloadedListener() {
            @Override
            public void get(List<Item> items) {
                newLoaded.loadNews(mSource, mCategory);
            }
        });
        newDownloader.downloadNews();


        return mListOfNews;
    }

    private Source mSource;
    private Category mCategory;


    @Override
    protected void onPostExecute(List<Item> items) {
        super.onPostExecute(items);
    }

    public void select(Source source, Category category) {
        this.mSource = source;
        this.mCategory = category;
    }

    public void onLoadingContent(DLoading downloadingListener) {
        mDLoading = downloadingListener;
    }

    public void onLoadedContent(DLoaded downloadedListener) {
        mDLoaded = downloadedListener;
    }

    // region interface Downloading and Downloaded
    private DLoading mDLoading;
    private DLoaded mDLoaded;

    public interface DLoading {
        void onDownload();
    }

    public interface DLoaded {
        void onDownloaded();
    }

    // endregion
}

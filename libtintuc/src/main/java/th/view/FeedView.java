package th.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import th.data.ContentAdapter;
import th.data.dynamic.Source;
import th.data.dynamic.source._24h.group.category.Category;
import th.data.statik.Item;
import th.view.adapter.ItemAdapter;

/**
 * Created by The on 9/16/2017.
 */

public class FeedView extends ListView {

    private List<Item> mListOfNews;
    private ContentAdapter contentAdapter;
    private Context mContext;

    public FeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        contentAdapter = new ContentAdapter(context);
        this.mContext = context;
        mListOfNews = new ArrayList<>();
        this.setAdapter(new ItemAdapter(mListOfNews, context));
        loadContent(Source._24H, Category.TIN_NONG);
    }


    public ContentAdapter getContentAdapter() {
        return contentAdapter;
    }


    public void loadContent(Source feed_src, Category feed_category) {
        contentAdapter = new ContentAdapter(mContext);
        contentAdapter.select(feed_src, feed_category);
        contentAdapter.onLoadingContent(new MDLoading());
        contentAdapter.onLoadedContent(new MDLoaded());

        contentAdapter.execute(mListOfNews);
    }

    class MDLoading implements ContentAdapter.DLoading {
        @Override
        public void onDownload() {

        }
    }

    class MDLoaded implements ContentAdapter.DLoaded {

        @Override
        public void onDownloaded() {

            getFirstVisiblePosition();
            invalidateViews();

            ((BaseAdapter) getAdapter()).notifyDataSetChanged();
            Log.d("the.dv", "DOWNLOADED 00");
        }
    }
}

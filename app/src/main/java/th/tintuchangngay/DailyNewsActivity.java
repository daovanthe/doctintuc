package th.tintuchangngay;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.MobileAds;
import com.ms_square.etsyblur.BlurSupport;

import java.util.ArrayList;
import java.util.List;

import th.data.ContentManager;
import th.data.dynamic.Source;
import th.data.dynamic.source._24h.group.category.Category;
import th.data.statik.Item;
import th.view.adapter.ItemAdapter;

public class DailyNewsActivity extends Activity {

    private List<Item> mListOfNews;
    private ListView mListNews;
    private Context mContext;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nav_view;
    private ContentManager contentNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tintuchangngay);
        mListNews = (ListView) findViewById(R.id.list_new_item);
        mListOfNews = new ArrayList<>();
        mContext = this;

        MobileAds.initialize(this, getResources().getText(R.string.test_admob_app_id).toString());
        String[] testDevicesIds = new String[]{"ca-app-pub-1510017343836393/2466767868"}; //"ca-app-pub-1510017343836393/6679291067", ca-app-pub-1510017343836393/8792503061
        mListNews.setAdapter(new ItemAdapter(mListOfNews, this));

        contentNew = new ContentManager(this);
        Log.d("the.dv", "DailyNewActivity.onCreate()");
        contentNew.select(Source._24H, Category.TIN_NONG);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        BlurSupport.addTo(mDrawerLayout);
        mDrawerLayout.setScrimColor(Color.WHITE);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        nav_view = (NavigationView) findViewById(R.id.nav_view);
        int item_length = nav_view.getMenu().size();

        // load content through content New
        for (int i = 0; i < item_length; i++) {
            MenuItem item = nav_view.getMenu().getItem(i);
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d("the.dv", "DailyNewActivity.MenuItemClicked");
                    contentNew = new ContentManager(mContext);
                    if (getResources().getString(R.string.hot_new).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.TIN_NONG);
                    } else if (getResources().getString(R.string.bong_da).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.BONG_DA);
                    } else if (getResources().getString(R.string.annninh_hinhsu).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.ANNINH_HINHSU);
                    } else if (getResources().getString(R.string.thoi_trang).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.THOI_TRANG);
                    } else if (getResources().getString(R.string.tai_chinh).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.TAI_CHINH);
                    } else if (getResources().getString(R.string.xa_hoi).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.XA_HOI);
                    } else if (getResources().getString(R.string.am_thuc).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.AM_THUC);
                    } else if (getResources().getString(R.string.lam_dep).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.LAM_DEP);
                    } else if (getResources().getString(R.string.phim).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.PHIM);
                    } else if (getResources().getString(R.string.giao_duc).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.GIAO_DUC);
                    } else if (getResources().getString(R.string.ban_tre).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.BAN_TRE);
                    } else if (getResources().getString(R.string.ca_nhac).equals(item.getTitle())) {
                        contentNew.select(Source._24H, Category.CA_NHAC);
                    }
                    mListNews.setSelectionAfterHeaderView();
                    mDrawerLayout.closeDrawer(nav_view);
                    contentNew.onLoadingContent(new ContentManager.DLoading() {
                        @Override
                        public void onDownload() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((BaseAdapter) mListNews.getAdapter()).notifyDataSetChanged();
                                    Log.d("the.dv", "notified() " + mListOfNews.size());
                                }
                            });
                        }
                    });
                    contentNew.execute(mListOfNews);
                    return true;
                }
            });
        }

        contentNew.onLoadingContent(new ContentManager.DLoading() {
            @Override
            public void onDownload() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((BaseAdapter) mListNews.getAdapter()).notifyDataSetChanged();
                        Log.d("the.dv", "notified() " + mListOfNews.size());
                    }
                });
            }
        });
        contentNew.execute(mListOfNews);
    }

    @Override
    protected void onStop() {
//        try {
//            newDownloader.unRegisterBroadCast(this);
//        } catch (Exception e) {
//
//        }
        super.onStop();
    }


}

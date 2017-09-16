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

import com.google.android.gms.ads.MobileAds;
import com.ms_square.etsyblur.BlurSupport;

import th.data.ContentAdapter;
import th.data.dynamic.Source;
import th.data.dynamic.source._24h.group.category.Category;
import th.view.FeedView;

public class DailyNewsActivity extends Activity {



    private Context mContext;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nav_view;
    private ContentAdapter contentAdapter;

    private FeedView mFeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, getResources().getText(R.string.test_admob_app_id).toString());
        String[] testDevicesIds = new String[]{"ca-app-pub-1510017343836393/2466767868"};
        setContentView(R.layout.activity_tintuchangngay);
        //region create new feed
        mFeedView = (FeedView) findViewById(R.id.list_new_item);
        //endregion
        //region mDrawerLayout

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        BlurSupport.addTo(mDrawerLayout);
        mDrawerLayout.setScrimColor(Color.WHITE);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        nav_view = (NavigationView) findViewById(R.id.nav_view);

        //endregion
        MenuEventHandler();
    }




    public void MenuEventHandler() {
        int item_length = nav_view.getMenu().size();
        mContext = this;
        //region load content through content New
        for (int i = 0; i < item_length; i++) {
            MenuItem item = nav_view.getMenu().getItem(i);
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d("the.dv", "DailyNewActivity.MenuItemClicked");
                    if (getResources().getString(R.string.hot_new).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.TIN_NONG);
                    } else if (getResources().getString(R.string.bong_da).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.BONG_DA);
                    } else if (getResources().getString(R.string.annninh_hinhsu).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.ANNINH_HINHSU);
                    } else if (getResources().getString(R.string.thoi_trang).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.THOI_TRANG);
                    } else if (getResources().getString(R.string.tai_chinh).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.TAI_CHINH);
                    } else if (getResources().getString(R.string.xa_hoi).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.XA_HOI);
                    } else if (getResources().getString(R.string.am_thuc).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.AM_THUC);
                    } else if (getResources().getString(R.string.lam_dep).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.LAM_DEP);
                    } else if (getResources().getString(R.string.phim).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.PHIM);
                    } else if (getResources().getString(R.string.giao_duc).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.GIAO_DUC);
                    } else if (getResources().getString(R.string.ban_tre).equals(item.getTitle())) {
                        mFeedView.loadContent (Source._24H, Category.BAN_TRE);
                    } else if (getResources().getString(R.string.ca_nhac).equals(item.getTitle())) {
                        mFeedView.loadContent(Source._24H, Category.CA_NHAC);
                    }
                    mFeedView.setSelectionAfterHeaderView();
                    mDrawerLayout.closeDrawer(nav_view);
                    return true;
                }
            });
        }
        //endregion

    }

}

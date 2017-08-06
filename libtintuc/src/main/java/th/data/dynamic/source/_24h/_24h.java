package th.data.dynamic.source._24h;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

import th.data.download.DataDownloaded;
import th.data.dynamic.News;
import th.data.dynamic.Source;
import th.data.dynamic.source._24h.group.Group;
import th.data.dynamic.source._24h.group.category.AmThucNews;
import th.data.dynamic.source._24h.group.category.AnNinhHinhSuNews;
import th.data.dynamic.source._24h.group.category.BanTreNews;
import th.data.dynamic.source._24h.group.category.BongDaNews;
import th.data.dynamic.source._24h.group.category.CaNhacNews;
import th.data.dynamic.source._24h.group.category.Category;
import th.data.dynamic.source._24h.group.category.GiaoGiucNews;
import th.data.dynamic.source._24h.group.category.HotNews;
import th.data.dynamic.source._24h.group.category.LamDepNews;
import th.data.dynamic.source._24h.group.category.PhimNews;
import th.data.dynamic.source._24h.group.category.TaiChinhNews;
import th.data.dynamic.source._24h.group.category.ThoiTranNews;
import th.data.dynamic.source._24h.group.category.XaHoiNews;
import th.data.statik.Item;

/**
 * Created by The on 5/1/2017.
 */
public class _24h extends News {

    class GroupOfCategory {
        private HashMap<Category, Group> mListOfGroup;

        GroupOfCategory() {
            mListOfGroup = new HashMap<>();
        }


        public void addCategory(Category cate, Group group) {
            group.setOnDownloadingItem(onGetItems);
            group.setOnDownloadItem(onListingItem);
            group.setOnErr(onErr);
            group.loadNews();
            mListOfGroup.put(cate, group);
        }

        public boolean contains(Category cate) {
            return mListOfGroup.containsKey(cate);
        }
    }

    private GroupOfCategory listOfCategory;

    public _24h() {
        Log.d("the.dv", "load 24h");
        listOfCategory = new GroupOfCategory();
    }


    @Override
    public List<Item> getNewByCategory(Category tinNong) {

        switch (tinNong) {
            case TIN_NONG:
                if (!listOfCategory.contains(Category.TIN_NONG)) {
                    Log.d("the.dv", "load tin nong" +
                            "");
                    listOfCategory.addCategory(Category.TIN_NONG, new HotNews());
                }
                break;
            case BONG_DA:
                if (!listOfCategory.contains(Category.BONG_DA))
                    listOfCategory.addCategory(Category.BONG_DA, new BongDaNews());
                break;
            case ANNINH_HINHSU:
                if (!listOfCategory.contains(Category.ANNINH_HINHSU))
                    listOfCategory.addCategory(Category.ANNINH_HINHSU, new AnNinhHinhSuNews());
                break;
            case THOI_TRANG:
                if (!listOfCategory.contains(Category.THOI_TRANG))
                    listOfCategory.addCategory(Category.THOI_TRANG, new ThoiTranNews());
                break;
            case TAI_CHINH:
                if (!listOfCategory.contains(Category.TAI_CHINH))
                    listOfCategory.addCategory(Category.TAI_CHINH, new TaiChinhNews());
                break;
            case XA_HOI:
                if (!listOfCategory.contains(Category.XA_HOI))
                    listOfCategory.addCategory(Category.XA_HOI, new XaHoiNews());
                break;
            case AM_THUC:
                if (!listOfCategory.contains(Category.AM_THUC))
                    listOfCategory.addCategory(Category.AM_THUC, new AmThucNews());
                break;
            case LAM_DEP:
                if (!listOfCategory.contains(Category.LAM_DEP))
                    listOfCategory.addCategory(Category.LAM_DEP, new LamDepNews());
                break;
            case PHIM:
                if (!listOfCategory.contains(Category.PHIM))
                    listOfCategory.addCategory(Category.PHIM, new PhimNews());
                break;
            case GIAO_DUC:
                if (!listOfCategory.contains(Category.GIAO_DUC))
                    listOfCategory.addCategory(Category.GIAO_DUC, new GiaoGiucNews());
                break;
            case BAN_TRE:
                if (!listOfCategory.contains(Category.BAN_TRE))
                    listOfCategory.addCategory(Category.BAN_TRE, new BanTreNews());
                break;
            case CA_NHAC:
                if (!listOfCategory.contains(Category.CA_NHAC))
                    listOfCategory.addCategory(Category.CA_NHAC, new CaNhacNews());
                break;
            default:
                if (!listOfCategory.contains(Category.TIN_NONG))
                    listOfCategory.addCategory(Category.TIN_NONG, new HotNews());
                break;
        }
        return DataDownloaded.getInstance().getItems(Source._24H.name() + tinNong.name());
    }


}


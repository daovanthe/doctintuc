package th.data.download;

import java.util.List;

import th.data.statik.Item;

/**
 * Created by The on 5/1/2017.
 */
public interface DownloadedListener {
    void get(List<Item> items);
}

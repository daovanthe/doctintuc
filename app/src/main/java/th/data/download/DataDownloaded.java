package th.data.download;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import th.data.statik.Item;

/**
 * Created by The on 5/2/2017.
 */
public class DataDownloaded {
    private DataDownloaded() {
        listItems = new Hashtable<>();
    }

    static class helper {
        static DataDownloaded INSTANCE = new DataDownloaded();
    }

    public static DataDownloaded getInstance() {
        return helper.INSTANCE;
    }

    private Hashtable<String, List<Item>> listItems;


    public void put(String key, Item item) {
        if (!listItems.containsKey(key)) {
            listItems.put(key, new ArrayList<Item>());
        }
        listItems.get(key).add(item);

    }


    public List<Item> getItems(String key) {
        return listItems.get(key);
    }


}

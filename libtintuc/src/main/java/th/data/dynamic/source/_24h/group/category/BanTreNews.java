package th.data.dynamic.source._24h.group.category;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import th.data.download.DataDownloaded;
import th.data.dynamic.Source;
import th.data.statik.Item;

/**
 * Created by The on 5/7/2017.
 */

public class BanTreNews extends th.data.dynamic.source._24h.group.Group {


    public static final String rss = "http://www.24h.com.vn/upload/rss/bantrecuocsong.rss";
    public static final String KEY = Source._24H.name() + Category.BAN_TRE.name();


    protected void loadItems() {
        if (openConnection()) {
            if (rootDocument != null) {
                NodeList items = rootDocument.getElementsByTagName("item");
//                Log.d("the.dv", "start Item");
                for (int temp = 0; temp < items.getLength(); temp++) {
                    Node nodeItem = items.item(temp);
                    Item item = createItemFromNode(nodeItem);
//            if (onDownloadItem != null) {
//                onDownloadItem.get(item);
//            }
//                    if (onDownloadingItem != null) {
//                        onDownloadingItem.get(item);
//                    }
                    DataDownloaded.getInstance().put(KEY, item);
//            mListItem.add(item);
//                    Log.d("the.dv", "item: " + item.toString());
                }
//                Log.d("the.dv", "end Item -> " + items.getLength());
            }
        }
    }


    private Item createItemFromNode(Node nodeItem) {
        Element itemElement = (Element) nodeItem;
        String title = itemElement.getElementsByTagName("title").item(0).getTextContent();
        String link = itemElement.getElementsByTagName("link").item(0).getTextContent();
        String guid = itemElement.getElementsByTagName("guid").item(0).getTextContent();
        String pubDate = itemElement.getElementsByTagName("pubDate").item(0).getTextContent();

        Element descript = (Element) itemElement.getElementsByTagName("description").item(0);
        String context = descript.getTextContent();
        context = StringEscapeUtils.unescapeXml(context);

        String descriptionText = null;
        String imageSrc = null;

        Node aNode = descript.getFirstChild().getNextSibling();
        Element aElement = (Element) aNode;
        descriptionText = aElement.getAttribute("title");

        Node imageNode = aElement.getElementsByTagName("img").item(0);
        Element imageElement = (Element) imageNode;
        imageSrc = imageElement.getAttribute("src");

        Item.Desciption descriptionDSItem = new Item.Desciption();
        descriptionDSItem.setContent(context);
        descriptionDSItem.setImage(imageSrc);

        return new Item(title, link, guid, descriptionDSItem, pubDate);
    }

    public String getRSS() {
        return rss;
    }

    //    @Override
//    public List<Item> loadNews() {
//        loadItems();
//        return mListItem;
//    }
    public void loadNews() {
        loadItems();
    }
}

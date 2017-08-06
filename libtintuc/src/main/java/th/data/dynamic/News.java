package th.data.dynamic;

import java.util.List;

import th.data.download.DownloadingListener;
import th.data.download.GetItemListener;
import th.data.download.IErrorListener;
import th.data.dynamic.source._24h.group.Group;
import th.data.dynamic.source._24h.group.category.Category;
import th.data.statik.Item;

/**
 * Created by The on 5/1/2017.
 */
public abstract class News {

    protected DownloadingListener onListingItem;
    protected GetItemListener onGetItems;
    protected IErrorListener onErr;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private String title, description, link, language, copyright;
    private Image image;

    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void getGroup(Category tinNong) {
    }

    public void setOnListingItem(DownloadingListener onListingItem) {
        this.onListingItem = onListingItem;
    }

    public abstract List<Item> getNewByCategory(Category tinNong);

    public void setOnGetItems(GetItemListener onGetItems) {
        this.onGetItems = onGetItems;
    }

    public void setOnErr(IErrorListener onErr) {
        this.onErr = onErr;
    }


    public static class Image {
        String url, title, link;
    }

}

package th.data.statik;

import android.graphics.Bitmap;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by The on 5/1/2017.
 */
public class Item {
    private String title, link, guid;
    private Desciption description;
    private String publicDate;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    private Bitmap image = null;


    public Item(String title, String link, String guid, Desciption description, String publicDate) {
        this.title = StringEscapeUtils.unescapeXml(title);
        this.link = StringEscapeUtils.unescapeXml(link);
        this.guid = StringEscapeUtils.unescapeXml(guid);
        this.description = description;
        this.publicDate = publicDate;
    }

    public String toString() {
        return "title: " + title + "\n" +
                "link: " + link + "\n" +
                "description: " + description + "\n" +
                "publicDate: " + publicDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Desciption getDescription() {
        return description;
    }

    public void setDescription(Desciption description) {
        this.description = description;
    }

    public String getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
    }

    public static class Desciption {
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String toString() {
            return "image: " + getImage() + "\n" + "content: " + content;
        }

        private String content;
    }
}

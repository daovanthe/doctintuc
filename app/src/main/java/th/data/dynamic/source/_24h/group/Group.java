package th.data.dynamic.source._24h.group;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import th.data.download.DownloadingListener;
import th.data.download.GetItemListener;
import th.data.download.IErrorListener;
import th.data.dynamic.source._24h.group.category.Category;
import th.data.statik.Item;

/**
 * Created by The on 5/1/2017.
 */
public abstract class Group {
    private DomParser parser;
    private String rss;
    private Category category;
    private List<Item> listItems;
    protected Document rootDocument;
    protected DownloadingListener onDownloadItem;
    protected GetItemListener onDownloadingItem;
    private IErrorListener onErr;

    protected Document getDocument() {
        return rootDocument;
    }


    public Group() {

    }


    protected boolean openConnection() {
        URL oracle = null;
        try {
            oracle = new URL(getRSS());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            if (onErr != null) {
                onErr.onError();
                return false;
            }
        }
        URLConnection yc = null;
        try {
            yc = oracle.openConnection();
            yc.setConnectTimeout(1000);
        } catch (IOException e) {
            e.printStackTrace();
            if (onErr != null) {
                onErr.onError();
                return false;
            }
        }
        StringBuffer bufferReader = new StringBuffer();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
//                System.out.println(inputLine);
                bufferReader.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            if (onErr != null) {
                onErr.onError();
                return false;
            }
        }
        parser = new DomParser();
        rootDocument = parser.getData(bufferReader.toString().replace("<![CDATA[", "").replace("]]>", ""));
        bufferReader.setLength(0);
        return true;
    }


    public void setRss(String rss) {
        this.rss = rss;
    }

    public abstract String getRSS();

    public abstract void loadNews();


    public void setOnDownloadItem(DownloadingListener onDownloadItem) {
        this.onDownloadItem = onDownloadItem;
    }

    public void setOnDownloadingItem(GetItemListener onDownloadingItem) {
        this.onDownloadingItem = onDownloadingItem;
    }

    public void setOnErr(IErrorListener onErr) {
        this.onErr = onErr;
    }

    public static class DomParser {
        DocumentBuilder builder = null;

        DomParser() {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

        private Document getData(String data) {
            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(data);
            ByteArrayInputStream input = null;
            try {
                input = new ByteArrayInputStream(
                        xmlStringBuilder.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                return builder.parse(input);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}

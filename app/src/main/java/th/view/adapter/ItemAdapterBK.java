package th.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import th.data.download.ImageDownload;
import th.data.statik.Item;
import th.tintuchangngay.DailyNewsDetailsActivity;
import th.tintuchangngay.R;

/**
 * Created by The on 5/1/2017.
 */
public class ItemAdapterBK extends BaseAdapter {


    List<Item> data;
    Context mContext;
    LayoutInflater layoutInflater;


    public ItemAdapterBK(List data, Context context) {
        super();
        this.data = data;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_news, null);
            viewHolder = new ViewHolder();
            viewHolder.title_text_view = (TextView) convertView.findViewById(R.id.title_text);
            viewHolder.content_description_view = (TextView) convertView.findViewById(R.id.content_description_tx);
            viewHolder.pubTime_text_view = (TextView) convertView.findViewById(R.id.pubTime_txt);
//            viewHolder.background_image = (ImageView) convertView.findViewById(R.id.background_image);
            viewHolder.desciption_img_view = (ImageView) convertView.findViewById(R.id.desciption_img);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Item item = data.get(position);
        viewHolder.title_text_view.setText(item.getTitle());
        viewHolder.content_description_view.setText(item.getDescription().getContent());
        viewHolder.pubTime_text_view.setText(item.getPublicDate());
//        desciption_img_view.setImageURI(Uri.parse(item.getDescription().getImage()));
//
        viewHolder.content_description_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mbundle = new Bundle();
                mbundle.putString("link", item.getLink());
                Intent intent = new Intent(mContext, DailyNewsDetailsActivity.class);
                intent.putExtras(mbundle);
                mContext.startActivity(intent);
            }
        });


//        desciption_img_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("CLICK");
//            }
//        });

        if (item.getImage() == null) {
            try {
                final URL newurl = new URL(item.getDescription().getImage());
                ImageDownload imageDownload = new ImageDownload();


                imageDownload.setDownloading(new ImageDownload.IDownloading() {
                    @Override
                    public Bitmap downloading() throws IOException {
                        Bitmap bm = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                        return bm;
                    }
                });

                imageDownload.setDownloadEnd(new ImageDownload.IDownloadEnd() {
                    @Override
                    public void end(final Bitmap bm) throws IOException {
                        viewHolder.desciption_img_view.setImageBitmap(bm);
//                        viewHolder.background_image.setImageBitmap(bm);/**/

                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    data.get(position).setImage(bm);
                                } catch (Exception e) {
                                    Log.e("the.dv", e.toString());
                                }
                            }
                        });
                    }
                });


                imageDownload.execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            viewHolder.desciption_img_view.setImageBitmap(item.getImage());
//            viewHolder.background_image.setImageBitmap(item.getImage());
        }

        convertView.setTag(viewHolder);
//        txt.setText(data[position]);
        return convertView;
    }


    static class ViewHolder {
        TextView title_text_view;
        TextView content_description_view;
        TextView pubTime_text_view;
        ImageView desciption_img_view;
        public ImageView background_image;
    }


    static class AdviewHolder {
        AdView adView;
    }


}



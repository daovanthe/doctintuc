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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
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
public class ItemAdapter extends BaseAdapter {
    private List<Item> data;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private final int POSITION = 10;

    public ItemAdapter(List data, Context context) {
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

    private AdviewHolder adviewHolder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (POSITION != position) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_news, null);
                TinTucViewHolder tintucViewHolder = new TinTucViewHolder();
                tintucViewHolder.title_text_view = (TextView) convertView.findViewById(R.id.title_text);
                tintucViewHolder.content_description_view = (TextView) convertView.findViewById(R.id.content_description_tx);
                tintucViewHolder.pubTime_text_view = (TextView) convertView.findViewById(R.id.pubTime_txt);
//            tintucViewHolder.background_image = (ImageView) convertView.findViewById(R.id.background_image);
                tintucViewHolder.desciption_img_view = (ImageView) convertView.findViewById(R.id.desciption_img);
                viewHolder = tintucViewHolder;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (viewHolder instanceof TinTucViewHolder) {
                final TinTucViewHolder tintucViewHolder = (TinTucViewHolder) viewHolder;
                final Item item = data.get(position);
                tintucViewHolder.title_text_view.setText(item.getTitle());
                tintucViewHolder.content_description_view.setText(item.getDescription().getContent());
                tintucViewHolder.pubTime_text_view.setText(item.getPublicDate());
//        desciption_img_view.setImageURI(Uri.parse(item.getDescription().getImage()));
//
                tintucViewHolder.content_description_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle mbundle = new Bundle();
                        mbundle.putString("link", item.getLink());
                        Intent intent = new Intent(mContext, DailyNewsDetailsActivity.class);
                        intent.putExtras(mbundle);
                        mContext.startActivity(intent);
                    }
                });
                tintucViewHolder.title_text_view.setOnClickListener(new View.OnClickListener() {
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
                                tintucViewHolder.desciption_img_view.setImageBitmap(bm);
//                        tintucViewHolder.background_image.setImageBitmap(bm);/**/

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
                    tintucViewHolder.desciption_img_view.setImageBitmap(item.getImage());
//            tintucViewHolder.background_image.setImageBitmap(item.getImage());
                }
            } else {
                if (position == POSITION)
                    return convertView;
                else return null;
            }
//        txt.setText(data[position]);

            return convertView;

        } else {

            if (adviewHolder == null) {
                adviewHolder = new AdviewHolder();

                Log.d("the.dv", " convertView null + potition: " + position);
                AdView adView = new AdView(mContext);
                adView.setAdSize(AdSize.FLUID);
                adView.setAdUnitId("ca-app-pub-1510017343836393/2466767868");
//                AdRequest adRequest = new AdRequest.Builder().addTestDevice("96BA1B44FF42703C0FBBE67ADD9E9DF1").build();
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                Log.d("the.dv", adRequest.isTestDevice(mContext) + "");
                adviewHolder.adView = adView;
            }
            convertView = adviewHolder.adView;
            Log.d("the.dv", " convertView  + potition: " + position);
            return convertView;
        }
    }


    class ViewHolder {
    }

    class TinTucViewHolder extends ViewHolder {
        TextView title_text_view;
        TextView content_description_view;
        TextView pubTime_text_view;
        ImageView desciption_img_view;
        public ImageView background_image;
    }

    class AdviewHolder extends ViewHolder {
        AdView adView;
    }
}



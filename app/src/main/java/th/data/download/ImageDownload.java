package th.data.download;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by The on 5/2/2017.
 */
public class ImageDownload extends AsyncTask<String, String, String> {
    public static interface IDownloadEnd {
        public void end(Bitmap bm) throws IOException;
    }

    public static interface IDownloading {
        public Bitmap downloading() throws IOException;
    }

    private IDownloadEnd mDownloadEnd;
    private IDownloading mIDownloading;

    public void setDownloadEnd(IDownloadEnd mDownloadEnd) {
        this.mDownloadEnd = mDownloadEnd;
    }

    public void setDownloading(IDownloading mDownloading) {
        this.mIDownloading = mDownloading;
    }

    private Bitmap mBitmap;

    @Override
    protected String doInBackground(String... params) {
        if (mIDownloading != null) {
            try {
                mBitmap = mIDownloading.downloading();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (mDownloadEnd != null) {
            try {
                mDownloadEnd.end(mBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onPostExecute(s);
    }
}

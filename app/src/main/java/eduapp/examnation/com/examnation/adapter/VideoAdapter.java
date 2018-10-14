package eduapp.examnation.com.examnation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import eduapp.examnation.com.examnation.ChapterDetailsActivity;
import eduapp.examnation.com.examnation.PlayYoutubeActivity;
import eduapp.examnation.com.examnation.R;
import eduapp.examnation.com.examnation.helper.Utility;
import eduapp.examnation.com.examnation.model.Video;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {



    private static final String TAG = "ChapterLineAdepter";

    private final List<Video> videoList ;
    private Context context;
    String cue;

    public VideoAdapter(List<Video> videoLists) {
        this.videoList = videoLists;
    }

    public class VideoHolder extends RecyclerView.ViewHolder  {

        public TextView title;
        public TextView chapterName;
        public ImageView imageView;
        private CardView cardView;

        public VideoHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.videoSNO);
            chapterName = (TextView) itemView.findViewById(R.id.videoName);
            imageView = (ImageView) itemView.findViewById(R.id.videoLink);
            cardView = (CardView) itemView.findViewById(R.id.videoCradView);

        }

    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context =  parent.getContext();
        return new VideoAdapter.VideoHolder(LayoutInflater.from(context)
                .inflate(R.layout.video_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, final int position) {


        String link = videoList.get(position).getLink();
        try{
            cue = link.substring(link.indexOf("v=")+2);
        }catch (Exception e){
            cue = "5xVh-7ywKpE";
        }


        Uri uri = new Uri.Builder().path("https://i.ytimg.com/vi/"+cue+"/mqdefault.jpg").build();
        holder.title.setText( videoList.get(position).getName());

        ImageLoadTask imageLoadTask = new ImageLoadTask("https://i.ytimg.com/vi/"+cue+"/mqdefault.jpg",holder.imageView);
        imageLoadTask.execute();

        holder.title.setText(String.format(Locale.getDefault(), "%s",
                (position+1)<10?"0"+(position+1):position+1
        ));

        holder.chapterName.setText(String.format(Locale.getDefault(), "%s",
                videoList.get(position).getName()
        ));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + videoList.get(position));
                Toast.makeText(context, videoList.get(position).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,PlayYoutubeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("ID", videoList.get(position).getId());
                bundle.putString("NAME", videoList.get(position).getName());
                bundle.putString("LINK", cue);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            URL urlConnection;
            HttpURLConnection connection = null;
            try {
                urlConnection = new URL(url);
                connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(connection!=null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}

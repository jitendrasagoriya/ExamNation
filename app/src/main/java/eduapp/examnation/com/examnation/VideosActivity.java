package eduapp.examnation.com.examnation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eduapp.examnation.com.examnation.adapter.ChapterAdapter;
import eduapp.examnation.com.examnation.adapter.VideoAdapter;
import eduapp.examnation.com.examnation.http.HttpManager;
import eduapp.examnation.com.examnation.listener.AppBarStateChangeListener;
import eduapp.examnation.com.examnation.model.Chapter;
import eduapp.examnation.com.examnation.model.Video;

public class VideosActivity extends AppCompatActivity {

    private long id;
    private String subjectName;
    private String chapterName;
    private AppBarLayout appBarLayout ;
    private ProgressDialog progress;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private TextView chapterNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("ID");
        chapterName = extras.getString("CHAPTERNAME");
        subjectName = extras.getString("SUBJECTNAME");



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_video);
        setSupportActionBar(toolbar);
        toolbar.setTitle("  ");

        //Get recycle item
        recyclerView = (RecyclerView)findViewById(R.id.recycleVideo);

        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        //SET CHAPTER NAME IN VIEW activity_video_header_name
        chapterNameTextView = findViewById(R.id.activity_video_header_name);
        chapterNameTextView.setText(chapterName);

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_video);
        collapsingToolbar.setTitle("  ");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = findViewById(R.id.app_bar_video);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state.name().equalsIgnoreCase(State.COLLAPSED.name())){
                    collapsingToolbar.setTitle( chapterName );
                }else{
                    collapsingToolbar.setTitle("  ");
                }
            }
        });

        //Get instance on progress bar
        progress = PrograssBarBuilder.getInstance(this);

        VideosActivity.GetVideoListAsynTask videoListAsynTask = new VideosActivity.GetVideoListAsynTask(this, id);
        videoListAsynTask.execute();

    }




    public class GetVideoListAsynTask extends AsyncTask<String,String,List<Video>>{

        private Context context;
        private long id;

        public GetVideoListAsynTask(Context context, long id) {
            this.id = id;
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            progress.show();
        }

        @Override
        protected void onPostExecute(List<Video> videoList) {
            progress.dismiss();
            if(videoList != null) {
                videoAdapter = new VideoAdapter(videoList);
                recyclerView.setAdapter(videoAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                progress.dismiss();
            }else{
                Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Video> doInBackground(String... strings) {
            List<Video> videoList = new ArrayList<>();
            String result =  HttpManager.getData(DeveloperKey.BASE_URL+"video/chapter/"+id);

            try {
                Video video;
                JSONObject obj = new JSONObject(result);
                JSONArray jsonArray = obj.getJSONArray("content");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    video = new Video();
                    video.setName(jsonObject.getString("name"));
                    video.setId(jsonObject.getLong("id"));
                    video.setLink(jsonObject.getString("link"));
                    video.setDec(jsonObject.getString("description"));
                    videoList.add(video);
                }
                return  videoList;
            }catch (JSONException jsonException){
                jsonException.printStackTrace();
            }finally {

            }
            return  videoList;
        }
    }
}

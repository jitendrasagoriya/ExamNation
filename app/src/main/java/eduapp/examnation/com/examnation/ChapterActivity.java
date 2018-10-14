package eduapp.examnation.com.examnation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import eduapp.examnation.com.examnation.adapter.ChapterAdapter;
import eduapp.examnation.com.examnation.adapter.RecyclerAdapter;
import eduapp.examnation.com.examnation.database.DatabaseHelper;
import eduapp.examnation.com.examnation.helper.Utility;
import eduapp.examnation.com.examnation.http.HttpManager;
import eduapp.examnation.com.examnation.model.Chapter;

public class ChapterActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ChapterAdapter chapterAdapter;
    private ProgressDialog progress;
    private DatabaseHelper databaseHelper;


    private Long id = 0l;
    private String classz = "";
    private String name = "";
    private String imgName;

    private List<Chapter> chapters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        //Get parameter values from previous activity
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("ID");
        classz = extras.getString("CLASS");
        name = extras.getString("NAME");




        //Get ToolBar
        recyclerView = (RecyclerView)findViewById(R.id.recycleChapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_chapter);
        collapsingToolbar.setTitle(name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = collapsingToolbar.findViewById(R.id.imageChemistry);

        imageView.setImageResource(Utility.getBackGroungImageName(Integer.parseInt(id+"")));

        //Get instance on progress bar
        progress = PrograssBarBuilder.getInstance(this);

        //Fetch data from Database.
        databaseHelper = new DatabaseHelper(this);
        chapters =  databaseHelper.getAllChapters(id,classz);
       // databaseHelper.dropTable("");

        if(chapters == null || chapters.isEmpty()) {
            GetChapterListAsynTask chapterListAsynTask = new GetChapterListAsynTask(this, Long.toString(id), classz);
            chapterListAsynTask.execute();
        }else {
            chapterAdapter = new ChapterAdapter(chapters);
            recyclerView.setAdapter(chapterAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            progress.dismiss();
        }
    }




   /* private void setupRecycler(List<Chapter> chapters) {

        // Configurando o gerenciador de layout para ser uma lista.

        MyLinearLayoutManager layoutManager= new MyLinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);

        chapterAdapter = new ChapterAdapter(chapters);
        mRecyclerView.setAdapter(chapterAdapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }*/



    public class  GetChapterListAsynTask extends AsyncTask<String,String,List<Chapter>> {

        private  String subject;

        private String classz;

        private Context context;

        public GetChapterListAsynTask() {
        }

        public GetChapterListAsynTask(Context context, String subject, String classz) {
            this.subject = subject;
            this.classz = classz;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progress.show();
        }

        @Override
        protected void onPostExecute(List<Chapter> chapters) {
            if(chapters != null) {
                chapterAdapter = new ChapterAdapter(chapters);
                recyclerView.setAdapter(chapterAdapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                progress.dismiss();
            }else{
                Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }

        @Override
        protected List<Chapter> doInBackground(String... strings) {
            List<Chapter> chapters = new ArrayList<>();
            String result =  HttpManager.getData(DeveloperKey.BASE_URL+"chapter/subject/"+this.subject+"/class/"+this.classz+"?size=200");

            try {
                Chapter chapter;
                JSONObject obj = new JSONObject(result);
                JSONArray jsonArray = obj.getJSONArray("content");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    chapter = new Chapter();
                    chapter.setName(jsonObject.getString("name"));
                    chapter.setId(jsonObject.getLong("id"));
                    chapter.setSubjectId(jsonObject.getLong("subject"));
                    chapter.setClassz(jsonObject.getString("classz"));
                    chapter.setVideoCount(jsonObject.getInt("videoCount"));
                    chapter.setQuestionCount(jsonObject.getInt("questionCount"));
                    chapter.setConceptCount(jsonObject.getInt("conceptCount"));
                    chapter.setSequence(jsonObject.getInt("sequence"));

                    //Insert into database
                    databaseHelper.insertChapter(chapter);

                    chapters.add(chapter);
                }

                return chapters;
            }catch (JSONException jsonException){
                jsonException.printStackTrace();
                return null;
            }finally {

            }

        }
    }
}

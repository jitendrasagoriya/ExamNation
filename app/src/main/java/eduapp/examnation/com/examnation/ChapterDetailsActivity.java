package eduapp.examnation.com.examnation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import eduapp.examnation.com.examnation.listener.AppBarStateChangeListener;

public class ChapterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarLayout appBarLayout ;
    private long id;
    private String chapterName;
    private String subjectName;

    private TextView chapterNameTextView;
    private TextView subjectNameTextView;

    private CardView videoCardView;
    private CardView questionCardView;
    private CardView conceptCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_details);

        //Get parameter values from previous activity
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("ID");
        chapterName = extras.getString("NAME");
        subjectName = extras.getString("CHAPTERNAME");

        chapterNameTextView = findViewById(R.id.chapter_detail_chapter_name);
        chapterNameTextView.setText(chapterName);

        subjectNameTextView = findViewById(R.id.chapter_detail_subject_name);
        subjectNameTextView.setText(subjectName);


        //Add click listner
        videoCardView = findViewById(R.id.cardViewVideo);
        videoCardView.setOnClickListener(this);

        questionCardView = findViewById(R.id.cardViewQuestion);
        questionCardView.setOnClickListener(this);

        conceptCardView = findViewById(R.id.cardViewConcept);
        conceptCardView.setOnClickListener(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chapter_datails);
        setSupportActionBar(toolbar);
        toolbar.setTitle("  ");

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_chapter_datails);

        collapsingToolbar.setTitle("  ");

        appBarLayout = findViewById(R.id.app_bar_chapter_datails);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if(state.name().equalsIgnoreCase(State.COLLAPSED.name())){
                    collapsingToolbar.setTitle(chapterName);
                    collapsingToolbar.animate();
                }else{
                    collapsingToolbar.setTitle("  ");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        int id = view.getId();
        Bundle bundle = new Bundle();

        if(id == R.id.cardViewVideo){
            intent = new Intent(this,VideosActivity.class);
        }else if (id == R.id.cardViewConcept){
            intent = new Intent(this,VideosActivity.class);
        }else if (id == R.id.cardViewQuestion){
            intent = new Intent(this,VideosActivity.class);
        }
        bundle.putLong("ID",this.id);
        bundle.putString("CHAPTERNAME",this.chapterName);
        bundle.putString("SUBJECTNAME",this.subjectName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

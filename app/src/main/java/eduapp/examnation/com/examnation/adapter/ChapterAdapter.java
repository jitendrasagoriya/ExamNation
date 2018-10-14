package eduapp.examnation.com.examnation.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import eduapp.examnation.com.examnation.ChapterDetailsActivity;
import eduapp.examnation.com.examnation.R;
import eduapp.examnation.com.examnation.helper.Utility;
import eduapp.examnation.com.examnation.model.Chapter;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterHolder> {

    private static final String TAG = "ChapterLineAdepter";

    private final List<Chapter> chapterList ;

    private Context context;



    public ChapterAdapter(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    public class ChapterHolder extends RecyclerView.ViewHolder  {

        public TextView title;
        public TextView chapterName;
        public ProgressBar progressBar;
        public CardView cardView;

        public ChapterHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.sNo);
            chapterName = (TextView) itemView.findViewById(R.id.chapterName);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            cardView = (CardView) itemView.findViewById(R.id.chapterCardView);

        }

    }

    @NonNull
    @Override
    public ChapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context =  parent.getContext();
        return new ChapterHolder(LayoutInflater.from(context)
                .inflate(R.layout.chapter_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterHolder holder, final int position) {

        holder.title.setText(String.format(Locale.getDefault(), "%s",
                (position+1)<10?"0"+(position+1):position+1
        ));

        holder.chapterName.setText(String.format(Locale.getDefault(), "%s",
                chapterList.get(position).getName()
        ));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + chapterList.get(position));
                Toast.makeText(context, chapterList.get(position).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,ChapterDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("ID", chapterList.get(position).getId());
                bundle.putString("CHAPTERNAME", chapterList.get(position).getName());
                bundle.putString("SUBJECTNAME", Utility.getSucjectNameById(chapterList.get(position).getSubjectId()) );
                bundle.putLong("SUBJECTID", chapterList.get(position).getSubjectId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList != null ? chapterList.size() : 0;
    }
}

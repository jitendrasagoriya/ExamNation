package eduapp.examnation.com.examnation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eduapp.examnation.com.examnation.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    Context context;
    LayoutInflater inflater;
    ArrayList<String> items = new ArrayList<>();


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public RecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        for (int i = 1; i < 30; i++) {
            items.add("City " + i);
        }
    }


    @NonNull
    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.card_item, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerViewHolder holder, int position) {
        holder.tv1.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
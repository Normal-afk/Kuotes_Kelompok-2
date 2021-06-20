package com.example.kuotes.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuotes.R;
import com.example.kuotes.entity.DataQuotes;
import com.example.kuotes.view.MainContact;

import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.viewHolder> {

    private Context context;
    private List<DataQuotes> list;
    private MainContact.favoView fView;

    public QuotesAdapter(Context context, List<DataQuotes> list, MainContact.favoView fView) {
        this.context = context;
        this.list = list;
        this.fView = fView;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final DataQuotes dataQuotes = list.get(position);
        holder.tvQuotes.setText(dataQuotes.getQuote());
        holder.tvAuthor.setText(dataQuotes.getAuthor());
        holder.ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = getSystemService(context,ClipboardManager.class);
                ClipData clip = ClipData.newPlainText("text", holder.tvQuotes.getText().toString()+" - "+holder.tvAuthor.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context,"Copy to Clipboard",Toast.LENGTH_SHORT).show();
            }
        });
        holder.ivFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fView.deleteData(dataQuotes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuotes, tvAuthor;
        private ImageView ivCopy, ivFavo;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuotes = itemView.findViewById(R.id.item_list_quotes);
            tvAuthor = itemView.findViewById(R.id.item_list_author);
            ivCopy = itemView.findViewById(R.id.item_list_copy);
            ivFavo = itemView.findViewById(R.id.item_list_favo);
        }
    }
}

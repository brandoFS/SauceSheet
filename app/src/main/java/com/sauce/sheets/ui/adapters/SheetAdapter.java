package com.sauce.sheets.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sauce.sheets.R;

/**
 * Created by brandomadden on 8/4/17.
 */

public class SheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AdapterCallback adapterCallback;
    private Context context;


    public SheetAdapter(AdapterCallback callback, Context context) {
        this.adapterCallback = callback;
        this.context = context;
    }

    public interface AdapterCallback {
        void editCell(String content, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_cell, parent, false);
        return new CellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.editCell(viewHolder.cell.getText().toString(), position);
            }
        });
    }

    public void setContent() {

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 64;
    }

    private static class CellViewHolder extends RecyclerView.ViewHolder {
        TextView cell;
        TextView rowColumnLabel;

        public CellViewHolder(View itemView) {
            super(itemView);
            rowColumnLabel = (TextView) itemView.findViewById(R.id.cell_label_textview);
            cell = (TextView) itemView.findViewById(R.id.cell_body_textview);
        }


    }
}

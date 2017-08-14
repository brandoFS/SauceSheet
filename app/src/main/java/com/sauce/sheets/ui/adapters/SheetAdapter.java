package com.sauce.sheets.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sauce.sheets.R;

import java.util.ArrayList;

/**
 * Created by brandomadden on 8/4/17.
 */

public class SheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AdapterCallback adapterCallback;
    private Context context;
    private ArrayList<String> cellData = new ArrayList<>();

    public SheetAdapter(AdapterCallback callback, Context context, int size) {
        adapterCallback = callback;
        this.context = context;
        for (int i = 0; i < size; i++) {
            cellData.add(i, "");
        }
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
        viewHolder.cell.setText(cellData.get(position));
        viewHolder.cell.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_radius_blackborder));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.findViewById(R.id.cell_body_textview).setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_radius_redborder));
                adapterCallback.editCell(viewHolder.cell.getText().toString(), position);
            }
        });
    }

    public void setCellContent(int position, String content) {
        cellData.set(position, content);
        notifyDataSetChanged();
    }

    public void AddRowOfCells(int columns) {
        for(int i = 0; i < columns; i++){
            cellData.add("");
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cellData.size();
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

package com.sauce.sheets.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sauce.sheets.R;
import com.sauce.sheets.SheetsApp;
import com.sauce.sheets.ui.adapters.SheetAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements SheetAdapter.AdapterCallback {


    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_cell_edit_area)
    EditText mMainCellEditArea;

    private SheetAdapter mSheetAdapter;
    private GridLayoutManager mGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SheetsApp) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.mSheetAdapter = new SheetAdapter(this, this);
        mGridLayoutManager = new GridLayoutManager(this, 8);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mSheetAdapter);

    }


    @Override
    public void editCell(String content, int position) {
        mMainCellEditArea.setText(content);
        Toast.makeText(this, "position = " + position, Toast.LENGTH_SHORT).show();

    }

    @OnClick({R.id.main_add_column_button, R.id.main_add_row_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_add_column_button:
                mGridLayoutManager.setSpanCount(mGridLayoutManager.getSpanCount()+1);
                break;
            case R.id.main_add_row_button:
                break;
        }
    }
}

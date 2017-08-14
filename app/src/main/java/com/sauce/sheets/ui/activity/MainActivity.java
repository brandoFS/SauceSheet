package com.sauce.sheets.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sauce.sheets.R;
import com.sauce.sheets.SheetsApp;
import com.sauce.sheets.constants.Constants;
import com.sauce.sheets.model.CellData;
import com.sauce.sheets.model.EditStack;
import com.sauce.sheets.ui.adapters.SheetAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements SheetAdapter.AdapterCallback {


    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_cell_edit_area)
    EditText mMainCellEditArea;
    @BindView(R.id.main_header_container)
    LinearLayout mMainHeaderContainer;

    private SheetAdapter mSheetAdapter;
    private GridLayoutManager mGridLayoutManager;
    static int positionToEdit = -1;
    private EditStack mEditStack = new EditStack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SheetsApp) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSheetAdapter = new SheetAdapter(this, this, Constants.INTIAL_SIZE);
        mGridLayoutManager = new GridLayoutManager(this, Constants.NUM_OF_COLUMNS, GridLayoutManager.VERTICAL, false);
        //mGridLayoutManager = new GridLayoutManager(this, Constants.NUM_OF_COLUMNS, GridLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mSheetAdapter);

        SheetsTextWatcher sheetsTextWatcher = new SheetsTextWatcher();
        mMainCellEditArea.addTextChangedListener(sheetsTextWatcher);
        mMainCellEditArea.setOnFocusChangeListener(sheetsTextWatcher);
        mMainCellEditArea.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    mMainCellEditArea.clearFocus();
                    mMainHeaderContainer.requestFocus();
                    mMainCellEditArea.setEnabled(false);
                    mEditStack.pushEditonStack(new CellData(positionToEdit, mMainCellEditArea.getText().toString().trim()));
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mMainCellEditArea.getApplicationWindowToken(), 0);
                }
                return false;
            }
        });

    }

    public class SheetsTextWatcher implements TextWatcher, View.OnFocusChangeListener {


        public SheetsTextWatcher() {

        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mSheetAdapter.setCellContent(positionToEdit, s.toString().trim());
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                System.out.println(" ========== HERE ==============  ");

                mMainCellEditArea.setEnabled(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mMainCellEditArea.getApplicationWindowToken(), 0);
            } else {
                System.out.println(" ========== HERE 22 ==============  ");

            }
        }
    }


    @Override
    public void editCell(String content, int position) {
        positionToEdit = position;
        mMainCellEditArea.setEnabled(true);
        mMainCellEditArea.requestFocus();
        mMainCellEditArea.setText(content);
        mMainCellEditArea.setSelection(mMainCellEditArea.getText().length());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mMainCellEditArea, InputMethodManager.SHOW_IMPLICIT);
        // mSheetAdapter.
        Toast.makeText(this, "position = " + position, Toast.LENGTH_SHORT).show();

    }

    @OnClick({R.id.main_add_column_button, R.id.main_add_row_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_add_column_button:
                mGridLayoutManager.setSpanCount(mGridLayoutManager.getSpanCount() + 1);
                break;
            case R.id.main_add_row_button:
                mSheetAdapter.AddRowOfCells(mGridLayoutManager.getSpanCount());
                break;
        }
    }
}

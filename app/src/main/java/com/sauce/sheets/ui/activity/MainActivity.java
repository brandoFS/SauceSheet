package com.sauce.sheets.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.sauce.sheets.R;
import com.sauce.sheets.SheetsApp;
import com.sauce.sheets.constants.Constants;
import com.sauce.sheets.managers.PersistentDataManager;
import com.sauce.sheets.model.CellData;
import com.sauce.sheets.model.EditStack;
import com.sauce.sheets.ui.adapters.SheetAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements SheetAdapter.AdapterCallback {

    @Inject
    PersistentDataManager mPersistentDataManager;

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_cell_edit_area)
    EditText mMainCellEditArea;
    @BindView(R.id.main_header_container)
    LinearLayout mMainHeaderContainer;
    @BindView(R.id.action_menu)
    FloatingActionsMenu mActionMenu;

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
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mMainCellEditArea.getApplicationWindowToken(), 0);
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.would_you_like_to_exit)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .show();
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
                //TODO not working :(
                mMainCellEditArea.setEnabled(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mMainCellEditArea.getApplicationWindowToken(), 0);
            } else {

            }
        }
    }


    @Override
    public void editCell(String content, int position) {
        mEditStack.pushEditonStack(new CellData(position, content));
        positionToEdit = position;
        mMainCellEditArea.setEnabled(true);
        mMainCellEditArea.requestFocus();
        mMainCellEditArea.setText(content);
        mMainCellEditArea.setSelection(mMainCellEditArea.getText().length());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mMainCellEditArea, InputMethodManager.SHOW_IMPLICIT);

    }

    private void saveCurrentSheet() {
        if (mSheetAdapter != null) {
            mPersistentDataManager.saveArray(Constants.SAVE_KEY, mSheetAdapter.getCellDataArray());
            Toast.makeText(this, getString(R.string.sheet_saved), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadLastSheet() {
        String[] savedData = mPersistentDataManager.getArray(Constants.SAVE_KEY);
        List<String> loadedData = new ArrayList<>();
        Collections.addAll(loadedData, savedData);
        mSheetAdapter = new SheetAdapter(this, this, loadedData);
        mGridLayoutManager = new GridLayoutManager(this, Constants.NUM_OF_COLUMNS, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mSheetAdapter);
        mEditStack.clearStack();
        Toast.makeText(this, getString(R.string.Last_Saved_Sheet_Loaded), Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.main_add_column_button, R.id.main_add_row_button, R.id.action_save, R.id.action_clear, R.id.action_load, R.id.action_undo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_add_column_button:
                mGridLayoutManager.setSpanCount(mGridLayoutManager.getSpanCount() + 1);
                mSheetAdapter.AddRowOfCells((mGridLayoutManager.getSpanCount() * 2) - 1);
                mEditStack.pushEditonStack(new CellData(-1, Constants.ADD_COLUMN));
                break;
            case R.id.main_add_row_button:
                mSheetAdapter.AddRowOfCells(mGridLayoutManager.getSpanCount());
                mEditStack.pushEditonStack(new CellData(-1, Constants.ADD_ROW));
                break;
            case R.id.action_save:
                saveCurrentSheet();
                mActionMenu.collapse();
                break;
            case R.id.action_clear:
                mActionMenu.collapse();
                mSheetAdapter = new SheetAdapter(this, this, Constants.INTIAL_SIZE);
                mGridLayoutManager = new GridLayoutManager(this, Constants.NUM_OF_COLUMNS, GridLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(mGridLayoutManager);
                mRecyclerView.setAdapter(mSheetAdapter);
                mEditStack.clearStack();
                break;
            case R.id.action_load:
                loadLastSheet();
                mActionMenu.collapse();
                break;
            case R.id.action_undo:
                if (!mEditStack.isStackEmpty()) {
                    CellData undoEdit = mEditStack.popLastEdit();
                    if (undoEdit.getCellContent().matches(Constants.ADD_COLUMN)) {
                        if (mGridLayoutManager.getSpanCount() > 8) {
                            mSheetAdapter.removeRowOfCells((mGridLayoutManager.getSpanCount() * 2) - 1);
                            mGridLayoutManager.setSpanCount(mGridLayoutManager.getSpanCount() - 1);
                        }
                    } else if (undoEdit.getCellContent().matches(Constants.ADD_ROW)) {
                        mSheetAdapter.removeRowOfCells(mGridLayoutManager.getSpanCount());
                    } else {
                        mSheetAdapter.setCellContent(undoEdit.getIndex(), undoEdit.getCellContent());
                    }
                } else {
                    Toast.makeText(this, getString(R.string.nothing_to_undo), Toast.LENGTH_SHORT).show();
                }
                mActionMenu.collapse();
                break;
        }
    }

}

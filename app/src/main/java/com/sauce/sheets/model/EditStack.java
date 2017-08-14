package com.sauce.sheets.model;

import java.util.ArrayList;

/**
 * Created by brandomadden on 8/13/17.
 */

public class EditStack {

    private ArrayList<CellData> editStack = new ArrayList<>();


    public void pushEditonStack(CellData cellData) {
        editStack.add(cellData);
    }

    public CellData popLastEdit(){
        if(!editStack.isEmpty()) {
            CellData lastEdit = editStack.get(editStack.size() - 1);
            editStack.remove(editStack.size() - 1);
            return  lastEdit;
        } else {
            return null;
        }
    }

    boolean isStackEmpty() {
        return editStack.isEmpty();
    }


}

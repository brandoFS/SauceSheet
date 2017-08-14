package com.sauce.sheets.model;

/**
 * Created by brandomadden on 8/13/17.
 */

public class CellData {

    int index;
    String cellContent;

    public CellData(int position, String data) {
        this.index = position;
        this.cellContent = data;
    }

}

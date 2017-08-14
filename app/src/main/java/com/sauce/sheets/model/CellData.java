package com.sauce.sheets.model;

/**
 * Created by brandomadden on 8/13/17.
 */

public class CellData {

    private int index;
    private String cellContent;

    public CellData(int position, String data) {
        this.index = position;
        this.cellContent = data;
    }

    public int getIndex(){
        return index;
    }

    public String getCellContent(){
        return  cellContent;
    }

}

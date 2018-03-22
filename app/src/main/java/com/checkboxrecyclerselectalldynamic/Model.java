package com.checkboxrecyclerselectalldynamic;

import java.io.Serializable;

/**
 * Created by G2evolution on 12/5/2017.
 */

public class Model implements Serializable {

    private String itemName;
    private String itemid;
    private boolean isSelected;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public Model(String itemid,String itemName, boolean isSelected) {
        this.itemid = itemid;
        this.itemName = itemName;
        this.isSelected = isSelected;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
package org.vanguardmatrix.engine.sidemenu;

public class SideMenuItem {

    String ItemName;
    int imgResID;
    boolean selected;

    public SideMenuItem(String itemName, int imgResID) {
        super();
        this.ItemName = itemName;
        this.imgResID = imgResID;
        this.setSelected(false);
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

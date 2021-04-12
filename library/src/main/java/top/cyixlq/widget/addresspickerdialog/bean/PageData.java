package top.cyixlq.widget.addresspickerdialog.bean;

import java.util.List;

public class PageData<T> {

    private List<T> items;
    private int selectedIndex = -1; // 当前页面选中的下标

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
}

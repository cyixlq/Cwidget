package top.cyixlq.widget.addresspickerdialog.bean;

import java.util.List;

public class PageData<T> {

    private List<T> items;
    private int selectIndex = -1;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }
}

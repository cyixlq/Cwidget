package top.cyixlq.widget.addresspickerdialog.bean;

import java.util.List;

public class PageData<T> {

    private List<T> items;
    private int selectedIndex = -1; // 当前页面选中的下表
    private Object selectedId; // 当前页面选中的地址的id
    private String selectedAddressText; // 当前页面选中的地址名称

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

    public Object getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Object selectedId) {
        this.selectedId = selectedId;
    }

    public String getSelectedAddressText() {
        return selectedAddressText;
    }

    public void setSelectedAddressText(String selectedAddressText) {
        this.selectedAddressText = selectedAddressText;
    }
}

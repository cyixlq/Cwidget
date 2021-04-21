package top.cyixlq.widget.addresspickerdialog.bean;

public class AddressItem {

    private String address;
    private Object id;
    private boolean isChecked;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AddressItem{" +
                "address='" + address + '\'' +
                ", id=" + id +
                '}';
    }
}

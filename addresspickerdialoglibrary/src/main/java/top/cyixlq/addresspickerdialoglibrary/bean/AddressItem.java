package top.cyixlq.addresspickerdialoglibrary.bean;

public class AddressItem {

    private String address;
    private boolean isChecked;
    private Object id;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
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
                ", isChecked=" + isChecked +
                ", id=" + id +
                '}';
    }
}

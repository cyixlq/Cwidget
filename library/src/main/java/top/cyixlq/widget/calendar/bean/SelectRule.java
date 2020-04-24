package top.cyixlq.widget.calendar.bean;

public class SelectRule {

    private boolean enable = true; // 是否可以选择时间的开关，true可以选择，false不可以选择
    private boolean canSelectAfterToday = false; // 是否可以选择超过今天的日子，true可以，false不可以
    private int maxDayCount = -1; // 最多可以选择多少天数，如果设置成小于0则不限制

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isCanSelectAfterToday() {
        return canSelectAfterToday;
    }

    public void setCanSelectAfterToday(boolean canSelectAfterToday) {
        this.canSelectAfterToday = canSelectAfterToday;
    }

    public int getMaxDayCount() {
        return maxDayCount;
    }

    public void setMaxDayCount(int maxDayCount) {
        this.maxDayCount = maxDayCount;
    }
}

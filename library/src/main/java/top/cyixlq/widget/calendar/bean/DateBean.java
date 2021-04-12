package top.cyixlq.widget.calendar.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DateBean {

    public DateBean() { }

    public DateBean(int year, int month, int day, long stamp, boolean isInMonth, boolean isLatterThanToday) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.stamp = stamp;
        this.isInMonth = isInMonth;
        this.isLatterThanToday = isLatterThanToday;
    }

    private int year;
    private int month; // 适用于Calendar的月份，例如5月是4
    private int day;
    private long stamp;
    private boolean isInMonth; // 这一天是否是当月的日子
    private boolean isLatterThanToday; // 是否超过了今天

    // 对比是否比另一个时间实体要晚，如果年月日均相等也会返回false，只有更晚了才会返回true
    public boolean latterThan(DateBean other) {
        if (other == null)
            return false;
        return this.stamp > other.stamp;
    }

    public boolean beforeThan(DateBean other) {
        if (other == null)
            return false;
        return  this.stamp < other.stamp;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public boolean isInMonth() {
        return isInMonth;
    }

    public void setInMonth(boolean alive) {
        isInMonth = alive;
    }

    public boolean isLatterThanToday() {
        return isLatterThanToday;
    }

    public void setLatterThanToday(boolean latterThanToday) {
        isLatterThanToday = latterThanToday;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof DateBean) {
            DateBean other = (DateBean) obj;
            return this.year == other.year && this.month == other.month && this.day == other.day;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return year + "年" + (month + 1) + "月" + day + "日";
    }
}

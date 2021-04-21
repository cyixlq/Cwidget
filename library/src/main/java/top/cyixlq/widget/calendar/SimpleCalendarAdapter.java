package top.cyixlq.widget.calendar;

import top.cyixlq.widget.R;
import top.cyixlq.widget.calendar.base.BaseCalendarAdapter;
import top.cyixlq.widget.calendar.bean.DateBean;
import top.cyixlq.widget.calendar.view.DateItemView;
import top.cyixlq.widget.common.BaseViewHolder;

public class SimpleCalendarAdapter extends BaseCalendarAdapter {

    public SimpleCalendarAdapter() {
        super(R.layout.item_date_picker);
    }

    @Override
    public void itemInMonth(BaseViewHolder holder) {
        holder.<DateItemView>getView(R.id.dateItemView).setState(DateItemView.STATE_ALIVE);
    }

    @Override
    public void itemOutMonth(BaseViewHolder holder) {
        holder.<DateItemView>getView(R.id.dateItemView).setState(DateItemView.STATE_UN_ALIVE);
    }

    @Override
    public void setDayOfMonth(BaseViewHolder holder, int dayOfMonth) {
        final String day = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth + "";
        holder.<DateItemView>getView(R.id.dateItemView).setDay(day);
    }

    @Override
    public void itemInInterval(BaseViewHolder holder) {
        holder.<DateItemView>getView(R.id.dateItemView).setState(DateItemView.STATE_NORMAL);
    }

    @Override
    public void itemStart(BaseViewHolder holder) {
        holder.<DateItemView>getView(R.id.dateItemView).setState(DateItemView.STATE_START);
    }

    @Override
    public void itemEnd(BaseViewHolder holder) {
        holder.<DateItemView>getView(R.id.dateItemView).setState(DateItemView.STATE_END);
    }

    @Override
    public void itemSingleDay(BaseViewHolder holder) {
        holder.<DateItemView>getView(R.id.dateItemView).setState(DateItemView.STATE_SINGLE_DAY);
    }

    @Override
    public void onMaxDayExceed(BaseViewHolder holder, DateBean date) {
        if (selectRule.getMaxDayCount() == 1)
            changeFirstDateBean(date);
        else
            super.onMaxDayExceed(holder, date);
    }
}

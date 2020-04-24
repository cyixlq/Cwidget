package top.cyixlq.widget.calendar.base;

import android.support.annotation.Nullable;
import android.widget.Toast;

import top.cyixlq.widget.calendar.bean.DateBean;
import top.cyixlq.widget.calendar.bean.SelectRule;
import top.cyixlq.widget.common.BaseAdapter;
import top.cyixlq.widget.common.BaseViewHolder;

public abstract class BaseCalendarAdapter extends BaseAdapter<DateBean> {

    private DateBean firstDateBean;
    private DateBean lastDateBean;
    private SelectRule selectRule;

    public BaseCalendarAdapter(int layoutId) {
        super(layoutId);
        this.selectRule = new SelectRule();
    }

    public BaseCalendarAdapter(int layoutId, SelectRule selectRule) {
        super(layoutId);
        this.selectRule = selectRule == null ? new SelectRule() : selectRule;
    }

    @Override
    public void convert(BaseViewHolder holder, DateBean time) {
        setDayOfMonth(holder, time.getDay());
        if (time.isInMonth())
            itemInMonth(holder);
        else
            itemOutMonth(holder);
        holder.itemView.setOnClickListener(v -> {
            if (time.isLatterThanToday() && !selectRule.isCanSelectAfterToday()) {
                onSelectAfterToday(holder);
                return;
            }
            // 如果点选的是已经选中过的日期，始终优先赋值给first
            if (time.equals(firstDateBean)) {
                firstDateBean = lastDateBean;
                lastDateBean = null;
                notifyDataSetChanged();
            } else if (time.equals(lastDateBean)) {
                lastDateBean = null;
                notifyDataSetChanged();
            } else {
                if (firstDateBean == null) {
                    firstDateBean = time;
                    notifyDataSetChanged();
                } else if (selectRule.getMaxDayCount() > 0 && (Math.abs(time.getStamp() - firstDateBean.getStamp()) / 1000
                        > selectRule.getMaxDayCount() * 24 * 3600)) {
                    onMaxDayExceed(holder);
                } else {
                    lastDateBean = time;
                    notifyDataSetChanged();
                }
            }
        });
        if (firstDateBean == null) return; // 一定要确保已经选择了一个时间
        // 如果第一次选中的时间更晚，说明只要晚于last的就是位于时间区间内
        if (firstDateBean.latterThan(lastDateBean)) {
            if (time.equals(lastDateBean)) { // 如果是开始当天
                itemStart(holder);
            } else if (time.equals(firstDateBean)) { // 如果是结束当天
                itemEnd(holder);
            } else if (time.latterThan(lastDateBean) && time.beforeThan(firstDateBean)) { // 如果位于区间内
                itemInInterval(holder);
            } else { // 如果比开始时间还要早或者比结束时间还要晚，即不位于选择的时间区间内
                if (time.isInMonth()) {
                    itemInMonth(holder);
                } else {
                    itemOutMonth(holder);
                }
            }
        } else { // 说明晚于first就位于区间内
            if (time.equals(firstDateBean)) {
                if (lastDateBean != null)// 如果是开始当天
                    itemStart(holder);
                else
                    itemSingleDay(holder); // 如果暂时只选择了一天，有的UI需要单独处理
            } else if (time.equals(lastDateBean)) { // 如果是结束当天
                itemEnd(holder);
            } else if (time.latterThan(firstDateBean) && time.beforeThan(lastDateBean)) { // 如果位于区间内
                itemInInterval(holder);
            } else { // 如果比开始时间还要早或者比结束时间还要晚，即不位于选择的时间区间内
                if (time.isInMonth()) {
                    itemInMonth(holder);
                } else {
                    itemOutMonth(holder);
                }
            }
        }
    }

    //------------------------------------ 对外接口方法 ----------------------------------------------
    // 设置选择规则
    public void setSelectRule(SelectRule rule) {
        if (rule == null) {
            throw new NullPointerException("You can not set null value of the rule parameter");
        }
        this.selectRule = rule;
    }

    @Nullable
    public DateBean getFirstDateBean() {
        return firstDateBean;
    }

    @Nullable
    public DateBean getLastDateBean() {
        return lastDateBean;
    }

    // -------------------------—————————— 按照需求自定义方法 -----------------------------------------

    /**
     * 当规则中设置了不可以选择超出当天的日子并且用户选择了超出当天的日子的回调，这是一个默认实现，你可以重写此方法来自己实现
     * @param holder holder
     */
    public void onSelectAfterToday(BaseViewHolder holder) {
        Toast.makeText(holder.itemView.getContext(), "选择的时间不能超过今天", Toast.LENGTH_SHORT).show();
    }

    /**
     * 当用户选择的天数超出了规则中设置的最多天数的回调，这是一个默认实现，你可以重写此方法来自己实现
     * @param holder holder
     */
    public void onMaxDayExceed(BaseViewHolder holder) {
        Toast.makeText(holder.itemView.getContext(), "最多只能选择" + selectRule.getMaxDayCount() + "天", Toast.LENGTH_SHORT).show();
    }

    /**
     * 这天是当前选择的月份中的日子的时候，并且如果这天不在选择的区间内，会对应回调itemInMonth或者itemOutMonth来设置为普通状态
     * @param holder holder
     */
    public abstract void itemInMonth(BaseViewHolder holder);

    /**
     * 这天不是当前选择的月份中日子的时候
     * @param holder holder
     */
    public abstract void itemOutMonth(BaseViewHolder holder);

    /**
     * 用于展示日子，几号
     * @param holder holder
     * @param dayOfMonth 几号
     */
    public abstract void setDayOfMonth(BaseViewHolder holder, int dayOfMonth);

    /**
     * 这天是在选择了的区域内
     * @param holder holder
     */
    public abstract void itemInInterval(BaseViewHolder holder);

    /**
     * 这天是选择了的开始的日子
     * @param holder holder
     */
    public abstract void itemStart(BaseViewHolder holder);

    /**
     * 这天是选择了的结束的日子
     * @param holder holder
     */
    public abstract void itemEnd(BaseViewHolder holder);

    /**
     * 暂时只选择了一天，即用户只点击了一次日历
     * @param holder holder
     */
    public abstract void itemSingleDay(BaseViewHolder holder);
}

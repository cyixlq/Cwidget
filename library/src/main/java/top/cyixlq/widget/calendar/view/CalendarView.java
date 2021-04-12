package top.cyixlq.widget.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.cyixlq.widget.calendar.base.BaseCalendarAdapter;
import top.cyixlq.widget.calendar.bean.DateBean;
import top.cyixlq.widget.common.utils.DateUtil;

public class CalendarView extends RecyclerView {

    private BaseCalendarAdapter cacheAdapter;

    public CalendarView(@NonNull Context context) {
        this(context, null);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setLayoutManager(new GridLayoutManager(context, 7));
    }

    public void setAdapter(@Nullable BaseCalendarAdapter adapter) {
        super.setAdapter(adapter);
        this.cacheAdapter = adapter;
    }

    // 设置要显示的月份的日历，以Calendar中的月份方式传，例如6月传5
    public void setMonth(final int year, final int month) {
        BaseCalendarAdapter adapter = checkAdapter();
        List<DateBean> list = DateUtil.getMonthDays(year, month);
        adapter.setData(list);
    }

    @SuppressWarnings("unchecked")
    private BaseCalendarAdapter checkAdapter() {
        if (cacheAdapter != null)
            return cacheAdapter;
        Adapter adapter = getAdapter();
        if (adapter == null) {
            throw new NullPointerException("You should setAdapter before setMonth");
        }
        Class clz = adapter.getClass();
        if (!clz.isAssignableFrom(BaseCalendarAdapter.class)) {
            throw new RuntimeException("The adapter you set should be a subclass of BaseCalendarAdapter");
        }
        cacheAdapter = (BaseCalendarAdapter) adapter;
        return cacheAdapter;
    }

    /**
     *  获取比较过的时间
     * @param isGetStart 是获取开始时间吗
     * @return 返回开始时间或者结束时间
     */
    private DateBean getCompareDateBean(final boolean isGetStart) {
        BaseCalendarAdapter adapter = checkAdapter();
        final DateBean first = adapter.getFirstDateBean();
        final DateBean last = adapter.getLastDateBean();
        if (first == null) {
            return null;
        }
        if (last == null) {
            return first;
        }
        final DateBean start;
        final DateBean end;
        if (last.latterThan(first)) {
            start = first;
            end = last;
        } else {
            start = last;
            end = first;
        }
        if (isGetStart) //first比last晚，first是结束时间，lase是开始时间
            return start;
        else
            return end;
    }

    @Nullable
    public DateBean getStartDate() {
        return getCompareDateBean(true);
    }

    @Nullable
    public DateBean getEndDate() {
        return getCompareDateBean(false);
    }
}

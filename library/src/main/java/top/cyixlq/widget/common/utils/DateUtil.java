package top.cyixlq.widget.common.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import top.cyixlq.widget.calendar.bean.DateBean;

public class DateUtil {

    /**
     *  获取当月天数列表，衔接上下月
     * @param year 年份
     * @param month 月份（适用于Calendar的，例如5月就传4）
     * @return 当前月份DateBean列表，包含了上个月一些尾巴和下一个月一些头
     */
    public static List<DateBean> getMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        final long currentStamp = calendar.getTimeInMillis();

        // 需要提前将当天，当年，当月数据拿出来
        final int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentYear = calendar.get(Calendar.YEAR);

        calendar.set(year, month, 1, 0 ,0 ,0); // 将时间设置到月初
        List<DateBean> list = new ArrayList<>();
        final int prevDayCount = getMonthStartDayInWeek(year, month);
        // 这个月第一天礼拜几就将时间往前挪几天，例如星期一就挪一天，用上个月最后一天来补，星期天就不需要挪
        calendar.add(Calendar.DAY_OF_MONTH, -prevDayCount);
        final int monthDayCount = getDayCountOfMonth(year, month); // 这个月最后一天是几号
        final int afterDayCount = 6 - getMonthEndDayInWeek(year, month); // 后面需要补下一个月几天时间
        final int sum = prevDayCount + monthDayCount + afterDayCount; // 总共需要的天数
        for (int i = 0; i < sum; i++) {
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int year1 = calendar.get(Calendar.YEAR);
            final int month1 = calendar.get(Calendar.MONTH);
            final long stamp = calendar.getTimeInMillis();
            final boolean isInMonth = month1 == month; // 这一天是否在传入的月份中
            final boolean isLatterThanToday = stamp > currentStamp;
            final DateBean dateBean = new DateBean(year1,month1, day, stamp, isInMonth, isLatterThanToday);
            list.add(dateBean);
            calendar.add(Calendar.DAY_OF_MONTH, +1);
        }
        return list;
    }

    // {"", 星期日，星期一， 星期二， 星期三， 星期四， 星期五， 星期六} 所以结果需要-1
    // 获取这个月1号是礼拜几，星期天返回0，星期一返回1，如此类推
    private static int getMonthStartDayInWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private static int getMonthEndDayInWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, getDayCountOfMonth(year, month));
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    // 获得某年某月有多少天
    private static int getDayCountOfMonth(int year, int month) {
        final int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            monthDays[1]++;
        return monthDays[month];
    }

}

package top.cyixlq.widget.calendar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.cyixlq.widget.R;

public class DateItemView extends LinearLayout {

    private static final int WHITE = 0xffffffff;
    private static final int ALIVE_GREEN = 0xff43B480;
    private static final int NORMAL_GREEN = 0xff82DBB2;
    private static final int ALIVE_BLACK = 0xff202020;
    private static final int UN_ALIVE_BLACK = 0xffd2d2d2;

    public static final int STATE_START = 0; // 开始这天
    public static final int STATE_END = 1; // 结束这天
    public static final int STATE_NORMAL = 2; // 位于区间内
    public static final int STATE_ALIVE = 3; // 处于这个月并且早于当天
    public static final int STATE_UN_ALIVE = 4; // 不处于这个月或者晚于当天
    public static final int STATE_SINGLE_DAY = 5; // 仅选中了一天

    private View bgLeft, bgRight;
    private TextView tvDay;

    public DateItemView(Context context) {
        this(context, null);
    }

    public DateItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.item_date_picker_day, this);
        bgLeft = findViewById(R.id.bgLeft);
        bgRight = findViewById(R.id.bgRight);
        tvDay = findViewById(R.id.tvDay);
    }

    public void setState(final int state) {
        switch (state) {
            case STATE_START:
                tvDay.setTextColor(WHITE);
                tvDay.setBackgroundColor(ALIVE_GREEN);
                bgRight.setBackgroundColor(NORMAL_GREEN);
                bgLeft.setBackgroundColor(WHITE);
                break;
            case STATE_END:
                tvDay.setTextColor(WHITE);
                tvDay.setBackgroundColor(ALIVE_GREEN);
                bgLeft.setBackgroundColor(NORMAL_GREEN);
                bgRight.setBackgroundColor(WHITE);
                break;
            case STATE_NORMAL:
                tvDay.setTextColor(WHITE);
                tvDay.setBackgroundColor(NORMAL_GREEN);
                bgLeft.setBackgroundColor(NORMAL_GREEN);
                bgRight.setBackgroundColor(NORMAL_GREEN);
                break;
            case STATE_ALIVE:
                tvDay.setTextColor(ALIVE_BLACK);
                tvDay.setBackgroundColor(WHITE);
                bgLeft.setBackgroundColor(WHITE);
                bgRight.setBackgroundColor(WHITE);
                break;
            case STATE_UN_ALIVE:
                tvDay.setTextColor(UN_ALIVE_BLACK);
                tvDay.setBackgroundColor(WHITE);
                bgLeft.setBackgroundColor(WHITE);
                bgRight.setBackgroundColor(WHITE);
                break;
            case STATE_SINGLE_DAY:
                tvDay.setTextColor(WHITE);
                tvDay.setBackgroundColor(ALIVE_GREEN);
                bgLeft.setBackgroundColor(WHITE);
                bgRight.setBackgroundColor(WHITE);
                break;
        }
    }

    public void setDay(final String day) {
        if (tvDay != null && day != null)
            tvDay.setText(day);
    }
}

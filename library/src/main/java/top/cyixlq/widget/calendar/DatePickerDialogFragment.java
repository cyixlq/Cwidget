package top.cyixlq.widget.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import top.cyixlq.widget.R;
import top.cyixlq.widget.calendar.bean.DateBean;
import top.cyixlq.widget.calendar.bean.SelectRule;
import top.cyixlq.widget.calendar.view.CalendarView;

public class DatePickerDialogFragment extends DialogFragment implements View.OnClickListener{

    private TextView tvTime;
    private CalendarView rvCalendar;
    private Calendar calendar;
    private OnTimeSelectListener listener;
    private SelectRule selectRule; // 选择规则

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_date_area_picker, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        ImageView leftArrow = view.findViewById(R.id.ivArrowLeft);
        ImageView rightArrow = view.findViewById(R.id.ivArrowRight);
        rvCalendar = view.findViewById(R.id.rvCalendar);
        Button btnOk = view.findViewById(R.id.btnOk);
        tvTime = view.findViewById(R.id.tvTime);

        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() == null) return;
        Window window = getDialog().getWindow();
        if(window == null) return;
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.DialogBottom);
        View decorView = window.getDecorView();
        decorView.setPadding(0,0,0,0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setBackgroundDrawableResource(R.drawable.bg_date_area_picker);
        calendar = Calendar.getInstance(Locale.CHINA);
        final SimpleCalendarAdapter adapter = new SimpleCalendarAdapter();
        adapter.setSelectRule(selectRule);
        rvCalendar.setAdapter(adapter);
        setTimeView();
    }

    @SuppressLint("SetTextI18n")
    private void setTimeView() {
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        tvTime.setText(year + "年" + (month + 1) + "月");
        rvCalendar.setMonth(year, month);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.ivArrowLeft) {
            calendar.add(Calendar.MONTH, -1);
            setTimeView();
        } else if (id == R.id.ivArrowRight) {
            calendar.add(Calendar.MONTH, +1);
            setTimeView();
        } else if (id == R.id.btnOk) {
            final DateBean start = rvCalendar.getStartDate();
            final DateBean end = rvCalendar.getEndDate();
            if (start == null && end == null) {
                Toast.makeText(getContext(), "请选择时间", Toast.LENGTH_SHORT).show();
                return;
            }
            if (this.listener != null) {
                this.listener.onTimeSelect(start, end);
            }
            dismiss();
        }
    }

    public void show(FragmentManager manager) {
        super.show(manager, "DatePicker");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public void setOnTimeSelectListener(OnTimeSelectListener listener) {
        this.listener = listener;
    }

    public void setSelectRule(SelectRule rule) {
        if (rule == null) {
            this.selectRule = new SelectRule();
        } else {
            this.selectRule = rule;
        }
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(DateBean start, DateBean end);
    }
}

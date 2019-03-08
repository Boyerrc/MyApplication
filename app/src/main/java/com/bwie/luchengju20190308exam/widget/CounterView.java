package com.bwie.luchengju20190308exam.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.luchengju20190308exam.R;

public class CounterView extends LinearLayout {
    private TextView number;
    private Button decrease;
    private Button add;
    private   OnCounterLisenter onCounterLisenter;

    public CounterView(final Context context, AttributeSet attrs) {
        super(context,attrs);
        //填充的条目布局
        View view = LayoutInflater.from(context).inflate(R.layout.counter, this);
        decrease = view.findViewById(R.id.decrease);
        add = view.findViewById(R.id.add);
        number = view.findViewById(R.id.shop_number);

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"能进入这个方法",Toast.LENGTH_LONG).show();
                String numberString = number.getText().toString();
                int Snumber = Integer.parseInt(numberString);
                Snumber = Snumber + 1;
                number.setText(String.valueOf(Snumber));
                //借口回掉回传数据
                onCounterLisenter.onAdd(Snumber);
            }
        });
        decrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberString = number.getText().toString();
                int Snumber = Integer.parseInt(numberString);
                Snumber = Snumber - 1;
                //number.setText(String.valueOf(Snumber));
                if (Snumber<0) {
                    Snumber = 0;
                    //最少数量为0
                    number.setText(String.valueOf(Snumber));
                }
                number.setText(String.valueOf(Snumber));
                //借口回调回传数字
                onCounterLisenter.onDecrese(Snumber);
            }
        });
    }



    public interface OnCounterLisenter{
        //减少
        void onDecrese(int Snumber);
        //增加
         void onAdd(int Snumber);
    }
    public void SetOnCounterLisenter(OnCounterLisenter onCounterLisenter){
        this.onCounterLisenter = onCounterLisenter;
    }
}

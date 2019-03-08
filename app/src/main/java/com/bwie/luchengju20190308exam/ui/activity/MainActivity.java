package com.bwie.luchengju20190308exam.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.bwie.luchengju20190308exam.R;
import com.bwie.luchengju20190308exam.ui.fragment.MineFragment;
import com.bwie.luchengju20190308exam.ui.fragment.ShouFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup group;
    private FragmentTransaction transaction;
    private ShouFragment shouFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        group = findViewById(R.id.group);
        //创建fragment
        shouFragment = new ShouFragment();
        mineFragment = new MineFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, shouFragment);
        transaction.add(R.id.frame, mineFragment);
        //show..hide
        transaction.show(shouFragment).hide(mineFragment).commit();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //创建对象
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();
                switch (checkedId){
                    case R.id.shou:
                        transaction.show(shouFragment).hide(mineFragment);
                        break;
                    case R.id.mine:
                        transaction.show(mineFragment).hide(shouFragment);
                        break;
                }
                transaction.commit();
            }
        });
    }
}

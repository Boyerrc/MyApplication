package com.bwie.luchengju20190308exam.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.luchengju20190308exam.R;
import com.bwie.luchengju20190308exam.data.bean.Goods;
import com.bwie.luchengju20190308exam.di.contract.IContract;
import com.bwie.luchengju20190308exam.di.presenter.IShoppingCarPresenterImpl;
import com.bwie.luchengju20190308exam.ui.adapter.ChexkAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShouFragment extends Fragment implements IContract.ShoppingCartView,View.OnClickListener {
    @BindView(R.id.review)
    RecyclerView review;
    @BindView(R.id.srfl)
    SmartRefreshLayout srfl;
    @BindView(R.id.check_all)
    CheckBox checkAll;
    @BindView(R.id.sum_price)
    Button sumPrice;
    @BindView(R.id.money)
    TextView money;
    Unbinder unbinder;
    private IShoppingCarPresenterImpl presenter;
    private List<Goods.DataBean> beanList;
    private Context context;
    private ChexkAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shou, container, false);
        presenter = new IShoppingCarPresenterImpl();
        presenter.attachView(this);
        presenter.requestData();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView( this);
    }

    @Override
    public void showData(String mCartString) {
        //点击事件
        checkAll.setOnClickListener( this);
        //数据解析展示
        Goods goods = new Gson().fromJson(mCartString, Goods.class);
        //外层条目商家条目
        beanList = goods.getData();
        //Toast.makeText(this, "" + beanList, Toast.LENGTH_SHORT).show();
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        review.setLayoutManager(manager);
        //设置适配器
        adapter = new ChexkAdapter(R.layout.review_check, beanList);
        review.setAdapter(adapter);
        //条目点击
        adapter.SetOnChexItemClickLisenter(new ChexkAdapter.OnChexItemClickLisenter() {
            @Override
            public void CallBack() {
                boolean result = true;
                for (int i = 0; i < beanList.size(); i++) {
                    //外层选中状态
                    boolean businessChecked = beanList.get(i).getBusinessChecked(checkAll.isChecked());
                    result = result & businessChecked;
                    for (int j = 0; j < beanList.get(i).getList().size(); j++) {
                        //里层选中状态
                        boolean goodsChecked = beanList.get(i).getList().get(j).getGoodsChecked();
                        result = result & goodsChecked;
                    }
                }
                checkAll.setChecked(result);
                //计算总价
                GoodsCounter();
            }
        });
    }

    private void GoodsCounter() {
        //对总价进行计算
        double counter = 0;
        //外层条目
        for (int i = 0; i < beanList.size(); i++) {
            //内层条目
            for (int j = 0; j < beanList.get(i).getList().size(); j++) {
                //判断内层条目是否勾选
                if (beanList.get(i).getList().get(j).getGoodsChecked() == true) {
                    //获取商品数据*商品价格
                    double price = beanList.get(i).getList().get(j).getPrice();
                    int defalutNumber = beanList.get(i).getList().get(j).getDefalutNumber();
                    double goodsPrice = price * defalutNumber;
                    counter = counter + goodsPrice;
                }
            }
        }
        money.setText(""+"总价：" + String.valueOf(counter));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        //全选逻辑的处理
        for (int i = 0; i < beanList.size(); i++) {
            //1.首先让我层的商家目录全部选中
            beanList.get(i).setBusinessChecked(checkAll.isChecked());
            //2.再让里层的商品目录全部选中
            for (int j = 0; j < beanList.get(i).getList().size(); j++) {
                //beanList.get(i).getList().get(j).setGoodsChecked(checkAll.isChecked());
                beanList.get(i).getList().get(j).setGoodsChecked(checkAll.isChecked());
            }
        }
        adapter.notifyDataSetChanged();
        //计算总价
        GoodsCounter();
    }
}

package com.bwie.luchengju20190308exam.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bwie.luchengju20190308exam.R;
import com.bwie.luchengju20190308exam.data.bean.Goods;
import com.bwie.luchengju20190308exam.widget.CounterView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

class GoodsAdapter extends BaseQuickAdapter<Goods.DataBean.ListBean, BaseViewHolder> {


    OnGoodsItemClickLisenter onGoodsItemClickLisenter;
    public interface OnGoodsItemClickLisenter{
        void CallBack();
    }
    public void SetOnGoodsItemClickLisenter(OnGoodsItemClickLisenter onGoodsItemClickLisenter){
        this.onGoodsItemClickLisenter = onGoodsItemClickLisenter;
    }

    public GoodsAdapter(int layoutResId, @Nullable List<Goods.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    protected void convert(BaseViewHolder helper, final Goods.DataBean.ListBean item) {
        helper.setText(R.id.price, "￥：" + item.getPrice());
        helper.setText(R.id.title, item.getTitle());
        ImageView image = helper.getView(R.id.image);
        String imagesString = item.getImages();
        String[] imagesStr = imagesString.split("\\|");
        Glide.with(mContext).load(imagesStr[0]).into(image);
        //避免焦点抢占
        final CheckBox shop_goods = helper.getView(R.id.shop_goods);
        shop_goods.setOnCheckedChangeListener(null);
        shop_goods.setChecked(item.getGoodsChecked());
        //以接口的方式把状态回传给外层
        shop_goods.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Bean对象状态进行更新完毕
                item.setGoodsChecked(isChecked);
                onGoodsItemClickLisenter.CallBack();
            }
        });
        //加减器
        CounterView counterView = helper.getView(R.id.goods_counter);
        counterView.SetOnCounterLisenter(new CounterView.OnCounterLisenter() {
            @Override
            public void onDecrese(int Snumber) {
                //对新增字段进行改正
                item.setDefalutNumber(Snumber);
                onGoodsItemClickLisenter.CallBack();
            }

            @Override
            public void onAdd(int Snumber) {
                item.setDefalutNumber(Snumber);
                onGoodsItemClickLisenter.CallBack();
            }
        });
    }
}

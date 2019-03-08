package com.bwie.luchengju20190308exam.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.bwie.luchengju20190308exam.R;
import com.bwie.luchengju20190308exam.data.bean.Goods;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
public class ChexkAdapter extends BaseQuickAdapter<Goods.DataBean,BaseViewHolder> {

    OnChexItemClickLisenter onChexItemClickLisenter;

    public interface OnChexItemClickLisenter{
         void CallBack();
    }
    public void SetOnChexItemClickLisenter(OnChexItemClickLisenter onChexItemClickLisenter){
        this.onChexItemClickLisenter = onChexItemClickLisenter;
    }
    public ChexkAdapter(int layoutResId, @Nullable List<Goods.DataBean>
            data) {
        super(layoutResId,data);
    }

    protected void convert(final BaseViewHolder helper, final Goods.DataBean item) {
        //设置商家名称
        helper.setText(R.id.shoper, item.getSellerName());
        //设置商家下的子商品条目
        RecyclerView reviewGoods = helper.getView(R.id.review_goods);
        //避免焦点抢占
        final CheckBox check = helper.getView(R.id.check_All);
        check.setOnCheckedChangeListener(null);
        //************************************
        //获取商家条目是否选中状态
        check.setChecked(item.getBusinessChecked(check.isChecked()));
        //子条目内容（数据源）
        List<Goods.DataBean.ListBean> goodsList = item.getList();
        Log.e("lcj","aaa"+goodsList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        //设置子条目适配器
        final GoodsAdapter goodsAdapter = new GoodsAdapter(R.layout.review_goods, goodsList);
        reviewGoods.setLayoutManager(manager);
        reviewGoods.setAdapter(goodsAdapter);
        //goodsAdapter.setOnGoodsItemAdapter(new GoodsAdapter.)
        //然后外层的商品类别条目需要控制里面的商品条目
        goodsAdapter.SetOnGoodsItemClickLisenter(new GoodsAdapter.OnGoodsItemClickLisenter() {
            @Override
            public void CallBack() {
                //遍历获取所有子条目，只要有一个未勾选，商品类别也应该是未勾选
                boolean result = true;
                for (int i = 0; i < item.getList().size(); i++) {
                    result = result & item.getList().get(i).getGoodsChecked();
                }
                check.setChecked(result);
                //刷新适配器
                goodsAdapter.notifyDataSetChanged();
                //把最后的状态进行回传
                //把最后的状态进行回传
                onChexItemClickLisenter.CallBack();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取商品类别勾选状态
                //外层商品类别条目获取的关键
                for (int i = 0; i < item.getList().size(); i++) {
                    item.getList().get(i).setGoodsChecked(check.isChecked());
                    item.setBusinessChecked(check.isChecked());
                    notifyDataSetChanged();
                    //把最后的状态进行回传
                    onChexItemClickLisenter.CallBack();
                }
            }
        });
    }
}

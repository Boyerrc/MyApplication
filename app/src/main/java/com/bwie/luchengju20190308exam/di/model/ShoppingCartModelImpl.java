package com.bwie.luchengju20190308exam.di.model;


import com.bwie.luchengju20190308exam.data.content.Constant;
import com.bwie.luchengju20190308exam.di.contract.IContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
public class ShoppingCartModelImpl implements IContract.ShoppingCartModel {
    @Override
    public void containData(final CallBack callBack) {
            OkGo.<String>get(Constant.SHOPPING_URL).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String resposneData = response.body().toString();
                    //回传给P层
                    callBack.CallBack(resposneData);
                }
            });
        }
    }

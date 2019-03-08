package com.bwie.luchengju20190308exam.di.contract;

public class IContract {
    public interface ShoppingCartView{
        public void showData(String mCartString);
    }
    public interface ShoppingCartPresenter<ShoppingCartView>{
        //绑定---------*
        public void attachView(IContract.ShoppingCartView
                                       shoppingCartView);
        //解绑---------*
        public void detachView(IContract.ShoppingCartView
                                       shoppingCartView);

        public void requestData();
    }
    public interface ShoppingCartModel{
        public void containData(CallBack callBack);
        public interface CallBack{
            public void CallBack(String mCartString);
        }
    }
}

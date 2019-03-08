package com.bwie.luchengju20190308exam.di.presenter;

import com.bwie.luchengju20190308exam.di.contract.IContract;
import com.bwie.luchengju20190308exam.di.model.ShoppingCartModelImpl;

import java.lang.ref.SoftReference;

public class IShoppingCarPresenterImpl implements IContract.ShoppingCartPresenter {
    private SoftReference<IContract.ShoppingCartView> softReference;
    private IContract.ShoppingCartView shoppingCartView;
    private ShoppingCartModelImpl model;


    @Override
    public void attachView(IContract.ShoppingCartView shoppingCartView) {
        this.shoppingCartView = shoppingCartView;
        softReference = new SoftReference<>(shoppingCartView);
        model = new ShoppingCartModelImpl();
    }

    @Override
    public void detachView(IContract.ShoppingCartView shoppingCartView) {
        softReference.clear();
    }

    @Override
    public void requestData() {
        model.containData(new IContract.ShoppingCartModel.CallBack() {
            @Override
            public void CallBack(String mCartString) {
                shoppingCartView.showData(mCartString);
            }
        });
    }
}

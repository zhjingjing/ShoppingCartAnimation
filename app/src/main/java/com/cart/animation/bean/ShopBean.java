package com.cart.animation.bean;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * create by zj on 2019/2/19
 */
public class ShopBean  {


    public ShopBean(){

    }

    public ShopBean(String name,int imgId,int amount,double price,int type){
        this.name=name;
        this.imgId=imgId;
        this.amount=amount;
        this.price=price;
        this.type=type;
    }

    public String name;
    public int imgId;
    public int amount;
    public double price;
    public int checkNum;//添加购物车了几个
    public int type;//0:左侧menu 1：右侧商品


}

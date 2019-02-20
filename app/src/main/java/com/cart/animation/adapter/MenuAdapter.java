package com.cart.animation.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cart.animation.bean.ShopBean;
import com.cart.animation.databinding.ItemMenuBinding;
import com.cart.animation.databinding.ItemShopBinding;

import java.sql.Array;
import java.util.List;

/**
 * create by zj on 2019/2/20
 */
public class MenuAdapter extends AbsRVAdapter<ShopBean,AbsRVAdapter.BindingViewHolder> {
    private Context context;
    private int checkPos;
    private final static int ITEM_MENU=0;
    private final static int ITEM_SHOP=1;
    public MenuAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewDataBinding binding;
        if (getItemViewType(i)==ITEM_MENU){
            binding=ItemMenuBinding.inflate(mInflater,viewGroup,false);
        }else {
            binding= ItemShopBinding.inflate(mInflater,viewGroup,false);
        }
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {

        final ShopBean shopBean= (ShopBean) getItem(position);

        if (holder.mBinding instanceof ItemMenuBinding){
            ItemMenuBinding binding = (ItemMenuBinding) holder.mBinding;
            binding.setData(shopBean);
            binding.setPosition(position);
            if (checkPos==position){
                binding.tvMenuName.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else{
                binding.tvMenuName.setBackgroundColor(Color.parseColor("#EAEAEA"));
            }


            binding.tvMenuName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPos=position;
                    notifyDataSetChanged();
                    if (onItemSelectedListener!=null){
                        onItemSelectedListener.onLeftSelect(position,shopBean);
                    }
                }
            });
        }else{
            ItemShopBinding binding = (ItemShopBinding) holder.mBinding;
            binding.setData(shopBean);
            binding.setPosition(position);

            if (shopBean.checkNum==0){
                binding.tvShopNum.setVisibility(View.GONE);
                binding.ivRemove.setVisibility(View.GONE);
            }else{
                binding.tvShopNum.setVisibility(View.VISIBLE);
                binding.ivRemove.setVisibility(View.VISIBLE);
            }

            binding.tvShopNum.setText(shopBean.checkNum+"");

            binding.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemSelectedListener!=null){
                        onItemSelectedListener.remove(position,shopBean);
                    }
                }
            });

            binding.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shopBean.amount>shopBean.checkNum){
                        if (onItemSelectedListener!=null){
                            int[] location=new int[2];
                            v.getLocationInWindow(location);
                            location[0]-=100;
                            onItemSelectedListener.add(position,shopBean,location);
                        }
                    }else{
                        Toast.makeText(context,"该商品不足",Toast.LENGTH_LONG).show();
                    }
                }
            });



        }


    }


    public interface onItemSelectedListener{
        void onLeftSelect(int pos,ShopBean shopBean);
        void add(int pos, ShopBean shopBean,int[]  location);
        void remove(int pos,ShopBean shopBean);
    }

    onItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(MenuAdapter.onItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        List<ShopBean> list=getDataList();
        if (list!=null){
            if (list.get(position).type==ITEM_MENU){
                return ITEM_MENU;
            }else  if (list.get(position).type==ITEM_SHOP){
                return ITEM_SHOP;
            }else{
                return ITEM_MENU;
            }
        }
        return super.getItemViewType(position);
    }

    /**
     * 返回左侧menu选中的pos
     * @return
     */
    public int getCheckPos(){
        return checkPos;
    }


}

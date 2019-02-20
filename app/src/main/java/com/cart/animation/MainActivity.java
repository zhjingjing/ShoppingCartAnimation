package com.cart.animation;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cart.animation.adapter.MenuAdapter;
import com.cart.animation.bean.ShopBean;
import com.cart.animation.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding  binding;
    private MenuAdapter leftAdapter;
    private List<ShopBean> leftMenu;


    private List<List<ShopBean>> listShop;
    private MenuAdapter shopAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this, R.layout.activity_main);
        binding.setPresenter(this);


        initData();
        getData();
    }


    public void initData(){
        leftMenu=new ArrayList<>();
        binding.recycleMenu.setHasFixedSize(true);
        binding.recycleMenu.setLayoutManager(new LinearLayoutManager(this));

        leftAdapter=new MenuAdapter(this);
        leftAdapter.setOnItemSelectedListener(new MenuAdapter.onItemSelectedListener() {
            @Override
            public void onLeftSelect(int pos, ShopBean shopBean) {
                if (pos<listShop.size()){
                    shopAdapter.setData(listShop.get(pos));
                }
            }

            @Override
            public void add(int pos, ShopBean shopBean,int[] location) {

            }

            @Override
            public void remove(int pos, ShopBean shopBean) {
            }
        });
        binding.recycleMenu.setAdapter(leftAdapter);


        listShop=new ArrayList<>();
        binding.recycleList.setHasFixedSize(true);
        binding.recycleList.setLayoutManager(new LinearLayoutManager(this));
        shopAdapter=new MenuAdapter(this);
        shopAdapter.setOnItemSelectedListener(new MenuAdapter.onItemSelectedListener() {
            @Override
            public void onLeftSelect(int pos, ShopBean shopBean) {
            }

            @Override
            public void add(int pos, ShopBean shopBean,int[] location) {
                shopBean.checkNum+=1;
                shopAdapter.setDataIndex(pos,shopBean);

                showAnimation(location);
            }

            @Override
            public void remove(int pos, ShopBean shopBean) {
                shopBean.checkNum-=1;
                shopAdapter.setDataIndex(pos,shopBean);
            }
        });
        binding.recycleList.setAdapter(shopAdapter);

    }


    public void getData(){
        leftMenu.add(new ShopBean("水果",1,1,1,0));
        leftMenu.add(new ShopBean("肉",1,1,1,0));
        leftMenu.add(new ShopBean("套餐",1,1,1,0));
        leftAdapter.setData(leftMenu);

        List<ShopBean> shopBeanList1=new ArrayList<>();
        shopBeanList1.add(new ShopBean("水果1",1,1,1,1));
        shopBeanList1.add(new ShopBean("水果2",1,1,1,1));
        shopBeanList1.add(new ShopBean("水果3",1,1,1,1));
        shopBeanList1.add(new ShopBean("水果4",1,1,1,1));
        shopBeanList1.add(new ShopBean("水果5",1,1,1,1));

        List<ShopBean> shopBeanList2=new ArrayList<>();
        shopBeanList2.add(new ShopBean("肉1",1,1,1,1));
        shopBeanList2.add(new ShopBean("肉2",1,1,1,1));
        shopBeanList2.add(new ShopBean("肉3",1,1,1,1));
        shopBeanList2.add(new ShopBean("肉4",1,1,1,1));
        shopBeanList2.add(new ShopBean("肉5",1,1,1,1));

        List<ShopBean> shopBeanList3=new ArrayList<>();
        shopBeanList3.add(new ShopBean("套餐1",1,11,10,1));
        shopBeanList3.add(new ShopBean("套餐2",1,21,13,1));
        shopBeanList3.add(new ShopBean("套餐3",1,14,15,1));
        shopBeanList3.add(new ShopBean("套餐4",1,51,12,1));
        shopBeanList3.add(new ShopBean("套餐5",1,17,14,1));

        listShop.add(shopBeanList1);
        listShop.add(shopBeanList2);
        listShop.add(shopBeanList3);

        shopAdapter.setData(shopBeanList1);
    }



    public void showAnimation(int[] startLocation){

        int[] endLocation=new int[2];
        binding.ivShopCart.getLocationInWindow(endLocation);

        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标

        int endY = endLocation[1] - startLocation[1]+100;// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        final AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间

        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);

        final ViewGroup  anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(imageView);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, imageView,
                startLocation);
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                anim_mask_layout.removeAllViews();
                set.cancel();
                animation.cancel();
            }
        });


    }



    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                50,
                50);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }
}

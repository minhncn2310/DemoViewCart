package com.example.bookstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
public class ProductFragment extends Fragment implements ProductAdapter.IClickAddToCartListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView rcvProduct;
    private View mView;
    private MainActivity mainActivity;
    public ProductFragment() {

    }
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product, container, false);
        mainActivity = (MainActivity) getActivity();

        rcvProduct = mView.findViewById(R.id.rcv_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcvProduct.setLayoutManager(linearLayoutManager);

        mainActivity.productAdapter = new ProductAdapter();
        mainActivity.productAdapter.setData(mainActivity.productList, new ProductAdapter.IClickAddToCartListener() {
            @Override
            public void onClickAddToCart(final ImageView imgAddToCart, Product product) {
                AnimationUtil.translateAnimation(mainActivity.getViewAnimation(), imgAddToCart, mainActivity.getViewEndAnimation(), new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        product.setAddToCart(true);
                        imgAddToCart.setBackgroundResource(R.drawable.bg_gray_corner_6);
                        mainActivity.productAdapter.notifyDataSetChanged();
                        mainActivity.cartList.add(product);
                        mainActivity.cartAdapter.notifyDataSetChanged();
                        mainActivity.setCountProductInCart(mainActivity.getmCountProduct() + 1);

                        SharedPreferences mPrefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        Gson gson = new Gson();
                        String productJson = gson.toJson(mainActivity.productList);
                        String cartJson = gson.toJson(mainActivity.cartList);
                        prefsEditor.putString("PRODUCT_LIST", productJson);
                        prefsEditor.putString("CART_LIST", cartJson);
                        prefsEditor.commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        rcvProduct.setAdapter(mainActivity.productAdapter);
        return mView;
    }
    @Override
    public void onClickAddToCart(ImageView imgAddToCart, Product product) {
        
    }
}
package com.example.bookstore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity mainActivity;
    private RecyclerView rcvCart;
    private Button btnOrder;
    private View mView;

    public CartFragment() {
        // Required empty public constructor
    }
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cart, container, false);
        mainActivity = (MainActivity) getActivity();
        rcvCart = mView.findViewById(R.id.rcv_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcvCart.setLayoutManager(linearLayoutManager);
        mainActivity.cartAdapter = new CartAdapter();

        btnOrder = mView.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(view -> {
            for (int i = 0; i < mainActivity.productList.size(); ++i) {
                mainActivity.productList.get(i).setAddToCart(false);
            }
            mainActivity.cartList.removeAll(mainActivity.cartList);
            mainActivity.setCountProductInCart(0);
            mainActivity.productAdapter.notifyDataSetChanged();
            mainActivity.cartAdapter.notifyDataSetChanged();

            SharedPreferences mPrefs = mainActivity.getSharedPreferences("loctt12345", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();

            Gson gson = new Gson();
            String productJson = gson.toJson(mainActivity.productList);
            String cartJson = gson.toJson(mainActivity.cartList);
            prefsEditor.putString("PRODUCT_LIST", productJson);
            prefsEditor.putString("CART_LIST", cartJson);

            prefsEditor.commit();

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-notification-2e5e7-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference myRef = database.getReference("message");
            mainActivity.flag = true;
            myRef.setValue("Value: " + String.valueOf((new Random().nextInt())));
        });


        mainActivity.cartAdapter.setData(mainActivity.cartList, new CartAdapter.IClickRemoveListener() {
                        @Override
                        public void onClickRemove(final ImageView imgAddToCart, Product product)
                        {
                            product.setAddToCart(false);
                            int index = -1;
                            for (int i = 0; i < mainActivity.productList.size(); ++i) {
                                if (mainActivity.productList.get(i).getProductId() == product.getProductId()) {
                                    index =i;
                                    break;
                                }
                            }
                            mainActivity.productList.get(index).setAddToCart(false);
                            mainActivity.productAdapter.notifyDataSetChanged();
                            mainActivity.cartList.remove(product);
                            mainActivity.cartAdapter.notifyDataSetChanged();
                            mainActivity.setCountProductInCart(mainActivity.getmCountProduct() - 1);

                            SharedPreferences mPrefs = mainActivity.getSharedPreferences("loctt12345", Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson = new Gson();
                            String productJson = gson.toJson(mainActivity.productList);
                            String cartJson = gson.toJson(mainActivity.cartList);
                            prefsEditor.putString("PRODUCT_LIST", productJson);
                            prefsEditor.putString("CART_LIST", cartJson);
                            prefsEditor.commit();
                        }
            }
        );

        rcvCart.setAdapter(mainActivity.cartAdapter);
        return mView;
    }
}
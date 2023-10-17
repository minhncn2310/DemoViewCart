package com.example.bookstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ProductAdapter productAdapter;
    private List<Product> productsInCart = new ArrayList<>();
    private CartAdapter cartAdapter;
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

        productAdapter = new ProductAdapter();
        productAdapter.setData(getListProduct(), new ProductAdapter.IClickAddToCartListener() {
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
                        productAdapter.notifyDataSetChanged();
                        mainActivity.setCountProductInCart(mainActivity.getmCountProduct() + 1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        rcvProduct.setAdapter(productAdapter);
        return mView;
    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();

        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description"));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description"));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description"));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description"));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description"));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description"));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description"));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description"));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description"));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description"));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description"));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description"));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description"));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description"));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description"));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description"));

        return list;
    }

    @Override
    public void onClickAddToCart(ImageView imgAddToCart, Product product) {
        
    }
}
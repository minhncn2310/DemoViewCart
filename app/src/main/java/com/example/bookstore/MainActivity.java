package com.example.bookstore;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigation ahBottomNavigation;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    private ViewPagerAdapter adapter;
    private View viewEndAnimation;
    private ImageView viewAnimation;
    public List<Product> productList;
    public ProductAdapter productAdapter;
    public List<Product> cartList;
    public CartAdapter cartAdapter;
    private int mCountProduct;
    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();

        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description", 1));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description", 2));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description",3));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description",4));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description",5));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description",6));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description",7));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description",8));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description",9));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description",10));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description",11));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description",12));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description",13));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description", 14));
        list.add(new Product(R.drawable.img_1, "Product Name 1", "This is description",15));
        list.add(new Product(R.drawable.img_2, "Product Name 2", "This is description",16));

        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewEndAnimation = findViewById(R.id.view_end_animation);
        viewAnimation = findViewById(R.id.view_animation);

        ahBottomNavigation = findViewById(R.id.AHBottomNavigation);
        ahBottomNavigationViewPager = findViewById(R.id.AHBottomNavigationViewPager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ahBottomNavigationViewPager.setAdapter(adapter);
        //allow user can slide to change fragment
        ahBottomNavigationViewPager.setPagingEnabled(true);

        //Declare Fragment
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_product, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_cart, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_noitce, R.color.color_tab_3);

        //add fragment
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                ahBottomNavigationViewPager.setCurrentItem(position);
                return true;
            }
        });

        ahBottomNavigationViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                ahBottomNavigation.setCurrentItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        //mPrefs.edit().clear().commit();
        Gson gson = new Gson();
        String productJson = mPrefs.getString("PRODUCT_LIST", null);
        String cartJson = mPrefs.getString("CART_LIST", null);

        if (productJson != null) {
            productList = gson.fromJson(productJson, new TypeToken<ArrayList<Product>>() { }.getType());
        }
        else
            productList = getListProduct();
        if (cartJson != null) {
            cartList = gson.fromJson(cartJson, new TypeToken<ArrayList<Product>>() { }.getType());
            setCountProductInCart(cartList.size());
        }
        else
            cartList = new ArrayList<>();
    }

    public View getViewEndAnimation() {
        return viewEndAnimation;
    }

    public ImageView getViewAnimation() {
        return viewAnimation;
    }

    public void setCountProductInCart(int count){
        mCountProduct = count;
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(count))
                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                .build();
        ahBottomNavigation.setNotification(notification, 1);
    }

    public int getmCountProduct() {
        return mCountProduct;
    }
}
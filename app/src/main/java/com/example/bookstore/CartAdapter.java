package com.example.bookstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> mCartList;
    public interface IClickAddToCartListener{
        void onClickAddToCart(ImageView imgAddToCart, Product product);
    }
    public CartAdapter(List<Product> cartList) {
        this.mCartList = cartList;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        final Product product = mCartList.get(position);
        if (product == null) {
            return;
        }
        holder.imgProduct.setImageResource(product.getResourceId());
        holder.tvProductName.setText(product.getName());
        holder.tvDescription.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductName;
        private TextView tvDescription;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}

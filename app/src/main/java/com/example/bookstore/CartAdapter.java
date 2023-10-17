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
    private CartAdapter.IClickRemoveListener iClickRemoveListener;
    public interface IClickRemoveListener{
        void onClickRemove(ImageView imgAddToCart, Product product);
    }

    public void setData(List<Product> cartList, CartAdapter.IClickRemoveListener listener) {
        this.mCartList = cartList;
        this.iClickRemoveListener = listener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
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
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRemoveListener.onClickRemove(holder.imgRemove, product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductName;
        private TextView tvDescription;
        private ImageView imgRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgRemove = itemView.findViewById(R.id.img_remove);
        }
    }
}

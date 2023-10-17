package com.example.bookstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> mListProduct;
    private IClickAddToCartListener iClickAddToCartListener;
    public interface IClickAddToCartListener{
        void onClickAddToCart(ImageView imgAddToCart, Product product);
    }
    public void setData(List<Product> list, IClickAddToCartListener listener) {
        this.mListProduct = list;
        this.iClickAddToCartListener = listener;
        notifyDataSetChanged();
    }

    public List<Product> getData(){
        return mListProduct;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        final Product product = mListProduct.get(position);
        if (product == null){
            return;
        }
        holder.imgProduct.setImageResource(product.getResourceId());
        holder.tvProductName.setText(product.getName());
        holder.tvDescription.setText(product.getDescription());
        if (!product.isAddToCart()){
            holder.imgAddToCart.setBackgroundResource(R.drawable.bg_red_corner_6);
        }else {
            holder.imgAddToCart.setBackgroundResource(R.drawable.bg_gray_corner_6);
        }

        holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!product.isAddToCart()){
                    iClickAddToCartListener.onClickAddToCart(holder.imgAddToCart, product);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductName;
        private TextView tvDescription;
        private ImageView imgAddToCart;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgAddToCart = itemView.findViewById(R.id.img_add_to_card);
        }
    }
}

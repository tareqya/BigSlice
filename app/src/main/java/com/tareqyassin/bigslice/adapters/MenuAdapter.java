package com.tareqyassin.bigslice.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tareqyassin.bigslice.R;
import com.tareqyassin.bigslice.database.Category;
import com.tareqyassin.bigslice.database.Product;
import com.tareqyassin.bigslice.interfaces.MenuClickedListener;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ArrayList<Product> menu;
    private MenuClickedListener menuClickedListener;

    public MenuAdapter(Activity activity, ArrayList<Product> menu) {
        this.activity = activity;
        this.menu = menu;
    }

    public MenuAdapter setMenuClickedListener(MenuClickedListener menuClickedListener) {
        this.menuClickedListener = menuClickedListener;
        return this;
    }

    public void setMenu(ArrayList<Product> menu){
        this.menu = menu;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, viewGroup, false);
        return new MenuAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
        Product product = getItem(position);
        menuViewHolder.menu_TV_Title.setText(product.getName());
        menuViewHolder.menu_TV_price.setText(product.getPrice() + "₪");
        menuViewHolder.menu_RB_rate.setRating((float) product.getRate());
        menuViewHolder.menu_IMG_product.setImageResource(product.getImageId());

    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    private Product getItem(int position) {
        return menu.get(position);
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

       public TextView menu_TV_Title;
       public TextView menu_TV_price;
       public RatingBar menu_RB_rate;
       public ImageView menu_IMG_product;

        public MenuViewHolder(final View itemView) {
            super(itemView);
            this.menu_TV_Title = itemView.findViewById(R.id.menu_TV_Title);
            this.menu_TV_price = itemView.findViewById(R.id.menu_TV_price);
            this.menu_RB_rate = itemView.findViewById(R.id.menu_RB_rate);
            this.menu_IMG_product = itemView.findViewById(R.id.menu_IMG_product);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = getItem(getAdapterPosition());
                    menuClickedListener.menuItemClicked(product, getAdapterPosition());
                }
            });
        }
    }
}

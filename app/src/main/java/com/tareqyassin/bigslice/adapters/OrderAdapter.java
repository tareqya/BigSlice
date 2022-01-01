package com.tareqyassin.bigslice.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.card.MaterialCardView;
import com.tareqyassin.bigslice.R;
import com.tareqyassin.bigslice.database.Order;
import com.tareqyassin.bigslice.interfaces.OrderClickedListener;


import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ArrayList<Order> orders = new ArrayList<>();
    private OrderClickedListener orderClickedListener;

    public OrderAdapter(Activity activity, ArrayList<Order> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    public void setOrderClickedListener(OrderClickedListener orderClickedListener){
        this.orderClickedListener = orderClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
        Order order = getItem(position);
        orderViewHolder.order_TV_price.setText(order.getProduct().getPrice() + " ₪");
        orderViewHolder.order_TV_orderNumber.setText("Order number: " + order.getOrderNumber());
        orderViewHolder.order_TV_size.setText(order.getProduct().getSize());
        orderViewHolder.order_TV_title.setText(order.getProduct().getName());
        orderViewHolder.order_TV_orderDate.setText(order.getOrderDate());
        orderViewHolder.order_IMG_orderImg.setImageResource(order.getProduct().getImageId());
        if(order.isExtend()){
            orderViewHolder.order_TV_more.setText("Read less");
            orderViewHolder.product_LL_statusBox.setVisibility(View.VISIBLE);
        }else {
            orderViewHolder.order_TV_more.setText("Read more");
            orderViewHolder.product_LL_statusBox.setVisibility(View.GONE);
        }


        switch (order.getOrderStatus()){
            case 0:
                orderViewHolder.order_CV_status1.setBackgroundColor(Color.GREEN);
                orderViewHolder.order_CV_status2.setBackgroundColor(Color.WHITE);
                orderViewHolder.order_CV_status3.setBackgroundColor(Color.WHITE);
                break;
            case 1:
                orderViewHolder.order_CV_status1.setBackgroundColor(Color.WHITE);
                orderViewHolder.order_CV_status1.setBackgroundColor(Color.GREEN);
                orderViewHolder.order_CV_status2.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                orderViewHolder.order_CV_status1.setBackgroundColor(Color.GREEN);
                orderViewHolder.order_CV_status2.setBackgroundColor(Color.GREEN);
                orderViewHolder.order_CV_status3.setBackgroundColor(Color.GREEN);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private Order getItem(int position) {
        return orders.get(position);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        public TextView order_TV_orderNumber, order_TV_title, order_TV_size, order_TV_orderDate, order_TV_price, order_TV_more;
        public MaterialCardView order_CV_status1, order_CV_status2, order_CV_status3;
        public ImageView order_IMG_orderImg;
        public LinearLayout product_LL_statusBox;
        private boolean isExtends;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            isExtends = false;
            this.order_TV_orderNumber = itemView.findViewById(R.id.order_TV_orderNumber);
            this.order_TV_title = itemView.findViewById(R.id.order_TV_title);
            this.order_TV_size = itemView.findViewById(R.id.order_TV_size);
            this.order_TV_orderDate = itemView.findViewById(R.id.order_TV_orderDate);
            this.order_TV_price = itemView.findViewById(R.id.order_TV_price);
            this.order_TV_more = itemView.findViewById(R.id.order_TV_more);
            this.order_CV_status1 = itemView.findViewById(R.id.order_CV_status1);
            this.order_CV_status2 = itemView.findViewById(R.id.order_CV_status2);
            this.order_CV_status3 = itemView.findViewById(R.id.order_CV_status3);
            this.order_IMG_orderImg = itemView.findViewById(R.id.order_IMG_orderImg);
            this.product_LL_statusBox = itemView.findViewById(R.id.product_LL_statusBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isExtends = !isExtends;
                    Order order = getItem(getAdapterPosition());
                    order.setExtend(!order.isExtend());
                    orderClickedListener.OrderItemClicked(order, getAdapterPosition());
                }
            });

        }


    }


}

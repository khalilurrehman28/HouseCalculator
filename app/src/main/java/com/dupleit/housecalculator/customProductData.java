package com.dupleit.housecalculator;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by khalil on 12/1/17.
 */

public class customProductData extends BaseAdapter {
    //ViewHolder vholder;
    private Activity activity;
    private LayoutInflater inflater;
    private List<customProductList> ProductData;
    String Et,Et1;
    private String[] arrTemp,arrTemp1;
    ViewHolder holder;


    public customProductData(Activity activity, List<customProductList> ProductItems) {
        this.activity = activity;
        this.ProductData = ProductItems;
    }

    @Override
    public int getCount() {
        return ProductData.size();
    }

    @Override
    public Object getItem(int i) {
        return ProductData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        holder = new ViewHolder(new ProductQuantity(),new ProductPrice());

        if (view == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_listview, null);

            holder.name = (TextView) view.findViewById(R.id.tv_product_name);
            holder.price = (TextView) view.findViewById(R.id.tv_product_price);
            holder.code = (TextView) view.findViewById(R.id.tv_product_code);
            holder.unit = (TextView) view.findViewById(R.id.tv_product_unit);
            holder.qtyUser = (EditText) view.findViewById(R.id.ET_product);
            holder.priceUser = (EditText) view.findViewById(R.id.ET_product1);
            holder.qtyUser.addTextChangedListener(holder.productQuantity);
            holder.priceUser.addTextChangedListener(holder.productPrice);

            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.pos = position;

        //holder.qtyUser.setText(ProductData.get(holder.pos).setEtprice("def"));
        //holder.qtyUser.setText(arrTemp1[i]);



        holder.productPrice.updatePosition(position);
        holder.productQuantity.updatePosition(position);

        // getting movie data for the row
        customProductList m = ProductData.get(position);

        holder.name.setText(m.getProductName());
        holder.price.setText(m.getProductPrice());
        holder.code.setText(m.getProductCode());
        holder.unit.setText(m.getUnit());
        holder.qtyUser.setText(m.getEtqty());
        holder.priceUser.setText(m.getEtprice());
        return view;
    }

    // to listen user input for product price
    private class ProductPrice implements TextWatcher{
        private int position;

        public void updatePosition(int position){

            this.position=position;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3){
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3){

            if(position<=ProductData.size())
                ProductData.get(position).setEtprice(charSequence.toString());

        }

        @Override
        public void afterTextChanged(Editable editable){
            // no op
        }
    }


    // to listen user input for product quantity
    private class ProductQuantity implements TextWatcher{
        private int position;

        public void updatePosition(int position){

            this.position=position;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3){
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3){

            if(position<=ProductData.size())
                ProductData.get(position).setEtqty(charSequence.toString());

        }

        @Override
        public void afterTextChanged(Editable editable){
            // no op
        }
    }
    class ViewHolder{
        public TextView name;
        public TextView price;
        public TextView code;
        public TextView unit;
        public EditText qtyUser;
        public EditText priceUser;
        public int pos;
        public ProductQuantity productQuantity;
        public ProductPrice productPrice;

        public ViewHolder(ProductQuantity productQuantity,ProductPrice productPrice)
        {
            this.productQuantity=productQuantity;
            this.productPrice=productPrice;
        }
        }

}

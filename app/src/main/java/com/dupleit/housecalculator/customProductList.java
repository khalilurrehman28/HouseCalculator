package com.dupleit.housecalculator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khalil on 12/1/17.
 */
//parcelbale so that we can transfer with bundle
public class customProductList implements Parcelable {
    private String ProductCode="",ProductName="",Unit="",ProductPrice="",Etprice="",Etqty="";
    public customProductList(){
    }

    public customProductList(String ProductCode, String ProductName,String Unit,String ProductPrice, String Etprice,String Etqty) {
        this.ProductCode = ProductCode;
        this.ProductName = ProductName;
        this.Unit = Unit;
        this.ProductPrice = ProductPrice;
        this.Etprice= Etprice;
        this.Etqty= Etqty;


    }

    protected customProductList(Parcel in) {
        ProductCode = in.readString();
        ProductName = in.readString();
        Unit = in.readString();
        ProductPrice = in.readString();
        Etprice = in.readString();
        Etqty = in.readString();
    }

    public static final Creator<customProductList> CREATOR = new Creator<customProductList>() {
        @Override
        public customProductList createFromParcel(Parcel in) {
            return new customProductList(in);
        }

        @Override
        public customProductList[] newArray(int size) {
            return new customProductList[size];
        }
    };

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getEtprice() {
        return Etprice;
    }

    public void setEtprice(String etprice) {
        Etprice = etprice;
    }

    public String getEtqty() {
        return Etqty;
    }

    public void setEtqty(String etqty) {
        Etqty = etqty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ProductCode);
        parcel.writeString(ProductName);
        parcel.writeString(Unit);
        parcel.writeString(ProductPrice);
        parcel.writeString(Etprice);
        parcel.writeString(Etqty);
    }
}

package com.skypremiuminternational.app.app.features.estore.detail.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.IOnClickAddToCart;
import com.skypremiuminternational.app.app.features.estore.IOnClickBuyNow;
import com.skypremiuminternational.app.app.features.estore.detail.viewholder.VHEstoreProduct;
import com.skypremiuminternational.app.app.features.estore.detail.viewholder.VHProduct;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.view.ProductActionListener;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wmw on 2/6/2018.
 */

public class RecommendationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final int PRODUCT_VIEW = 0;
  private final int ESTORE_PRODUCT_VIEW = 1;
  private List<ItemsItem> list = new ArrayList<>();
  private ProductActionListener<ItemsItem> actionListener;
  private  IOnClickAddToCart iOnClickAddToCart;
  private  IOnClickBuyNow iOnClickBuyNow;
  private boolean isEnableBuyNow;

  public void setActionListener(ProductActionListener<ItemsItem> actionListener) {
    this.actionListener = actionListener;
  }
  public RecommendationAdapter (IOnClickAddToCart iOnClickAddToCart, IOnClickBuyNow iOnClickBuyNow){
    this.iOnClickAddToCart = iOnClickAddToCart;
    this.iOnClickBuyNow = iOnClickBuyNow;
  }
  public RecommendationAdapter (){
  }
  public void setItemList(List<ItemsItem> list) {
   /* String id ="";
    List<ItemsItem> listadd = new ArrayList<>();

    if(category.equals("THE TIME")){
      id = "70";
    }else if(category.equals("THE DIRECT")){
      id = "56";
    }else if(category.equals("THE SELECTION")){
      id = "77";
    }else if(category.equals("Travel")){
      id = "24";
    }
    else if(category.equals("Hotels")){
      id = "57";
    }
    else if(category.equals("Hotels Plus")){
      id = "25";
    }
    else if(category.equals("Cruises & Trains")){
      id = "49";
    }
    else if(category.equals("Packages")){
      id = "50";
    }
    else if(category.equals("Services")){
      id = "53";
    }
    else if(category.equals("Wine & Dine")){
      id = "5";
    }
    else if(category.equals("American")){
      id = "17";
    }
    else if(category.equals("Asian")){
      id = "18";
    }
    else if(category.equals("Australian")){
      id = "44";
    }
    else if(category.equals("Chinese")){
      id = "43";
    }
    else if(category.equals("French")){
      id = "32";
    }
    else if(category.equals("Fusion")){
      id = "28";
    }
    else if(category.equals("Indian")){
      id = "46";
    }
    else if(category.equals("International")){
      id = "34";
    }
    else if(category.equals("Italian")){
      id = "29";
    }
    else if(category.equals("Japanese")){
      id = "42";
    }
    else if(category.equals("Mediterranean")){
      id = "33";
    }
    else if(category.equals("Mexican")){
      id = "80";
    }
    else if(category.equals("Modern European")){
      id = "30";
    }
    else if(category.equals("Western")){
      id = "31";
    }
    else if(category.equals("Wholesome")){
      id = "54";
    }
    else if(category.equals("Reservations")){
      id = "58";
    }
    else if(category.equals("Beverages")){
      id = "83";
    }
    else if(category.equals("Dessert")){
      id = "84";
    }
    else if(category.equals("German")){
      id = "86";
    }
    else if(category.equals("Shopping")){
      id = "4";
    }
    else if(category.equals("Fashion")){
      id = "10";
    }
    else if(category.equals("Floral")){
      id = "7";
    }
    else if(category.equals("Fragrance")){
      id = "37";
    }
    else if(category.equals("Gourmet")){
      id = "47";
    }
    else if(category.equals("Grocery")){
      id = "38";
    }
    else if(category.equals("Lifestyle & Décor")){
      id = "81";
    }
    else if(category.equals("Skin Care")){
      id = "48";
    }
    else if(category.equals("Wellness")){
      id = "6";
    }
    else if(category.equals("Aesthetics")){
      id = "12";
    }
    else if(category.equals("Enrichment Classes")){
      id = "13";
    }
    else if(category.equals("Gym")){
      id = "14";
    }
    else if(category.equals("Health")){
      id = "15";
    }
    else if(category.equals("Meditation & Mindfulness")){
      id = "36";
    }
    else if(category.equals("Spa")){
      id = "16";
    }
    else if(category.equals("Flash Sales ")){
      id = "23";
    }



    for (ItemsItem item : list){
      for (CustomAttributesItem customAttribute : item.getCustomAttributes()) {
        if (customAttribute.getAttributeCode().equals("category_ids")) {
          JSONArray jsonArray = null;
          try {
            jsonArray = new JSONArray(customAttribute.getValue().toString());
            String[] strArr = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
              strArr[i] = jsonArray.getString(i);
            }
            if(Arrays.asList(strArr).contains(id)){
              listadd.add(item);
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }


        }
      }
    }
    if(listadd.size() == 0){
        //this.list = list;
    }else{
      this.list = listadd;
    }

    notifyDataSetChanged(); */

    this.list = list;
    notifyDataSetChanged();
  }

  @Override
  public int getItemViewType(int position) {
    ItemsItem item = list.get(position);
    if (item.getPillarId() != null && item.getPillarId().equals(Constants.E_STORE)) {
      return ESTORE_PRODUCT_VIEW;
    } else {
      return PRODUCT_VIEW;
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == ESTORE_PRODUCT_VIEW) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estore, parent, false);
      VHEstoreProduct vhEstoreProduct;
      if(this.iOnClickAddToCart!=null&&this.iOnClickBuyNow!=null){
        vhEstoreProduct = new VHEstoreProduct(view,this.iOnClickAddToCart,this.iOnClickBuyNow);
      }else {
        vhEstoreProduct = new VHEstoreProduct(view);
      }
      vhEstoreProduct.itemView.setOnClickListener(v -> {
        if (actionListener != null && vhEstoreProduct.getAdapterPosition() != RecyclerView.NO_POSITION) {
          actionListener.onItemClicked(list.get(vhEstoreProduct.getAdapterPosition()),
              vhEstoreProduct.getAdapterPosition());
        }
      });

      vhEstoreProduct.ivFav.setOnClickListener(v -> {
        if (actionListener != null && vhEstoreProduct.getAdapterPosition() != RecyclerView.NO_POSITION) {
          actionListener.onFavItemClicked(list.get(vhEstoreProduct.getAdapterPosition()),
              vhEstoreProduct.getAdapterPosition());
        }
      });

      return vhEstoreProduct;
    } else if (viewType == PRODUCT_VIEW) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended_product, parent, false);
      final VHProduct vhProduct = new VHProduct(view);
      vhProduct.itemView.setOnClickListener(view1 -> {
        if (actionListener != null && vhProduct.getAdapterPosition() != RecyclerView.NO_POSITION) {
          actionListener.onItemClicked(list.get(vhProduct.getAdapterPosition()),
              vhProduct.getAdapterPosition());
        }
      });

      vhProduct.ivFav.setOnClickListener(v -> {
        if (actionListener != null && vhProduct.getAdapterPosition() != RecyclerView.NO_POSITION) {
          actionListener.onFavItemClicked(list.get(vhProduct.getAdapterPosition()),
              vhProduct.getAdapterPosition());
        }
      });

      return vhProduct;
    } else {
      throw new RuntimeException("Unknown ViewType");
    }
  }
  public List<ItemsItem> getData(){
    return  this.list;
  }
  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof VHProduct) {
      VHProduct vhProduct = (VHProduct) holder;
      vhProduct.bind(list.get(position));
    }

    if (holder instanceof VHEstoreProduct) {
      VHEstoreProduct vhEstoreProduct = (VHEstoreProduct) holder;
      vhEstoreProduct.bind(list.get(position),isEnableBuyNow);
    }
  }

  public void setEnableBuyNow(boolean isEnableBuyNow){
    this.isEnableBuyNow = isEnableBuyNow;
  }

  @Override
  public int getItemCount() {
    return list.size();
  }


}

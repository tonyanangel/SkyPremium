package com.skypremiuminternational.app.data.network.request;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCommentRequest {

  String rating;
  String nickName;
  String title;
  String detail;
  String productId;
  String itemId;
  String orderId;



  List<String> nameImage;
  List<String> listBase64;

  public List<String> getListBase64() {
    return listBase64;
  }

  public void setListBase64(List<String> listBase64) {
    this.listBase64 = listBase64;
  }


  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }



  public List<String> getNameImage() {
    return nameImage;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public void setNameImage(List<String> nameImage) {
    this.nameImage = nameImage;
  }

  public Map<String,String> toMap (){
    Map<String,String> map = new HashMap<>();
    map.put("rating",getRating());
    map.put("nickname",getNickName());
    map.put("title",getTitle());
    map.put("detail",getDetail());
    map.put("item_id",getItemId());
    int pos = 0;
    for(String s : getNameImage()){

      map.put("image["+pos+"]",s);
      Log.e("image",""+s);
      pos++;
    }

    for(String s : getListBase64()){

      map.put("image_base64["+pos+"]",s);
      Log.e("image_base64",""+s);
      pos++;
    }

    return map;
  }
}

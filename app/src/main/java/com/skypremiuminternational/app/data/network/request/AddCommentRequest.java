package com.skypremiuminternational.app.data.network.request;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCommentRequest {

  String rating;
  String nickName;
  String title;
  String detail;
  String product_id;
  String order_id;

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

  public String getProductId() {
    return product_id;
  }

  public void setProductId(String product_id) {
    this.product_id = product_id;
  }

  public String getOrderId() {
    return order_id;
  }

  public void setOrderId(String order_id) {
    this.order_id = order_id;
  }

  public Map<String,String> toMap (){
    Map<String,String> map = new HashMap<>();
    map.put("rating",getRating());
    map.put("nickname",getNickName());
    map.put("title",getTitle());
    map.put("detail",getDetail());
    map.put("product_id",getProductId());
    map.put("order_id",getOrderId());
    int pos = 0;
    for(String s : getListBase64()){

      map.put("image_base64["+pos+"]",s);
      Log.e("image_base64",""+s);
      pos++;
    }

    return map;
  }
}

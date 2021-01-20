package com.skypremiuminternational.app.app.features.estore.detail.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.detail.EstoreDetailActivity;
import com.skypremiuminternational.app.app.features.estore.detail.PageCallback;
import com.skypremiuminternational.app.app.model.CommentPage;

import java.util.List;


public class CommentPageAdapter extends RecyclerView.Adapter<CommentPageViewHolder> {


  Context context;
  List<CommentPage> listData;
  int currentPage =1 ;
  private int countPage;
  private int maxPage;
  PageCallback pageCallback ;
  public CommentPageAdapter(Context context, PageCallback pageCallback) {
    this.pageCallback = pageCallback;
    this.context = context;
  }

  @NonNull
  @Override
  public CommentPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.item_page_hightlight,parent ,false);
    CommentPageViewHolder holder = new CommentPageViewHolder(view,context);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull CommentPageViewHolder holder, int position) {
    holder.bind(listData.get(position),position,this,pageCallback);
  }

  @Override
  public int getItemCount() {
    if(listData!=null)
      return listData.size();
    return 0;
  }

  public void setNormalAll(){
    for(CommentPage page : listData){
      page.setHightLight(false);
    }
  }
  public List<CommentPage> getListData(){
      return  this.listData;
  }

  public void setCurrentPage(int currentPage){
    this.currentPage = currentPage;
    initListPage(currentPage);
  }
  public void nextPage(){
    this.currentPage++;
    if(currentPage>countPage){
      this.currentPage = countPage;
    }
    initListPage(this.currentPage);
  }
  public void prePage(){
    this.currentPage--;
    if(this.currentPage<=0){
      this.currentPage = 1 ;
    }
    initListPage(this.currentPage);
  }

  public int getCurrentPage(){
    return this.currentPage;
  }


  private void initListPage(int currentPage){
    int minCurrentPage = currentPage - (maxPage/2);
    int maxCurrentPage = currentPage + (maxPage/2);
    pageCallback.renderNewPage(currentPage);

    Log.d("PAGE-T minCurrentPage",""+minCurrentPage);
    Log.d("PAGE-T currentPage",""+currentPage);
    Log.d("PAGE-T maxPage",""+maxPage);
    Log.d("PAGE-T countPage",""+countPage);

    if(minCurrentPage<=0){
      for(int i =0 ;i<listData.size();i++){
        listData.get(i).setNumberPage(i+1);
      }
      setNormalAll();
      for(CommentPage page : listData){
        if(page.getNumberPage()==currentPage){
          page.setHightLight(true);
        }
      }
    }else if(currentPage+2>=countPage+1){
      if(maxPage==1&&countPage==1&&currentPage==1&&maxCurrentPage==1){
        listData.get(0).setNumberPage(1);
      }

      setNormalAll();
      for(CommentPage page : listData){
        if(page.getNumberPage()==currentPage){
          page.setHightLight(true);
        }
      }
    }
    else {
      for(int i =0 ;i<listData.size();i++){
        listData.get(i).setNumberPage(minCurrentPage);
        minCurrentPage++;
      }
      setNormalAll();
      for(CommentPage page : listData){
        if(page.getNumberPage()==currentPage){
          page.setHightLight(true);
        }
      }
    }
    notifyDataSetChanged();
  }





  public void setListData(List<CommentPage> listData,int countPage,int maxPage) {
    this.listData = listData;
    this.countPage = countPage;
    this.maxPage = maxPage;

    notifyDataSetChanged();
  }
}

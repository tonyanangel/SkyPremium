package com.skypremiuminternational.app.app.features.estore.detail.review.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.comment_rating.Comment;

import java.util.List;

public class ReviewImageAdapter extends RecyclerView.Adapter<ReviewImageViewHolder> {


  Context context;
  List<String> listUrl;
  ViewPager vpgImageComment;
  FragmentManager fragmentManager;
  private Comment comment;


  public ReviewImageAdapter(Context context, ViewPager vpgImageComment, FragmentManager fragmentManager) {
    this.context = context;
    this.vpgImageComment = vpgImageComment;
    this.fragmentManager =fragmentManager;
  }

  public ReviewImageAdapter(Context context,FragmentManager fragmentManager) {
    this.context = context;
    this.fragmentManager =fragmentManager;
  }

  @NonNull
  @Override
  public ReviewImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(R.layout.item_image_page,parent,false);
    ReviewImageViewHolder holder = new ReviewImageViewHolder(view,fragmentManager);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ReviewImageViewHolder holder, int position) {
    holder.bind(listUrl.get(position),vpgImageComment,position,comment);
  }

  @Override
  public int getItemCount() {
    if(listUrl!=null)
      return listUrl.size();
    return 0;
  }


  public void setData(Comment comment){
    this.listUrl =  comment.getFullImagerURL();
    this.comment = comment;
    notifyDataSetChanged();
  }
}

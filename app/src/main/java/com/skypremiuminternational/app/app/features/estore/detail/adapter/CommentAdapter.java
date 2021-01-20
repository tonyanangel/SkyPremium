package com.skypremiuminternational.app.app.features.estore.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.detail.viewholder.CommentViewHolder;
import com.skypremiuminternational.app.domain.models.comment_rating.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

  Context context;
  List<Comment> commentList;
  FragmentManager  fragmentManager;
  List<String> listData;

  public CommentAdapter(Context context, List<Comment> commentList,
                        FragmentManager  fragmentManager) {
    this.context = context;
    this.commentList = commentList;
    this.fragmentManager=fragmentManager;
  }

  public CommentAdapter(Context context,FragmentManager  fragmentManager) {
    this.context = context;
    this.fragmentManager=fragmentManager;

    listData = new ArrayList<>();



  }


  @NonNull
  @Override
  public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.item_comment_rating,parent ,false);
    CommentViewHolder holder = new CommentViewHolder(view, context,fragmentManager);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
    holder.bind(commentList.get(position));

  }

  @Override
  public int getItemCount() {
    if(commentList!=null)
      return commentList.size();
    return 0;
  }

  public void setCommentList(List<Comment> commentList) {
    this.commentList = commentList;
    notifyDataSetChanged();
  }
}

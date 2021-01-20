package com.skypremiuminternational.app.app.features.estore.detail.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.detail.PageCallback;
import com.skypremiuminternational.app.app.model.CommentPage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentPageViewHolder extends RecyclerView.ViewHolder {


  @BindView(R.id.tvPage)
  TextView tvPage;

  Context context;

  public void bind(CommentPage page , int position, CommentPageAdapter adapter, PageCallback pageCallback){
    tvPage.setText(""+page.getNumberPage());
    if(page.isHightLight()){
      tvPage.setTextColor(Color.WHITE);
      tvPage.setBackground(context.getDrawable(R.drawable.bg_page_btn_hightlight));
    }else {
      tvPage.setTextColor(Color.GRAY);
      tvPage.setBackground(null);
    }
    tvPage.setOnClickListener(v -> {
        adapter.setNormalAll();
        adapter.setCurrentPage(page.getNumberPage());
        pageCallback.renderNewPage(page.getNumberPage());
    });
  }


  public CommentPageViewHolder(@NonNull View itemView, Context context) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    this.context = context;
  }
}

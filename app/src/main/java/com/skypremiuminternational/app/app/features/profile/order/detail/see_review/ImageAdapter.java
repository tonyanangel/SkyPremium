package com.skypremiuminternational.app.app.features.profile.order.detail.see_review;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

  Fragment context;
  List<String> listData;

  final int ITEM_NORMAL =0 ;
  final int ADD_BTN =1 ;
  final int ITEM_COUNT =2 ;

  static boolean isEdit = true;

  public ImageAdapter(Fragment context) {
    this.context = context;
  }

  public ImageAdapter(Fragment context, List<String> listData) {
    this.context = context;
    this.listData = listData;
  }

  @NonNull
  @Override
  public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_image, parent, false);
      view.setTag("NORMAL");
      final ImageViewHolder holder = new ImageViewHolder(view);
      return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
    holder.bind(listData.get(position));


  }

  @Override
  public int getItemCount() {
    if(listData!=null){
      return listData.size();
    }
    return 0;
  }

  public void setData(List<String> listData) {
    this.listData = new ArrayList<>();
    this.listData = listData;
    notifyDataSetChanged();
  }


  public class ImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img)
    ImageView imageView;

    public void bind(String url) {

        Picasso.get().load(url)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .resize(50,50)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder).into(imageView);

    }

    public ImageViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }
  }
}
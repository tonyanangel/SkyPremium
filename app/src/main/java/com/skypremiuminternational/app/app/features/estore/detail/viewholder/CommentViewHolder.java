package com.skypremiuminternational.app.app.features.estore.detail.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.estore.detail.review.ImageReviewDialogFragment;
import com.skypremiuminternational.app.app.features.estore.detail.review.adapter.ReviewImageAdapter;
import com.skypremiuminternational.app.domain.models.comment_rating.Comment;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.ll_comment)
  LinearLayout llComment;

  @BindView(R.id.tvReviewerName)
  TextView tvReviewerName;
  @BindView(R.id.tvReviewDate)
  TextView tvReviewDate;
  @BindView(R.id.tv_content_comment)
  TextView tvContentComment;

  @BindView(R.id.rvImagePage)
  RecyclerView rvImagePage;
  @BindView(R.id.rtStar)
  ScaleRatingBar rtStar;

  Context context;

  FragmentManager fragmentManager;
  ReviewImageAdapter reviewImagePagerAdapter;
  Comment comment;

 public void bind(Comment comment){
   llComment.setOnClickListener(v -> {
      if(comment.getImage().size()>0){
       ImageReviewDialogFragment fragment = ImageReviewDialogFragment.newInstance(comment);
       fragment.show(fragmentManager,"IMG_REVIEW");
      }
   });


   String names[] = comment.getNickName().split(" ");
   String finalName = "";
   for(String name :  names){
     String temp ="";
     if(name.length()>=2)
      temp =   name.replace(name.substring(1,name.length()),makeSecretCharacter(name.length()-1));
     else
       temp = name;
     finalName += temp + " ";
   }

   tvReviewerName.setText(finalName);
   tvReviewDate.setText(parseDateFormat(comment.getCreateAt()));
   tvContentComment.setText(comment.getDetail());
   reviewImagePagerAdapter.setData(comment);
   if(!comment.getRatingValue().isEmpty()){
    rtStar.setRating(Float.parseFloat(comment.getRatingValue())/2f);
   }
   else {
     rtStar.setRating(0.5f);
   }

 }
 String makeSecretCharacter(int lenght){
   String s="";
   for(int i = 0 ;i < lenght ; i++ ){
     s+="*";
   }
   return s ;
 }

  public CommentViewHolder(@NonNull View itemView , Context context, FragmentManager  fragmentManager) {
    super(itemView);
    ButterKnife.bind(this,itemView);
    this.context = context;
    this.fragmentManager = fragmentManager;


    reviewImagePagerAdapter = new ReviewImageAdapter(context,fragmentManager);
    rvImagePage.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
    rvImagePage.setAdapter(reviewImagePagerAdapter);
  }
  private String parseDateFormat(String time) {
    String inputPattern = "yyyy-MM-dd HH:mm:ss";
    String outputPattern = "dd MMM yyyy";
    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
    Date date = null;
    String str = null;

    try {
      date = inputFormat.parse(time);
      str = outputFormat.format(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return str;
  }

}

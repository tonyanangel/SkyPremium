package com.skypremiuminternational.app.app.utils;

import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.executor.PostExecutionThread;
import com.skypremiuminternational.app.domain.executor.ThreadExecutor;
import com.skypremiuminternational.app.domain.models.comment_rating.RatingOption;

import javax.inject.Inject;

public class RatingUtils {



  public static String rating(float point, RatingOption ratingOption){


      int ratingPoint = (int) (point/0.5f);
      for(RatingOption.RatingItem item : ratingOption.getItems()){
        if(item.getRatingCode().equalsIgnoreCase("Overall Quality")){
          if(ratingPoint==Integer.parseInt(item.getValue())){
            return item.getOptionId();
          }
        }
      }

      return null;
  }

  public static float getRating(String point){
      float displayPoint = 5f;

      displayPoint = Float.parseFloat(point)/2;




      return displayPoint;
  }
  public static float getRatingRound(final String point){
    float displayPoint = 5f;

    displayPoint = Float.parseFloat(point)/2;

    if(displayPoint%0.5f!=0){
      int iDisplayPoint  = (int) displayPoint;
      float fDisplayPoint =  iDisplayPoint+0.5f;
      return fDisplayPoint;
    }


    return displayPoint;
  }

  public static float getRating(float point){
    float displayPoint = 5f;

    displayPoint =point/2;




    return displayPoint;
  }
  public static float getRatingRound(final float point){
    float displayPoint = 5f;

    displayPoint = point/2;

    if(displayPoint%0.5f!=0){
      int iDisplayPoint  = (int) displayPoint;
      float fDisplayPoint =  iDisplayPoint+0.5f;
      return fDisplayPoint;
    }


    return displayPoint;
  }
}

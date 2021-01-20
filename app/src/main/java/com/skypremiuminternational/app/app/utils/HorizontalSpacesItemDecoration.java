package com.skypremiuminternational.app.app.utils;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by johnsonmaung on 8/20/17.
 */

public class HorizontalSpacesItemDecoration extends RecyclerView.ItemDecoration {

  private int spacing;
  private boolean includeTop;

  public HorizontalSpacesItemDecoration(int spacing, boolean includeTop) {
    this.spacing = spacing;
    this.includeTop = includeTop;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                             RecyclerView.State state) {
    int position = parent.getChildAdapterPosition(view); // item position
    if (position == 0) {
      outRect.left = spacing; // item left
    }
    if (includeTop) {
      outRect.top = spacing; // item top
      outRect.bottom = spacing; // item bottom
    }
    outRect.right = spacing; // item right
  }
}

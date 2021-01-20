package com.skypremiuminternational.app.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class AlbumnItemDecoration extends RecyclerView.ItemDecoration {

  Bitmap bitmap;
  int bitmap_w, bitmap_h;
  Rect rectSrc;
  private Paint paintBlue, paintRed, paintBorder;
  private int offset;

  public AlbumnItemDecoration(Context c, int offset) {
    this.offset = offset;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    outRect.set(offset, offset, offset, offset);
  }

  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDraw(c, parent, state);

//        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
//
//        c.drawRect(
//                0,
//                0,
//                c.getWidth(),
//                c.getHeight(),
//                paintBorder);
//
//        for(int i=0; i<parent.getChildCount(); i++){
//            final View child = parent.getChildAt(i);
//            c.drawRect(
//                    layoutManager.getDecoratedLeft(child),
//                    layoutManager.getDecoratedTop(child),
//                    layoutManager.getDecoratedRight(child),
//                    layoutManager.getDecoratedBottom(child),
//                    paintBlue);
//            c.drawRect(
//                    layoutManager.getDecoratedLeft(child) + offset,
//                    layoutManager.getDecoratedTop(child) + offset,
//                    layoutManager.getDecoratedRight(child) - offset,
//                    layoutManager.getDecoratedBottom(child) - offset,
//                    paintRed);
//
//        }

  }

//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
//        c.drawBitmap(bitmap,
//                rectSrc,
//                new Rect(
//                        c.getWidth()-(2*bitmap_w),
//                        c.getHeight()-(2*bitmap_h),
//                        c.getWidth(),
//                        c.getHeight()),
//                null);
//    }
}

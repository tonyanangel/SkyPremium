package com.skypremiuminternational.app.app.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ExpandableWebView extends ElementView {

  private ExpandableWebView.OnExpandListener onExpandListener;
  private TimeInterpolator collapseInterpolator;
  private TimeInterpolator expandInterpolator;
  private long animationDuration;
  private boolean animating;
  private boolean expanded = true;
  private final int collapsedHeight = 230;

  public ExpandableWebView(Context context) {
    super(context, null);
  }

  public ExpandableWebView(Context context, AttributeSet attrs) {
    super(context, attrs, null);
    TypedArray
        attributes = context.obtainStyledAttributes(attrs,
        R.styleable.ExpandableWebView);
    this.animationDuration = (long) attributes.getInt(
        R.styleable.ExpandableWebView_animation_duration, 750);
    attributes.recycle();
    this.expandInterpolator = new AccelerateDecelerateInterpolator();
    this.collapseInterpolator = new AccelerateDecelerateInterpolator();
  }

  public ExpandableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr, null);
    TypedArray
        attributes = context.obtainStyledAttributes(attrs,
        R.styleable.ExpandableWebView, defStyleAttr, 0);
    this.animationDuration = (long) attributes.getInt(
        R.styleable.ExpandableWebView_animation_duration, 750);
    attributes.recycle();
    this.expandInterpolator = new AccelerateDecelerateInterpolator();
    this.collapseInterpolator = new AccelerateDecelerateInterpolator();
  }

  @Override
  public void render() {
    setOrientation(VERTICAL);
  }

  public void setText(String html) {

    String header =
        "<html><head><style type=\"text/css\">@font-face {font-family: Lato;src: url(\"fonts/Lato-Medium.ttf\")} [style] {\n"
            + "font-family : Lato !important; "
            + "font-size : small !important; "
            + "text-align: justify !important;"
            + "}"
            + " ul {"
            + "    margin: auto;\n"
            + "    border: 3px solid #F9F9F9;\n"
            + "    width: 90%;\n"
            + "    padding: 2px;\n"
            + "}"
            + "</style></head><body>";

    Document document = Jsoup.parse(header + html);

    Element element = document.body();
    element.attr("style", "text-align: center;background-color: #F9F9F9; line-height: 1.7em;");
    setElement(document);

    Utils.appendWebView(this, getElement());
    invalidate();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
  }

  public boolean toggle() {
    return this.expanded ? this.collapse() : this.expand();
  }

  public boolean expand() {
    if (!this.expanded && !this.animating) {
      this.animating = true;
      if (this.onExpandListener != null) {
        this.onExpandListener.onExpand(this);
      }
      this.measure(MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), MeasureSpec.EXACTLY),
          MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

      int expandedHeight = this.getMeasuredHeight();

      ValueAnimator
          valueAnimator = ValueAnimator.ofInt(collapsedHeight, expandedHeight);
      valueAnimator.addUpdateListener(animation -> {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = (Integer) animation.getAnimatedValue();
        setLayoutParams(layoutParams);
      });
      valueAnimator.addListener(new AnimatorListenerAdapter() {
        public void onAnimationEnd(Animator animation) {
          ViewGroup.LayoutParams layoutParams = getLayoutParams();
          layoutParams.height = WRAP_CONTENT;
          setLayoutParams(layoutParams);
          expanded = true;
          animating = false;
        }
      });
      valueAnimator.setInterpolator(this.expandInterpolator);
      valueAnimator.setDuration(this.animationDuration).start();
      return true;
    } else {
      return false;
    }
  }

  public boolean collapse() {
    if (this.expanded && !this.animating) {
      this.animating = true;
      if (this.onExpandListener != null) {
        this.onExpandListener.onCollapse(this);
      }

      int expandedHeight = this.getMeasuredHeight();

      ValueAnimator valueAnimator =
          ValueAnimator.ofInt(expandedHeight, this.collapsedHeight);
      valueAnimator.addUpdateListener(animation -> {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = (Integer) animation.getAnimatedValue();
        setLayoutParams(layoutParams);
      });
      valueAnimator.addListener(new AnimatorListenerAdapter() {
        public void onAnimationEnd(Animator animation) {
          ViewGroup.LayoutParams layoutParams = getLayoutParams();
          layoutParams.height = collapsedHeight;
          setLayoutParams(layoutParams);
          expanded = false;
          animating = false;
        }
      });
      valueAnimator.setInterpolator(this.collapseInterpolator);
      valueAnimator.setDuration(this.animationDuration).start();
      return true;
    } else {
      return false;
    }
  }

  public void setAnimationDuration(long animationDuration) {
    this.animationDuration = animationDuration;
  }

  public void setOnExpandListener(ExpandableWebView.OnExpandListener onExpandListener) {
    this.onExpandListener = onExpandListener;
  }

  public ExpandableWebView.OnExpandListener getOnExpandListener() {
    return this.onExpandListener;
  }

  public void setInterpolator(TimeInterpolator interpolator) {
    this.expandInterpolator = interpolator;
    this.collapseInterpolator = interpolator;
  }

  public void setExpandInterpolator(TimeInterpolator expandInterpolator) {
    this.expandInterpolator = expandInterpolator;
  }

  public TimeInterpolator getExpandInterpolator() {
    return this.expandInterpolator;
  }

  public void setCollapseInterpolator(TimeInterpolator collapseInterpolator) {
    this.collapseInterpolator = collapseInterpolator;
  }

  public TimeInterpolator getCollapseInterpolator() {
    return this.collapseInterpolator;
  }

  public boolean isExpanded() {
    return this.expanded;
  }

  public interface OnExpandListener {
    void onExpand(ExpandableWebView var1);

    void onCollapse(ExpandableWebView var1);
  }
}

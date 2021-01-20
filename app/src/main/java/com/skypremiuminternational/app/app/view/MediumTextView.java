package com.skypremiuminternational.app.app.view;

import android.content.Context;
import android.util.AttributeSet;

import com.skypremiuminternational.app.app.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import timber.log.Timber;

/**
 * Created by angebagui on 06/08/2016.
 */

public class MediumTextView extends ElementView {

  public MediumTextView(Context context) {
    super(context, null);
  }

  public MediumTextView(Context context, AttributeSet attrs) {
    super(context, attrs, null);
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
            + "}</style></head><body>";
    Document document = Jsoup.parse(header + html);

    Element element = document.body();
    element.attr("style", "background-color: #F9F9F9; line-height: 1.7em;");
    Timber.e(element.toString());
    setElement(document);

    if (getElement().children() != null && getElement().children().size() > 0) {
      Utils.appendView(this, getElement().children());
    } else {
      Utils.appendView(this, getElement());
    }
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
}

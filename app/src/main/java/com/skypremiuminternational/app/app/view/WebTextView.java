package com.skypremiuminternational.app.app.view;

import android.content.Context;
import android.util.AttributeSet;

import com.skypremiuminternational.app.app.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import timber.log.Timber;

public class WebTextView extends ElementView {

  public WebTextView(Context context) {
    super(context, null);
  }

  public WebTextView(Context context, AttributeSet attrs) {
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
}

package com.skypremiuminternational.app.app.utils;

import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;

import com.skypremiuminternational.app.app.features.checkout.steptwo.CheckoutReviewFragment;

public class CustomNoUnderLineLinkUtils {

  public static Spannable removeUnderlines(Spannable p_Text) {
    URLSpan[] spans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);
    for (URLSpan span : spans) {
      int start = p_Text.getSpanStart(span);
      int end = p_Text.getSpanEnd(span);
      p_Text.removeSpan(span);
      span = new CheckoutReviewFragment.URLSpanNoUnderline(span.getURL());
      p_Text.setSpan(span, start, end, 0);
    }
    return p_Text;
  }

  public static class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String p_Url) {
      super(p_Url);
    }

    public void updateDrawState(TextPaint p_DrawState) {
      super.updateDrawState(p_DrawState);
      p_DrawState.setUnderlineText(false);
    }
  }
}

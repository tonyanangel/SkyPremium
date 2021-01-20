package com.skypremiuminternational.app.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skypremiuminternational.app.R;

import timber.log.Timber;

/**
 * Created by aeindraaung on 3/8/18.
 */

public class SkyTextInputEditEventLayout extends LinearLayout {
  TextView tv;
  TextView dollarTv;
  EditText edt;
  View v;

  int hint_color;
  String text = "";
  int error_color;
  String error = "";
  int inputType = -1;
  int imeOptions = -1;
  InputFilter[] inputFilters = null;
  int maxLines = -1;
  boolean enabled = true;
  Drawable drawableRight = null;
  private Float point = 0f;
  private String latestValidAmount;

  public Float getPoint() {
    return point;
  }

  public void setPoint(String point) {
    try {
      this.point = Float.parseFloat(point);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SkyTextInputEditEventLayout(Context context) {
    super(context);
  }

  public SkyTextInputEditEventLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public SkyTextInputEditEventLayout(Context context, @Nullable AttributeSet attrs,
                                     int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    if (!isInEditMode()) {
      TypedArray typedArray =
          getContext().obtainStyledAttributes(attrs, R.styleable.SkyTextInputLayout);
      try {

        hint_color = typedArray.getColor(R.styleable.SkyTextInputLayout_hintColor,
            ContextCompat.getColor(getContext(), R.color.orchid));

        text = typedArray.getString(R.styleable.SkyTextInputLayout_android_text);

        error_color = typedArray.getColor(R.styleable.SkyTextInputLayout_errorColor,
            ContextCompat.getColor(getContext(), R.color.wineRed));
        error = typedArray.getString(R.styleable.SkyTextInputLayout_error);

        if (typedArray.hasValue(R.styleable.SkyTextInputLayout_android_inputType)) {
          inputType = (typedArray.getInt(R.styleable.SkyTextInputLayout_android_inputType, -1));
        }

        if (typedArray.hasValue(R.styleable.SkyTextInputLayout_android_enabled)) {
          enabled = typedArray.getBoolean(R.styleable.SkyTextInputLayout_android_enabled, true);
        }

        if (typedArray.hasValue(R.styleable.SkyTextInputLayout_android_drawableRight)) {
          drawableRight =
              typedArray.getDrawable(R.styleable.SkyTextInputLayout_android_drawableRight);
        }

        if (typedArray.hasValue(R.styleable.SkyTextInputLayout_android_imeOptions)) {
          imeOptions = typedArray.getInt(R.styleable.SkyTextInputLayout_android_imeOptions, -1);
        }

        if (typedArray.hasValue(R.styleable.SkyTextInputLayout_android_maxLength)) {
          inputFilters = new InputFilter[]{
              new InputFilter.LengthFilter(
                  typedArray.getInt(R.styleable.SkyTextInputLayout_android_maxLength, 0))
          };
        }

        if (typedArray.hasValue(R.styleable.SkyTextInputLayout_android_maxLines)) {
          maxLines = typedArray.getInt(R.styleable.SkyTextInputLayout_android_maxLines, 1);
        }
      } finally {
        typedArray.recycle();
      }
    }
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    inflate(getContext(), R.layout.layout_sky_dollars_input_field, this);
    tv = findViewById(R.id.tv);
    dollarTv = findViewById(R.id.tv_dollar_sign);
    edt = findViewById(R.id.edt);
    v = findViewById(R.id.v);

    tv.setTextColor(hint_color);
    v.setBackgroundColor(hint_color);
    dollarTv.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

    edt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String currentAmount = charSequence.toString();
        setHint(edt.getHint().toString());

        if (TextUtils.isEmpty(currentAmount)) {
          tv.setVisibility(View.INVISIBLE);
        } else {
          tv.setVisibility(View.VISIBLE);
        }

        try {
          if (currentAmount != null && !TextUtils.isEmpty(currentAmount)) {
            if (point < Float.parseFloat(currentAmount)) {
              setError("You can redeem up to S$" + point + " only");
              edt.setText(latestValidAmount);
              edt.setSelection(latestValidAmount.length());
            } else {
              latestValidAmount = currentAmount;
            }
          }
        } catch (Exception ignored) {
          Timber.e(ignored);
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    if (inputType != -1) edt.setInputType(inputType);
    if (imeOptions != -1) edt.setImeOptions(imeOptions);
    if (maxLines != -1) edt.setMaxLines(maxLines);
    if (inputFilters != null) edt.setFilters(inputFilters);
    if (drawableRight != null) {
      edt.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
    }
    edt.setEnabled(enabled);
    //edt.setText(text);
    //edt.setSelection(edt.getText().length());
  }

  private void setHint(String hint) {
    tv.setTextColor(hint_color);
    tv.setText(hint);
  }

  public void setText(String text) {
    this.text = text;
    edt.setText(text);
    edt.setSelection(edt.getText().length());
  }

  public String getText() {
    return edt.getText().toString();
  }

  public void setError(String error) {
    this.error = error;
    tv.setText(error);
    tv.setTextColor(error_color);
  }

  public String getError() {
    return error;
  }

  public void setInputType(int inputType) {
    this.inputType = inputType;
    edt.setInputType(inputType);
  }

  public int getInputType() {
    return edt.getInputType();
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    edt.setEnabled(enabled);
  }
}

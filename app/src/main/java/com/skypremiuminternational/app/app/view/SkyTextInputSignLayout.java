package com.skypremiuminternational.app.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.listener.ItemClickListener;
import com.skypremiuminternational.app.app.utils.listener.OnTextChangedListener;

/**
 * Created by aeindraaung on 2/7/18.
 */

public class SkyTextInputSignLayout extends LinearLayout {
  TextView tv, tvSign;
  public EditText edt;
  View v;

  int hint_color;
  String hint = "";
  String text = "";
  int error_color;
  String error = "";
  int inputType = -1;
  int imeOptions = -1;
  InputFilter[] inputFilters = null;
  int maxLines = -1;
  boolean enabled = true;
  Drawable drawableRight = null;

  private boolean isMandatory = true;
  private ItemClickListener<String> itemClickListener;
  private OnTextChangedListener onTextChangedListener;

  public SkyTextInputSignLayout(Context context) {
    super(context);
  }

  public SkyTextInputSignLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public SkyTextInputSignLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  public void setOnEditorActionListener(TextView.OnEditorActionListener listener) {
    edt.setOnEditorActionListener(listener);
  }

  private void init(AttributeSet attrs) {
    if (!isInEditMode()) {
      TypedArray typedArray =
          getContext().obtainStyledAttributes(attrs, R.styleable.SkyTextInputLayout);
      try {

        hint_color = typedArray.getColor(R.styleable.SkyTextInputLayout_hintColor,
            ContextCompat.getColor(getContext(), R.color.orchid));

        hint = typedArray.getString(R.styleable.SkyTextInputLayout_android_hint);
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

  public void setItemClickListener(ItemClickListener<String> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    inflate(getContext(), R.layout.skytextinputsignlayout, this);
    tv = findViewById(R.id.tv);
    tvSign = findViewById(R.id.tvSign);
    edt = findViewById(R.id.edt);
    v = findViewById(R.id.v);

    edt.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (view.getId() == R.id.edt) {
          if (hasFocus()) {
            if (TextUtils.isEmpty(edt.getText().toString())) {
              tv.setVisibility(View.INVISIBLE);
              tvSign.setVisibility(INVISIBLE);
            } else {
              tv.setVisibility(View.VISIBLE);
              if (isMandatory) {
                tvSign.setVisibility(VISIBLE);
              } else {
                tvSign.setVisibility(INVISIBLE);
              }
            }
          } else {

          }
        }
      }
    });

    edt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = charSequence.toString();
        tv.setTextColor(hint_color);
        v.setBackgroundColor(hint_color);
        if (edt.getHint() != null && !edt.getHint().equals("")) {
          String hintText = String.valueOf(edt.getHint());
          if (hintText.endsWith("*")) {
            hintText = hintText.substring(0, edt.getHint().length() - 2);
          }
          tv.setText(hintText);
        }

        if (TextUtils.isEmpty(text)) {
          tv.setVisibility(View.INVISIBLE);
          tvSign.setVisibility(INVISIBLE);
          if (itemClickListener != null) {
            itemClickListener.onItemClicked("empty");
          }
        } else {
          tv.setVisibility(View.VISIBLE);
          if (isMandatory) {
            tvSign.setVisibility(VISIBLE);
          } else {
            tvSign.setVisibility(INVISIBLE);
          }
        }

        if (onTextChangedListener != null) {
          onTextChangedListener.onTextChanged(charSequence.toString());
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    tv.setText(hint);

    edt.setHint(hint);
    if (inputType != -1) edt.setInputType(inputType);
    if (imeOptions != -1) edt.setImeOptions(imeOptions);
    if (maxLines != -1) edt.setMaxLines(maxLines);
    if (inputFilters != null) edt.setFilters(inputFilters);
    if (drawableRight != null) {
      edt.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
    }
    edt.setEnabled(enabled);
    edt.setText(text);
    edt.setSelection(edt.getText().length());
  }

  public void setMaxLength(int maxLength) {
    inputFilters = new InputFilter[]{
        new InputFilter.LengthFilter(maxLength)
    };

    edt.setFilters(inputFilters);
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
    edt.setHint(hint);
  }

  public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
    this.onTextChangedListener = onTextChangedListener;
  }

  public void showError(String error_text) {
    //if (!TextUtils.isEmpty(edt.getText().toString())) {
    tv.setVisibility(VISIBLE);
    tv.setTextColor(error_color);
    tvSign.setVisibility(INVISIBLE);
    //v.setBackgroundColor(error_color);
    if ((error_text != null) && (!TextUtils.isEmpty(error_text))) {
      tv.setText(error_text);
    } else {
      tv.setText(this.error);
    }
    //}
  }

  public void showError() {
    //if (!TextUtils.isEmpty(edt.getText().toString())) {
    //Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
    //edt.startAnimation(shake);
    tv.setVisibility(VISIBLE);
    tv.setTextColor(error_color);
    if (!TextUtils.isEmpty(error)) {
      tv.setText(error);
    }
    //}
  }

  public void hideError() {
    if (TextUtils.isEmpty(edt.getText().toString())) {
      tv.setVisibility(INVISIBLE);
    } else {
      tv.setVisibility(VISIBLE);
    }
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

  public void setTransformationMethod(TransformationMethod transformationMethod) {
    edt.setTransformationMethod(transformationMethod);
  }

  public boolean isMandatory() {
    return isMandatory;
  }

  public void setMandatory(boolean mandatory) {
    isMandatory = mandatory;
  }
}

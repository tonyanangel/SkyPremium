package com.skypremiuminternational.app.app.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RxEditText {

  private PublishSubject<CharSequence> textChangeSubject;

  public RxEditText(EditText editText) {
    textChangeSubject = PublishSubject.create();
    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        textChangeSubject.onNext(s);
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  public Observable<CharSequence> textChanges() {
    return textChangeSubject;
  }
}

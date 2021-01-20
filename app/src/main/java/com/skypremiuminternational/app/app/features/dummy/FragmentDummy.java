package com.skypremiuminternational.app.app.features.dummy;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skypremiuminternational.app.R;

public class FragmentDummy extends Fragment {
  AppCompatActivity mActivity;
  TextView tv;

  public static FragmentDummy newInstance(String data) {
    FragmentDummy myFragment = new FragmentDummy();

    Bundle args = new Bundle();
    args.putString("Text", data);
    myFragment.setArguments(args);
    return myFragment;
  }

  @Override
  public void onAttach(Activity activity) {
    mActivity = (AppCompatActivity) activity;
    super.onAttach(activity);
  }

  @Override
  @Nullable
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_dummy, null);
    tv = (TextView) view.findViewById(R.id.tv_Dummy);
    String text = getArguments().getString("Text", "Default");
    tv.setText(text);
    return view;
  }
}

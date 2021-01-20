package com.skypremiuminternational.app.app.features.landing.popup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skypremiuminternational.app.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class FragmentImage extends Fragment {


  @BindView(R.id.imgView)
  ImageView imageView;

  String url;

  public static FragmentImage newInstance(String url){
    FragmentImage fragmentImage = new FragmentImage();

    fragmentImage.url = url;

    return fragmentImage;
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_image,null);
    imageView = view.findViewById(R.id.imgView);


    Picasso.get().load(url).into(imageView);

    return view;
  }
}

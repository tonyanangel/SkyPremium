package com.skypremiuminternational.app.app.features.shopping.detail;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.RxBus;

import java.util.List;

/**
 * Created by johnsonmaung on 7/22/17.
 */

public class FragmentImageItem extends Fragment {

  ImageView img;

  List<String> dataset;
  int position;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    RxBus.get().register(this);
  }

  @Override
  @Nullable
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_image_item, null);
    img = (ImageView) view.findViewById(R.id.img_fragment_image_item);

    //Glide.with(getActivity()).load(dataset.get(position)).asBitmap().dontAnimate().into(img);
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.error(R.drawable.white);
    Glide.with(getActivity()).asBitmap()
        .load(dataset.get(position))
        .into(new SimpleTarget<Bitmap>() {
          @Override
          public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            img.setImageBitmap(resource); // Possibly runOnUiThread()
          }
        });

    /*img.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent mIntent = new Intent(mActivity, ImageSliderActivity.class);
        mIntent.putStringArrayListExtra("images", new ArrayList<>(dataset));
        mIntent.putExtra("position", position);
        mActivity.startActivity(mIntent);
      }
    });*/
    return view;
  }

  @Override
  public void onDestroy() {
    RxBus.get().unregister(this);
    super.onDestroy();
  }

  /*@Subscribe public void onImageReload(ImageReloadEvent imageReloadEvent) {
  }*/

  public static FragmentImageItem newInstance(List<String> dataset, int position) {
    FragmentImageItem myFragment = new FragmentImageItem();
    myFragment.dataset = dataset;
    myFragment.position = position;
    return myFragment;
  }
}

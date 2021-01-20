package com.skypremiuminternational.app.app.features.estore.detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.RxBus;
import com.skypremiuminternational.app.app.utils.URLUtils;

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
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.error(R.drawable.white);
    //Glide.with(getActivity()).load(dataset.get(position)).asBitmap().dontAnimate().into(img);
    Glide.with(getActivity())
        .load(URLUtils.formatImageURL(dataset.get(position)))
        .apply(requestOptions)
        .listener(URLUtils.getGlideListener(getActivity(),dataset.get(position),img))
        .into(img);

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
    FragmentImageItem
        myFragment = new FragmentImageItem();
    myFragment.dataset = dataset;
    myFragment.position = position;
    return myFragment;
  }
}

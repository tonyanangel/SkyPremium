package com.skypremiuminternational.app.app.features.travel.detail;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.skypremiuminternational.app.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by johnsonmaung on 7/22/17.
 */

public class TravelDetailFragmentImageItem extends Fragment {

  ImageView img;

  List<String> dataset;
  int position;

  @Override
  @Nullable
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_image_item, null);
    img = (ImageView) view.findViewById(R.id.img_fragment_image_item);
    RequestOptions requestOptions = new RequestOptions();
    requestOptions.placeholder(R.drawable.placeholder);
    requestOptions.dontAnimate();
    requestOptions.centerCrop();
    requestOptions.error(R.drawable.white);
    Glide.with(getActivity())
        .load(dataset.get(position))
        .apply(requestOptions)
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

  public static TravelDetailFragmentImageItem newInstance(List<String> dataset, int position) {
    TravelDetailFragmentImageItem myFragment = new TravelDetailFragmentImageItem();
    myFragment.dataset = dataset;
    myFragment.position = position;
    return myFragment;
  }
}

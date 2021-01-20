package com.skypremiuminternational.app.app.features.landing.popup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerActivity;
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerView;
import com.skypremiuminternational.app.R;

public class FragmentVimeo extends Fragment {
  VimeoPlayerView vimeoPlayer;
  String videoId;

  public static FragmentVimeo newInstance(String videoId){
    FragmentVimeo fragmentVimeo = new FragmentVimeo();
    fragmentVimeo.videoId =  videoId;

    return fragmentVimeo;
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_vimeo,null );
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    vimeoPlayer = view.findViewById(R.id.vimeoPlayer);
    getLifecycle().addObserver(vimeoPlayer);

//public video
    vimeoPlayer.initialize(false,Integer.parseInt(videoId));
    vimeoPlayer.pause();

    vimeoPlayer.setFullscreenClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivityForResult(VimeoPlayerActivity.createIntent(getActivity(), VimeoPlayerActivity.REQUEST_ORIENTATION_AUTO, vimeoPlayer), 1224);
      }
    });

  }


  public VimeoPlayerView getVimeoPlayer(){
    return vimeoPlayer;
  }


}
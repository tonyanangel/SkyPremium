package com.skypremiuminternational.app.app.features.landing.popup;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.play_video.PlayVideoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentVideo extends Fragment implements View.OnClickListener {

  VideoView videoView;
  static Activity activity;
  View viewPlay;
  FrameLayout frmOpacity;
  static ImageView imgPlay;
  ImageView enterFullscreen;
  String url;

  public static FragmentVideo newInstance(Activity activity, String url){
    FragmentVideo fragmentVideo = new FragmentVideo();
    fragmentVideo.activity = activity;
    fragmentVideo.url = url;
    return fragmentVideo;
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view  = inflater.inflate(R.layout.fragment_videos,null );
    ButterKnife.bind(this,view);
    videoView = view.findViewById(R.id.videoView);

    viewPlay = view.findViewById(R.id.viewPlay);
    frmOpacity = view.findViewById(R.id.frmOpacity);
    enterFullscreen = view.findViewById(R.id.enterFullscreen);
    videoView.setVisibility(View.VISIBLE);


    videoView.setBackgroundColor(Color.TRANSPARENT);
    //videoView.setVideoPath("https://res.cloudinary.com/sky-premium-sg/video/upload/v1601377229/video.mp4");
    videoView.setVideoPath(url);
    viewPlay.setOnClickListener(this);

    MediaController mediaController = new MediaController(getActivity());
    mediaController.setAnchorView(videoView);
    videoView.setMediaController(mediaController);

   /* viewPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(videoView.isPlaying()){
          videoView.pause();
          frmOpacity.setVisibility(View.VISIBLE);
          videoView.setVisibility(View.VISIBLE);

        }
        else{
          videoView.start();
          frmOpacity.setVisibility(View.INVISIBLE);
          videoView.setVisibility(View.VISIBLE);
        }
      }
    });*/
    return view;
  }



  public VideoView getVideoView(){
    return videoView;
  }
  public void pauseVideo(){
    frmOpacity.setVisibility(View.VISIBLE);
    videoView.pause();
  }

  public void showPlayer(){
    videoView.setVideoPath(url);
    videoView.setVisibility(View.VISIBLE);
  }
  public void hidePlayer(){
    videoView.setVisibility(View.INVISIBLE);
  }

  @Override
  public void onClick(View view) {
    if(videoView.isPlaying()){
      videoView.pause();
      frmOpacity.setVisibility(View.VISIBLE);
      videoView.setVisibility(View.VISIBLE);

    }
    else{
      videoView.start();
      frmOpacity.setVisibility(View.INVISIBLE);
      videoView.setVisibility(View.VISIBLE);
    }
  }

  @OnClick(R.id.enterFullscreen)
  void onClickEnterFull(){
    PlayVideoActivity.startMe(this.getActivity(),url);
  }

}
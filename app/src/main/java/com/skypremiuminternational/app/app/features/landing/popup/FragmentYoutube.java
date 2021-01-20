package com.skypremiuminternational.app.app.features.landing.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.features.landing.LandingActivity;
import com.skypremiuminternational.app.app.features.profile.ProfileActivity;

public class FragmentYoutube extends Fragment {

  static YouTubePlayerView youTubePlayerView;

  YouTubePlayer player ;
  Activity activity;
  String videoId;


  public static FragmentYoutube newInstance(Activity activity, String videoId){
    FragmentYoutube fragmentYoutube = new FragmentYoutube();
    fragmentYoutube.activity  = activity;
    fragmentYoutube.videoId = videoId;
    return fragmentYoutube;
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view  = inflater.inflate(R.layout.fragment_youtube,null );

    youTubePlayerView = view.findViewById(R.id.youtube_player_view);
    getLifecycle().addObserver(youTubePlayerView);
    youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
      @Override
      public void onYouTubePlayer(YouTubePlayer youTubePlayer) {
        youTubePlayer.loadVideo(videoId,0);
        player = youTubePlayer;
        player.pause();
      }
    });

    youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
      @Override
      public void onYouTubePlayerEnterFullScreen() {
        if(getActivity() instanceof  LandingActivity){
          ((LandingActivity)getActivity()).showFullscreen();
        }else if(getActivity() instanceof ProfileActivity){
          ((ProfileActivity)getActivity()).showFullscreen();
        }
      }

      @Override
      public void onYouTubePlayerExitFullScreen() {
        if(getActivity() instanceof  LandingActivity){
          ((LandingActivity)getActivity()).exitFullscreen();
        }else if(getActivity() instanceof ProfileActivity){
          ((ProfileActivity)getActivity()).exitFullscreen();
        }

      }
    });


    return view;
  }

  public YouTubePlayer getYouTubePlayerView(){
    return player;
  }
}

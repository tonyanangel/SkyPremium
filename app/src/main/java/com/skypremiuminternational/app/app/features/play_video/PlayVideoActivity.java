package com.skypremiuminternational.app.app.features.play_video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skypremiuminternational.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayVideoActivity extends AppCompatActivity {
  String url;

  @BindView(R.id.videoView)
  VideoView videoView;

  public static void startMe(Context context, String url){
    Intent intent = new Intent(context, PlayVideoActivity.class);
    intent.putExtra("url", url);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_video);
    ButterKnife.bind(this);


    this.url = getIntent().getStringExtra("url");
    MediaController mediaController = new MediaController(this);
    mediaController.setAnchorView(videoView);
    videoView.setMediaController(mediaController);
    videoView.setVideoPath(this.url);
    videoView.setBackgroundColor(Color.TRANSPARENT);
    videoView.start();
  }
  @OnClick(R.id.exitFull)
  void  onClickExitFull(){
      onBackPressed();
  }
}

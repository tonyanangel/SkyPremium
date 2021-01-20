package com.skypremiuminternational.app.app.features.travel.ean.suggestion;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;

public class SuggestionViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.horizontal_line)
  View horizontalLine;
  @BindView(R.id.tv_suggestion)
  TextView tvSuggestion;

  public SuggestionViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(Suggestion suggestion, int position) {
    if (position == 0) {
      horizontalLine.setVisibility(View.INVISIBLE);
    } else {
      horizontalLine.setVisibility(View.VISIBLE);
    }
    tvSuggestion.setText(suggestion.suggestion);
  }
}

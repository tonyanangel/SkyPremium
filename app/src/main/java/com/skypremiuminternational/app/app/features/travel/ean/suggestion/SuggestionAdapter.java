package com.skypremiuminternational.app.app.features.travel.ean.suggestion;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.ean.Suggestion;

import java.util.ArrayList;
import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionViewHolder> {

  private List<Suggestion> suggestions = new ArrayList<>();
  private ActionListener actionListener;

  @Override
  public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
    SuggestionViewHolder viewHolder = new SuggestionViewHolder(view);
    viewHolder.tvSuggestion.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onItemClicked(suggestions.get(viewHolder.getAdapterPosition()));
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(SuggestionViewHolder holder, int position) {
    holder.bind(suggestions.get(position), position);
  }

  @Override
  public int getItemCount() {
    return suggestions == null ? 0 : suggestions.size();
  }

  public void setSuggestions(List<Suggestion> suggestions) {
    this.suggestions = suggestions;
    notifyDataSetChanged();
  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  public interface ActionListener {
    void onItemClicked(Suggestion suggestion);
  }
}

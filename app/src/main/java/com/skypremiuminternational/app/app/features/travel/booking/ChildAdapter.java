package com.skypremiuminternational.app.app.features.travel.booking;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.ean.Child;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildViewHolder> {

  private List<Child> children;

  private ActionListener actionListener;

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  public ChildAdapter() {
    children = new ArrayList<>();
  }

  @Override
  public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
    ChildViewHolder viewHolder = new ChildViewHolder(view);
    viewHolder.tvChildAge.setOnClickListener(v -> {
      if (actionListener != null && viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onEditAgeClicked(children.get(viewHolder.getAdapterPosition()),
            viewHolder.getAdapterPosition());
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ChildViewHolder holder, int position) {
    holder.bind(children.get(position));
  }

  @Override
  public int getItemCount() {
    return children == null ? 0 : children.size();
  }

  public void setChildren(List<Child> children) {
    this.children = children;
    notifyDataSetChanged();
  }

  public interface ActionListener {
    void onEditAgeClicked(Child child, int position);
  }
}

package com.skypremiuminternational.app.app.features.profile.my_sky_dollar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.domain.models.crm.HistoryRecord;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkyDollarAdapter extends RecyclerView.Adapter<SkyDollarAdapter.SKDViewHolder> {


  List<HistoryRecord> data;


  @NonNull
  @Override
  public SKDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.item_sky_dollar,parent,false);

    SKDViewHolder viewHolder  = new SKDViewHolder(view);

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull SKDViewHolder holder, int position) {
    holder.bind(this.data.get(position));
  }

  @Override
  public int getItemCount() {
    return this.data==null ? 0 : data.size();
  }

  public void setData(List<HistoryRecord> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  public class SKDViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvValue)
    TextView tvValue;
    public void bind(HistoryRecord record){
      tvName.setText(record.getName());
      tvDate.setText(record.getTrxDate());
      tvValue.setText(record.getPoints());
    }

    public SKDViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

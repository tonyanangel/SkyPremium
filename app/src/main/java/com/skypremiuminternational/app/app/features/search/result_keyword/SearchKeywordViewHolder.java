package com.skypremiuminternational.app.app.features.search.result_keyword;

import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.RxBus;
import com.skypremiuminternational.app.domain.models.search.SearchKeyword;

/**
 * Created by johnsonmaung on 10/1/17.
 */

public class SearchKeywordViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tv_item_search_keyword)
  TextView tv;

  private String keyword;

  public SearchKeywordViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        /*Intent intent = new Intent(itemView.getContext(), HistoryDetailsActivity.class);
        intent.putExtra("History", new Gson().toJson(history));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ((Activity) itemView.getContext()).startActivityForResult(intent, 1234);*/
        RxBus.get().post(new SearchKeyword(keyword));
      }
    });
  }

  public void bind(final String item) {
    this.keyword = item;
    tv.setText(Html.fromHtml(keyword));
  }
}

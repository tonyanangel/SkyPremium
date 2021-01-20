package com.skypremiuminternational.app.app.features.faq;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skypremiuminternational.app.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by johnsonmaung on 11/15/17.
 */

public class FaqExpandableAdapter extends BaseExpandableListAdapter {

  private Context context;
  private List<String> listDataHeader;
  private HashMap<String, List<String>> listDataChild;

  public FaqExpandableAdapter(Context context, List<String> listDataHeader,
                              HashMap<String, List<String>> listChildData) {
    this.context = context;
    this.listDataHeader = listDataHeader;
    this.listDataChild = listChildData;
  }

  @Override
  public Object getGroup(int groupPosition) {
    return this.listDataHeader.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return this.listDataHeader.size();
  }

  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                           ViewGroup parent) {
    String headerTitle = (String) getGroup(groupPosition);
    if (convertView == null) {
      LayoutInflater infalInflater =
          (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.item_faq_question, null);
    }
    LinearLayout llFaqQuestion = convertView.findViewById(R.id.llFaqQuestion);
    TextView lblListHeader = convertView.findViewById(R.id.tvFaqQuestion);
    lblListHeader.setText(headerTitle);

    ImageView iv = convertView.findViewById(R.id.iv);

    if (isExpanded) {
      llFaqQuestion.setBackgroundColor(Color.WHITE);
      lblListHeader.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
      iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_remove_accent));
    } else {
      llFaqQuestion.setBackgroundColor(Color.TRANSPARENT);
      lblListHeader.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
      iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_add_accent));
    }

    return convertView;
  }

  @Override
  public Object getChild(int groupPosition, int childPosititon) {
    return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                           View convertView, ViewGroup parent) {

    final String childText = (String) getChild(groupPosition, childPosition);

    if (convertView == null) {
      LayoutInflater infalInflater =
          (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.item_faq_answer, null);
    }

    TextView txtListChild = convertView.findViewById(R.id.tvFaqAnswer);

    txtListChild.setText(Html.fromHtml(childText).toString().trim());
    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
}


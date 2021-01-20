package com.skypremiuminternational.app.app.features.memership_services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.RxBus;
import com.skypremiuminternational.app.domain.models.membership.MembershipEvent;
import com.skypremiuminternational.app.domain.models.membership.Title;

import java.util.HashMap;
import java.util.List;

/**
 * Created by johnsonmaung on 11/15/17.
 */

public class MembershipExpandableAdapter extends BaseExpandableListAdapter {

  private Context context;
  private List<Title> listDataHeader;
  private HashMap<String, List<String>> listDataChild;

  public MembershipExpandableAdapter(Context context, List<Title> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
    this.context = context;
    this.listDataHeader = listDataHeader;
    this.listDataChild = listChildData;
  }

  @Override
  public Title getGroup(int groupPosition) {
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
    Title headerTitle = (Title) getGroup(groupPosition);
    if (convertView == null) {
      LayoutInflater infalInflater =
          (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.item_membership_title, null);
    }

    ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
    TextView tv = (TextView) convertView.findViewById(R.id.tvTitle);
    View view = (View) convertView.findViewById(R.id.vv);

    iv.setImageResource(headerTitle.getIcon());
    tv.setText(headerTitle.getTitle());

    if (isExpanded) {
      //iv.setImageResource(headerTitle.getIcon_expended());
      view.setVisibility(View.VISIBLE);
    } else {
      //iv.setImageResource(headerTitle.getIcon());
      view.setVisibility(View.GONE);
    }

    return convertView;
  }

  @Override
  public Object getChild(int groupPosition, int childPosititon) {
    return this.listDataChild.get(this.listDataHeader.get(groupPosition).getTitle())
        .get(childPosititon);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                           View convertView, ViewGroup parent) {

    final String childText = (String) getChild(groupPosition, childPosition);

    if (convertView == null) {
      LayoutInflater infalInflater =
          (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.item_membership_description, null);
    }

    TextView tvSubmit = (TextView) convertView.findViewById(R.id.tvSubmit);
    final EditText edtDescription = (EditText) convertView.findViewById(R.id.edtDescription);

    edtDescription.setText(childText);
    edtDescription.setSelection(edtDescription.getText().length());

    edtDescription.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        RxBus.get().post(new MembershipEvent());
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    tvSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!TextUtils.isEmpty(edtDescription.getText().toString())) {
          Intent intent = new Intent(Intent.ACTION_SENDTO);
          intent.setType("plain/text");
          intent.setData(Uri.parse("mailto:"));
          intent.putExtra(Intent.EXTRA_EMAIL, new String[]{BuildConfig.EMAIL});
          intent.putExtra(Intent.EXTRA_SUBJECT, getGroup(groupPosition).getTitle());
          intent.putExtra(Intent.EXTRA_TEXT, edtDescription.getText().toString());
          context.startActivity(Intent.createChooser(intent, "Email via..."));
        }
      }
    });

    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return this.listDataChild.get(this.listDataHeader.get(groupPosition).getTitle()).size();
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


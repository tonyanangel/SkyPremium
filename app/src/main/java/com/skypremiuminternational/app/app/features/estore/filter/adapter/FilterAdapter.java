package com.skypremiuminternational.app.app.features.estore.filter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.j256.ormlite.stmt.query.In;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.AnimationUtil;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.MetricsUtils;
import com.skypremiuminternational.app.app.utils.listener.AnimationListener;
import com.skypremiuminternational.app.domain.models.category.TreeItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
/**
 *  Create by Toan Tran
 * */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder>{

  public static final int LABEL_EXPAND_ITEM=1;
  public static final int CHECK_EXPAND_ITEM=3;
  public static final int LABEL_ITEM=0;
  public static final int CHECK_ITEM=2;
  public static final int SEEK_BAR=4;
  public static final int TOP_ITEM=5;
  public static final int BOTTOM_ITEM=6;
  public static final int BLANK_ITEM=7;

  public static int MAX_LEVEL=4;
  public static final int FINAL_MAX_LEVEL=MAX_LEVEL+2;


  List<TreeItem> itemList;

  Context context;
  IOnClickItemFilterAdapter callBack ;
  Animation amin;

  float maxPriceFirst=0;
  float minPriceFirst=0;
  float maxPrice=0;
  float onePercent=0;

  /*<<START>> 20200323 - WIKI Toan Tran - Update code for default collapsed */
  boolean isPriceExpanded = false;
  boolean isBrandExpanded = false;
  /*<<END>> 20200323 - WIKI Toan Tran - Update code for default collapsed */
  int indicatorTextSize =48;
  LinearLayout.LayoutParams lp;
  /**
   * 20200217 – - WIKI Toan Tran -
   * <Contructor for adapter>
   * @param itemList
   * @param context
   * @param listener
   *
   */

  public FilterAdapter(List<TreeItem> itemList, Context context,IOnClickItemFilterAdapter listener) {
    this.itemList = itemList;
    this.context = context;
    this.callBack =  listener;
//    indicatorTextSize = MetricsUtils.convertDpToPixel(12.0f,context);
    boolean isEStore =false;
    for(TreeItem item : itemList){
      if(item.getAttributeCode().compareToIgnoreCase("eStore")==0){
        isEStore = true;
      }
      if(item.getAttributeCode().compareToIgnoreCase("price")==0){
        maxPriceFirst = Float.parseFloat(item.getMaximumPrice());
        minPriceFirst = Float.parseFloat(item.getMinimumPrice());

        onePercent = (maxPriceFirst/100);
      }
    }
    if(!isEStore){
      TreeItem item = new TreeItem();
      item.setCategoryId("55");
      item.setParentId("2");
      item.setAttributeCode("eStore");
      item.setIsActive("1");
      item.setPosition("7");
      item.setLevel("2");
      item.setProductCount("");
      itemList.add(1    ,item);
    }

    itemList.add(0,new TreeItem());
    itemList.add(new TreeItem());
  }

  public Animation getAmin() {
    return amin;
  }

  public void setAmin(Animation amin) {
    this.amin = amin;
  }

  /**
   * 20200217 – - WIKI Toan Tran -
   * Create view for holder on recycler view library
   * @param parent
   * @return filterAdapter.ViewHolder
   */
  @NonNull
  @Override
  public FilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(this.context);
    View view;
    switch(viewType){
      case LABEL_ITEM :{
        view = inflater.inflate(R.layout.item_filter_recycler_label, parent, false);
        break;
      }
      case LABEL_EXPAND_ITEM :{
        view = inflater.inflate(R.layout.item_filter_recycler_label_expand, parent, false);
        break;
      }
      case CHECK_ITEM :{
        view = inflater.inflate(R.layout.item_filter_recycler_check, parent, false);

        break;
      }
      case CHECK_EXPAND_ITEM :{
        view = inflater.inflate(R.layout.item_filter_recycler_check_expand, parent, false);
        break;
      }
      case SEEK_BAR :{
        view = inflater.inflate(R.layout.item_filter_recycler_seek_bar, parent, false);
        break;
      }
      case TOP_ITEM :{
        view = inflater.inflate(R.layout.item_filter_recycler_top_, parent, false);
        break;
      }
      case BOTTOM_ITEM :{
        view = inflater.inflate(R.layout.item_filter_recycler_bottom, parent, false);
        break;
      }
      case BLANK_ITEM :{
        view = inflater.inflate(R.layout.item_blank, parent, false);
        break;
      }
      default:{
        view = inflater.inflate(R.layout.item_filter_recycler_check, parent, false);
      }
    }
    FilterAdapter.ViewHolder viewHolder = new FilterAdapter.ViewHolder(view);


    return viewHolder;
  }

  /**
   * 20200217 – - WIKI Toan Tran -
   * select type view for tree category
   * @param  position
   * @return mixed
   */
  @Override
  public int getItemViewType(int position) {
    try {
      if(Integer.parseInt(itemList.get(position).getLevel())>FINAL_MAX_LEVEL)
        return BLANK_ITEM;
    }catch (NumberFormatException ex)
    {}
    if(position==0){
      return TOP_ITEM;
    }else if(position == itemList.size()-1){
      return BOTTOM_ITEM;
    }else
    if(itemList.get(position).getAttributeCode()=="eStore"){
      return CHECK_ITEM;
    }
    if(itemList.get(position).getFilterLabel()!=""){
      if(itemList.get(position).getMaximumPrice()!=""||itemList.get(position).getMinimumPrice()!="") {
        if(maxPrice==0)
          maxPrice =  Float.parseFloat(itemList.get(position).getMaximumPrice());
        return SEEK_BAR;
      }
      if(itemList.get(position).getAttributeCode().equalsIgnoreCase("brand")){
        return LABEL_EXPAND_ITEM;
      }
      return LABEL_ITEM;
    }
    /*if(itemList.get(position).getCategoryId().equalsIgnoreCase(Constants.THE_TIME)||
        itemList.get(position).getCategoryId().equalsIgnoreCase(Constants.THE_DIRECT)){
      return CHECK_EXPAND_ITEM;
    }*/

    if(itemList.get(position).getParentId().equalsIgnoreCase(Constants.E_STORE)
        &&itemList.get(position).getLevel().equalsIgnoreCase("3")){
      return CHECK_EXPAND_ITEM;
    }
    return CHECK_ITEM;
  }

  /**
   * 20200217 – - WIKI Toan Tran -
   * binding for each item view
   * @param  holder
   * @param  position
   * @return mixed
   */
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.setIsRecyclable(false);
    if(holder.tvLabel != null)
      if(itemList.get(position).getMinimumPrice()!=""|| itemList.get(position).getMinimumPrice()!=""){
        holder.tvLabel.setText(itemList.get(position).getFilterLabel());
      }else if(itemList.get(position).getFilterLabel()!=""&&itemList.get(position).getFilterLabel()!=null){
        holder.tvLabel.setText(itemList.get(position).getFilterLabel());
      }else if(itemList.get(position).getLabel()!=""&&itemList.get(position).getLabel()!=null) {
        holder.tvLabel.setText(itemList.get(position).getLabel()+" ("+itemList.get(position).getProductCount()+")");
        holder.bind(position);
      }else if(itemList.get(position).getAttributeCode().compareTo("eStore")!=0&&
              itemList.get(position).getAttributeCode().compareTo("")!=0){
        holder.tvLabel.setText(itemList.get(position).getAttributeCode()+" ("+itemList.get(position).getProductCount()+")");
        holder.bind(position);
      }
    if(itemList.get(position).getLevel()!=""&&itemList.get(position).getLevel()!=null){
      holder.bindExpand(position);
    }
    if(itemList.get(position).getAttributeCode().compareTo("eStore")==0){
      if(itemList.get(position).getProductCount().compareToIgnoreCase("")==0){
        holder.tvLabel.setText("View all");
      }else{
        holder.tvLabel.setText("View all"+" ("+itemList.get(position).getProductCount()+")");
      }

      holder.bind(position);
    }
    if(itemList.get(position).getAttributeCode().compareToIgnoreCase("price")==0){
      holder.bindPrice(position);
    }
    holder.bindExpand(position);


  }


  public  List<TreeItem> getCurrentList(){
    return this.itemList;
  };

  @Override
  public int getItemCount() {
    return this.itemList.size();
  }

  /*<<START>> 20200220 - WIKI Toan Tran - new logic check box base in field*/
  public void logicCheckBox1(int position){
    int adapterPosition = position;
    String idClick = itemList.get(adapterPosition).getCategoryId();
    boolean isChecked = itemList.get(adapterPosition).isCheck();
    if(!isChecked){
      itemList.get(adapterPosition).setCheck(true);
    }else {
      itemList.get(adapterPosition).setCheck(false);
    }

    if(itemList.get(adapterPosition).getParentId().compareToIgnoreCase("")==0){
      return;
    }

    int levelItemClick = Integer.parseInt(itemList.get(adapterPosition).getLevel());
    for(int i = adapterPosition+1; i<itemList.size() ; i++){
      try {
        int levelItem =0;
        levelItem = Integer.parseInt(itemList.get(i).getLevel());
        if(levelItemClick > levelItem){
          break;
        }
        if(levelItemClick == levelItem){
          if(itemList.get(i).getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getParentId())==0){
            break;
          }
        }

        if(levelItemClick < levelItem){
          if(!isChecked){
            itemList.get(i).setCheck(true);
          }else {
            itemList.get(i).setCheck(false);
          }
        }
      }catch (NumberFormatException ex){}
    }
  }
  public void logicCheckBoxByOptions(int position){
    int adapterPosition = position;
    String idClick = itemList.get(adapterPosition).getCategoryId();
    boolean isChecked = itemList.get(adapterPosition).isCheck();

    itemList.get(adapterPosition).setCheck(true);


    if(itemList.get(adapterPosition).getParentId().compareToIgnoreCase("")==0){
      return;
    }

    int levelItemClick = Integer.parseInt(itemList.get(adapterPosition).getLevel());
    for(int i = adapterPosition+1; i<itemList.size() ; i++){
      try {
        int levelItem =0;
        levelItem = Integer.parseInt(itemList.get(i).getLevel());
        if(levelItemClick > levelItem){
          break;
        }
        if(levelItemClick == levelItem){
          if(itemList.get(i).getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getParentId())==0){
            break;
          }
        }

        if(levelItemClick < levelItem){
          if(!isChecked){
            itemList.get(i).setCheck(true);
          }else {
            itemList.get(i).setCheck(false);
          }
        }
      }catch (NumberFormatException ex){}
    }
  }

  /*<<END>> 20200220 - WIKI Toan Tran - new logic check box base in field */

  /**
   * Created by Toan Tran
   *
   * View holder for recycle apdapter
   * */
  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvLabel;
    LinearLayout  linearLayout,layoutSeekBar;
    CheckBox checkBox;
    Button btnApplFilter;
    RangeSeekBar priceSeekBar;
    ImageView imgClose;
    ImageButton  imgExpand,imgExpandSeekbar;
    TextView tvClearFilter;
    TextView tvPriceMin,tvPriceMax;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      tvLabel = itemView.findViewById(R.id.tvlabel);
      linearLayout = itemView.findViewById(R.id.item_recycler_filter_layout);
      checkBox = itemView.findViewById(R.id.ckFilterAttrs);
      btnApplFilter = itemView.findViewById(R.id.btn_apply_filter);
      priceSeekBar = itemView.findViewById(R.id.seek_bar_item);
      imgClose = itemView.findViewById(R.id.img_close);
      tvClearFilter = itemView.findViewById(R.id.tv_clear_filter);
      imgExpand = itemView.findViewById(R.id.img_expand);
      tvPriceMin = itemView.findViewById(R.id.txtPriceMin);
      tvPriceMax = itemView.findViewById(R.id.txtPriceMax);
      imgExpandSeekbar = itemView.findViewById(R.id.img_expand_seek_bar);
      layoutSeekBar = itemView.findViewById(R.id.layout_content_seek_bar);


      //=============== Click label category

        /*if(tvLabel!=null){
          tvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int adapterPosition = getAdapterPosition();
              if(itemList.get(adapterPosition).getAttributeCode().compareToIgnoreCase("eStore")==0){
                return;
              }
              if(itemList.get(adapterPosition).getCategoryId().compareToIgnoreCase("")!=0) {
                int idCate = Integer.parseInt(itemList.get(adapterPosition).getCategoryId());
                callBack.onClickCategoryItem(idCate);
              }
            }
          });
        }*/
      //=============== Click label category
      if(tvLabel!=null){
        tvLabel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if(checkBox!=null){
              logicCheckBox1(getAdapterPosition());
            }
            Log.e("TONA-DEV","CHECKED 1"+getAdapterPosition());
            notifyDataSetChanged();
          }
        });
      }

      /*<<START>> 20200217 - WIKI Toan Tran - Set click expand */
      if(imgExpand!=null){
        imgExpand.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            int adapterPositon = getAdapterPosition();
            if(!itemList.get(adapterPositon).getFilterLabel().equalsIgnoreCase("")) {
              if(itemList.get(adapterPositon).getAttributeCode().equalsIgnoreCase("brand")){
                for (TreeItem item : itemList) {
                  if (item.getIdParentExpand().compareToIgnoreCase(itemList.get(adapterPositon).getFilterLabel()) == 0) {
                    item.setVisible(isBrandExpanded);
                  }
                }
                if(isBrandExpanded){
                  isBrandExpanded = false;
                }else {
                  isBrandExpanded = true;
                }
              }else{
                for (TreeItem item : itemList) {
                  if (item.getIdParentExpand().compareToIgnoreCase(itemList.get(adapterPositon).getFilterLabel()) == 0) {
                    if (item.isVisible())
                      item.setVisible(false);
                    else
                      item.setVisible(true);
                  }
                }
              }
            }else{
              for (TreeItem item : itemList) {
                if (item.getIdParentExpandOption().compareToIgnoreCase(itemList.get(adapterPositon).getCategoryId()) == 0
                    &&item.getLevel().compareToIgnoreCase(itemList.get(adapterPositon).getLevel())>0) {
                  if (item.isVisible())
                    item.setVisible(false);
                  else
                    item.setVisible(true);
                }
              }
            }



            if(itemList.get(adapterPositon).isUp()){
              itemList.get(adapterPositon).setUp(false);
            }else{
              itemList.get(adapterPositon).setUp(true);
            }

            notifyDataSetChanged();
          }
        });
      }
      /*<<END>> 20200217 - WIKI Toan Tran - Set click expand */
      /*<<START>> 20200217 - WIKI Toan Tran - Set click expand seek bar*/
      if(imgExpandSeekbar!=null){
        imgExpandSeekbar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if(layoutSeekBar!=null){
              if(layoutSeekBar.getVisibility()==View.VISIBLE){
                isPriceExpanded = false;

              }else if(layoutSeekBar.getVisibility()==View.GONE){
                isPriceExpanded = true;
              }
            }
            notifyDataSetChanged();
          }
        });
      }
      /*<<END>> 20200217 - WIKI Toan Tran - Set click expand */

      /*<<START>> 20200301 - WIKI Toan Tran - change value seek bar  */
      if(priceSeekBar!=null){
        /*<<START>> 20200301 - WIKI Toan Tran -  Format decimal  */
        priceSeekBar.setIndicatorTextDecimalFormat("###,###,##0.00");
        priceSeekBar.setIndicatorTextStringFormat("SGD$%s");
        DecimalFormat df  = new DecimalFormat("###,###,##0.00");
        /*<<END>> 20200301 - WIKI Toan Tran -  Format decimal  */

        /*<<START>> 20200309 - WIKI Toan Tran - Move position of Indicator  */
        priceSeekBar.getLeftSeekBar().setIndicatorPaddingTop((int)MetricsUtils.convertDpToPixel(120,context) );
        priceSeekBar.getLeftSeekBar().setIndicatorPaddingBottom((int)MetricsUtils.convertDpToPixel(-60,context));
        priceSeekBar.getRightSeekBar().setIndicatorPaddingTop((int)MetricsUtils.convertDpToPixel(20,context));
        priceSeekBar.getRightSeekBar().setIndicatorPaddingBottom((int)MetricsUtils.convertDpToPixel(-20,context));
        priceSeekBar.getLeftSeekBar().setIndicatorMargin(0);
        /*<<END>> 20200309 - WIKI Toan Tran - Move position of Indicator  */

        /*<<START>> 20200301 - WIKI Toan Tran - Set label max and min  */
        tvPriceMin.setText("SGD$"+ df.format(minPriceFirst));
        tvPriceMax.setText("SGD$"+ df.format(maxPriceFirst));
        /*<<END>> 20200301 - WIKI Toan Tran -  Set label max and min  */
        priceSeekBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {

          }
        });

        priceSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
          @Override
          public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
            int from  = (int) leftValue;
            int to  = (int) rightValue;
            /*<<START>> 20200310 - WIKI Toan Tran - Try to design new animation for price bar  */
            float scalePercent =1-( 0.002f*(leftValue/onePercent));
            if(scalePercent<=0){
              scalePercent =1;
            }
            //priceSeekBar.getLeftSeekBar().setIndicatorTextSize((int)(indicatorTextSize*scalePercent));

            Log.d("TOAN-TEXT-SIZE",indicatorTextSize+" : - "+scalePercent+" S : - "+indicatorTextSize/scalePercent);
            /*<<START>> 20200310 - WIKI Toan Tran -  Try to design new animation for price bar  */
            if(!isFromUser)
              for(TreeItem item : itemList){
                if(item.getAttributeCode().compareToIgnoreCase("price")==0){
                  //Ranger
                  //set value max and after change
                  item.setMinimumPrice(""+from);
                  item.setMaximumPrice(""+to);
                  item.setCheck(true);
                }
              }
          }

          @Override
          public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
          }

          @Override
          public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            notifyDataSetChanged();
          }
        });
      }
      /*<<END>> 20200217 - WIKI Toan Tran - change value seek bar  */


      /*<<START>> 20200217 - WIKI Toan Tran -  Click To Close*/
      if(imgClose != null){
        imgClose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            callBack.onCloseFilterPopup();
          }
        });
      }
      /*<<END>> 20200217 - WIKI Toan Tran -  Click To Close*/
      /*<<START>> 20200217 - WIKI Toan Tran -  Click to clear filter*/
      if(tvClearFilter!=null){
        tvClearFilter.setPaintFlags(tvClearFilter.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvClearFilter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            for(TreeItem item : itemList){
              item.setCheck(false);
              if(item.getMaximumPrice().compareToIgnoreCase("")!=0){
                item.setMaximumPrice(""+maxPrice);
                //item.setMinimumPrice("0");
              }
              notifyDataSetChanged();
            }
          }
        });

      }
      /*<<END>> 20200217 - WIKI Toan Tran -  Click to clear filter*/

      /*<<START>> 20200217 - WIKI Toan Tran -  Click to apply filter and call back to the
      activity for action*/
      if(btnApplFilter != null)
        btnApplFilter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            List<TreeItem> checkList = new ArrayList<>();

            for(TreeItem item  : itemList){
              if(item.isCheck()){
                checkList.add(item);
              }
            }
            callBack.onClickApplyFilter(checkList);

          }
        });
      /*<<END>> 20200217 - WIKI Toan Tran -  Click to apply filter and call back to the
      activity for action*/
      /*<<START>> 20200217 - WIKI Toan Tran - catch on click check box */
      try{
        checkBox.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if(checkBox!=null){
              logicCheckBox1(getAdapterPosition());
            }
            notifyDataSetChanged();
          }
        });
      /*<<END>> 20200217 - WIKI Toan Tran - catch on click check box */
      /*<<START>> 20200217 - WIKI Toan Tran - catch on click lable after check box */
        tvLabel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if(checkBox!=null){
              logicCheckBox1(getAdapterPosition());
            }
            notifyDataSetChanged();
          }
        });
      }catch (NullPointerException ex){}
      /*<<END>> 20200217 - WIKI Toan Tran - catch on click lable after check box */
    }
    /*<<START>> 20200217 - WIKI Toan Tran - old logic check box  */
    void logicCheckBox(CheckBox checkBox){
      int adapterPosition = getAdapterPosition();
      if(itemList.get(adapterPosition).getAttributeCode().compareToIgnoreCase("eStore")==0){
        if (itemList.get(adapterPosition).isCheck()) {
          itemList.get(adapterPosition).setCheck(false);
          for(TreeItem item : itemList){
            if(item.getParentId().compareToIgnoreCase("")!=0)
              item.setCheck(false);
          }
        }else {
          itemList.get(adapterPosition).setCheck(true);
          for(TreeItem item : itemList){
            if(item.getParentId().compareToIgnoreCase("")!=0)
              item.setCheck(true);
          }
        }

      }else if(itemList.get(adapterPosition).getAttributeCode().compareToIgnoreCase("")==0&&
              itemList.get(adapterPosition).getLevel().compareToIgnoreCase("")!=0){
        int tmpMax = Integer.parseInt(itemList.get(adapterPosition).getLevel());
        if (itemList.get(adapterPosition).isCheck()) {
          checkBox.setChecked(false);
          itemList.get(adapterPosition).setCheck(false);
          for(int i = adapterPosition+1; i < itemList.size(); i++){
            try {
              if(Integer.parseInt(itemList.get(i).getLevel()) < tmpMax){

                if(itemList.get(i).getLevel().compareToIgnoreCase(itemList.get(adapterPosition).getLevel())==0)
                  break;
                else {
                  itemList.get(i).setCheck(false);
                }
              }
              if (Integer.parseInt(itemList.get(i).getLevel()) == tmpMax) {
                if(adapterPosition+1 == i){
                  break;
                }
                if(itemList.get(i).getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getParentId())!=0){
                  tmpMax = Integer.parseInt(itemList.get(i).getLevel());
                  itemList.get(i).setCheck(false);
                }else if(itemList.get(i).getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getParentId())==0){
                  break;
                }
              }else {
                if (Integer.parseInt(itemList.get(i).getLevel()) > tmpMax) {
                  tmpMax = Integer.parseInt(itemList.get(i).getLevel());
                  itemList.get(i).setCheck(false);
                }
              }
            }catch (NumberFormatException ex ){}
            //                    if(item.getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getCategoryId())==0){
            //                      item.setCheck(true);
            //                    }
          }
        }
        else {
          checkBox.setChecked(true);
          itemList.get(adapterPosition).setCheck(true);
          for(int i = adapterPosition+1; i < itemList.size(); i++){
            try {
              if(Integer.parseInt(itemList.get(i).getLevel()) < tmpMax){
                if(itemList.get(i).getLevel().compareToIgnoreCase(itemList.get(adapterPosition).getLevel())==0)
                  break;
                else {
                  itemList.get(i).setCheck(true);
                }
              }
              if (Integer.parseInt(itemList.get(i).getLevel()) == tmpMax) {
                if(adapterPosition+1 == i){
                  break;
                }
                if(itemList.get(i).getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getParentId())!=0){
                  tmpMax = Integer.parseInt(itemList.get(i).getLevel());
                  itemList.get(i).setCheck(true);
                }else if(itemList.get(i).getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getParentId())==0){
                  break;
                }
              }else {
                if (Integer.parseInt(itemList.get(i).getLevel()) > tmpMax) {
                  tmpMax = Integer.parseInt(itemList.get(i).getLevel());
                  itemList.get(i).setCheck(true);
                }
              }
            }catch (NumberFormatException ex ){}
            //                    if(item.getParentId().compareToIgnoreCase(itemList.get(adapterPosition).getCategoryId())==0){
            //                      item.setCheck(true);
            //                    }
          }
        }

      }else{
        if (itemList.get(adapterPosition).isCheck()) {
          checkBox.setChecked(false);
          itemList.get(adapterPosition).setCheck(false);

        }
        else {
          checkBox.setChecked(true);
          itemList.get(adapterPosition).setCheck(true);
        }
      }
    }
    /*<<END>> 20200217 - WIKI Toan Tran - old logic check box  */





    /* <<START>> 20200217 - WIKI Toan Tran - binding item not have expand button*/
    void bind(int position) {
      try{
        if (itemList.get(position).isCheck()) {
          checkBox.setChecked(true);
        }
        else {
          checkBox.setChecked(false);
        }

         Log.d("BRAND:",itemList.get(position).getIdParentExpand().toString());

        if (itemList.get(position).getIdParentExpand().compareToIgnoreCase("brand") == 0) {
          itemList.get(position).setVisible(isBrandExpanded);
        }
      }catch (NullPointerException ex){}

    }
    /* <<END>> 20200217 - WIKI Toan Tran - binding item not have expand button*/
    /* <<START>> 20200217  - WIKI Toan Tran - binding button expand*/
    void bindExpand(int position) {

      int lv = 1;
      try{
        lv  = ( Integer.parseInt(itemList.get(position).getLevel()));
      }catch (NumberFormatException ex){}
      try {
        //
        //        if (itemList.get(position).isVisible()) {
        //
        //          linearLayout.setVisibility(View.VISIBLE);
        //          LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        //                  LinearLayout.LayoutParams.WRAP_CONTENT);
        //          if(itemList.get(position).getAttributeCode().compareToIgnoreCase("eStore")==0)
        //            lp.setMargins((60*(lv)),0,0,0);
        //          else if(itemList.get(position).getCategoryId().compareToIgnoreCase("")==0&&
        //                  itemList.get(position).getFilterLabel().compareToIgnoreCase("")==0){
        //            lp.setMargins(60*2,0,0,0);
        //          }else
        //            lp.setMargins((60*(lv-1)),0,0,0);
        //          linearLayout.setLayoutParams(lp);
        //        } else {
        //          linearLayout.setVisibility((View.GONE));
        //          LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(0,0);
        //          if(itemList.get(position).getAttributeCode().compareToIgnoreCase("eStore")==0)
        //            lp.setMargins((60*(lv)),0,0,0);
        //          else if(itemList.get(position).getCategoryId().compareToIgnoreCase("")==0&&
        //                  itemList.get(position).getFilterLabel().compareToIgnoreCase("")==0){
        //            lp.setMargins(60*2,0,0,0);
        //          }else
        //            lp.setMargins((60*(lv-1)),0,0,0);
        //          linearLayout.setLayoutParams(lp);
        //        }
        lp = new LinearLayout.LayoutParams(0,0);;

        if(itemList.get(position).isVisible()){
          lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT);
        }else {
          linearLayout.setVisibility(View.GONE);
          lp = new LinearLayout.LayoutParams(0,0);
        }

        if(itemList.get(position).getFilterLabel().compareToIgnoreCase("")!=0){
          lp.setMargins(0,0,0,0);
        }else if(itemList.get(position).getAttributeCode().compareToIgnoreCase("eStore")==0){
          lp.setMargins(60,0,0,0);
        }else if(itemList.get(position).getLevel().compareToIgnoreCase("")!=0){
          int level = Integer.parseInt(itemList.get(position).getLevel())-2;
          lp.setMargins(60*level,0,0,0);
        }else {
          lp.setMargins(60,0,0,0);
        }
        linearLayout.setLayoutParams(lp);
        /*<<START>> 20200323 - WIKI Toan Tran - Update code for default collapsed (BRAND) */
        if(!itemList.get(position).getFilterLabel().equalsIgnoreCase("")) {
          if(itemList.get(position).getAttributeCode().equalsIgnoreCase("brand")){
            if(isBrandExpanded){
              imgExpand.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            }else{
              imgExpand.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            }
          }
        }else {
          if(itemList.get(position).isUp()){
            imgExpand.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
          }else{
            imgExpand.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
          }
        }
        /*<<END>> 20200323 - WIKI Toan Tran - Update code for default collapsed (BRAND) */
      }catch (NullPointerException ex){}
    }
    /* <<END>> 20200217 - WIKI Toan Tran - binding button expand*/


    /* <<START>> 20200217  - WIKI Toan Tran - binding price bar*/
    void bindPrice(int position) {
      try {

        /*<<START>> 20200323 - WIKI Toan Tran - Update code for default collapsed (PRICE) */
        if(isPriceExpanded){
          imgExpandSeekbar.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
          layoutSeekBar.setVisibility(View.VISIBLE);
        }else {
          imgExpandSeekbar.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
          layoutSeekBar.setVisibility(View.GONE);
        }
        /*<<END>> 20200323 - WIKI Toan Tran - Update code for default collapsed (PRICE) */
      }catch (NullPointerException ex){}


      priceSeekBar.setRange(0,maxPrice);
      if (itemList.get(position).getMinimumPrice().compareToIgnoreCase("")!=0&&
              itemList.get(position).getMaximumPrice().compareToIgnoreCase("")!=0&&
              itemList.get(position).getMaximumPrice().compareToIgnoreCase("0")!=0) {
        //Ranger
        priceSeekBar.setProgress(Float.parseFloat(itemList.get(position).getMinimumPrice()),
                Float.parseFloat(itemList.get(position).getMaximumPrice()));

        //Single
        //priceSeekBar.setProgress(Float.parseFloat(itemList.get(position).getMaximumPrice()));

      }
    }
    /* <<END>>  20200217 - WIKI Toan Tran - binding price bar*/
  }

  public List<TreeItem> getItemList() {
    return this.itemList;
  }
  public void setCheckItem(int position){
    this.itemList.get(position).setCheck(true);
  }
}
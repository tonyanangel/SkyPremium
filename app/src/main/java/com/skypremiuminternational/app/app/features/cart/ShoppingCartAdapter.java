package com.skypremiuminternational.app.app.features.cart;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.utils.listener.OnTextChangeListener;
import com.skypremiuminternational.app.domain.models.cart.CartDetailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aeindraaung on 1/27/18.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartViewHolder> {

  private List<CartDetailItem> dataList = new ArrayList<>();
  private Integer[] counts;
  private ActionListener actionListener;
  private boolean containVirtualProduct;
  private int currentFocusPosition = RecyclerView.NO_POSITION;

  public Integer[] getCounts() {
    return counts;
  }

  public void setDataList(List<CartDetailItem> items, boolean containVirtualProduct) {
    this.dataList.clear();
    this.dataList.addAll(items);
    this.counts = new Integer[dataList.size()];
    for (int i = 0; i < dataList.size(); i++) {
      CartDetailItem item = dataList.get(i);
      counts[i] = item.getQty();
    }
    this.containVirtualProduct = containVirtualProduct;
    notifyDataSetChanged();
  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  @Override
  public ShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_shopping_cart, parent, false);
    final ShoppingCartViewHolder holder = new ShoppingCartViewHolder(view);
    holder.imgDelete.setOnClickListener(v -> {
      if (actionListener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
        actionListener.onDeleteClicked(dataList.get(holder.getAdapterPosition()));
      }
    });

    holder.editQty.addTextChangedListener(new OnTextChangeListener() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        int itemCount;
        try {
          itemCount = Integer.parseInt(s.toString());
        } catch (Exception e) {
          itemCount = 0;
        }
        counts[holder.getAdapterPosition()] = itemCount;
      }
    });

    // Tempery to disable Toan Tran
//
//    holder.editQty.setOnFocusChangeListener((v, hasFocus) -> {
//      //actionListener.onFocusChanged(hasFocus, holder.getAdapterPosition());
//      if(holder.getAdapterPosition()>=0){
//        CartDetailItem item = dataList.get(holder.getAdapterPosition());
//        final int originQyt = item.getQty();
//        int editedQyt;
//        try {
//          editedQyt = Integer.parseInt(holder.editQty.getText().toString());
//        }catch(NumberFormatException ex){
//          editedQyt = 0;
//        }
//        actionListener.onItemCountUpdated(dataList.get(holder.getAdapterPosition()), editedQyt);
//      }
//    });

    //Disable old code by Toan Tran
    /*
    holder.editQty.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {

        CartDetailItem item = dataList.get(holder.getAdapterPosition());
        final int originQyt = item.getQty();
        int editedQyt;
        try {
          editedQyt = Integer.parseInt(holder.editQty.getText().toString());
        } catch (Exception e) {
          editedQyt = 0;
        }

        if (editedQyt <= 0) {
          hideKeyBoard(view);
          notifyDataSetChanged();
        } else if (editedQyt != originQyt && actionListener != null) {
          hideKeyBoard(view);
          if(editedQyt > item.getQty_stock()){
            holder.tvErrorMessage.setVisibility(View.VISIBLE);
            holder.tvErrorMessage.setText("You have exceeded the product quantity allowed for purchase.");
          }
          else {
            holder.tvErrorMessage.setVisibility(View.GONE);
            actionListener.onItemCountUpdated(dataList.get(holder.getAdapterPosition()), editedQyt);
          }
        } else if (editedQyt == originQyt ) {
          hideKeyBoard(view);
        }
        return true;
      }
      return false;
    });
    */
    holder.editQty.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {

        CartDetailItem item = dataList.get(holder.getAdapterPosition());
        final int originQyt = item.getQty();
        int editedQyt;
        try {
           editedQyt = Integer.parseInt(holder.editQty.getText().toString());
        }catch(NumberFormatException ex){
          editedQyt = 0;
        }
          actionListener.onItemCountUpdated(dataList.get(holder.getAdapterPosition()), editedQyt);
        return true;
      }
      return false;
    });

    return holder;
  }

  private void hideKeyBoard(View view) {
    InputMethodManager imm =
        (InputMethodManager) view.getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

  }

  @Override
  public void onBindViewHolder(ShoppingCartViewHolder holder, int position) {
    holder.bind(dataList.get(position), position, containVirtualProduct,
        currentFocusPosition == position && getItemCount() != 1);
  }

  @Override
  public int getItemCount() {
    return dataList != null ? dataList.size() : 0;
  }

  public List<CartDetailItem> getItems() {
    return dataList;
  }

  public void setCurrentFocusPosition(int currentFocusPosition) {
    this.currentFocusPosition = currentFocusPosition;
  }

  public interface ActionListener {
    void onDeleteClicked(CartDetailItem item);

    void onItemCountUpdated(CartDetailItem item, int updatedQyt);

    //Toan Tran add new Interface for in-stock check
    void onItemCheckInStock();

    void onFocusChanged(boolean hasFocus, int adapterPosition);
  }
}

package com.skypremiuminternational.app.domain.models.favourite;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Toan Tran on 10/04/2020.
 */

public class ExtensionAttributes implements Serializable {
    @SerializedName("product_id")
    private int productId;
    @SerializedName("stock_id")
    private int qty;
    @SerializedName("is_in_stock")
    private String isInStock;
    @SerializedName("is_qty_decimal")
    private boolean isQtyDecimal;
    @SerializedName("show_default_notification_message")
    private boolean showDefaultNotificationMessage;
    @SerializedName("use_config_min_qty")
    private boolean useConfigMinQty;
    @SerializedName("min_qty")
    private int minQty;
    @SerializedName("use_config_min_sale_qty")
    private int useConfigMinSaleQty;
    @SerializedName("min_sale_qty")
    private int minSaleQty;
    @SerializedName("use_config_max_sale_qty")
    private boolean useConfigMaxSaleQty;
    @SerializedName("max_sale_qty")
    private int maxSaleQty;
    @SerializedName("use_config_backorders")
    private boolean useConfigBackorders;
    @SerializedName("backorders")
    private int backOrders;
    @SerializedName("use_config_notify_stock_qty")
    private boolean useConfigNOtifyStockQty;
    @SerializedName("notify_stock_qty")
    private int notifyStockQty;
    @SerializedName("use_config_qty_increments")
    private boolean useConfigQtyIncrements;
    @SerializedName("qty_increments")
    private int qtyIncrements;
    @SerializedName("use_config_enable_qty_inc")
    private boolean useConfigEnableQtyInc;
    @SerializedName("enable_qty_increments")
    private boolean enableQtyIncrements;
    @SerializedName("use_config_manage_stock")
    private boolean useConfigManageStock;
    @SerializedName("manage_stock")
    private boolean manageStock;
    @SerializedName("low_stock_date")
    private String lowStockDate;
    @SerializedName("is_decimal_divided")
    private boolean isDecimalDivided;
    @SerializedName("stock_status_changed_auto")
    private int sotckStatusChangedAuto;

    public ExtensionAttributes(int productId, int qty, String isInStock, boolean isQtyDecimal,
                     boolean showDefaultNotificationMessage, boolean useConfigMinQty, int minQty,
                     int useConfigMinSaleQty, int minSaleQty, boolean useConfigMaxSaleQty, int maxSaleQty,
                     boolean useConfigBackorders, int backOrders, boolean useConfigNOtifyStockQty,
                     int notifyStockQty, boolean useConfigQtyIncrements, int qtyIncrements,
                     boolean useConfigEnableQtyInc, boolean enableQtyIncrements, boolean useConfigManageStock,
                     boolean manageStock, String lowStockDate, boolean isDecimalDivided,
                     int sotckStatusChangedAuto) {
        this.productId = productId;
        this.qty = qty;
        this.isInStock = isInStock;
        this.isQtyDecimal = isQtyDecimal;
        this.showDefaultNotificationMessage = showDefaultNotificationMessage;
        this.useConfigMinQty = useConfigMinQty;
        this.minQty = minQty;
        this.useConfigMinSaleQty = useConfigMinSaleQty;
        this.minSaleQty = minSaleQty;
        this.useConfigMaxSaleQty = useConfigMaxSaleQty;
        this.maxSaleQty = maxSaleQty;
        this.useConfigBackorders = useConfigBackorders;
        this.backOrders = backOrders;
        this.useConfigNOtifyStockQty = useConfigNOtifyStockQty;
        this.notifyStockQty = notifyStockQty;
        this.useConfigQtyIncrements = useConfigQtyIncrements;
        this.qtyIncrements = qtyIncrements;
        this.useConfigEnableQtyInc = useConfigEnableQtyInc;
        this.enableQtyIncrements = enableQtyIncrements;
        this.useConfigManageStock = useConfigManageStock;
        this.manageStock = manageStock;
        this.lowStockDate = lowStockDate;
        this.isDecimalDivided = isDecimalDivided;
        this.sotckStatusChangedAuto = sotckStatusChangedAuto;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isInStock() {
        if(isInStock!=null&&!TextUtils.isEmpty(isInStock)){
            if(isInStock.equalsIgnoreCase("1")){
                return true;
            }else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void setInStock(boolean inStock) {
        if(inStock)
            isInStock = "1";
        else
            isInStock = "0";
    }

    public boolean isQtyDecimal() {
        return isQtyDecimal;
    }

    public void setQtyDecimal(boolean qtyDecimal) {
        isQtyDecimal = qtyDecimal;
    }

    public boolean isShowDefaultNotificationMessage() {
        return showDefaultNotificationMessage;
    }

    public void setShowDefaultNotificationMessage(boolean showDefaultNotificationMessage) {
        this.showDefaultNotificationMessage = showDefaultNotificationMessage;
    }

    public boolean isUseConfigMinQty() {
        return useConfigMinQty;
    }

    public void setUseConfigMinQty(boolean useConfigMinQty) {
        this.useConfigMinQty = useConfigMinQty;
    }

    public int getMinQty() {
        return minQty;
    }

    public void setMinQty(int minQty) {
        this.minQty = minQty;
    }

    public int getUseConfigMinSaleQty() {
        return useConfigMinSaleQty;
    }

    public void setUseConfigMinSaleQty(int useConfigMinSaleQty) {
        this.useConfigMinSaleQty = useConfigMinSaleQty;
    }

    public int getMinSaleQty() {
        return minSaleQty;
    }

    public void setMinSaleQty(int minSaleQty) {
        this.minSaleQty = minSaleQty;
    }

    public boolean isUseConfigMaxSaleQty() {
        return useConfigMaxSaleQty;
    }

    public void setUseConfigMaxSaleQty(boolean useConfigMaxSaleQty) {
        this.useConfigMaxSaleQty = useConfigMaxSaleQty;
    }

    public int getMaxSaleQty() {
        return maxSaleQty;
    }

    public void setMaxSaleQty(int maxSaleQty) {
        this.maxSaleQty = maxSaleQty;
    }

    public boolean isUseConfigBackorders() {
        return useConfigBackorders;
    }

    public void setUseConfigBackorders(boolean useConfigBackorders) {
        this.useConfigBackorders = useConfigBackorders;
    }

    public int getBackOrders() {
        return backOrders;
    }

    public void setBackOrders(int backOrders) {
        this.backOrders = backOrders;
    }

    public boolean isUseConfigNOtifyStockQty() {
        return useConfigNOtifyStockQty;
    }

    public void setUseConfigNOtifyStockQty(boolean useConfigNOtifyStockQty) {
        this.useConfigNOtifyStockQty = useConfigNOtifyStockQty;
    }

    public int getNotifyStockQty() {
        return notifyStockQty;
    }

    public void setNotifyStockQty(int notifyStockQty) {
        this.notifyStockQty = notifyStockQty;
    }

    public boolean isUseConfigQtyIncrements() {
        return useConfigQtyIncrements;
    }

    public void setUseConfigQtyIncrements(boolean useConfigQtyIncrements) {
        this.useConfigQtyIncrements = useConfigQtyIncrements;
    }

    public int getQtyIncrements() {
        return qtyIncrements;
    }

    public void setQtyIncrements(int qtyIncrements) {
        this.qtyIncrements = qtyIncrements;
    }

    public boolean isUseConfigEnableQtyInc() {
        return useConfigEnableQtyInc;
    }

    public void setUseConfigEnableQtyInc(boolean useConfigEnableQtyInc) {
        this.useConfigEnableQtyInc = useConfigEnableQtyInc;
    }

    public boolean isEnableQtyIncrements() {
        return enableQtyIncrements;
    }

    public void setEnableQtyIncrements(boolean enableQtyIncrements) {
        this.enableQtyIncrements = enableQtyIncrements;
    }

    public boolean isUseConfigManageStock() {
        return useConfigManageStock;
    }

    public void setUseConfigManageStock(boolean useConfigManageStock) {
        this.useConfigManageStock = useConfigManageStock;
    }

    public boolean isManageStock() {
        return manageStock;
    }

    public void setManageStock(boolean manageStock) {
        this.manageStock = manageStock;
    }

    public String getLowStockDate() {
        return lowStockDate;
    }

    public void setLowStockDate(String lowStockDate) {
        this.lowStockDate = lowStockDate;
    }

    public boolean isDecimalDivided() {
        return isDecimalDivided;
    }

    public void setDecimalDivided(boolean decimalDivided) {
        isDecimalDivided = decimalDivided;
    }

    public int getSotckStatusChangedAuto() {
        return sotckStatusChangedAuto;
    }

    public void setSotckStatusChangedAuto(int sotckStatusChangedAuto) {
        this.sotckStatusChangedAuto = sotckStatusChangedAuto;
    }
}

package com.skypremiuminternational.app.app.features.estore;

import android.text.TextUtils;
import android.util.Log;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.CaregoryDetailsRequest;
import com.skypremiuminternational.app.data.network.request.FeatureProductRequest;
import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.data.network.request.ProductFilterSortRequest;
import com.skypremiuminternational.app.data.network.request.ProductListRequest;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.DataManager;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.AddToBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.AddToCart;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.category.GetCategory;
import com.skypremiuminternational.app.domain.interactor.category.GetCategoryDetails;
import com.skypremiuminternational.app.domain.interactor.category.GetFilterProductWithSort;
import com.skypremiuminternational.app.domain.interactor.category.GetFilterProducts;
import com.skypremiuminternational.app.domain.interactor.category.GetTreeCategory;
import com.skypremiuminternational.app.domain.interactor.category.GetTreeCategoryByCheckList;
import com.skypremiuminternational.app.domain.interactor.category.GetTreeCategoryById;
import com.skypremiuminternational.app.domain.interactor.category.GetTreeFilterMini;
import com.skypremiuminternational.app.domain.interactor.favourite.AddToFavourite;
import com.skypremiuminternational.app.domain.interactor.favourite.GetFavourites;
import com.skypremiuminternational.app.domain.interactor.favourite.RemoveFromFavourite;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetRatingOption;
import com.skypremiuminternational.app.domain.interactor.travel.GetFeatureTravel;
import com.skypremiuminternational.app.domain.interactor.travel.GetTravel;
import com.skypremiuminternational.app.domain.models.category.CategoryDetailsResponse;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by johnsonmaung on 9/22/17.
 */

public class EstorePresenter extends BasePresenter<EstoreView> {

  private final AddToFavourite addToFavourite;
  private final RemoveFromFavourite removeFromFavourite;
  private final GetFavourites getFavourites;
  private final GetTravel getTravel;
  private final GetFilterProducts getFilterProducts;
  private final GetFilterProductWithSort getFilterProductWithSort;
  private final GetCategory getCategory;
  private final GetTreeCategory getTreeCategory;
  private final GetTreeCategoryById getTreeCategoryById;
  private final GetTreeFilterMini getTreeFilterMini;
  private final GetTreeCategoryByCheckList getTreeCategoryByCheckList;
  private final GetFeatureTravel getFeatureTravel;
  private final AddToCart addToCart;
  private final AddToBuyNow addToBuyNow;
  private final GetCategoryDetails getCategoryDetails;
  private final GetCartCount getCartCount;
  private final GetRatingOption getRatingOption;
  private final DataManager dataManager;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  private CategoryResponse category;

  CategoryResponse categoryResponse = null;
  String json = null;

  @Inject
  public EstorePresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                         GetCategory getCategory, GetCategoryDetails getCategoryDetails,
                         GetTravel getTravel, GetFilterProducts getFilterProducts , GetFeatureTravel getFeatureTravel, GetFavourites getFavourites,
                         RemoveFromFavourite removeFromFavourite, AddToFavourite addToFavourite,
                         GetCartCount getCartCount, GetTreeCategory getTreeCategory,
                         GetTreeCategoryById getTreeCategoryById, GetTreeCategoryByCheckList getTreeCategoryByCheckList,
                         GetFilterProductWithSort getFilterProductWithSort,
                         GetRatingOption getRatingOption, DataManager dataManager, AddToCart addToCart, AddToBuyNow addToBuyNow,
                         GetTreeFilterMini getTreeFilterMini) {
    super(getAppVersion, internalStorageManager);
    this.getCategory = getCategory;
    this.getTravel = getTravel;
    this.getFilterProducts = getFilterProducts;
    this.getTreeCategory = getTreeCategory;
    this.getFeatureTravel = getFeatureTravel;
    this.getCategoryDetails = getCategoryDetails;
    this.getFavourites = getFavourites;
    this.removeFromFavourite = removeFromFavourite;
    this.addToFavourite = addToFavourite;
    this.getCartCount = getCartCount;
    this.getTreeCategoryById = getTreeCategoryById;
    this.getTreeCategoryByCheckList = getTreeCategoryByCheckList;
    this.getFilterProductWithSort = getFilterProductWithSort;
    this.getRatingOption = getRatingOption;
    this.dataManager = dataManager;
    this.addToCart = addToCart;
    this.addToBuyNow = addToBuyNow;
    this.getTreeFilterMini = getTreeFilterMini;
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  void getCategoryDetails(final String category_id,ProductFilterSortRequest filterSortRequest) {
    final CaregoryDetailsRequest request = new CaregoryDetailsRequest(category_id);
    add(getCategoryDetails.execute(new BaseSubscriber<CategoryDetailsResponse>() {
      @Override
      public void onSuccess(CategoryDetailsResponse response) {
        getView().render(response);
      }
      @Override
      public void onError(Throwable error) {
        getView().render(error);
      }
    }, request));
    getCategories(category_id);
    getProductFilter(filterSortRequest);
  }
  void getCategoryDetails(final String category_id) {
    final CaregoryDetailsRequest request = new CaregoryDetailsRequest(category_id);
    add(getCategoryDetails.execute(new BaseSubscriber<CategoryDetailsResponse>() {
      @Override
      public void onSuccess(CategoryDetailsResponse response) {
        getView().render(response);

      }
      @Override
      public void onError(Throwable error) {
        getView().render(error);
      }
    }, request));
    getCategoriesSimple(category_id);
  }
  private void getCategories(final String category_id) {
    add(getCategory.execute(new BaseSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse response) {
        EstorePresenter.this.category = response;
        getView().render(response);
        //getFeatureProducts(category_id);
      }
      @Override
      public void onError(Throwable error) {
        getView().render(new Exception("Failed to load categories"));
      }
    } ));
  }

  public void getProductWithFilterLocal(){
    String strTreeFilter = "";
    strTreeFilter =  getStringTreeFilterLocal();

    try {
      if(!TextUtils.isEmpty(strTreeFilter)){
        getView().renderTreeCategoryLocal(strTreeFilter);
        EstoreActivity.isFlagFilter = false;
      }else {
        EstoreActivity.isFlagFilter = true;
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void getProductWithFilterMini(String request) {
    add(getTreeFilterMini.asObservable(request)
        .subscribe(response -> {
          String strJson = "";
          try {
             strJson = response.string();
          } catch (IOException e) {
            e.printStackTrace();
          }
          try {
            if (!TextUtils.isEmpty(strJson)) {
              getView().renderTreeCategoryMini(strJson);
              EstoreActivity.isFlagFilter = false;
            } else {
              EstoreActivity.isFlagFilter = true;
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }, throwable -> {
          throwable.printStackTrace();
        }));

  }

  private void getCategoriesSimple(final String category_id) {
    add(getCategory.execute(new BaseSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse response) {
        EstorePresenter.this.category = response;
      }
      @Override
      public void onError(Throwable error) {
        getView().render(new Exception("Failed to load categories"));
      }
    } ));
  }
  void getProductList(String category_id, String field, String direction,String isThetime) {
    ProductListRequest request = new ProductListRequest(category_id, field, direction,isThetime);
    request.setSaveResult(true);
    getView().showLoading("Getting product list...");
    add(getTravel.asObservable(request)
        .zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
            (productListResponse, favouriteListResponses) -> {
              for (ItemsItem item : productListResponse.getItems()) {
                setAsFavIfNeeded(item, favouriteListResponses);
              }
              return productListResponse;
            })
        .subscribe(productListResponse -> {
          addCategoryNames(productListResponse.getItems(), category);
          getView().hideLoading();
          getView().render(productListResponse);
        }, throwable -> {
          getView().hideLoading();
          getView().render(new Exception("Failed to load product list"));
        }));
    
  }
  //get string json for tree category
  public void getTreeCateStringJson(){
    String request = "55";
    add(getTreeCategory.asObservable(request)
            .subscribe(response -> {
              try {
                getView().renderTreeCategory(response.string());
              } catch (JSONException e) {
                e.printStackTrace();
                getView().render(new Exception("Failed to load featured category"));

              } catch (IOException e) {
                e.printStackTrace();
                getView().render(new Exception("Failed to load featured category"));

              }
            }, throwable -> {
              throwable.printStackTrace();
              getView().render(new Exception("Failed to load featured category"));

            }));
  }

    /**
     * 20200301 - WIKI Toan Tran - get product after filter non sort
     * @param params
     */
  void getProductFilter(String params) {
    String request = ""+params;
    getView().showLoading("Getting Product list...");
    add(getFilterProducts.asObservable(request).zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
            (productListResponse, favouriteListResponses) -> {
                for (ItemsItem item : productListResponse.getItems()) {
                    setAsFavIfNeeded(item, favouriteListResponses);
                }
                return productListResponse;
            })
            .subscribe(response -> {
              addCategoryNames(response.getItems(), dataManager.getCategories());
              getView().hideLoading();
              getView().renderFilter(response);
              Log.d("JSON-PRODUCT",""+response.getItems().size());

            }, throwable -> {
              throwable.printStackTrace();
              getView().render(new Exception("Failed to load products"));

                getView().hideLoading();
            }));




//    Subscription subscription = getFilterProducts.execute(new BaseSubscriber<ProductListResponse>() {
//      @Override
//      public void onSuccess(ProductListResponse value) {
//     //   getView().hideLoading();
//        ProductListResponse v = value;
//        getView().render(v);
//      }
//    },request);
//    add(subscription);
  }

    /**
     * 20200306 - WIKI Toan Tran - get product after filter
     * @param request
     */
  void getProductFilter(ProductFilterSortRequest request) {

    getView().showLoading("Getting Product list...");
    add(getFilterProductWithSort.asObservable(request).zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
            (productListResponse, favouriteListResponses) -> {
                for (ItemsItem item : productListResponse.getItems()) {
                    setAsFavIfNeeded(item, favouriteListResponses);
                }
                return productListResponse;
            })
                .subscribe(response -> {
                    addCategoryNames(response.getItems(), dataManager.getCategories());
                    getView().renderFilter(response);
                    getView().hideLoading();

                }, throwable -> {
                    throwable.printStackTrace();
                    getView().render(new Exception("Failed to load products"));
                    getView().hideLoading();
                }));
    }
    /**
     * 20200306 - WIKI Toan Tran - get product after filter more (non show loading dialog)
     * @param request
     */
    void getProductFilterMore(ProductFilterSortRequest request) {
        add(getFilterProductWithSort.asObservable(request).zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
            (productListResponse, favouriteListResponses) -> {
              for (ItemsItem item : productListResponse.getItems()) {
                setAsFavIfNeeded(item, favouriteListResponses);
              }
              return productListResponse;
            })
                .subscribe(response -> {
                    addCategoryNames(response.getItems(), dataManager.getCategories());
                    getView().renderFilterMore(response);
                }, throwable -> {
                    throwable.printStackTrace();
                    getView().render(new Exception("Failed to load products"));
                    getView().renderSrlRefreshFalse();
                }));
    }

    /**
     * 20200301 - WIKI Toan Tran - get information of tree filter the first time with param
     * is a string request get from Class LisrParamsFilter
     * @param request
     */
  public void getTreeCateStringJson(String request){
    add(getTreeCategoryById.asObservable(request)
            .subscribe(response -> {
              try {
                getView().renderTreeCategory(response.string());
              } catch (JSONException e) {
                e.printStackTrace();
                getView().render(new Exception("Failed to category list ..."));
              } catch (IOException e) {
                e.printStackTrace();
                getView().render(new Exception("Failed to category list ..."));
              }
            }, throwable -> {
              throwable.printStackTrace();
              getView().render(new Exception("Failed to category list ..."));
            }));
  }

    /**
     * 20200305 - WIKI Toan Tran - get information of tree filter when click category
     * @param idCate
     */
    public void getTreeCateStringJsonByCheckList(String idCate){
        String request = ""+idCate;
        getView().showLoading("Getting category list...");
        add(getTreeCategoryByCheckList.asObservable(request)
                .subscribe(response -> {
                    try {
                        getView().renderTreeCategory(response.string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        getView().hideLoading();
                        getView().render(new Exception("Failed to load category "));
                    } catch (IOException e) {
                        e.printStackTrace();
                        getView().hideLoading();
                        getView().render(new Exception("Failed to load category"));
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getView().hideLoading();
                    getView().render(new Exception("Failed to load category"));
                }));
    }
  
  public void getFeatureProducts(String category_id) {
    FeatureProductRequest request = new FeatureProductRequest(category_id);
    request.setSaveResult(true);
    add(getFeatureTravel.asObservable(request)
        .zipWith(getFavourites.asObservable(GetFavouriteRequest.getAll()),
            (featureProduct, favouriteListResponses) -> {
              for (ItemsItem item : featureProduct.getFeatureItems()) {
                setAsFavIfNeeded(item, favouriteListResponses);
              }
              return featureProduct;
            })
        .subscribe(response -> {
          addCategoryNames(response.getFeatureItems(), category);
          getView().render(response);
        }, throwable -> {
          throwable.printStackTrace();
          getView().render(new Exception("Failed to load featured products"));

        }));
  }

  void removeFromFavourite(final ItemsItem item, final boolean featuredProduct, int position) {

    item.setFavourite(false);
    getView().notifyFavStatusChanged(featuredProduct, position);
    getView().showLoading("Removing...");
    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .flatMapIterable(favouriteListResponses -> favouriteListResponses)
        .filter(
            favouriteListResponse -> favouriteListResponse.getProductId().equals(item.getId() + ""))
        .map(favouriteListResponse -> new RemoveFromFavourite.Params(
            favouriteListResponse.getWishlistItemId()))
        .flatMap(removeFromFavourite::asObservable)
        .subscribe(favouriteResponse -> {
          getView().hideLoading();
          if (!favouriteResponse.status) {
            item.setFavourite(true);
            getView().notifyFavStatusChanged(featuredProduct, position);
          }
        }, throwable -> {
          getView().hideLoading();
          item.setFavourite(true);
          getView().notifyFavStatusChanged(featuredProduct, position);
        }));
  }

  void addToFavourite(final ItemsItem item, final boolean featuredItem, int position) {
    item.setFavourite(true);
    getView().notifyFavStatusChanged(featuredItem, position);
    getView().showLoading("Adding ...");

    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .filter(favouriteListResponses -> favouriteListResponses.size() < Constants.MAX_FAV_COUNT)
        .map(favouriteListResponses -> new AddToFavourite.Params("" + item.getId()))
        .flatMap(addToFavourite::asObservable)
        .defaultIfEmpty(FavouriteResponse.numExceeded())
        .subscribe(favouriteResponse -> {
          getView().hideLoading();
          if (!favouriteResponse.status) {
            item.setFavourite(false);
            getView().notifyFavStatusChanged(featuredItem, position);
            getView().render(new Exception(favouriteResponse.message));
          }
        }, throwable -> {
          getView().hideLoading();
          item.setFavourite(false);
          getView().render(new Exception(throwable));
          getView().notifyFavStatusChanged(featuredItem, position);
        }));
  }

  private void setAsFavIfNeeded(ItemsItem item,
                                List<FavouriteListResponse> favouriteListResponses) {
    for (FavouriteListResponse fav : favouriteListResponses) {
      if (fav.getProductId().equals(item.getId() + "")) {
        item.setFavourite(true);
        return;
      }
    }
  }

  private void addCategoryNames(List<ItemsItem> itemsItems, CategoryResponse categories) {
    for (ItemsItem item : itemsItems) {
      String categoryName = getCategoryName(categories.getChildrenData(), item);
      item.setCategoryName(categoryName);
    }
  }

  private String getCategoryName(List<ChildData> categories, ItemsItem item) {
    for (CustomAttributesItem attributesItem : item.getCustomAttributes()) {
      if (attributesItem.getAttributeCode().equals("category_ids")) {
        Object value = attributesItem.getValue();
        try {
          StringBuilder subCatBuilder = new StringBuilder();
          List<String> ids = (List<String>) value;
          for (String id : ids) {
            for (ChildData pillar : categories) {
              if (!id.equals("" + pillar.getId())) {
                for (ChildData_ subCat : pillar.getChildrenData()) {
                  if (id.equals("" + subCat.getId())) {
                    subCatBuilder.append(subCat.getName());
                    subCatBuilder.append(",");
                  }
                }
              }
            }
          }
          String subCategories = subCatBuilder.toString();
          if (subCategories.endsWith(",")) {
            subCategories = subCategories.substring(0, subCategories.lastIndexOf(","));
          }
          return subCategories;
        } catch (Exception e) {
          Timber.e(e);
          return "";
        }
      }
    }
    return "";
  }

  public void getCartCount() {
    add(getCartCount.asObservable()
        .subscribe(s -> getView().updateCartCount(s), Timber::e));
  }
  public void getRatingOption() {
    add(getRatingOption.asObservable()
        .subscribe(s -> {
          dataManager.saveRatingOption(s);
        }, Timber::e));
  }


  public void addToCart(final AddToCart.Params params) {
    getView().showLoading("Add to cart ...");
    add(addToCart.asObservable(params).subscribe(cart -> {
      getView().hideLoading();
      getView().render(new Exception("You added  \""+params.name+"\" to  your shopping cart."));
      getCartCount();
    }, throwable -> {
      getView().hideLoading();
      if (throwable instanceof IOException) {
        //getView().showAddToCartFailedDialog(params, throwable);
      } else if (throwable instanceof CartLimitException) {
        CartLimitException cartLimitException = (CartLimitException) throwable;
        if (cartLimitException.getLimitErrors() != null
            && cartLimitException.getLimitErrors().size() > 0) {
          //getView().showAddToCartFailedDialog(null,new Exception(cartLimitException.getLimitErrors().get(0).message));
        }
      } else {
        getView().render(throwable);
        // getView().render(new Exception("Failed to add product to cart"));

      }
    }));
  }

  public void addToBuyNow(final AddToBuyNow.Params params) {
    getView().showLoading("Add to cart ...");
    add(addToBuyNow.asObservable(params).subscribe(cart -> {
      getView().hideLoading();
      getView().render(new Exception("You added  \""+params.name+"\" to  your shopping cart."));
      getCartCount();
      getView().renderShoppingCart();
    }, throwable -> {
      getView().hideLoading();
      if (throwable instanceof IOException) {
        //getView().showAddToCartFailedDialog(params, throwable);
      } else if (throwable instanceof CartLimitException) {
        CartLimitException cartLimitException = (CartLimitException) throwable;
        if (cartLimitException.getLimitErrors() != null
            && cartLimitException.getLimitErrors().size() > 0) {
          //getView().showAddToCartFailedDialog(null,new Exception(cartLimitException.getLimitErrors().get(0).message));
        }
      } else {
        getView().render(throwable);
        // getView().render(new Exception("Failed to add product to cart"));

      }
    }));
  }


  public void saveTreeFilter(String treeFilter){
      dataManager.saveTreeFilter(treeFilter);
  }

  public String getStringTreeFilterLocal(){
      return dataManager.getStringTreeFilterLocal();
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }
}

package com.skypremiuminternational.app.app.features.estore.detail;

import com.skypremiuminternational.app.app.internal.mvp.BasePresenter;
import com.skypremiuminternational.app.app.internal.mvp.BaseSubscriber;
import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.network.request.DetailsRequest;
import com.skypremiuminternational.app.data.network.request.GetFavouriteRequest;
import com.skypremiuminternational.app.data.network.request.ProductReviewRequest;
import com.skypremiuminternational.app.domain.exception.cart.CartLimitException;
import com.skypremiuminternational.app.domain.interactor.auth.GetAppVersion;
import com.skypremiuminternational.app.domain.interactor.cart.AddToBuyNow;
import com.skypremiuminternational.app.domain.interactor.cart.AddToCart;
import com.skypremiuminternational.app.domain.interactor.cart.GetCartCount;
import com.skypremiuminternational.app.domain.interactor.category.GetCategory;
import com.skypremiuminternational.app.domain.interactor.details.GetDetails;
import com.skypremiuminternational.app.domain.interactor.details.GetRecommendations;
import com.skypremiuminternational.app.domain.interactor.favourite.AddToFavourite;
import com.skypremiuminternational.app.domain.interactor.favourite.GetFavourites;
import com.skypremiuminternational.app.domain.interactor.favourite.RemoveFromFavourite;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetListReviewByProduct;
import com.skypremiuminternational.app.domain.interactor.rating_comment.GetRatingSummary;
import com.skypremiuminternational.app.domain.models.category.CategoryResponse;
import com.skypremiuminternational.app.domain.models.category.ChildData;
import com.skypremiuminternational.app.domain.models.category.ChildData_;
import com.skypremiuminternational.app.domain.models.details.CustomAttribute;
import com.skypremiuminternational.app.domain.models.details.DetailsItem;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteListResponse;
import com.skypremiuminternational.app.domain.models.favourite.FavouriteResponse;
import com.skypremiuminternational.app.domain.models.wellness.CustomAttributesItem;
import com.skypremiuminternational.app.domain.models.wellness.ItemsItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by wmw on 2/6/2018.
 */

public class EstoreDetailPresenter extends BasePresenter<EstoreDetailView> {
  private final AddToFavourite addToFavourite;
  private final RemoveFromFavourite removeFromFavourite;
  private final GetFavourites getFavourites;
  private final GetRecommendations getRecommendations;
  private final GetCategory getCategory;
  private final AddToCart addToCart;
  private final GetCartCount getCartCount;
  private final GetListReviewByProduct getListReviewByProduct;
  private final GetRatingSummary getRatingSummary;
  private final AddToBuyNow addToBuyNow;
  private GetDetails getDetails;
  private EstoreDetailViewState viewState;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject
  public EstoreDetailPresenter(GetAppVersion getAppVersion, InternalStorageManager internalStorageManager,
                               AddToCart addToCart, GetDetails getDetails, GetCategory getCategory,
                               GetFavourites getFavourites, RemoveFromFavourite removeFromFavourite,
                               AddToFavourite addToFavourite, GetRecommendations getRecommendations,
                               GetCartCount getCartCount,GetListReviewByProduct getListReviewByProduct,
                               GetRatingSummary getRatingSummary,AddToBuyNow addToBuyNow) {
    super(getAppVersion, internalStorageManager);

    viewState = EstoreDetailViewState.builder()
        .error(null)
        .isLoading(false)
        .isSuccess(false)
        .category(null)
        .message(null)
        .build();

    this.getCartCount = getCartCount;
    this.addToCart = addToCart;
    this.getCategory = getCategory;
    this.getDetails = getDetails;
    this.getRecommendations = getRecommendations;
    this.getFavourites = getFavourites;
    this.removeFromFavourite = removeFromFavourite;
    this.addToFavourite = addToFavourite;
    this.getListReviewByProduct = getListReviewByProduct;
    this.getRatingSummary = getRatingSummary;
    this.addToBuyNow = addToBuyNow;
  }

  @Override
  public void onStop() {
    compositeSubscription.clear();
    super.onStop();
  }

  public void getCategory(final String sku,final  String  id) {
    Subscription subscription = getCategory.execute(new SingleSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse value) {
        viewState = EstoreDetailViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .category(value)
            .message(viewState.message())
            .build();

            String s =EstoreDetailActivity.category_id;
        getRecommendationList(sku,id);
      }

      @Override
      public void onError(Throwable error) {
        viewState = EstoreDetailViewState.builder()
            .error(new Exception("Failed to load categories"))
            .isLoading(false)
            .isSuccess(false)
            .category(viewState.category())
            .message(viewState.message())
            .build();
        getView().render(viewState);
      }
    } );
    compositeSubscription.add(subscription);
  }

  private void getRecommendationList(String sku,String category_id) {
    DetailsRequest detailsRequest = new DetailsRequest(sku,category_id);

    Subscription subscription = getFavourites.asObservable(GetFavouriteRequest.getAll())
        .zipWith(getRecommendations.asObservable(detailsRequest),
            (favouriteListResponses, recommendItems) -> {
              for (ItemsItem item : recommendItems.getItems()) {
                setAsFavIfNeeded(item, favouriteListResponses);
              }
              addCategoryNames(recommendItems.getItems(), viewState.category());
              return recommendItems;
            })
        .subscribe(recommendItems -> getView().showRecommendationList(recommendItems.getItems()),
            throwable -> {
              viewState = EstoreDetailViewState.builder()
                  .error(new Exception("Failed to load recommended products"))
                  .isLoading(false)
                  .isSuccess(false)
                  .category(viewState.category())
                  .message(viewState.message())
                  .build();
              getView().render(viewState);
            });
    compositeSubscription.add(subscription);
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

  public void getCategoryName(ItemsItem itemsItem) {
    Subscription subscription = getCategory.execute(new SingleSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse value) {
        getView().renderCategory(getCategoryName(value.getChildrenData(),itemsItem));
      }

      @Override
      public void onError(Throwable error) {
        viewState = EstoreDetailViewState.builder()
            .error(new Exception("Failed to load categories"))
            .isLoading(false)
            .isSuccess(false)
            .category(viewState.category())
            .message(viewState.message())
            .build();
        getView().render(viewState);
      }
    } );
    compositeSubscription.add(subscription);
  }

  public void getCategoryName(DetailsItem itemsItem) {
    Subscription subscription = getCategory.execute(new SingleSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse value) {
        getView().renderCategory(getCategoryName(value.getChildrenData(),itemsItem));
      }

      @Override
      public void onError(Throwable error) {
        viewState = EstoreDetailViewState.builder()
            .error(new Exception("Failed to load categories"))
            .isLoading(false)
            .isSuccess(false)
            .category(viewState.category())
            .message(viewState.message())
            .build();
        getView().render(viewState);
      }
    } );
    compositeSubscription.add(subscription);
  }

  private void addCategoryNames(List<ItemsItem> itemsItems, CategoryResponse categories) {
    for (ItemsItem item : itemsItems) {
      String categoryName = getCategoryName(categories.getChildrenData(), item);
      item.setCategoryName(categoryName);
    }
  }


  private void addCategoryNames(DetailsItem item, CategoryResponse categories) {

      String categoryName = getCategoryName(categories.getChildrenData(), item);
      item.setCategoryName(categoryName);

  }
  private String getCategoryName(List<ChildData> categories, DetailsItem item) {
    for (CustomAttribute attributesItem : item.getCustomAttributes()) {
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
              } else {
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
              } else {
                item.setPillarName(pillar.getName());
                item.setPillarId(id);
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
  /*private String getCategoryName(List<ChildData> categories, DetailsItem item) {
    for (CustomAttribute attributesItem : item.getCustomAttributes()) {
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
              } else {
                item.setPillarName(pillar.getName());
                item.setPillarId(id);
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
  }*/

  public void getDetails(String sku) {
    onShowLoading();
    DetailsRequest detailsRequest = new DetailsRequest(sku,"");
    Subscription subscription = getDetails.execute(new BaseSubscriber<DetailsItem>() {
      @Override
      public void onSuccess(DetailsItem value) {
        viewState = EstoreDetailViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(value)
            .build();
        if(value.getExtensionAttributes() != null && value.getExtensionAttributes()
                .getStockItem().getIsInStock()){

          EstoreDetailActivity.qty = value.getExtensionAttributes().getStockItem().getQty();
        }
        EstoreDetailActivity.product_name = value.getName();
        getView().render(viewState);
      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        viewState = EstoreDetailViewState.builder()
            .error(new Exception("Failed to load product detail"))
            .isLoading(false)
            .isSuccess(true)
            .message(null)
            .build();
        getView().render(viewState);

      }
    }, detailsRequest);
    compositeSubscription.add(subscription);
  }
  public void getCategorySimple(final String sku) {
    Subscription subscription = getCategory.execute(new SingleSubscriber<CategoryResponse>() {
      @Override
      public void onSuccess(CategoryResponse value) {
        getDetailsSimple(sku,value);
      }

      @Override
      public void onError(Throwable error) {
        getView().render(viewState);
      }
    } );
    compositeSubscription.add(subscription);
  }
  public void getDetailsSimple(String sku,CategoryResponse categoryResponse) {
    onShowLoading();
    DetailsRequest detailsRequest = new DetailsRequest(sku,"");
    Subscription subscription = getDetails.execute(new BaseSubscriber<DetailsItem>() {
      @Override
      public void onSuccess(DetailsItem value) {
        viewState = EstoreDetailViewState.builder()
            .error(null)
            .isLoading(false)
            .isSuccess(true)
            .message(value)
            .build();
        if(value.getExtensionAttributes() != null && value.getExtensionAttributes()
                .getStockItem().getIsInStock()){

          EstoreDetailActivity.qty = value.getExtensionAttributes().getStockItem().getQty();
        }
        addCategoryNames(value, categoryResponse);
        EstoreDetailActivity.product_name = value.getName();
        getView().setContentEstoreDetail(value);
        getView().renderSimple(viewState);

      }

      @Override
      public void onError(Throwable error) {
        super.onError(error);
        viewState = EstoreDetailViewState.builder()
            .error(new Exception("Failed to load product detail"))
            .isLoading(false)
            .isSuccess(true)
            .message(null)
            .build();
        getView().render(viewState);

      }
    }, detailsRequest);
    compositeSubscription.add(subscription);
  }


  private void onShowLoading() {
    viewState = EstoreDetailViewState.builder()
        .error(null)
        .isLoading(true)
        .isSuccess(false)
        .message(null)
        .build();
    getView().render(viewState);
  }

  public void removeFromFavourite(final String id) {
    onShowLoading();
    getView().notifyFavStatusChanged(false, id);
    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .flatMapIterable(favouriteListResponses -> favouriteListResponses)
        .filter(favouriteListResponse -> favouriteListResponse.getProductId().equals(id)).map(
            favouriteListResponse -> new RemoveFromFavourite.Params(favouriteListResponse.getWishlistItemId()))
        .flatMap(removeFromFavourite::asObservable).subscribe(favouriteResponse -> {
          if (!favouriteResponse.status) {
            getView().notifyFavStatusChanged(true, id);
            viewState = EstoreDetailViewState.builder()
                .error(new Exception(favouriteResponse.message))
                .isLoading(false)
                .isSuccess(false)
                .message(viewState.message())
                .build();
            getView().render(viewState);
          } else {
            getView().hideLoading();
          }
        }, throwable -> {
          getView().notifyFavStatusChanged(true, id);
          viewState = EstoreDetailViewState.builder()
              .error(throwable)
              .isLoading(false)
              .isSuccess(false)
              .message(viewState.message())
              .build();
          getView().render(viewState);
        }));
  }

  public void addToFavourite(final String id) {
    onShowLoading();
    getView().notifyFavStatusChanged(true, id);
    add(getFavourites.asObservable(GetFavouriteRequest.getAll())
        .filter(favouriteListResponses -> favouriteListResponses.size() < Constants.MAX_FAV_COUNT)
        .map(favouriteListResponses -> new AddToFavourite.Params(id))
        .flatMap(addToFavourite::asObservable).defaultIfEmpty(FavouriteResponse.numExceeded())
        .subscribe(favouriteResponse -> {
          if (!favouriteResponse.status) {
            getView().notifyFavStatusChanged(false, id);
            viewState = EstoreDetailViewState.builder()
                .error(new Exception(favouriteResponse.message))
                .isSuccess(false)
                .isLoading(false)
                .message(viewState.message())
                .build();
            getView().render(viewState);
          } else {
            getView().hideLoading();
          }
        }, throwable -> {
          getView().notifyFavStatusChanged(false, id);
          viewState = EstoreDetailViewState.builder()
              .error(throwable)
              .isLoading(false)
              .isSuccess(false)
              .message(viewState.message())
              .build();
          getView().render(viewState);
        }));
  }

  @Override
  public void add(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  public void addToCart(final AddToCart.Params params) {
    onShowLoading();
    add(addToCart.asObservable(params).subscribe(cart -> {
      getView().hideLoading();
      getView().render("You added  \""+params.name+"\" to  your shopping cart.");
      getCartCount();
    }, throwable -> {
      getView().hideLoading();
      if (throwable instanceof IOException) {
        getView().showAddToCartFailedDialog(params, throwable);
      } else if (throwable instanceof CartLimitException) {
        CartLimitException cartLimitException = (CartLimitException) throwable;
        if (cartLimitException.getLimitErrors() != null
            && cartLimitException.getLimitErrors().size() > 0) {
          getView().showAddToCartFailedDialog(null,
              new Exception(cartLimitException.getLimitErrors().get(0).message));
        }
      } else {
          getView().render(throwable);
          // getView().render(new Exception("Failed to add product to cart"));

      }
    }));
  }
  public void addToBuyNow(final AddToBuyNow.Params params) {
    onShowLoading();
    add(addToBuyNow.asObservable(params).subscribe(cart -> {
      getView().hideLoading();
      getView().render("You added  \""+params.name+"\" to  your shopping cart.");
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
  public void getListReviewProduct(ProductReviewRequest reviewRequest) {
    add(getListReviewByProduct.asObservable(reviewRequest).subscribe(productReviewResponse -> {
      getView().renderProductReview(productReviewResponse);

    }, throwable -> {
      getView().renderFailedComment(throwable);
    }));
  }
  public void getListReviewProductByPage(ProductReviewRequest reviewRequest) {
    add(getListReviewByProduct.asObservable(reviewRequest).subscribe(productReviewResponse -> {
      getView().renderProductReviewByPage(productReviewResponse);

    }, throwable -> {
      getView().render(throwable);
    }));
  }

  public void getRatingSummary(String productId) {
    onShowLoading();
    Map<String, String> map = new HashMap<>();

    map.put("product",productId);

    add(getRatingSummary.asObservable(map).subscribe(responseBody -> {
      getView().renderRatingStar(responseBody);

    }, throwable -> {
      getView().render(throwable);
      getView().hideLoading();
    }));
  }

  void getCartCount() {
    add(getCartCount.asObservable()
        .subscribe(s -> getView().updateShoppingCartCount(s), Timber::e));
  }


}

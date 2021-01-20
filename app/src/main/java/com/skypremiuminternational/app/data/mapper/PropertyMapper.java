package com.skypremiuminternational.app.data.mapper;

import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.skypremiuminternational.app.app.utils.Constants;
import com.skypremiuminternational.app.app.utils.DecimalUtil;
import com.skypremiuminternational.app.app.utils.Validator;
import com.skypremiuminternational.app.data.model.ean.availability.AmenityItem;
import com.skypremiuminternational.app.data.model.ean.availability.AvailableProperty;
import com.skypremiuminternational.app.data.model.ean.availability.BedGroupsItem;
import com.skypremiuminternational.app.data.model.ean.availability.CancelPenaltiesItem;
import com.skypremiuminternational.app.data.model.ean.availability.NightlyItem;
import com.skypremiuminternational.app.data.model.ean.availability.RatesItem;
import com.skypremiuminternational.app.data.model.ean.availability.RoomsItem;
import com.skypremiuminternational.app.data.model.ean.content.BedGroup;
import com.skypremiuminternational.app.data.model.ean.content.Descriptions;
import com.skypremiuminternational.app.data.model.ean.content.ImagesItem;
import com.skypremiuminternational.app.data.model.ean.content.PropertyContent;
import com.skypremiuminternational.app.data.model.ean.content.RoomContent;
import com.skypremiuminternational.app.domain.models.ean.Amenity;
import com.skypremiuminternational.app.domain.models.ean.CancelPenalty;
import com.skypremiuminternational.app.domain.models.ean.Property;
import com.skypremiuminternational.app.domain.models.ean.Room;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class PropertyMapper {
  private List<String> euCountryCodes;

  @Inject
  public PropertyMapper() {
    euCountryCodes = Arrays.asList(Constants.EU_COUNTRY_CODE);
  }

  public String toStringt(StringBuilder result) {
     result = new StringBuilder();
    String newLine = System.getProperty("line.separator");

    result.append( this.getClass().getName() );
    result.append( " Object {" );
    result.append(newLine);

    //determine fields declared in this class only (no fields of superclass)
    Field[] fields = this.getClass().getDeclaredFields();

    //print field names paired with their values
    for ( Field field : fields  ) {
      result.append("  ");
      try {
        result.append( field.getName() );
        result.append(": ");
        //requires access to private field:
        result.append( field.get(this) );
      } catch ( IllegalAccessException ex ) {
        System.out.println(ex);
      }
      result.append(newLine);
    }
    result.append("}");

    return result.toString();
  }

  public Map<String, Property> transform(Map<String, PropertyContent> contentMap,
                                         List<AvailableProperty> availableProperties, List<String> occupancies,
                                         String roomItemOccupancy) {
    Map<String, Property> propertyMap = new HashMap<>();

    for (AvailableProperty availableProperty : availableProperties) {
      PropertyContent propertyContent = contentMap.get(availableProperty.getPropertyId());



      if (propertyContent != null) {
        int roomCount = 0;
        String checkInBeginTime = "";
        String checkInEndTime = "";
        String checkInInstructions = "";
        String specialCheckInInstructions = "";
        String checkoutTime = "";
        String minCheckInAge = "";
        if (propertyContent.getCheckin() != null) {
          if (propertyContent.getCheckin().getBeginTime() != null) {
            checkInBeginTime = propertyContent.getCheckin().getBeginTime();
          }
          if (propertyContent.getCheckin().getEndTime() != null) {
            checkInEndTime = propertyContent.getCheckin().getEndTime();
          }
          if (propertyContent.getCheckin().getInstructions() != null) {
            checkInInstructions = propertyContent.getCheckin().getInstructions();
          }

          if (propertyContent.getCheckin().getSpecialInstructions() != null) {
            specialCheckInInstructions = propertyContent.getCheckin().getSpecialInstructions();
          }
          if (propertyContent.getCheckin().getMinAge() != null) {
            minCheckInAge = propertyContent.getCheckin().getMinAge();
          }
        }
        if (propertyContent.getCheckout() != null
            && propertyContent.getCheckout().getTime() != null) {
          checkoutTime = propertyContent.getCheckout().getTime();
        }

        List<RoomsItem> roomsItems = availableProperty.getRooms();
        roomCount = roomsItems.size();
        List<Room> rooms = new ArrayList<>();
        if (propertyContent.getRooms() != null) {
          for (RoomsItem roomsItem : roomsItems) {
            RoomContent roomContent = propertyContent.getRooms().get(roomsItem.getId());
            if (roomContent != null) {
              for (RatesItem rates : roomsItem.getRates()) {

                rooms.add(buildRoom(rates, roomContent, roomsItem, roomItemOccupancy, occupancies,
                    checkInBeginTime, checkInEndTime, checkInInstructions,
                    specialCheckInInstructions,
                    checkoutTime,
                    minCheckInAge));
              }


            }
          }

        }


        List<String> carouselImages = new ArrayList<>();
        String image = "";
        if (propertyContent.getImages() != null) {
          for (ImagesItem imagesItem : propertyContent.getImages()) {
            if (imagesItem.getLinks().getDimension1000Px() != null) {
              if (carouselImages.size() < 10) {
                carouselImages.add(imagesItem.getLinks().getDimension1000Px().getHref());
                if (TextUtils.isEmpty(image)) {
                  image = imagesItem.getLinks().getDimension1000Px().getHref();
                }
              } else {
                break;
              }
            }
          }
        }
        boolean isEurope = false;
        if (propertyContent.getAddress() != null) {
          isEurope = isEurope(propertyContent.getAddress().getCountryCode());
        }
        String address = "";
        if (propertyContent.getAddress() != null) {
          if (propertyContent.getAddress().getLine1() != null) {
            address += propertyContent.getAddress().getLine1();
          }

          if (propertyContent.getAddress().getCity() != null) {
            address += ", " + propertyContent.getAddress()
                .getCity();
          }

          if (propertyContent.getAddress().getPostalCode() != null) {
            address += ", " + propertyContent.getAddress().getPostalCode();
          }
        }
        if (address.startsWith(",")) {
          address = address.substring(1, address.length());
        }
        Property property = Property.builder()
            .id(availableProperty.getPropertyId())
            .name(propertyContent.getName())
            .type(propertyContent.getCategory().getName())
            .city(propertyContent.getAddress().getCity())
            .lat(propertyContent.getLocation().getCoordinates().getLatitude())
            .lng(propertyContent.getLocation().getCoordinates().getLongitude())
            .address(address)
            .description(parseDescriptions(propertyContent.getDescriptions()))
            .rooms(rooms)
            .carouselImages(carouselImages)
            .image(image)
            .lowestNightlyPrice("")
            .isEurope(isEurope)
            .roomCount(roomCount)
            .build();
        propertyMap.put(property.id(), property);
      }
    }
      return propertyMap;
  }

  private boolean isEurope(String countryCode) {
    return euCountryCodes.contains(countryCode);
  }

  private Room buildRoom(RatesItem rates, RoomContent roomContent, RoomsItem roomItem,
                         String roomItemOccupancy, List<String> occupancies, String checkInBeginTime,
                         String checkInEndTime, String checkInInstructions, String specialCheckInInstructions,
                         String checkOutTime,
                         String minCheckInAge) {

    String paymentOptionLink = "";
    if (rates.getLinks() != null
        && rates.getLinks().getPayment() != null) {

      paymentOptionLink = rates.getLinks().getPayment().getHref();
    }
    String image = "";
    if (roomContent.getImages() != null) {
      for (ImagesItem imagesItem : roomContent.getImages()) {
        if (imagesItem.getLinks().getDimension350Px() != null) {
          image = imagesItem.getLinks().getDimension350Px().getHref();
          break;
        }
      }
    }

    String nightlyPrice = "";
    if (rates.getOccupancies().get(occupancies.get(0)) != null) {
      List<NightlyItem> nightlyItems =
          rates.getOccupancies().get(occupancies.get(0)).getNightly().get(0);

      if (nightlyItems != null) {
        for (NightlyItem nightlyItem : nightlyItems) {
          if (nightlyItem.getType().equalsIgnoreCase("base_rate")) {
            double price;
            try {
              price = Double.parseDouble(nightlyItem.getValue());
            } catch (Exception ignored) {
              price = 0.0;
            }
            nightlyPrice =
                "S$" + DecimalUtil.roundTwoDecimals(price);
            break;
          }
        }
      }
    }

    CancelPenalty cancelPenalty = null;
    List<CancelPenalty> cancelsPenaltiesList = new ArrayList<>();
    List<Amenity> amenities = new ArrayList<>();

    List<String> priceCheckLinks = new ArrayList<>();


    if (rates.getBedGroups() != null && rates.getBedGroups().size() > 0) {

      Iterator it = rates.getBedGroups().entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        if (rates.getBedGroups() != null
                && rates.getBedGroups().size() > 0
                && rates.getBedGroups().get(pair.getKey()).getLinks() != null
                && rates.getBedGroups().get(pair.getKey()).getLinks().getPriceCheck() != null) {
          priceCheckLinks.add(rates.getBedGroups().get(pair.getKey()).getLinks().getPriceCheck().getHref());
          break;
        }
        // System.out.println(pair.getKey() + " = " + pair.getValue());
      //  it.remove(); // avoids a ConcurrentModificationException
      }


    }


    if (rates.isRefundable()
        && rates.getCancelPenalties() != null
        && rates.getCancelPenalties().size() > 0) {


      for(CancelPenaltiesItem cancelPenaltiesItem1 : rates.getCancelPenalties()) {
        CancelPenalty cancelPenalty1 = CancelPenalty.builder()
                .start(cancelPenaltiesItem1.getStart())
                .end(cancelPenaltiesItem1.getEnd())
                .percent(cancelPenaltiesItem1.getPercent())
                .amount(cancelPenaltiesItem1.getAmount())
                .nights(cancelPenaltiesItem1.getNights())
                .isRefundable(rates.isRefundable())
                .currency(cancelPenaltiesItem1.getCurrency())
                .build();
        cancelsPenaltiesList.add(cancelPenalty1);

      }
      CancelPenaltiesItem item = rates.getCancelPenalties().get(0);
      cancelPenalty = CancelPenalty.builder()
          .isRefundable(true)
          .start(item.getStart())
          .end(item.getEnd())
          .percent(item.getPercent())
          .cancelPenaltyList(cancelsPenaltiesList)
          .amount(item.getAmount())
          .nights(item.getNights())
          .currency(item.getCurrency())
          .build();
    }
    if (rates.getAmenities() != null && rates.getAmenities().size() > 0) {


        Iterator it = rates.getAmenities().entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry pair = (Map.Entry)it.next();
          Amenity amenity = Amenity.builder()
                  .id(rates.getAmenities().get(pair.getKey()).id)
                  .description(rates.getAmenities().get(pair.getKey()).name)
                  .isCancellationPolicy(false)
                  .build();
          addToAmenities(amenities, amenity);
         // it.remove(); // avoids a ConcurrentModificationException
        }

      /* for (AmenityItem amenityItem : rates.getAmenities()) {
        Amenity amenity = Amenity.builder()
            .id(amenityItem.id)
            .description(amenityItem.name)
            .isCancellationPolicy(false)
            .build();
        addToAmenities(amenities, amenity);
      } */
    }

    addToAmenities(amenities, Amenity.builder()
        .id(-1)
        .description("")
        .isCancellationPolicy(true)
        .build());

    if (cancelPenalty == null) {
      cancelPenalty = CancelPenalty.builder()
          .isRefundable(false)
          .nights("")
          .start("")
          .amount("")
          .end("")
          .currency("")
          .build();
    }
    String bedTypes = "";
    if (roomContent.getBedGroups() != null) {
      bedTypes = parseBedGroup(roomContent);
    }
    String detail = "";
    if (roomContent.getDescriptions() != null) {
      detail = roomContent.getDescriptions().getOverview();
    }
    return Room.builder()
        .id(roomItem.getId())
        .image(image)
        .name(roomItem.getRoomName())
        .cancelPenalty(cancelPenalty)
        .amenities(amenities)
        .type(bedTypes)
        .occupancy(roomItemOccupancy)
        .detail(detail)
        .cancellationPolicy("Cancellation policy")
        .nightlyPrice(nightlyPrice)
        .priceCheckLinks(priceCheckLinks)
        .checkInBeginTime(checkInBeginTime)
        .checkInEndTime(checkInEndTime)
        .checkInInstructions(checkInInstructions)
        .checkoutTime(checkOutTime)
        .minCheckInAge(minCheckInAge)
        .paymentOptionLink(paymentOptionLink)
        .specialCheckInInstructions(specialCheckInInstructions)
        .build();
  }

  private String parseDescriptions(Descriptions descriptions) {
    String description = "";
    if (descriptions == null) return description;
    if (Validator.isTextValid(descriptions.getLocation())) {
      description += "<b>Location</b><br>";
      description += descriptions.getLocation() + "<br>";
    }

    if (Validator.isTextValid(descriptions.getAttractions())) {
      description += "<br><b>Attractions</b><br>";
      description += descriptions.getAttractions();
    }

    if (Validator.isTextValid(descriptions.getDining())) {
      description += "<br><b>Dining</b><br>";
      description += descriptions.getDining() + "<br>";
    }

    if (Validator.isTextValid(descriptions.getAmenities())) {
      description += "<br><b>Amenities</b><br>";
      description += descriptions.getAmenities() + "<br>";
    }

    if (Validator.isTextValid(descriptions.getBusinessAmenities())) {
      description += "<br><b>Business Amenities</b><br>";
      description += descriptions.getBusinessAmenities() + "<br>";
    }

    if (Validator.isTextValid(descriptions.getRooms())) {
      description += "<br><b>Rooms</b><br>";
      description += descriptions.getRooms() + "<br>";
    }

    if (Validator.isTextValid(descriptions.getRenovations())) {
      description += "<br><b>Renovations</b><br>";
      description += descriptions.getRenovations() + "<br>";
    }
    return description;
  }

  private String parseBedGroup(@NonNull RoomContent roomContent) {
    if (roomContent.getBedGroups().size() >= 1) {
      StringBuilder bedTypes = new StringBuilder();

      Map<String, BedGroup> bedGroupsItemMap = roomContent.getBedGroups();

      for (Map.Entry<String, BedGroup> bedGroup : bedGroupsItemMap.entrySet()) {
        bedTypes.append(bedGroup.getValue().description);
        bedTypes.append(" or ");
      }

      String refinedBedTypes = bedTypes.toString();
      if (refinedBedTypes.length() > 0) {
        refinedBedTypes = refinedBedTypes.substring(0, refinedBedTypes.lastIndexOf("or"));
      }

      return refinedBedTypes;
    } else {
      return "";
    }
  }

  private void addToAmenities(List<Amenity> amenities, Amenity amenity) {
    boolean alreadyInTheList = false;
    for (Amenity amenity1 : amenities) {

      if (amenity.isCancellationPolicy()) {
        if (amenity1.isCancellationPolicy()) {
          alreadyInTheList = true;
          break;
        }
      } else {
        if (!amenity1.isCancellationPolicy() && amenity.description()
            .equalsIgnoreCase(amenity1.description())) {
          alreadyInTheList = true;
          break;
        }
      }
    }
    if (!alreadyInTheList) {
      amenities.add(amenity);
    }
  }
}

package com.skypremiuminternational.app.app.features.travel.ean.occupancy;

import com.skypremiuminternational.app.app.internal.mvp.BaseFragmentPresenter;
import com.skypremiuminternational.app.domain.models.ean.Child;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.skypremiuminternational.app.app.utils.Constants.CHILDREN_AGES;
import static com.skypremiuminternational.app.app.utils.Constants.CHILDREN_AGES_IN_DIGIT;

/**
 * Created by codigo on 20/4/18.
 */

public class OccupancyPickerPresenter extends BaseFragmentPresenter<OccupancyPickerView> {
  private final static int ADULT_PER_ROOM = 2;
  private final static int CHILDREN_PER_ROOM = 2;
  private final static int MAX_ROOM_COUNT = 8;
  private final static int MIN_ROOM_COUNT = 1;
  private static final int MIN_ADULT_COUNT = 1;

  private List<Child> children;

  private int roomCount;
  private int adultCount;

  @Inject
  public OccupancyPickerPresenter() {
    children = new ArrayList<>();
  }

  void addChild(int selectedAgePosition) {
    Child child =
        new Child(CHILDREN_AGES_IN_DIGIT[selectedAgePosition], CHILDREN_AGES[selectedAgePosition],
            "Child " + (children.size() + 1));

    children.add(child);
    getView().render(roomCount, adultCount, children);
  }

  void editChildAge(Child child, int position) {
    getView().showChildAgePicker(child.name, CHILDREN_AGES, getPosition(child.digitAge), child,
        position);
  }

  private int getPosition(int digitAge) {
    for (int i = 0; i < CHILDREN_AGES_IN_DIGIT.length; i++) {
      if (CHILDREN_AGES_IN_DIGIT[i] == digitAge) return i;
    }
    return 0;
  }

  public void editChild(int selectedAgePosition, Child editingChild, int editingChildPosition) {
    editingChild.age = CHILDREN_AGES[selectedAgePosition];
    editingChild.digitAge = CHILDREN_AGES_IN_DIGIT[selectedAgePosition];
    getView().notifyChildStatusChange(editingChildPosition);
  }

  public void initValues(int roomCount, int adultCount, List<Child> children) {
    this.roomCount = roomCount;
    this.adultCount = adultCount;
    this.children = children;
  }

  public void decreaseRoomCount() {
    if (roomCount > MIN_ROOM_COUNT) {
      roomCount--;

      while (adultCount > roomCount * ADULT_PER_ROOM) {
        adultCount--;
      }

      while (children.size() > roomCount * CHILDREN_PER_ROOM) {
        children.remove(children.size() - 1);
      }

      getView().render(roomCount, adultCount, children);
    }
  }

  public void increaseRoomCount() {
    if (roomCount < MAX_ROOM_COUNT) {
      roomCount++;
      while (adultCount < roomCount * MIN_ADULT_COUNT) {
        adultCount++;
      }
      getView().render(roomCount, adultCount, children);
    } else {
      getView().renderError("You can choose eight rooms maximum.");
    }
  }

  public void decreaseAdultCount() {
    if (adultCount > roomCount * MIN_ADULT_COUNT) {
      adultCount--;
      getView().render(roomCount, adultCount, children);
    }
  }

  public void increaseAdultCount() {
    if (adultCount < roomCount * ADULT_PER_ROOM) {
      adultCount++;
      getView().render(roomCount, adultCount, children);
    } else {
      getView().renderError("You can choose only two persons in one room.");
    }
  }

  void increaseChildCount() {
    if (children.size() < roomCount * CHILDREN_PER_ROOM) {
      getView().showChildAgePicker("Child " + (children.size() + 1), CHILDREN_AGES,
          0, null, -1);
    } else {
      getView().renderError("Only Two children can available in one room");
    }
  }

  void decreaseChildCount() {
    if (children != null && children.size() > 0) {
      children.remove(children.size() - 1);
      getView().notifyChildCountChanged(children.size());
    }
  }

  public void collectValues() {
    getView().done(roomCount, adultCount, children);
  }

  public void reset() {
    roomCount = 1;
    adultCount = 1;
    children.clear();
    getView().render(roomCount, adultCount, children);
  }
}

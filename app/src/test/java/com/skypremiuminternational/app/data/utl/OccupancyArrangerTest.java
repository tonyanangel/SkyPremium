package com.skypremiuminternational.app.data.utl;

import static org.assertj.core.api.Assertions.assertThat;

import com.skypremiuminternational.app.domain.models.ean.Child;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class OccupancyArrangerTest {

  private OccupancyArranger occupancyArranger;
  private int roomCount;
  private int adultCount;
  private List<Child> children;
  private List<String> expectedResults;

  @Before public void setUp() {

    occupancyArranger = new OccupancyArranger();

    roomCount = 4;
    adultCount = 7;

    children = new ArrayList<>();
    children.add(buildChild(2, "2yo", "Child 1"));
    children.add(buildChild(3, "3yo", "Child 2"));
    children.add(buildChild(4, "4yo", "Child 3"));
    children.add(buildChild(5, "5yo", "Child 4"));
    children.add(buildChild(6, "6yo", "Child 5"));
    children.add(buildChild(7, "7yo", "Child 6"));

    expectedResults = new ArrayList<>();
    expectedResults.add("2-2,3");
    expectedResults.add("2-4,5");
    expectedResults.add("2-6,7");
    expectedResults.add("1");
  }

  @Test
  public void arrangeWithChildAge() {
    List<String> actualResults = occupancyArranger.groupOccupancies(roomCount, adultCount, children);

    assertThat(actualResults).isEqualTo(expectedResults);
  }

  private Child buildChild(int digitAge, String age, String name) {
    return new Child(digitAge, age, name);
  }
}
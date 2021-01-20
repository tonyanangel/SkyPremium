package com.skypremiuminternational.app.data.utl;

import android.util.Log;

import com.skypremiuminternational.app.domain.models.ean.Child;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class OccupancyArranger {

  @Inject
  public OccupancyArranger() {

  }

  public List<String> arrangeForName(int roomCount, int adultCount, int childCount) {
    List<String> occupancies = new ArrayList<>();
    String arrangeOccupancy;
    int[] adultOccupancy = new int[roomCount];
    int[] childOccupancy = new int[roomCount];

    int adultLeft = adultCount;
    int childLeft = childCount;

    while (adultLeft > 0) {
      for (int i = 0; i < roomCount; i++) {
        if (adultLeft > 0) {
          adultOccupancy[i] = adultOccupancy[i] + 1;
          adultLeft--;
        }

        if (childLeft >= 2) {
          childOccupancy[i] = 2;
          childLeft -= 2;
        } else if (childLeft == 1) {
          childOccupancy[i] = 1;
          childLeft--;
        }
      }
    }

    for (int i = 0; i < roomCount; i++) {
      int numAdult = adultOccupancy[i];
      int numChild = childOccupancy[i];
      if (numAdult > 1) {
        arrangeOccupancy = numAdult + " Adults";
      } else {
        arrangeOccupancy = numAdult + " Adult";
      }
      if (numChild > 0) {
        if (numChild > 1) {
          arrangeOccupancy += " + " + numChild + " Children";
        } else {
          arrangeOccupancy += " + " + numChild + " Child";
        }
      }
      occupancies.add(arrangeOccupancy);
    }
    return occupancies;
  }

  public List<int[]> arrangeForCount(int roomCount, int adultCount, int childCount) {
    List<int[]> occupancies = new ArrayList<>();

    int[] adultOccupancy = new int[roomCount];
    int[] childOccupancy = new int[roomCount];

    int adultLeft = adultCount;
    int childLeft = childCount;

    while (adultLeft > 0) {
      for (int i = 0; i < roomCount; i++) {
        if (adultLeft > 0) {
          adultOccupancy[i] = adultOccupancy[i] + 1;
          adultLeft--;
        }

        if (childLeft >= 2) {
          childOccupancy[i] = 2;
          childLeft -= 2;
        } else if (childLeft == 1) {
          childOccupancy[i] = 1;
          childLeft--;
        }
      }
    }

    for (int i = 0; i < roomCount; i++) {
      int numAdult = adultOccupancy[i];
      int numChild = childOccupancy[i];
      int[] occupancy = new int[2];
      occupancy[0] = numAdult;
      occupancy[1] = numChild;
      occupancies.add(occupancy);
    }
    return occupancies;
  }

  public List<String> groupOccupancies(final int roomCount, final int adultCount,
                                       List<Child> children) {
    List<String> occupancies = new ArrayList<>();
    int[] adultOccupancy = new int[roomCount];
    int[] childOccupancy = new int[roomCount];


    int adultLeft = adultCount;
    int childLeft = children.size();

    while (adultLeft > 0) {
      for (int i = 0; i < roomCount; i++) {
        if (adultLeft > 0) {
          adultOccupancy[i] = adultOccupancy[i] + 1;
          adultLeft--;
        }

        if (childLeft >= 2) {
          childOccupancy[i] = 2;
          childLeft -= 2;
        } else if (childLeft == 1) {
          childOccupancy[i] = 1;
          childLeft--;
        }
      }
    }
    int firstChildrenIndex = 0;
    for (int i = 0; i < roomCount; i++) {
      int numAdult = adultOccupancy[i];
      int numChild = childOccupancy[i];

      String occupancy = String.valueOf(numAdult);

      if (numChild > 0) {
        occupancy += "-";
        for (int j = 0; j < numChild; j++) {
          Child child = children.get(firstChildrenIndex);
          firstChildrenIndex++;
          occupancy += child.digitAge + ",";
        }
        occupancy = occupancy.substring(0, occupancy.lastIndexOf(","));
      }
      occupancies.add(occupancy);
    }
    return occupancies;
  }

  public String arrangeTotalGuest(int numAdult, List<Child> children) {
    int numChild = children.size();
    String occupancy;
    if (numAdult > 1) {
      occupancy = numAdult + " Adults";
    } else {
      occupancy = numAdult + " Adult";
    }
    int firstChildrenIndex = 0;
    if (numChild > 0) {
      if (numChild == 1) {
        occupancy += " + " + numChild + " Child (";
      } else {
        occupancy += " + " + numChild + " Children (";
      }

      for (int j = 0; j < numChild; j++) {
        Child child = children.get(firstChildrenIndex);
        firstChildrenIndex++;
        occupancy += (child.digitAge == 0 ? child.age : child.digitAge) + " & ";
      }
      occupancy = occupancy.substring(0, occupancy.lastIndexOf("&")).trim();
      occupancy += ")";
    }

    return occupancy;
  }

  public String arrangeForRoomItem(int adultCount, List<Child> children) {
    String occupancy;
    if (children != null && children.size() > 0) {
      if (children.size() == 1) {

        occupancy = String.valueOf(adultCount)
            + " Adults, "
            + String.valueOf(children.size())
            + " Child ("
            + children.get(0).age
            + ")";
      } else {
        List<Integer> childList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
          childList.add(children.get(i).digitAge);
        }
        int maxAge = Collections.max(childList);
        int minAge = Collections.min(childList);

        String age;
        if (maxAge == minAge) {
          if (maxAge == 0) {
            age = "(Under 1)";
          } else {
            age = "(" + maxAge + "yo)";
          }
        } else if (minAge == 0) {
          age = "(Under 1" + "- " + maxAge + "yo)";
        } else {
          age = "(" + minAge + "- " + maxAge + "yo)";
        }

        if (adultCount == 1) {
          occupancy = String.valueOf(adultCount) + " Adult, ";
        } else {
          occupancy = String.valueOf(adultCount) + " Adults, ";
        }
        occupancy += String.valueOf(children.size())
            + " Children "
            + age;
      }
    } else {
      occupancy = String.valueOf(adultCount) + " Adults, 0 Children";
    }

    return occupancy;
  }

  public List<String> arrangeChildAges(int roomCount, int adultCount, List<Child> children) {
    List<String> occupancies = new ArrayList<>();
    int[] adultOccupancy = new int[roomCount];
    int[] childOccupancy = new int[roomCount];

    int adultLeft = adultCount;
    int childLeft = children.size();

    while (adultLeft > 0) {
      for (int i = 0; i < roomCount; i++) {
        if (adultLeft > 0) {
          adultOccupancy[i] = adultOccupancy[i] + 1;
          adultLeft--;
        }

        if (childLeft >= 2) {
          childOccupancy[i] = 2;
          childLeft -= 2;
        } else if (childLeft == 1) {
          childOccupancy[i] = 1;
          childLeft--;
        }
      }
    }
    int firstChildrenIndex = 0;
    for (int i = 0; i < roomCount; i++) {
      int numAdult = adultOccupancy[i];
      int numChild = childOccupancy[i];

      String occupancy;

      //2 Adults + 6 Children (Under 1 & 1yo & 14yo & 15yo & 3yo & 17yo)

      if (numAdult > 1) {
        occupancy = numAdult + " Adults";
      } else {
        occupancy = numAdult + " Adult";
      }
      if (numChild > 0) {
        if (numChild == 1) {
          occupancy += " + " + numChild + " Child (";
        } else {
          occupancy += " + " + numChild + " Children (";
        }

        for (int j = 0; j < numChild; j++) {
          Child child = children.get(firstChildrenIndex);
          firstChildrenIndex++;
          occupancy += (child.digitAge == 0 ? child.age : child.digitAge) + " & ";
        }
        occupancy = occupancy.substring(0, occupancy.lastIndexOf("&")).trim();
        occupancy += ")";
      }
      occupancies.add(occupancy);
    }
    return occupancies;
  }

  public List<List<Integer>> arrangeForChildAge(final int roomCount, final int adultCount,
                                                List<Child> children) {

    List<List<Integer>> childAgesList = new ArrayList<>();

    int[] adultOccupancy = new int[roomCount];
    int[] childOccupancy = new int[roomCount];

    int adultLeft = adultCount;
    int childLeft = children.size();

    while (adultLeft > 0) {
      for (int i = 0; i < roomCount; i++) {
        if (adultLeft > 0) {
          adultOccupancy[i] = adultOccupancy[i] + 1;
          adultLeft--;
        }

        if (childLeft >= 2) {
          childOccupancy[i] = 2;
          childLeft -= 2;
        } else if (childLeft == 1) {
          childOccupancy[i] = 1;
          childLeft--;
        }
      }
    }
    int firstChildrenIndex = 0;
    for (int i = 0; i < roomCount; i++) {
      int numChild = childOccupancy[i];

      List<Integer> childAges = new ArrayList<>();

      if (numChild > 0) {
        for (int j = 0; j < numChild; j++) {
          Child child = children.get(firstChildrenIndex);
          firstChildrenIndex++;
          childAges.add(child.digitAge);
        }
      }
      childAgesList.add(childAges);
    }
    return childAgesList;
  }
}

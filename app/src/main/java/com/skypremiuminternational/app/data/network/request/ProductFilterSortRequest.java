package com.skypremiuminternational.app.data.network.request;

/**
 *  2020023 - WIKI Toan Tran - this class create string params to
 *  call api for filter product with sort.
 * */


public class ProductFilterSortRequest {
    String strFilter;
    String field;
    String direction;
    int postionGroup;

    final String DEFAULT_DIRECTOR = "ASC";

    public int getPostionGroup() {
        return postionGroup;
    }

    public void setPostionGroup(int postionGroup) {
        this.postionGroup = postionGroup;
    }
    public String getStrFilter() {
        return strFilter;
    }

    public void setStrFilter(String strFilter) {
        this.strFilter = strFilter;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 20200301 - WIKI Toan Tran - return sort by price query
     * @return
     */
    public String toQueryPrice(){
        String query="";
        query += getStrFilter();
        query += "&searchCriteria[sortOrders]["+postionGroup+"][field]="+getField();
        query +="&searchCriteria[sortOrders]["+postionGroup+"][direction]="+getDirection();
        query +="&searchCriteria[sortOrders]["+(postionGroup+1)+"][field]=name";
        query +="&searchCriteria[sortOrders]["+(postionGroup+1)+"][direction]="+DEFAULT_DIRECTOR;

        return query;
    }


    /**
     * 20200301 - WIKI Toan Tran - return sort by name query
     * @return
     */
    public String toQueryName(){
        String query="";
        query += getStrFilter();
        query += "&searchCriteria[sortOrders]["+postionGroup+"][field]="+getField();
        query +="&searchCriteria[sortOrders]["+postionGroup+"][direction]="+getDirection();
        return query;
    }




    /**
     * 20200301 - WIKI Toan Tran - return sort by popularity query
     * @return
     */
    public String toQueryPopularity(){
        String query="";
        query += getStrFilter();
        query += "&searchCriteria[sortOrders]["+postionGroup+"][field]="+getField();
        query +="&searchCriteria[sortOrders]["+postionGroup+"][direction]="+getDirection();
        query +="&searchCriteria[sortOrders]["+(postionGroup+1)+"][field]=name";
        query +="&searchCriteria[sortOrders]["+(postionGroup+1)+"][direction]="+DEFAULT_DIRECTOR;
        return query;
    }
  /**
     * 20200301 - WIKI Toan Tran - return sort by popularity query
     * @return
     */
    public String toQueryRating(){
        String query="";
        query += getStrFilter();
        query += "&searchCriteria[sortOrders]["+postionGroup+"][field]="+getField();
        query +="&searchCriteria[sortOrders]["+postionGroup+"][direction]="+getDirection();
        return query;
    }

}

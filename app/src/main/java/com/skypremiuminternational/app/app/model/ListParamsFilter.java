package com.skypremiuminternational.app.app.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ListParamsFilter {
    List<String> list;

    /**
     * 20200301 - WIKI Toan Tran - contructor
     */
    public ListParamsFilter() {
        this.list = new ArrayList<>();
        this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
        this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
        this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

        this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
        this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
        this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");


    }

    /**
     * 20200301 - WIKI Toan Tran - add param view all for filter
     */
    public void addeStrore(){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }
        this.list.add("searchCriteria[filterGroups][1][filters][0][field]=category_id");
        this.list.add("searchCriteria[filterGroups][1][filters][0][conditionType]=eq");
        this.list.add("searchCriteria[filterGroups][1][filters][0][value]=55");
    }
    /**
     * 20200301 - WIKI Toan Tran - add param view all for filter
     */
    public void addRoot(String rootId){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }
        this.list.add(list.size(),"rootCategory="+rootId);
        this.list.add("searchCriteria[filterGroups][1][filters][0][field]=category_id");
        this.list.add("searchCriteria[filterGroups][1][filters][0][conditionType]=eq");
        this.list.add("searchCriteria[filterGroups][1][filters][0][value]=55");
    }

    /**
     *
     * 20200301 - WIKI Toan Tran - add param view all for filter option group category
     *
     * @param value
     * @param filterNum
     */
    public void addOrCate(String value,int filterNum){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }
        this.list.add("searchCriteria[filterGroups][2][filters]["+filterNum+"][field]=category_id");
        this.list.add("searchCriteria[filterGroups][2][filters]["+filterNum+"][conditionType]=eq");
        this.list.add("searchCriteria[filterGroups][2][filters]["+filterNum+"][value]="+value);
    }

    /**
     * 20200301 - WIKI Toan Tran - add param feature option
     * @param filterNum
     * @param type
     */
    public void addOrTypeFeatures(int filterNum,String type){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }
        this.list.add("searchCriteria[filterGroups][4][filters]["+filterNum+"][field]="+type);
        this.list.add("searchCriteria[filterGroups][4][filters]["+filterNum+"][conditionType]=eq");
        this.list.add("searchCriteria[filterGroups][4][filters]["+filterNum+"][value]=1");
    }

    /**
     * 20200301 - WIKI Toan Tran - add param loyal
     * @param filterNum
     * @param type
     */
    public void addOrTypeLoyal(int filterNum,String type){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }
        this.list.add("searchCriteria[filterGroups][4][filters]["+filterNum+"][field]=loyalty_value_to_earn");
        this.list.add("searchCriteria[filterGroups][4][filters]["+filterNum+"][conditionType]=notnull");
        this.list.add("searchCriteria[filterGroups][4][filters]["+filterNum+"][value]=true");
    }

    /**
     * 20200301 - WIKI Toan Tran - add param newIn option
     * @param filterNum
     * @param type
     */
    public void addOrTypeNewIn(int filterNum,String type){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }
        this.list.add("searchCriteria[filterGroups][new][filters]["+filterNum+"][value]=true");
    }

    /**
     * 20200301 - WIKI Toan Tran - add brand option
     * @param value
     * @param filterNum
     */
    public void addOrBrand(String value,int filterNum){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }
        this.list.add("searchCriteria[filterGroups][3][filters]["+filterNum+"][field]=brand");
        this.list.add("searchCriteria[filterGroups][3][filters]["+filterNum+"][conditionType]=eq");
        this.list.add("searchCriteria[filterGroups][3][filters]["+filterNum+"][value]="+value);
    }

    /**
     * 20200301 - WIKI Toan Tran -  add conditions AND
     * @param value
     * @param attributeId
     */
    public void addAnd(String value,String attributeId){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }

        int stepGroup = this.list.size()/3;
        if(stepGroup == 1||stepGroup == 2||stepGroup == 3){
            stepGroup++;
        }
        this.list.add("searchCriteria[filterGroups]["+stepGroup+"][filters][0][field]="+attributeId);
        this.list.add("searchCriteria[filterGroups]["+stepGroup+"][filters][0][conditionType]=eq");
        this.list.add("searchCriteria[filterGroups]["+stepGroup+"][filters][0][value]="+value);
    }

    /**
     *  20200301 - WIKI Toan Tran -  add option with price bar
     * @param from
     * @param to
     */
    public void addAndPrice(int from , int to){
        if(list.size()<3){
            this.list.add("searchCriteria[filterGroups][0][filters][0][field]=visibility");
            this.list.add("searchCriteria[filterGroups][0][filters][0][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][0][filters][0][value]=4");

            this.list.add("searchCriteria[filterGroups][5][filters][1][field]=status");
            this.list.add("searchCriteria[filterGroups][5][filters][1][conditionType]=eq");
            this.list.add("searchCriteria[filterGroups][5][filters][1][value]=1");
        }

        int stepGroup = this.list.size()/3;
        if(stepGroup == 1||stepGroup == 2||stepGroup == 3){
            stepGroup+=2;
        }
        stepGroup+=2;
        this.list.add("searchCriteria[filterGroups]["+stepGroup+"][filters][0][field]=final_price");
        this.list.add("searchCriteria[filterGroups]["+stepGroup+"][filters][0][conditionType]=from");
        this.list.add("searchCriteria[filterGroups]["+stepGroup+"][filters][0][value]="+from);
        this.list.add("searchCriteria[filterGroups]["+(stepGroup+1)+"][filters][0][field]=final_price");
        this.list.add("searchCriteria[filterGroups]["+(stepGroup+1)+"][filters][0][conditionType]=to");
        this.list.add("searchCriteria[filterGroups]["+(stepGroup+1)+"][filters][0][value]="+to);
//
//        this.list.add("searchCriteria[filterGroups]["+(stepGroup)+"][filters][1][field]=special_price");
//        this.list.add("searchCriteria[filterGroups]["+(stepGroup)+"][filters][1][conditionType]=from");
//        this.list.add("searchCriteria[filterGroups]["+(stepGroup)+"][filters][1][value]="+from);
//        this.list.add("searchCriteria[filterGroups]["+(stepGroup+1)+"][filters][1][field]=special_price");
//        this.list.add("searchCriteria[filterGroups]["+(stepGroup+1)+"][filters][1][conditionType]=to");
//        this.list.add("searchCriteria[filterGroups]["+(stepGroup+1)+"][filters][1][value]="+to);

    }
    public void addSizePage(int size){
        this.list.add("searchCriteria[pageSize]="+size);
    }

    /**
     *  20200301 - WIKI Toan Tran -  list param
     * @return
     */
    public List<String> getList() {
        return list;
    }

    /**
     *  20200301 - WIKI Toan Tran - size of list param
     * @return
     */
    public int size(){
        return list.size();
    }

    /**
     *  20200301 - WIKI Toan Tran -  return params string
     * @return
     */
    @NonNull
    public String string(int page) {

        /*20200317 WIKI Toan Tran -  disable NEW version*/
        String string ="searchCriteria[currentPage]="+page;

        //String string ="searchCriteria[currentPage]=1";
        for(String s : list){
            string+="&"+s;
        }
        string.substring(1,string.length()-1);
        return string;
    }
}

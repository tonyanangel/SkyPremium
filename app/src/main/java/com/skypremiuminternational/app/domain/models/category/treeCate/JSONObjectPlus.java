package com.skypremiuminternational.app.domain.models.category.treeCate;

import org.json.JSONObject;
/**
 * 20200222 – - WIKI Toan Tran - Create this class
 *
 */
public class JSONObjectPlus {
    public String id;
    public JSONObject obj;

    public JSONObject getObj() {
        return obj;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

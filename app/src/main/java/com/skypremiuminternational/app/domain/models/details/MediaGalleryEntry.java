package com.skypremiuminternational.app.domain.models.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sandi on 10/24/17.
 */

public class MediaGalleryEntry implements Serializable {

  @SerializedName("id")
  @Expose
  public Integer id;
  @SerializedName("media_type")
  @Expose
  public String mediaType;
  @SerializedName("label")
  @Expose
  public Object label;
  @SerializedName("position")
  @Expose
  public Integer position;
  @SerializedName("disabled")
  @Expose
  public Boolean disabled;
  @SerializedName("types")
  @Expose
  public List<String> types = null;
  @SerializedName("file")
  @Expose
  public String file;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public Object getLabel() {
    return label;
  }

  public void setLabel(Object label) {
    this.label = label;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public Boolean getDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  public List<String> getTypes() {
    return types;
  }

  public void setTypes(List<String> types) {
    this.types = types;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }
}

package com.skypremiuminternational.app.app.utils;
import android.content.Context;
import com.skypremiuminternational.app.data.InternalStorageManager;
import com.skypremiuminternational.app.data.impl.InternalStorageManagerImpl;
import com.skypremiuminternational.app.domain.models.country_code.CountryCode;
import java.util.List;
public class CountryUtil {
  public static String parseCountryName(Context context , String countyId){
    String countryName;
      InternalStorageManager storageManager =
            new InternalStorageManagerImpl( new PreferenceUtils(context),new DataUtils(context));
        List<CountryCode> countryList = storageManager.getCountryCodes();
        for(CountryCode country : countryList){
          if(country.getId()!=null&& Integer.parseInt(country.getId())==(Integer.parseInt(countyId))){
            countryName = country.getName();
            return countryName;
            }
        }
    return "";
  }
  public static String parseCountryName(List<CountryCode> countryCodeList, String countyId){
    String countryName;
    if(countryCodeList!=null){
      for(CountryCode country : countryCodeList){
        if(country.getId()!=null&& Integer.parseInt(country.getId())==(Integer.parseInt(countyId))){
          countryName = country.getName();
          return countryName;
        }
      }
    }
    return "";
  }
}

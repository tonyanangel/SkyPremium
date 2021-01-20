package com.skypremiuminternational.app.app.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skypremiuminternational.app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import okhttp3.RequestBody;

public class CommonUtils {

    public static final String YYYY_MM_DD_HH_MM  = "yyyy-MM-dd HH:mm";

    public static void showToast(Context context, String message, int typeToast){
        Toast.makeText(context, message, typeToast).show();
    }

    public static RequestBody requestBody(Map<Object, Object> params){
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(params)).toString());
        return requestBody;
    }

    public static DisplayMetrics displayMetrics(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getRandomId(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String getFirebaseToken(){
        String token = FirebaseInstanceId.getInstance().getToken();
//        FirebaseInstanceId.getInstance()
//                .getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            return;
//                        }
//                        String token = task.getResult().getToken();
//                    }
//                });
        return token;
    }


    public static boolean isUnitNumber(String unitNumber) {
        boolean isvalid = false;
        String expression = "[a-zA-Z0-9]{1,}$";
//        String expression = "^[a-zA-Z][a-zA-Z0-9._-]{2,46}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        CharSequence inputStr = unitNumber;
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()){
            isvalid = true;
        }
        return isvalid;
    }

    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean isValidUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Pattern URL_PATTERN = Patterns.WEB_URL;
        boolean isURL = URL_PATTERN.matcher(url).matches();
        if (!isURL) {
            String urlString = url + "";
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    new URL(urlString);
                    isURL = true;
                } catch (Exception e) {
                }
            }
        }
        return isURL;
    }

    public static boolean checkValidURL(String url){
        final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(url);
        return m.find();
    }

    public static String getGMTZero(String pattern){
        Date nowDate = Calendar.getInstance().getTime();
        //2020-10-16 15:53
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String stringDate = dateFormat.format(nowDate);

        return stringDate;
    }

}

package com.skypremiuminternational.app.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by johnsonmaung on 25/11/16.
 */
@Singleton
public class DataUtils {

  private Context context;

  @Inject
  public DataUtils(Context context) {
    this.context = context;
  }

  public boolean isExist(String filename) {
    File file = context.getFileStreamPath(filename);
    if (file == null || !file.exists()) {
      return false;
    }
    return true;
  }

  /**
   * Write Object or List<Object>
   * DataUtils.WriteObject(context, "filename", Object or List<Object>);
   */
  public void writeObject(String filename, Object file) {
    try {
      FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
      OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
      myOutWriter.append(new Gson().toJson(file));
      myOutWriter.close();
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void SaveImage(Bitmap finalBitmap,Context context) {
    String fileName = "test.jpg";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

    File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
    File file = new File(ExternalStorageDirectory + File.separator + fileName);

    FileOutputStream fileOutputStream = null;
    try {
      file.createNewFile();
      fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(bytes.toByteArray());

      Toast.makeText(context,
          file.getAbsolutePath(),
          Toast.LENGTH_LONG).show();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if(fileOutputStream != null){
        try {
          fileOutputStream.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

  }
  public static Bitmap LoadImage() {

    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    Log.d("IMG_COUNT",Environment.getDataDirectory().getAbsolutePath());
    return BitmapFactory.decodeFile(Environment.getDataDirectory().getAbsolutePath() + "/saved_images/"+"Image"+".png", options);
  }

  /**
   * Read Object:
   * Object object = new Gson().fromJson(DataUtils.ReadObject(this, "filename"), Object.class);
   * Read List<Object>:
   * * List<Object> objectList = new Gson().fromJson(DataUtils.ReadObject(this, "filename"), new
   * TypeToken<List<Object>>() {}.getType());
   */
  public String readObject(String filename) {
    try {
      FileInputStream fis = context.openFileInput(filename);
      BufferedReader myReader = new BufferedReader(new InputStreamReader(fis));
      String aDataRow = "";
      String aBuffer = ""; //Holds the text
      while ((aDataRow = myReader.readLine()) != null) {
        aBuffer += aDataRow;
      }
      myReader.close();
      return aBuffer;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public boolean deleteObject(String filename) {
    File file = context.getFileStreamPath(filename);
    if (file != null || file.exists()) {
      file.delete();
      return true;
    }
    return false;
  }

  public static void deleteCache(Context context) {
    try {
      File dir = context.getCacheDir();
      deleteDir(dir);
    } catch (Exception e) { e.printStackTrace();}
  }


  public static void deleteAllData(Context context) {
    try {
      File dir = context.getCacheDir();
      deleteDir(dir);
      File dir2 = context.getFilesDir();
      deleteDir(dir2);
    } catch (Exception e) { e.printStackTrace();}
  }

  public static boolean deleteDir(File dir) {
    if (dir != null && dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
      return dir.delete();
    } else if(dir!= null && dir.isFile()) {
      return dir.delete();
    } else {
      return false;
    }
  }
}

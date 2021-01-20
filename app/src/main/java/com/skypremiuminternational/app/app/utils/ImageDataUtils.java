package com.skypremiuminternational.app.app.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.skypremiuminternational.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImageDataUtils {

  static String extension;
  public static void saveImage(Context context,Bitmap bitmap ,String name){
    Bitmap bit = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

    bit = bitmap;

        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, name + ".jpeg");
        if (!file.exists()) {
          Log.d("path", file.toString());
          FileOutputStream fos = null;
          try {
            fos = new FileOutputStream(file);

            bit.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
          } catch (java.io.IOException e) {
            e.printStackTrace();
          }


        }
      }

  public static void saveImageByPath(Context context, Uri uri , String name){
    Bitmap bit ;
    Bitmap bitmap =null;
    File fileCap = null;
    try {
      bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

      String imageurl = getRealPathFromURI(context, uri);

      Log.d("PATH-TEST-imageurl", "imageurl:"+imageurl);
      try{
        fileCap =  new File(imageurl);
      }catch (NullPointerException e){}
      String[] mediaColumns = {MediaStore.Files.FileColumns.SIZE,MediaStore.Files.FileColumns.MIME_TYPE};
      Cursor cursor = context.getContentResolver().query(uri, mediaColumns, null, null, null);
      cursor.moveToFirst();
      int sizeColInd = cursor.getColumnIndex(mediaColumns[0]);
      int columnIndex = cursor.getColumnIndex(mediaColumns[1]);
      String filePath = cursor.getString(columnIndex);
      Bitmap bitmapEx = BitmapFactory.decodeFile(filePath);
      extension =  filePath.substring(filePath.lastIndexOf("/")+1);

      if(!extension.equalsIgnoreCase("png")
          &&!extension.equalsIgnoreCase("gif")
        &&!extension.equalsIgnoreCase("jpg")
        &&!extension.equalsIgnoreCase("jpeg")){
        Toast.makeText(context, "Wrong type",Toast.LENGTH_LONG).show();
        return;
      }

      double fileSize = cursor.getDouble(sizeColInd);
      cursor.close();
      if(fileSize/(1024*1024)>2){
        Toast.makeText(context, R.string.limit_file,Toast.LENGTH_LONG).show();
        return;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    bit = bitmap;

    /*
    if(fileCap==null){*/
      Log.d("PATH-TEST-fileCap", "null");
      ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
      File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
      fileCap = new File(directory, name + "."+extension);
    /*}*/
    if (!fileCap.exists()) {
      Log.d("PATH-TEST-fileCap", "!fileCap.exists()");
      FileOutputStream fos = null;
      try {
        fos = new FileOutputStream(fileCap);

        fos = new FileOutputStream(fileCap);
        if(extension.equalsIgnoreCase("png")){
          bit.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }else {
          bit.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }
        fos.flush();
        fos.close();
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }
  }
  public static void saveImageByEditPath(Context context, Uri uri , String name){
    Bitmap bit ;
    Bitmap bitmap =null;
    File fileCap = null;
    try {
      bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

      String imageurl = getRealPathFromURI(context, uri);
      try{
        fileCap =  new File(imageurl);
      }catch (NullPointerException e){}
      String[] mediaColumns = {MediaStore.Files.FileColumns.SIZE,MediaStore.Files.FileColumns.MIME_TYPE};
      Cursor cursor = context.getContentResolver().query(uri, mediaColumns, null, null, null);
      cursor.moveToFirst();
      int sizeColInd = cursor.getColumnIndex(mediaColumns[0]);
      int columnIndex = cursor.getColumnIndex(mediaColumns[1]);
      String filePath = cursor.getString(columnIndex);
      Bitmap bitmapEx = BitmapFactory.decodeFile(filePath);
      extension =  filePath.substring(filePath.lastIndexOf("/")+1);

      if(!extension.equalsIgnoreCase("png")
          &&!extension.equalsIgnoreCase("gif")
          &&!extension.equalsIgnoreCase("jpg")
          &&!extension.equalsIgnoreCase("jpeg")){
        Toast.makeText(context, "Inappropriate format",Toast.LENGTH_LONG).show();
        return;
      }
      double fileSize = cursor.getDouble(sizeColInd);
      cursor.close();
      if(fileSize/(1024*1024)>2){
        Toast.makeText(context, R.string.limit_file,Toast.LENGTH_LONG).show();
        return;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    bit = bitmap;

    /*
    if(fileCap==null){*/
      ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
      File directory = cw.getDir("imageDirEdit", Context.MODE_PRIVATE);
      fileCap = new File(directory, name + "."+extension);
   /* }
*/

    if (!fileCap.exists()) {
      Log.d("path", fileCap.toString());
      FileOutputStream fos = null;
      try {
        fos = new FileOutputStream(fileCap);
        if(extension.equalsIgnoreCase("png")){
          bit.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }else {
          bit.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }

        fos.flush();
        fos.close();

      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }
  }
  public static String getRealPathFromURI(Context context, Uri contentUri) {
    String res = null;
    String[] proj = { MediaStore.Images.Media.DATA };
    Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
    if(cursor.moveToFirst()){;
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      res = cursor.getString(column_index);
    }
    cursor.close();

    Log.d("PATH-TEST", ""+res);
    return res;
  }
  public static void saveImageByEditPath(Context context,Bitmap bitmap  , String name){
    Bitmap bit ;
    bit = bitmap;

    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDirEdit", Context.MODE_PRIVATE);
    File file = new File(directory, name + ".jpeg");
    if (!file.exists()) {
      Log.d("path", file.toString());
      FileOutputStream fos = null;
      try {
        fos = new FileOutputStream(file);
        bit.compress(Bitmap.CompressFormat.PNG, 85, fos);
        fos.flush();
        fos.close();
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }
  }


  public static  void renameAllBitmap(Context context,String dir,String name){
    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir(dir, Context.MODE_PRIVATE);
    int i = 0;
    for(final File file : directory.listFiles()){
      Log.d("rename-test", name + "- -" +i);
      file.renameTo( new File( directory.getPath(),name +"--"+i+ ".jpeg"));
      i++;
    }
    i=0;
    for(final File file : directory.listFiles()){
      Log.d("rename-test", name + "- -" +i);
      file.renameTo( new File( directory.getPath(),name +"-"+i+ ".jpeg"));
      i++;
    }
  }

  public static  void clearAllBitmap(Context context){
    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
    for(File file : directory.listFiles()){
      file.delete();
    }
  }
  public static  void clearAllBitmapEditZone(Context context){
    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDirEdit", Context.MODE_PRIVATE);
    for(File file : directory.listFiles()){
      file.delete();
    }
  }

  public static  Bitmap deleteBitmap(Context context, String name){
    Bitmap bitRemove;

    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
    for(File file : directory.listFiles()){
      if(file.getName().equalsIgnoreCase(name)) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitRemove = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        if(file.delete()){
          return bitRemove;
        }
      }

    }
    return null;
  }
  public static List<Bitmap> getListBitmap(Context context){
    List<Bitmap> bitmaps = new ArrayList<>();

    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
    for(File file : directory.listFiles()){
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
      bitmaps.add(bitmap);
    }
    return bitmaps;
  }
  public static List<File> getFileImage(Context context){
    List<File> files = new ArrayList<>();

    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
    for(File file : directory.listFiles()){
      files.add(file);
    }
    return files;
  }
  public static List<File> getFileImageEditZone(Context context){
    List<File> files = new ArrayList<>();

    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDirEdit", Context.MODE_PRIVATE);
    for(File file : directory.listFiles()){
      files.add(file);
    }
    return files;
  }
  public static int getCountFile(Context context){

    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
    if(directory.listFiles()!=null)
      return directory.listFiles().length;
    return 0;
  }
  public static int getCountFileEditZone(Context context){

    ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
    File directory = cw.getDir("imageDirEdit", Context.MODE_PRIVATE);
    if(directory.listFiles()!=null)
      return directory.listFiles().length;
    return 0;
  }

  public static double  getSizeFile(Context context,Intent data){
    double size=0;
    Uri uri  = data.getData();
    Cursor cursor = context.getContentResolver().query(uri,
        null, null, null, null);
    cursor.moveToFirst();
    size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));
    cursor.close();
    return size;
  }
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
  public static int sizeOf(Bitmap data) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
      return data.getRowBytes() * data.getHeight();
    } else {
      return data.getByteCount();
    }
  }

  public static void grayScale(ImageView imageView){
    ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(0);
    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
    imageView.setColorFilter(filter);
  }
  public static void grayScaleRev(ImageView imageView){
    ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(1);
    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
    imageView.setColorFilter(filter);
  }
}

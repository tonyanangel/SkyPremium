package com.skypremiuminternational.app.app.features.profile.order.detail.edit_review;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.skypremiuminternational.app.BuildConfig;
import com.skypremiuminternational.app.R;
import com.skypremiuminternational.app.app.model.ImageFile;
import com.skypremiuminternational.app.app.utils.ImageDataUtils;
import com.skypremiuminternational.app.app.utils.PreferenceUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  Fragment context;
  List<ImageFile> listData;

  final int ITEM_NORMAL =0 ;
  final int ADD_BTN =1 ;
  final int ITEM_COUNT =2 ;

  static boolean isEdit = true;
  public static final  int REQUEST_TAKE_PHOTO = 2;
  public static final  int REQUEST_SELECT_PICTURE = 1;
  static String currentPhotoPath;

  File photoFile = null;
  public ImageEditAdapter(Fragment context) {
    this.context = context;
  }

  public ImageEditAdapter(Fragment context, List<ImageFile> listData) {
    this.context = context;
    this.listData = listData;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if(viewType==ADD_BTN){
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_add, parent, false);
      view.setTag("ADD");
      final AddImageViewHolder holder = new AddImageViewHolder(view);
      return holder;
    }else if(viewType==ITEM_COUNT){
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_image_count, parent, false);
      final ImageCountViewHolder holder = new ImageCountViewHolder(view);
      return holder;
    }else {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_image, parent, false);
      view.setTag("NORMAL");
      final ImageViewHolder holder = new ImageViewHolder(view);
      return holder;
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if(getItemViewType(position) ==ITEM_NORMAL) {
      ImageViewHolder hv = (ImageViewHolder) holder;
      hv.bind(context, listData.get(position), position);
    }else if(getItemViewType(position) ==ADD_BTN) {
      AddImageViewHolder hv = (AddImageViewHolder) holder;
      hv.bind(context, position);
    }else if(getItemViewType(position) ==ITEM_COUNT) {
      ImageCountViewHolder hv = (ImageCountViewHolder) holder;
      hv.bind(context, position);
    }


  }

  public void setIsEdit(boolean isEdit) {
    ImageEditAdapter.isEdit = isEdit;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    if(listData!=null){
      if(isEdit)
        return listData.size()+2;
      return listData.size();
    }
    if(isEdit)
      return 2;
    return 0;
  }

  public List<ImageFile> getData(){
    return this.listData;
  }

  public void addFile(List<File> listData){
    clearFile();

    if(this.listData==null){
      this.listData = new ArrayList<>();
    }
    for(File f  : listData){
      if(this.listData.size()>=7){
        notifyDataSetChanged();
        return;
      }
      ImageFile file =  new ImageFile();
      file.setFile(f);

      this.listData.add(file);
    }
    notifyDataSetChanged();
  }
  void clearFile(){
    if(this.listData!=null) {
      for (int i = 0; i < this.listData.size(); i++) {
        if (this.listData.get(i).getFile() != null) {
          this.listData.remove(i);
          i--;
        }
      }
    }
  }
  public int getCountItem(){
    int count=0;
    if(this.listData!=null){
      for(ImageFile file : this.listData){
        if(file.getUrl()!=null){
          count++;
        }
      }
    }
    return count;
  }


  public void setData(List<ImageFile> listData) {
    this.listData = new ArrayList<>();
    this.listData = listData;
    notifyDataSetChanged();
  }

  @Override
  public int getItemViewType(int position) {
    if(!isEdit){
      return ITEM_NORMAL;
    }
    if(position==getItemCount()-2)
      return ADD_BTN;
    if(position==getItemCount()-1)
      return ITEM_COUNT;
    if(position<getItemCount()-1)
      return ITEM_NORMAL;
    return -1;
  }

  public class ImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img)
    ImageView imageView;

    public void bind(Fragment context, ImageFile file, int pos) {

     /* Glide.with(context.getContext())
          .load(file)
          .skipMemoryCache(true)
          .placeholder(R.drawable.placeholder)
          .into(imageView);*/
        if(file.getUrl()!=null){
          Picasso.get().load(file.getUrl())
              .memoryPolicy(MemoryPolicy.NO_CACHE)
              .resize(50,50)
              .placeholder(R.drawable.placeholder)
              .error(R.drawable.placeholder).into(imageView);
        }else {
          Picasso.get().load(file.getFile())
              .memoryPolicy(MemoryPolicy.NO_CACHE)
              .resize(50,50)
              .placeholder(R.drawable.placeholder)
              .error(R.drawable.placeholder).into(imageView);
        }

        imageView.setEnabled(isEdit);
        imageView.setOnClickListener(v -> {
          new AlertDialog.Builder(context.getContext())
              .setMessage(R.string.ask_delete)
              .setNegativeButton(R.string.negative_answer,(dialog, which) -> {
              }).setPositiveButton(R.string.positive_answer,(dialog, which) -> {
                if(listData.get(pos).getFile()!=null){
                  listData.get(pos).getFile().delete();
                }else if(listData.get(pos).getUrl()!=null){
                  listData.remove(pos);
                }
                if(context.getTag().equalsIgnoreCase("EDIT-REVIEW")) {
                  ImageDataUtils.renameAllBitmap(context.getActivity(), "imageDirEdit", "image-edit");
                  addFile(ImageDataUtils.getFileImageEditZone(context.getActivity()));
                }else {
                  ImageDataUtils.renameAllBitmap(context.getActivity(),"imageDir","images");
                  addFile(ImageDataUtils.getFileImage(context.getActivity()));
                }
                notifyDataSetChanged();
              }).show();
          });
    }

    public ImageViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }

  }
  public class ImageCountViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvCount)
    TextView tvCount;

    public void bind(Fragment context, int pos) {
      if(tvCount!=null){
        tvCount.setText((getItemCount()-2)+"/7");
      }
    }

    public ImageCountViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }

  }
  public class AddImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgAddImage)
    ImageView imgAddImage;
    @BindView(R.id.layout)
    ConstraintLayout layout;
    public void bind(Fragment context, int pos) {
        imgAddImage.setEnabled(isEdit);
        if(getItemCount()>=9){
          layout.setVisibility(View.GONE);
        }else {
          layout.setVisibility(View.VISIBLE);
        }
        imgAddImage.setOnClickListener(v->{
          verifyStoragePermissions();
        });
    }
    public AddImageViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);

    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private  String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    };
    public void verifyStoragePermissions() {
      int permission = ActivityCompat.checkSelfPermission(context.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
      if (permission != PackageManager.PERMISSION_GRANTED|| permission == PackageManager.PERMISSION_DENIED) {
        context.requestPermissions(
            PERMISSIONS_STORAGE,
            REQUEST_EXTERNAL_STORAGE
        );
      }else {
        String choose[] = {"Take a photo", "Select Images"};
        new AlertDialog.Builder( context.getActivity())
            .setItems(choose, (dialog, item) -> {
              if(item==0){
                dispatchTakePictureIntent();
              }else {
                Intent viewIntent = new Intent(Intent.ACTION_GET_CONTENT);
                viewIntent.setType("image/*");
                Intent chooserIntent = Intent.createChooser(viewIntent, "Open in...");
                viewIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                context.startActivityForResult(Intent.createChooser(chooserIntent,"Select Picture"), REQUEST_SELECT_PICTURE);
              }
            }).show();
      }
    }
    private File createImageFile() throws IOException {
      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
      String imageFileName = "JPEG_" + timeStamp + "_";
      File storageDir =  context.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
      File image = File.createTempFile(
          imageFileName,
          ".jpg",
          storageDir
      );
      currentPhotoPath = image.getAbsolutePath();

      Log.d("INTENT-TEST","TEST "+currentPhotoPath);
      return image;
    }
    private void dispatchTakePictureIntent() {
      Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
      if (takePictureIntent.resolveActivity(context.getContext().getPackageManager()) != null) {
        try {
          photoFile = createImageFile();
          PreferenceUtils preferenceUtils = new PreferenceUtils(context.getContext());
          preferenceUtils.save("imageURL",photoFile.getAbsolutePath());
        } catch (IOException ex) {
        }
        if (photoFile != null) {
          Uri photoURI = FileProvider.getUriForFile(context.getContext(),
              BuildConfig.APPLICATION_ID+".provider",
              photoFile);
          takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
          context.startActivityForResult(Intent.createChooser(takePictureIntent,"Select Picture"),REQUEST_TAKE_PHOTO );
        }
      }
    }

  }
  public  String getPhotoFilePath(){
    return currentPhotoPath;
  }
}
package com.apkzube.wallfresco.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.databinding.ActivitySetWallpaperBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.db.repo.WallRepository;
import com.apkzube.wallfresco.util.Constant;
import com.apkzube.wallfresco.viewmodel.SetWallpaperViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class SetWallpaper extends AppCompatActivity {


    private static final String TAG = "SetWallpaper";
    private static final int SET_WALLPAPER_CODE = 11;
    ImageView imgWallpaper;
    Wallpaper mWallpaper;
    LottieAnimationView setWallpaperLoading;
    ImageView btnDownload, btnFavorite, btnShare, btnBackPress;
    FloatingActionButton btnSetWallpaper;
    ConstraintLayout grpSetUp;
    ProgressDialog downloadDialog;
    ProgressBar progressBar;
    private Vibrator vibrator;
    public String filePath;
    public String folderPath;
    Apply mApply;

    private PopupWindow window;

    private AlertDialog dialogDownload;

    private boolean IS_SHARE_FLAG,IS_SET_WALLPAPER_FLAG;

    private ActivitySetWallpaperBinding mBinding;
    private SetWallpaperViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mWallpaper = getIntent().getParcelableExtra(getString(R.string.wallpaper_obj_key));

        model = ViewModelProviders.of(this).get(SetWallpaperViewModel.class);
        model.setApplication(getApplication());
        model.getWallpaperLiveData().setValue(mWallpaper);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_set_wallpaper);
        mBinding.setModel(model);

        allocation();
        setEvent();
    }

    private void allocation() {
        grpSetUp = mBinding.grpSetUp;
        setWallpaperLoading = mBinding.setWallpaperLoading;
        btnBackPress = mBinding.btnBackPress;
        btnDownload = mBinding.btnDownload;
        btnFavorite = mBinding.btnFavorite;
        btnShare = mBinding.btnShare;
        btnSetWallpaper = mBinding.btnSetwallpaper;
        imgWallpaper = mBinding.imgSetWallpaper;
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        progressBar = mBinding.progressBar;
        setFavoriteButtonUI(mWallpaper);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setEvent() {
        //photographer URL set up
        mBinding.txtPhotographer.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWallpaper.getPhotographerUrl()));
            startActivity(intent);
        });

        //set wallpaper into image view
        Glide.with(this)
                .load(Uri.parse(mWallpaper.getPortrait()))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        setWallpaperLoading.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "onResourceReady: " + setWallpaperLoading.getVisibility());
                        return false;
                    }
                })
                .into(imgWallpaper);


        // set wallpaper when btnSet click
       /* btnSetWallpaper.setOnClickListener(view -> {
            if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)) {

                progressBar.setVisibility(View.VISIBLE);
                btnShare.setVisibility(View.GONE);
                final WallpaperManager manager = WallpaperManager.getInstance(SetWallpaper.this);

                try {

                    URL url = new URL(mWallpaper.getLarge2x());
                    Bitmap mBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    try {

                        Bitmap bitmap = getScaledBitmap(mBitmap);
                        manager.setBitmap(bitmap);
                        vibrator.vibrate(50);
                        setSuccessDialog();
                        progressBar.setVisibility(View.GONE);
                        btnShare.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Toast.makeText(SetWallpaper.this, "Fail to set Wallpaper", Toast.LENGTH_SHORT).show();
                        Log.d(Constant.TAG, "onResourceReady: " + e.getMessage());
                        setFailDialog();
                        progressBar.setVisibility(View.GONE);
                        btnShare.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + e.getMessage());
                }
*//*
                Glide.with(SetWallpaper.this)
                        .asBitmap()
                        .load(mWallpaper.getSrc().getOriginal())
                        .into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            }
                        });*//*

            } else {
                setCurrentWallpaper();
            }
        });*/


        btnSetWallpaper.setOnClickListener(v -> {
            IS_SHARE_FLAG=false;
            IS_SET_WALLPAPER_FLAG=true;
            btnShare.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            shareWallpaper(mWallpaper);

        });

        imgWallpaper.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                grpSetUp.setVisibility(View.VISIBLE);
            } else {
                grpSetUp.setVisibility(View.INVISIBLE);
            }
            return true;
        });

        btnBackPress.setOnClickListener(view -> onBackPressed());

        btnShare.setOnClickListener(view -> {
            IS_SHARE_FLAG=true;
            IS_SET_WALLPAPER_FLAG=false;
            btnShare.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            shareWallpaper(mWallpaper);
        });


        // TODO save favorite wallpaper into room
        btnFavorite.setOnClickListener(view -> {
            WallRepository repository = new WallRepository(getApplication());
            if (mWallpaper.isFavorite()) {
                mWallpaper.setFavorite(false);
            } else {
                mWallpaper.setFavorite(true);
            }
            repository.updateWallpaper(mWallpaper);
            setFavoriteButtonUI(mWallpaper);
            vibrator.vibrate(50);
        });


        btnDownload.setOnClickListener(view -> downloadPopupWindow());

    }


    public void onDownloadBtnClick(View view) {
        LinearLayout btn = (LinearLayout) view;
        String downloadURL = mWallpaper.getOriginal();

        final DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();
        try {
            switch (btn.getId()) {
                case R.id.btnMedium:
                    downloadURL = mWallpaper.getOriginal();
                    filePath = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name) + "/original_" + mWallpaper.getId() + ".jpeg";
                    break;
                case R.id.btnOriginal:
                    downloadURL = mWallpaper.getLarge();
                    filePath = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name) + "/medium_" + mWallpaper.getId() + ".jpeg";
                    break;
                case R.id.btnLarge:
                    downloadURL = mWallpaper.getLarge2x();
                    filePath = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name) + "/large_" + mWallpaper.getId() + ".jpeg";
                    break;
            }

            //dialogDownload.dismiss();
            window.dismiss();
            downloadDialog = new ProgressDialog(SetWallpaper.this);
            downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            downloadDialog.setCancelable(false);
            downloadDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", (dialogInterface, i) -> {
                if (!downloadFileFromURL.isCancelled()) {
                    downloadFileFromURL.cancel(true);
                    if (downloadDialog.isShowing())
                        downloadDialog.dismiss();
                }
            });
            downloadDialog.setTitle("Downloading...");
            downloadDialog.setMax(100);

            downloadFileFromURL.execute(downloadURL);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onDownloadBtnClick: "+e.getMessage());
        }
    }


    private void setCurrentWallpaper() {

        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(SetWallpaper.this);
            final View dialogView = LayoutInflater.from(SetWallpaper.this).inflate(R.layout.wallpaper_bottomsheet, null);
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(true);


            LinearLayout btnHome, btnLoack, btnBoth;
            btnHome = dialogView.findViewById(R.id.btnHome);
            btnLoack = dialogView.findViewById(R.id.btnLock);
            btnBoth = dialogView.findViewById(R.id.btnBoth);

            btnHome.setOnClickListener(view -> {
                mApply = Apply.HOMESCREEN;
                dialog.dismiss();
            });

            btnLoack.setOnClickListener(view -> {
                mApply = Apply.LOCKSCREEN;
                dialog.dismiss();
            });

            btnBoth.setOnClickListener(view -> {
                Log.d(TAG, "onClick: btnBoth");
                mApply = Apply.HOMESCREEN_LOCKSCREEN;
                dialog.dismiss();
            });

            dialog.setOnDismissListener(dialogInterface -> {

                progressBar.setVisibility(View.VISIBLE);
                btnShare.setVisibility(View.GONE);

                Log.d(SetWallpaper.TAG, "onDismiss: ");
                if (mApply != null) {
                    final WallpaperManager manager = WallpaperManager.getInstance(SetWallpaper.this);


                    Glide.with(SetWallpaper.this)
                            .asBitmap()
                            .load(mWallpaper.getLarge2x())
                            .into(new SimpleTarget<Bitmap>() {

                                @Override
                                public void onResourceReady(@NonNull Bitmap mBitmap, @Nullable Transition<? super Bitmap> transition) {

                                    try {

                                        //Bitmap bitmap = getScaledBitmap(mBitmap);
                                        Bitmap bitmap=mBitmap;
                                        if (mApply == Apply.HOMESCREEN_LOCKSCREEN) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                manager.setBitmap(
                                                        bitmap, null, true, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
                                            }
                                        }

                                        if (mApply == Apply.HOMESCREEN) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                manager.setBitmap(
                                                        bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                                            }

                                        }

                                        if (mApply == Apply.LOCKSCREEN) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                manager.setBitmap(
                                                        bitmap, null, true, WallpaperManager.FLAG_LOCK);
                                            }
                                        }

                                        vibrator.vibrate(50);
                                        setSuccessDialog();
                                        progressBar.setVisibility(View.GONE);
                                        btnShare.setVisibility(View.VISIBLE);
                                        mApply = null;
                                    } catch (Exception e) {
                                        Toast.makeText(SetWallpaper.this, "Fail to set Wallpaper", Toast.LENGTH_SHORT).show();
                                        Log.d(Constant.TAG, "onResourceReady: " + e.getMessage());
                                        setFailDialog();
                                        progressBar.setVisibility(View.GONE);
                                        btnShare.setVisibility(View.VISIBLE);

                                    }
                                }
                            });


                } else {
                    progressBar.setVisibility(View.GONE);
                    btnShare.setVisibility(View.VISIBLE);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSuccessDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(SetWallpaper.this);
        LayoutInflater inflater = LayoutInflater.from(SetWallpaper.this);
        final View view = inflater.inflate(R.layout.layout_wallpaper_set_success_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        Button btnDismiss = view.findViewById(R.id.btnDismiss);
        Button btnRateUs = view.findViewById(R.id.btnRateUs);

        btnDismiss.setOnClickListener(view1 -> dialog.dismiss());

        btnRateUs.setOnClickListener(view12 -> {
            dialog.dismiss();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        });
        dialog.show();
    }

    private void setFailDialog() {

    }

    public Bitmap getScaledBitmap(Bitmap bitmap) {

        // Get display dimensions
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        final int displayWidth = metrics.widthPixels;
        final int displayHeight = metrics.heightPixels;

        // Here I'm decoding a resource, just for the example sake
        //final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);

        // obtain the original Bitmap's dimensions
        final int originalWidth = bitmap.getWidth();
        final int originalHeight = bitmap.getHeight();

        // Obtain the horizontal and vertical scale factors
        final float horizontalScaleFactor = (float) originalWidth / (float) displayWidth;
        final float verticalScaleFactor = (float) originalHeight / (float) displayHeight;

        // Get the biggest scale factor to use in order to maintain original image's aspect ratio
        final float scaleFactor = Math.max(verticalScaleFactor, horizontalScaleFactor);
        final int finalWidth = (int) (originalWidth / scaleFactor);
        final int finalHeight = (int) (originalHeight / scaleFactor);

        // Create the final bitmap

      /*  // Recycle the original bitmap
      //*  if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }//*
*/


        return Bitmap.createScaledBitmap(
                bitmap, finalWidth, finalHeight, false);

    }

    private void shareWallpaper(final Wallpaper wallpaper) {

        try {
            DownloadFileFromURLAndShare  share=new DownloadFileFromURLAndShare();
            if(IS_SET_WALLPAPER_FLAG){
                share.execute(wallpaper.getLarge2x());
            }else if(IS_SHARE_FLAG){
                share.execute(wallpaper.getPortrait());
            }else{
                share.execute(wallpaper.getLarge2x());
            }

        } catch (Exception e) {
            Log.d(TAG, "shareWallpaper: "+e.getMessage());
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            downloadDialog.show();
            folderPath = android.os.Environment.getExternalStorageDirectory().getPath() + File.separator + getString(R.string.app_name);
            File downloadFolder = new File(folderPath);
            if (!downloadFolder.exists()) {
                downloadFolder.mkdirs();
            }
            Log.d(Constant.TAG, "onPreExecute: " + downloadFolder.getAbsolutePath());
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100%           progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream(filePath);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("ApkZube Exception", "" + e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            downloadDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            File f = new File(filePath);
            if (f.exists()) {
                f.delete();
                Log.d(TAG, "onCancelled: file deleted");
            }
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/

        @Override
        protected void onPostExecute(String file_url) {
            downloadDialog.dismiss();
            Log.d(TAG, "onPostExecute: " + filePath);
            // setting downloaded into image view
            try {
                if (new File(filePath).exists())
                    openFile(filePath);
            } catch (Exception e) {
                Log.d(Constant.TAG, "onPostExecute: " + e.getMessage());
            }

        }

    }


    //download and share
    class DownloadFileFromURLAndShare extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(IS_SET_WALLPAPER_FLAG){
                filePath=Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name)+File.separator+"Used Wallpaper"+File.separator + "/wallpaper_" + mWallpaper.getId() + ".jpeg";
                folderPath = android.os.Environment.getExternalStorageDirectory().getPath() + File.separator + getString(R.string.app_name)+File.separator+"Used Wallpaper";
            }else if(IS_SHARE_FLAG){
                filePath=Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name)+File.separator+"Shared"+File.separator + "/share_" + mWallpaper.getId() + ".jpeg";
                folderPath = android.os.Environment.getExternalStorageDirectory().getPath() + File.separator + getString(R.string.app_name)+File.separator+"Shared";
            }else{
                filePath=Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name)+File.separator+"Used Wallpaper"+File.separator + "/wallpaper_" + mWallpaper.getId() + ".jpeg";
                folderPath = android.os.Environment.getExternalStorageDirectory().getPath() + File.separator + getString(R.string.app_name)+File.separator+"Used Wallpaper";
            }
            File downloadFolder = new File(folderPath);
            if (!downloadFolder.exists()) {
                downloadFolder.mkdirs();
            }
            Log.d(Constant.TAG, "onPreExecute: DownloadFileFromURLAndShare " + downloadFolder.getAbsolutePath());
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100%           progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream(filePath);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("ApkZube Exception", "" + e.getMessage());
                //Toast.makeText(SetWallpaper.this, "Fail to share file", Toast.LENGTH_SHORT).show();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {
            try {
                Uri imgUri = Uri.parse(filePath);
                progressBar.setVisibility(View.GONE);
                btnShare.setVisibility(View.VISIBLE);
                if(IS_SHARE_FLAG) {
                      String sharingText = getResources().getString(R.string.share_image_text) + "\n\n" + getResources().getString(R.string.app_name) + "  : " + "https://play.google.com/store/apps/details?id=" + getPackageName();
                     Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("*/*");
                     shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                     shareIntent.putExtra(Intent.EXTRA_TEXT, sharingText);
                    startActivity(Intent.createChooser(shareIntent, "Share Wallpaper.."));
                }
                if(IS_SET_WALLPAPER_FLAG) {
                    Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(imgUri, "image/*");
                    intent.putExtra("mimeType", "image/*");
                    startActivityForResult(Intent.createChooser(intent, getString(R.string.app_name)+" : set as"),SET_WALLPAPER_CODE);
                }
            } catch (Exception e) {
                progressBar.setVisibility(View.GONE);
                btnShare.setVisibility(View.VISIBLE);
              //  Toast.makeText(SetWallpaper.this, "Fail share wallpaper", Toast.LENGTH_SHORT).show();
                Log.d(Constant.TAG, "onPostExecute: " + e.getMessage());
            }finally {
                filePath="";
            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==SET_WALLPAPER_CODE){
            setSuccessDialog();
        }
    }

    public void downloadPopupWindow() {
        //Create a View object yourself through inflater
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.diaload_download_wallpaper, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        window = new PopupWindow(popupView, width, height, true);
        window.showAtLocation(mBinding.getRoot(), Gravity.CENTER, 0, 0);
        window.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);

        ConstraintLayout conDismiss = popupView.findViewById(R.id.consDismiss);
        LinearLayout btnMedium, btnOriginal, btnLarge;
        btnMedium = popupView.findViewById(R.id.btnMedium);
        btnOriginal = popupView.findViewById(R.id.btnOriginal);
        btnLarge = popupView.findViewById(R.id.btnLarge);

        btnMedium.setOnClickListener(this::onDownloadBtnClick);
        btnOriginal.setOnClickListener(this::onDownloadBtnClick);
        btnLarge.setOnClickListener(this::onDownloadBtnClick);

        /*mBinding.grpSetUp.setVisibility(View.INVISIBLE);
        window.setOnDismissListener(() -> mBinding.grpSetUp.setVisibility(View.VISIBLE));*/
        conDismiss.setOnClickListener(view -> window.dismiss());
    }


    protected void openFile(final String fileName) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SetWallpaper.this);
        LayoutInflater inflater = LayoutInflater.from(SetWallpaper.this);
        final View view = inflater.inflate(R.layout.layout_download_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        Button btnDismiss = view.findViewById(R.id.btnDownloadDismiss);
        Button btnOpen = view.findViewById(R.id.btnOpenPhoto);

        btnDismiss.setOnClickListener(view1 -> dialog.dismiss());

        btnOpen.setOnClickListener(view12 -> {
            dialog.dismiss();
            try {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri apkURI = FileProvider.getUriForFile(
                        SetWallpaper.this,
                        SetWallpaper.this.getApplicationContext()
                                .getPackageName() + ".provider", new File(fileName));
                install.setDataAndType(apkURI, "image/*");
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                SetWallpaper.this.startActivity(install);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dialog.show();

    }

    private void setFavoriteButtonUI(Wallpaper wallpaper)  {
        if (wallpaper.isFavorite()) {
            btnFavorite.setImageResource(R.drawable.ic_favorite_checked);
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_unchecked);
        }
    }
    public enum Apply {
        LOCKSCREEN,
        HOMESCREEN,
        HOMESCREEN_LOCKSCREEN
    }
}

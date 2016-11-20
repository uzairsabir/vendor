/**
 * Copyright (c) 2013, Redsolution LTD. All rights reserved.
 * <p/>
 * This file is part of Xabber project; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License, Version 3.
 * <p/>
 * Xabber is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License,
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */
package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.initializer.engine.R;

import org.vanguardmatrix.engine.android.ui.helper.ManagedActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ImagePicker extends ManagedActivity implements View.OnClickListener {

    //    public static int PICK_FROM_CAMERA = 226471;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static int PICK_IMAGE = 464;
    public static RelativeLayout rightOption1, rightOption2, back;
    static boolean selectSingleImage = false;
    Activity activity;
    ProgressBar loader;
    TextView pageTitle;
    ImageView cameraBtn, camImageView;
    GridView imagesGridView;
    GalleryPicturesAdapter imagesAdapter;
    List<GalleryItem> imagesList = new ArrayList<>();
    Collection<GalleryItem> selectedList = new ArrayList<GalleryItem>();
    String selectedImages2Return = "";
    String mCurrentPhotoPath;

    public static Intent createIntent(Context context, boolean _isSingleImage) {
        selectSingleImage = _isSingleImage;
        return new Intent(context, ImagePicker.class);
    }

    public static Bitmap getThumbnail(Uri uri) {
        File image = new File(uri.getPath());
        int THUMBNAIL_SIZE = 100;

        Log.e("aw", "here in getPreview");
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image.getPath(), bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
            return null;

        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / THUMBNAIL_SIZE;
        return BitmapFactory.decodeFile(image.getPath(), opts);
    }

    private void initGallery() {
        imagesList.clear();
        GalleryItem x = new GalleryItem();
        x.set_isSeleted(false);
        imagesList.add(x);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_picker);

        activity = this;

        // ---- updating title bar
        back = (RelativeLayout) findViewById(R.id.back_arrow);
        back.setOnClickListener(this);
        pageTitle = (TextView) findViewById(R.id.page_title_text);
        pageTitle.setText("Select Picture");
        rightOption1 = (RelativeLayout) findViewById(R.id.option_right1);
        //rightOption1.setImageResource(R.drawable.title_tick);
        rightOption1.setOnClickListener(this);
        rightOption1.setVisibility(View.VISIBLE);

        camImageView = (ImageView) findViewById(R.id.camera_image);

        cameraBtn = (ImageView) findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        imagesGridView = (GridView) findViewById(R.id.images_gridview);
        imagesAdapter = new GalleryPicturesAdapter(activity,
                R.layout.gallery_pick_item, imagesList);
        imagesGridView.setAdapter(imagesAdapter);

        imagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//				if(imagesList.get(i).is_isSeleted())

                MyLogs.printinfo("Toggle Image Selection " + i + imagesList.get(i).get_imagePath());
            }
        });

        ViewTreeObserver vto = imagesGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                imagesGridView.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
                refreshGallery();
            }
        });


    }

    public void cameraButtonClick() {
        dispatchTakePictureIntent();
    }

    private void refreshGallery() {
        new loadGalleryAsync().execute();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.option_right1) {
            processSelectedImage();

        } else if (i == R.id.back_arrow) {
            finish();

        } else {
        }
    }

    private void processSelectedImage() {
        new processSelectedImageAsync().execute();
    }

    // And to convert the image URI to the direct file system path of the image file
    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            handleBigCameraPhoto();
        }
    }

//    @Override
//    protected void onActivityResult(int reqCode, int resCode, Intent data) {
//        if (resCode == Activity.RESULT_OK && data != null) {
//            String realPath;
//            // SDK < API11
//            if (Build.VERSION.SDK_INT < 11)
//                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
//
//                // SDK >= 11 && SDK < 19
//            else if (Build.VERSION.SDK_INT < 19)
//                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
//
//                // SDK > 19 (Android 4.4)
//            else
//                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
//
//
//            setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(), realPath);
//        }
//    }

    private void setTextViews(int sdkInt, String path, String realPath) {
        MyLogs.printinfo("sdkInt : " + sdkInt + " path : " + path + " realPath : " + realPath);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = setUpPhotoFile();
                mCurrentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            UtilityFunctions.showToast_onCenter("Camera is busy", activity);
        }

    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            galleryAddPic();
            setPic();
        }

    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void setPic() {

        try {
            // Get the dimensions of the View
            int targetW = camImageView.getWidth();
            int targetH = camImageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            int scaleFactor = 2;
            try {
                // Determine how much to scale down the image
                scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            camImageView.setImageBitmap(bitmap);
            camImageView.invalidate();


            GalleryItem newItem = new GalleryItem();
            newItem.set_isSeleted(true);
            newItem.set_filePath(Uri.fromFile(new File(mCurrentPhotoPath)).toString());

            imagesList.add(1, newItem);
            imagesAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private class processSelectedImageAsync extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyLogs.printinfo("processSelectedImageAsync onPreExecute() !");
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            int i = 0;

            for (GalleryItem item : imagesAdapter.list) {
                if (item.is_isSeleted()) {
                    MyLogs.printinfo("selected image : " + item.get_filePath());
                    selectedImages2Return = selectedImages2Return + item.get_filePath() + ",";
                    publishProgress(i++);
                }
            }
            if (selectedImages2Return.length() > 1)
                return true;
            else
                return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MyLogs.printinfo("processSelectedImageAsync item : " + values + " updated.");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            MyLogs.printinfo("processSelectedImageAsync  onPost() ! result : " + result);

            try {
                Intent output = new Intent();
                output.putExtra(ParamKeys.selectedImagesPath, selectedImages2Return);
                setResult(RESULT_OK, output);
            } catch (Exception e) {
                e.printStackTrace();
                setResult(RESULT_CANCELED);
            }

            finish();
        }
    }

    private class loadGalleryAsync extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MyLogs.printinfo("createUserPostAsync onPreExecute() !");
        }

        @Override
        protected Boolean doInBackground(String... values) {

            try {

                int index = 0;
                Uri uri;
                Cursor cursor;
                int column_index_id, column_index_data, column_index_data_thumb, column_index_folder_name;
                ArrayList<GalleryItem> listOfAllImages = new ArrayList<GalleryItem>();
                String imagePath = "", imageName = "", thumbPath = "", filePath = "";
                uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                String[] projection = {MediaStore.MediaColumns.DATA,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

                cursor = activity.getContentResolver().query(uri, projection, null,
                        null, null);

//                column_index_id = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);
                column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                column_index_data_thumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
                column_index_folder_name = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

                while (cursor.moveToNext()) {

                    imageName = cursor.getString(column_index_folder_name);
                    imagePath = cursor.getString(column_index_data);
                    thumbPath = cursor.getString(column_index_data_thumb);
                    filePath = Uri.fromFile(new File(imagePath)).toString();


                    MyLogs.printinfo("image reading : imageName=" + imageName);
                    MyLogs.printinfo("image reading : thumbPath=" + thumbPath);
                    MyLogs.printinfo("image reading : imagePath=" + imagePath);
                    MyLogs.printinfo("image reading : imageURI=" + Uri.parse(imagePath).toString());
                    MyLogs.printinfo("image reading : thumbURI=" + Uri.parse(thumbPath).toString());
                    MyLogs.printinfo("image reading : filePath=" + filePath);

//                    MyLogs.printinfo("image reading : image getRealPathFromURI=" + getRealPathFromURI(Uri.parse(imagePath)));
//                    MyLogs.printinfo("image reading : thumb getRealPathFromURI=" + getRealPathFromURI(Uri.parse(thumbPath)));

                    GalleryItem item = new GalleryItem();
                    item.set_imageUri(Uri.parse(filePath));
                    item.set_name(imageName);
                    item.set_imagePath(imagePath);
                    item.set_thumbPath(thumbPath);
                    item.set_filePath(filePath);

                    listOfAllImages.add(item);

                    imagesList.add(0, item);
                    publishProgress(++index);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MyLogs.printinfo("gallery item : " + values + " updated.");
            imagesAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            MyLogs.printinfo("createUserPostAsync  onPost() ! result : " + result);
            imagesAdapter.notifyDataSetChanged();
        }

    }
}

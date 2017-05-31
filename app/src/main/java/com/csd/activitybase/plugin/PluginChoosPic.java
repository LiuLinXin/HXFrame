package com.csd.activitybase.plugin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.csd.activitybase.activity.BasePlugin;
import com.csd.activitybase.activity.PluginActivity;

import java.io.File;

/**
 * Created by administrato on 2017/5/2.
 */

public class PluginChoosPic extends BasePlugin implements View.OnClickListener {

    private static final int CHOSSPIC_CODE = 134;
    private ImageView iv;

    public PluginChoosPic(ImageView iv) {
        this.iv = iv;
        iv.setOnClickListener(this);
    }

    @Override
    public void bindActivity(PluginActivity activity) {
        super.bindActivity(activity);
    }

    public void choosPic(){
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        getAlbum.setAction(Intent.ACTION_GET_CONTENT);

        getParentActivity().startActivityForResult(getAlbum, CHOSSPIC_CODE);
    }

    @Override
    public void onClick(View v) {
        if(v == iv){
            choosPic();
        }
    }

    private String filePath;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = activity.getContentResolver();
            Log.i("hx", "path : "+uri.getPath());
            try {
                iv.setImageURI(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                iv.setImageBitmap(bitmap);
                Cursor c = cr.query(uri, null, null, null, null);
                c.moveToFirst();
                //这是获取的图片保存在sdcard中的位置
                filePath = c.getString(c.getColumnIndex("_data"));
                System.out.println(filePath + "----------保存路径2");
            } catch (Exception e) {
                Log.e("hx", e.getMessage(),e);
            }
        }
    }

    public File getFile(){
        File file = new File(filePath);
        if(file.exists())
            return file;
        return null;
    }
}

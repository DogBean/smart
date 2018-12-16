package com.smart.linguoyong.smart.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);
    }

//    @Override
////    public ImageView createImageView(Context context) {
////        //圆角
////        return new RoundAngleImageView(context);
////    }
}
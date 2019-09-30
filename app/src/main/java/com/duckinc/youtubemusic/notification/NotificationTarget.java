package com.duckinc.youtubemusic.notification;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.core.app.NotificationCompat;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class NotificationTarget implements Target
{
    public NotificationCompat.Builder builder;

    public void setNotificationBuilder(NotificationCompat.Builder builder)
    {
        this.builder = builder;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {}

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {}

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {}
}
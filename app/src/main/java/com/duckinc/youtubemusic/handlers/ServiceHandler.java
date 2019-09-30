package com.duckinc.youtubemusic.handlers;

import android.os.Handler;
import android.os.Message;

import com.duckinc.youtubemusic.BackgroundExoAudioService;

import java.lang.ref.WeakReference;


public class ServiceHandler extends Handler
{
    private final WeakReference<BackgroundExoAudioService> weakReference;

    public ServiceHandler(BackgroundExoAudioService service)
    {
        weakReference = new WeakReference<>(service);
    }

    // Define how to handle any incoming messages here
    @Override
    public void handleMessage(Message message)
    {
//            LogHelper.e(TAG, "ServiceHandler | handleMessage");
        BackgroundExoAudioService service = weakReference.get();
        if (service != null && service.getPlayback() != null) {
            if (service.getPlayback().isPlaying()) {
                service.sendSessionTokenToActivity();
            }
        }
    }
}
package com.devteam.youtubemusic.handlers;

import android.os.Handler;
import android.os.Message;

import com.devteam.youtubemusic.BackgroundExoAudioService;
import com.devteam.youtubemusic.utils.LogHelper;

import java.lang.ref.WeakReference;


public class DelayedStopHandler extends Handler
{
    private static final String TAG = LogHelper.makeLogTag(DelayedStopHandler.class);

    private final WeakReference<BackgroundExoAudioService> weakReference;

    public DelayedStopHandler(BackgroundExoAudioService service)
    {
        weakReference = new WeakReference<>(service);
    }

    @Override
    public void handleMessage(Message msg)
    {
        BackgroundExoAudioService service = weakReference.get();
        if (service != null && service.getPlayback() != null) {
            if (service.getPlayback().isPlaying()) {
                LogHelper.d(TAG, "Ignoring delayed stop since the media player is in use.");
                return;
            }
            LogHelper.d(TAG, "Stopping service with delay handler.");
            service.stopSelf();
        }
    }
}

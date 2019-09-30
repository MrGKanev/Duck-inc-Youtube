package com.devteam.youtubemusic.interfaces;

import android.support.v4.media.session.PlaybackStateCompat;


public interface PlaybackServiceCallback
{
    void onPlaybackStart();

    void onNotificationRequired();

    void onPlaybackStop();

    void onPlaybackStateUpdated(PlaybackStateCompat newState);
}

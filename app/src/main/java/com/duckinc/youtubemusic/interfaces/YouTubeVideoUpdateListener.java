package com.duckinc.youtubemusic.interfaces;

import com.duckinc.youtubemusic.model.YouTubeVideo;

import java.util.List;

public interface YouTubeVideoUpdateListener
{
    void onYouTubeVideoChanged(YouTubeVideo youTubeVideo);

    void onYouTubeVideoRetrieveError();

    void onCurrentQueueIndexUpdated(int queueIndex);

    void onQueueUpdated(String title, List<YouTubeVideo> newQueue);
}

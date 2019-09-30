package com.duckinc.youtubemusic.interfaces;

import com.duckinc.youtubemusic.model.YouTubeVideo;

import java.util.List;

public interface YouTubeVideoReceiver
{
    void onVideosReceived(List<YouTubeVideo> youTubeVideos, String currentPageToken, String nextPageToken);
}
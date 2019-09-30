package com.devteam.youtubemusic.interfaces;

import com.devteam.youtubemusic.model.YouTubeVideo;

import java.util.List;

public interface YouTubeVideoReceiver
{
    void onVideosReceived(List<YouTubeVideo> youTubeVideos, String currentPageToken, String nextPageToken);
}
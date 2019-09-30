package com.devteam.youtubemusic.interfaces;

import com.devteam.youtubemusic.model.YouTubePlaylist;
import com.devteam.youtubemusic.model.YouTubeVideo;

import java.util.List;


public interface YouTubePlaylistReceiver
{
    void onPlaylistReceived(List<YouTubePlaylist> youTubePlaylistList);

    void onPlaylistNotFound(String playlistId, int errorCode);

    void onPlaylistVideoReceived(List<YouTubeVideo> youTubeVideos);
}

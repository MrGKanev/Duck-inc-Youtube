package com.duckinc.youtubemusic.interfaces;

import com.duckinc.youtubemusic.model.YouTubePlaylist;
import com.duckinc.youtubemusic.model.YouTubeVideo;

import java.util.List;


public interface YouTubePlaylistReceiver
{
    void onPlaylistReceived(List<YouTubePlaylist> youTubePlaylistList);

    void onPlaylistNotFound(String playlistId, int errorCode);

    void onPlaylistVideoReceived(List<YouTubeVideo> youTubeVideos);
}

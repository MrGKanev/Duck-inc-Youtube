package com.duckinc.youtubemusic.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.duckinc.youtubemusic.BackgroundExoAudioService;
import com.duckinc.youtubemusic.R;
import com.duckinc.youtubemusic.adapters.VideosAdapter;
import com.duckinc.youtubemusic.callbacks.SimpleItemTouchCallback;
import com.duckinc.youtubemusic.database.YouTubeSqlDb;
import com.duckinc.youtubemusic.interfaces.OnStartDragListener;
import com.duckinc.youtubemusic.model.YouTubeVideo;
import com.duckinc.youtubemusic.ui.decoration.DividerDecoration;
import com.duckinc.youtubemusic.utils.Config;
import com.duckinc.youtubemusic.utils.NetworkHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.duckinc.youtubemusic.utils.Config.CUSTOM_ACTION_PLAY;

/**
 * Class that handles list of the recently watched YouTube
 * Created by teocci on 7.3.16..
 */
public class RecentlyWatchedFragment extends RecyclerFragment implements OnStartDragListener
{
    private static final String TAG = RecentlyWatchedFragment.class.getSimpleName();

    private ArrayList<YouTubeVideo> recentlyPlayedVideos;
    private ItemTouchHelper itemTouchHelper;
    private AdView mAdView;

    public static RecentlyWatchedFragment newInstance()
    {
        RecentlyWatchedFragment fragment = new RecentlyWatchedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public RecentlyWatchedFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        recentlyPlayedVideos = new ArrayList<>();
        networkConf = new NetworkHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recently_watched, container, false);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = rootView.findViewById(R.id.adView);


        recyclerView = rootView.findViewById(R.id.recently_played);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.addItemDecoration(getItemDecoration());

        recyclerView.getItemAnimator().setAddDuration(500);
        recyclerView.getItemAnimator().setChangeDuration(500);
        recyclerView.getItemAnimator().setMoveDuration(500);
        recyclerView.getItemAnimator().setRemoveDuration(500);

        videoListAdapter = getAdapter();
        videoListAdapter.setOnItemClickListener(this);
        videoListAdapter.setOnStartDragListener(this);
        recyclerView.setAdapter(videoListAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchCallback(videoListAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }

    @Override
    public void onStart() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        super.onStart();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager()
    {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration()
    {
        //We must draw dividers ourselves if we want them in a list
        return new DividerDecoration(getActivity());
    }

    @Override
    protected VideosAdapter getAdapter()
    {
        return new VideosAdapter(getActivity(), false);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (!getUserVisibleHint()) {
            // Do nothing for now
        }

        recentlyPlayedVideos.clear();
        recentlyPlayedVideos.addAll(
                YouTubeSqlDb
                        .getInstance()
                        .videos(YouTubeSqlDb.VIDEOS_TYPE.RECENTLY_WATCHED)
                        .readAll()
        );

        if (videoListAdapter != null) {
            getActivity().runOnUiThread(() -> videoListAdapter.setYouTubeVideos(recentlyPlayedVideos));
        }
    }


    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);

        if (visible && isResumed()) {
//            Log.d(TAG, "RecentlyWatchedFragment visible and resumed");
            // Only manually call onResume if fragment is already visible
            // Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    /**
     * Adds listener for list item choosing
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (!networkConf.isNetworkAvailable(getActivity())) {
            networkConf.createNetErrorDialog();
            return;
        }

        Toast.makeText(
                getContext(),
                getResources().getString(R.string.toast_message_loading),
                Toast.LENGTH_SHORT
        ).show();

        YouTubeSqlDb.getInstance().videos(YouTubeSqlDb.VIDEOS_TYPE.RECENTLY_WATCHED)
                .create(videoListAdapter.getYouTubeVideo(position));

        Intent serviceIntent = new Intent(getContext(), BackgroundExoAudioService.class);
        serviceIntent.setAction(CUSTOM_ACTION_PLAY);
        serviceIntent.putExtra(Config.KEY_YOUTUBE_TYPE, Config.YOUTUBE_MEDIA_TYPE_VIDEO);
        serviceIntent.putExtra(Config.KEY_YOUTUBE_TYPE_VIDEO, videoListAdapter.getYouTubeVideo(position));
        getActivity().startService(serviceIntent);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    /**
     * Clears recently played list items
     */
    public void clearRecentlyPlayedList()
    {
        recentlyPlayedVideos.clear();
        videoListAdapter.notifyDataSetChanged();
    }
}

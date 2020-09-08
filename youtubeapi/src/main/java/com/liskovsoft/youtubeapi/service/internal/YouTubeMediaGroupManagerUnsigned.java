package com.liskovsoft.youtubeapi.service.internal;

import com.liskovsoft.mediaserviceinterfaces.data.MediaGroup;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.youtubeapi.browse.BrowseServiceUnsigned;
import com.liskovsoft.youtubeapi.browse.models.sections.BrowseSection;
import com.liskovsoft.youtubeapi.browse.models.sections.BrowseTab;
import com.liskovsoft.youtubeapi.browse.models.sections.TabbedBrowseResultContinuation;
import com.liskovsoft.youtubeapi.search.SearchServiceUnsigned;
import com.liskovsoft.youtubeapi.search.models.SearchResult;
import com.liskovsoft.youtubeapi.service.YouTubeMediaServiceHelper;
import com.liskovsoft.youtubeapi.service.data.YouTubeMediaGroup;

import java.util.List;

public class YouTubeMediaGroupManagerUnsigned implements MediaGroupManagerInt {
    private static final String TAG = YouTubeMediaGroupManagerUnsigned.class.getSimpleName();
    private static YouTubeMediaGroupManagerUnsigned sInstance;
    private final BrowseServiceUnsigned mBrowseServiceUnsigned;
    private final SearchServiceUnsigned mSearchServiceUnsigned;

    private YouTubeMediaGroupManagerUnsigned() {
        mSearchServiceUnsigned = SearchServiceUnsigned.instance();
        mBrowseServiceUnsigned = BrowseServiceUnsigned.instance();
    }

    public static YouTubeMediaGroupManagerUnsigned instance() {
        if (sInstance == null) {
            sInstance = new YouTubeMediaGroupManagerUnsigned();
        }

        return sInstance;
    }

    public static void unhold() {
        sInstance = null;
        BrowseServiceUnsigned.unhold();
        SearchServiceUnsigned.unhold();
    }

    @Override
    public MediaGroup getSearch(String searchText) {
        SearchResult searchResult = mSearchServiceUnsigned.getSearch(searchText);
        return YouTubeMediaGroup.from(searchResult, MediaGroup.TYPE_SEARCH);
    }

    @Override
    public MediaGroup continueGroup(MediaGroup mediaGroup) {
        Log.d(TAG, "Continue group " + mediaGroup.getTitle() + "...");

        if (mediaGroup.getType() == MediaGroup.TYPE_SEARCH) {
            return YouTubeMediaGroup.from(
                    mSearchServiceUnsigned.continueSearch(YouTubeMediaServiceHelper.extractNextKey(mediaGroup)),
                    mediaGroup);
        }

        return YouTubeMediaGroup.from(
                mBrowseServiceUnsigned.continueSection(YouTubeMediaServiceHelper.extractNextKey(mediaGroup)),
                mediaGroup
        );
    }

    @Override
    public BrowseTab getHomeTab() {
        Log.d(TAG, "Emitting home group...");
        return mBrowseServiceUnsigned.getHome();
    }

    @Override
    public MediaGroup getSubscriptions() {
        // SHOULD BE EMPTY FOR UNSIGNED
        return YouTubeMediaGroup.EMPTY_GROUP;
    }

    @Override
    public MediaGroup getHistory() {
        // SHOULD BE EMPTY FOR UNSIGNED
        return YouTubeMediaGroup.EMPTY_GROUP;
    }

    @Override
    public BrowseTab getMusicTab() {
        Log.d(TAG, "Emitting music group...");
        return mBrowseServiceUnsigned.getMusic();
    }

    @Override
    public BrowseTab getNewsTab() {
        Log.d(TAG, "Emitting news group...");
        return mBrowseServiceUnsigned.getNews();
    }

    @Override
    public BrowseTab getGamingTab() {
        Log.d(TAG, "Emitting gaming group...");
        return mBrowseServiceUnsigned.getGaming();
    }

    @Override
    public TabbedBrowseResultContinuation continueTab(String nextPageKey) {
        Log.d(TAG, "Continue tab...");
        return mBrowseServiceUnsigned.continueTab(nextPageKey);
    }
}

package com.liskovsoft.youtubeapi.next.result;

import com.liskovsoft.youtubeapi.common.converters.jsonpath.JsonPath;
import com.liskovsoft.youtubeapi.common.models.items.ItemWrapper;
import com.liskovsoft.youtubeapi.common.models.items.VideoItem;

import java.util.List;

public class WatchNextResultContinuation {
    @JsonPath("$.continuationContents.horizontalListContinuation.items[*]")
    private List<ItemWrapper> mItemWrappers;
    @JsonPath("$.continuationContents.horizontalListContinuation.continuations[1].nextContinuationData.continuation")
    private String mNextPageKey;

    public String getNextPageKey() {
        return mNextPageKey;
    }

    public List<ItemWrapper> getItemWrappers() {
        return mItemWrappers;
    }
}

package vn.me.simplenewsstand.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.me.simplenewsstand.utils.DisplayUtil;

/**
 * Created by binhlt on 21/10/2016.
 */

public class SearchRequest implements Parcelable {

    private int page;
    private String query;
    /**
     * Use for the 'beginDate' param in get articles api. In millisecond.
     */
    private long beginTime;
    private String sortType;
    private List<String> newsDesk;

    public SearchRequest() {
        reset();
    }

    protected SearchRequest(Parcel in) {
        page = in.readInt();
        query = in.readString();
        beginTime = in.readLong();
        sortType = in.readString();
        newsDesk = in.createStringArrayList();
    }

    public static final Creator<SearchRequest> CREATOR = new Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel in) {
            return new SearchRequest(in);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };

    public void setPage(int page) {
        this.page = page;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public void setNewsDesk(List<String> newsDesk) {
        this.newsDesk = newsDesk;
    }

    public int getPage() {
        return page;
    }

    public String getQuery() {
        return query;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public String getSortType() {
        return sortType;
    }

    public List<String> getNewsDesk() {
        return newsDesk;
    }

    public void nextPage() {
        this.page++;
    }

    public void reset() {
        this.page = 0;
        this.query = "";
        this.beginTime = 0;
        this.sortType = "";
        this.newsDesk = new ArrayList<>();
    }

    public Map<String, String> toQueryMap() {
        Map<String, String> options = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            options.put("q", query);
        }
        if (beginTime != 0) {
            options.put("begin_date", DisplayUtil.getFormattedDateForApi(beginTime));
        }
        if (sortType != null && !sortType.isEmpty()) {
            options.put("sort", sortType.toLowerCase());
        }
        if (newsDesk != null && !newsDesk.isEmpty()) {
            options.put("fq", "news_desk:(" + createNewsDeskParam() + ")");
        }
        options.put("page", String.valueOf(page));
        return options;
    }

    private String createNewsDeskParam() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < newsDesk.size(); i++) {
            builder.append("\"").append(newsDesk.get(i)).append("\"");
            if (i < newsDesk.size() - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeString(query);
        dest.writeLong(beginTime);
        dest.writeString(sortType);
        dest.writeStringList(newsDesk);
    }
}

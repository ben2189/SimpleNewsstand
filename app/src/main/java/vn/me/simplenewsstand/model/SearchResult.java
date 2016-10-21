package vn.me.simplenewsstand.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by binhlt on 21/10/2016.
 */

public class SearchResult {

    @SerializedName("docs")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

}

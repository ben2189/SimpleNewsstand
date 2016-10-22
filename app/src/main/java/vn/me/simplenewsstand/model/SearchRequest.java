package vn.me.simplenewsstand.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by binhlt on 21/10/2016.
 */

public class SearchRequest {

    private int page = 0;
    private String query = "";

    public void setPage(int page) {
        this.page = page;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void nextPage() {
        this.page++;
    }

    public void reset() {
        this.page = 0;
        this.query = "";
    }

    public Map<String, String> toQueryMap() {
        Map<String, String> options = new HashMap<>();
        options.put("page", String.valueOf(page));
        return options;
    }

}

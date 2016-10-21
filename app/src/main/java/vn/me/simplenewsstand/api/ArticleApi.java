package vn.me.simplenewsstand.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import vn.me.simplenewsstand.model.SearchResult;

/**
 * Created by taq on 21/10/2016.
 */

public interface ArticleApi {

    @GET("svc/search/v2/articlesearch.json")
    Call<SearchResult> search(@QueryMap(encoded = true)Map<String, String> options);

}

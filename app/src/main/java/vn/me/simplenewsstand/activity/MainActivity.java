package vn.me.simplenewsstand.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.me.simplenewsstand.R;
import vn.me.simplenewsstand.adapter.ArticleAdapter;
import vn.me.simplenewsstand.api.ArticleApi;
import vn.me.simplenewsstand.model.SearchRequest;
import vn.me.simplenewsstand.model.SearchResult;
import vn.me.simplenewsstand.utils.RetrofitUtil;

public class MainActivity extends AppCompatActivity {

    private ArticleAdapter mArticleAdapter;
    private ArticleApi mArticleApi;
    private StaggeredGridLayoutManager mLayoutManager;
    private SearchRequest mSearchRequest;

    @BindView(R.id.rvArticle)
    RecyclerView rvArticle;

    public interface Listener {
        void handleResult(SearchResult searchResult);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpApis();
        setUpViews();

        searchArticle();
    }

    private void setUpApis() {
        mSearchRequest = new SearchRequest();
        mArticleApi = RetrofitUtil.get().create(ArticleApi.class);
    }

    private void setUpViews() {
        mArticleAdapter = new ArticleAdapter();
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvArticle.setLayoutManager(mLayoutManager);
        rvArticle.setAdapter(mArticleAdapter);
    }

    private void searchArticle() {
        fetchArticles(new Listener() {
            @Override
            public void handleResult(SearchResult searchResult) {
                mArticleAdapter.setArticles(searchResult.getArticles());
            }
        });
    }

    private void fetchArticles(final Listener listener) {
        mArticleApi.search(mSearchRequest.toQueryMap()).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                listener.handleResult(response.body());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call article searching api fail!", Toast.LENGTH_SHORT);
            }
        });
    }
}

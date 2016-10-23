package vn.me.simplenewsstand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import vn.me.simplenewsstand.utils.Constants;
import vn.me.simplenewsstand.utils.RetrofitUtil;

public class MainActivity extends AppCompatActivity {

    private ArticleAdapter mArticleAdapter;
    private ArticleApi mArticleApi;
    private StaggeredGridLayoutManager mLayoutManager;
    private SearchRequest mSearchRequest;
    private SearchView mSearchView;

    @BindView(R.id.rvArticle)
    RecyclerView rvArticle;

    @BindView(R.id.pbLoading)
    RelativeLayout pbLoading;

    @BindView(R.id.pbLoadMore)
    ProgressBar pbLoadMore;

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

        mSearchRequest.reset();
        searchArticle();
    }

    private void setUpApis() {
        mSearchRequest = new SearchRequest();
        mArticleApi = RetrofitUtil.get().create(ArticleApi.class);
    }

    private void setUpViews() {
        mArticleAdapter = new ArticleAdapter();
        mArticleAdapter.setLoadMoreListener(new ArticleAdapter.Listener() {
            @Override
            public void handleLoadMore() {
                searchMoreArticle();
            }
        });
        mArticleAdapter.setContext(this);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvArticle.setLayoutManager(mLayoutManager);
        rvArticle.setAdapter(mArticleAdapter);
    }

    private void searchArticle() {
        pbLoading.setVisibility(View.VISIBLE);
        fetchArticles(new Listener() {
            @Override
            public void handleResult(SearchResult searchResult) {
                mArticleAdapter.setArticles(searchResult.getArticles());
                rvArticle.scrollToPosition(0);
            }
        });
    }

    private void searchMoreArticle() {
        pbLoadMore.setVisibility(View.VISIBLE);
        mSearchRequest.nextPage();
        fetchArticles(new Listener() {
            @Override
            public void handleResult(SearchResult searchResult) {
                mArticleAdapter.addArticles(searchResult.getArticles());
            }
        });
    }

    private void fetchArticles(final Listener listener) {
        mArticleApi.search(mSearchRequest.toQueryMap()).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                SearchResult result = response.body();
                if (result != null && result.getArticles() != null) {
                    listener.handleResult(result);
                }
                // TODO show the error message from api result
                hideLoading();
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                hideLoading();
            }
        });
    }

    private void hideLoading() {
        pbLoading.setVisibility(View.GONE);
        pbLoadMore.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        setUpSearchView(menuItem);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpSearchView(MenuItem menuItem) {
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                mSearchRequest.setQuery(query);
                searchArticle();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mSearchRequest.reset();
                searchArticle();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                Intent i = new Intent(MainActivity.this, FilterActivity.class);
                i.putExtra(Constants.SEARCH_REQUEST, mSearchRequest);
                startActivityForResult(i, Constants.REQUEST_CODE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE) {
            SearchRequest sr = (SearchRequest) data.getParcelableExtra(Constants.SEARCH_REQUEST);
            if (sr != null) {
                mSearchRequest = sr;
            }
            searchArticle();
        }
    }
}

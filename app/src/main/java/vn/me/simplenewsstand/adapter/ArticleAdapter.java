package vn.me.simplenewsstand.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.me.simplenewsstand.R;
import vn.me.simplenewsstand.model.Article;

/**
 * Created by binhlt on 20/10/2016.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> mArticles;
    private Listener mLoadMoreListener;

    public interface Listener {
        void handleLoadMore();
    }

    public ArticleAdapter() {
        this.mArticles = new ArrayList<>();
    }

    public void setLoadMoreListener(Listener listener) {
        mLoadMoreListener = listener;
    }

    public void setArticles(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }

    public void addArticles(List<Article> articles) {
        int positionStart = mArticles.size();
        mArticles.addAll(articles);
        notifyItemRangeInserted(positionStart, mArticles.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        switch (viewType) {
            case Article.NORMAL:
                itemView = inflater.inflate(R.layout.item_article, parent, false);
                return new ViewHolder(itemView);
            default:
                itemView = inflater.inflate(R.layout.item_article_no_image, parent, false);
                return new NoImageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        switch (article.getType()) {
            case Article.NORMAL:
                ((ViewHolder) holder).bindData(article);
                break;
            default:
                ((NoImageViewHolder) holder).bindData(article);
        }
        if (position == mArticles.size() - 1 && mLoadMoreListener != null) {
            // FIXME this method is called at the first load
            mLoadMoreListener.handleLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mArticles.get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;

        @BindView(R.id.tvSnippet)
        TextView tvSnippet;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Article article) {
            tvSnippet.setText(article.getSnippet());
            Glide.with(ivImage.getContext())
                    .load(article.getMedias().get(0).getUrl())
                    .into(ivImage);
        }
    }

    public class NoImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSnippet)
        TextView tvSnippet;

        public NoImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Article article) {
            tvSnippet.setText(article.getSnippet());
        }
    }
}

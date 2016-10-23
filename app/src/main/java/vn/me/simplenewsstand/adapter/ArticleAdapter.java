package vn.me.simplenewsstand.adapter;

import android.content.Context;
import android.content.Intent;
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
import vn.me.simplenewsstand.activity.WebViewActivity;
import vn.me.simplenewsstand.model.Article;
import vn.me.simplenewsstand.model.Media;
import vn.me.simplenewsstand.utils.Constants;
import vn.me.simplenewsstand.utils.DisplayUtil;

/**
 * Created by binhlt on 20/10/2016.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
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

    public void setContext(Context mContext) {
        this.mContext = mContext;
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
        final Article article = mArticles.get(position);
        switch (article.getType()) {
            case Article.NORMAL:
                ((ViewHolder) holder).bindData(article);
                break;
            default:
                ((NoImageViewHolder) holder).bindData(article);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext == null) {
                    return;
                }
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(Constants.WEB_URL, article.getWebUrl());
                mContext.startActivity(intent);
            }
        });
        if (position == mArticles.size() - 1 && mLoadMoreListener != null) {
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

        private final int minWidth = 75;
        private final int minHeight = 150;

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
            Media media = article.getMedias().get(0);
            for (Media tmp : article.getMedias()) {
                if (tmp.getWidth() > minWidth) {
                    media = tmp;
                    break;
                }
            }
            int height = media.getHeight() < minHeight ? media.getHeight() : minHeight;
            ViewGroup.LayoutParams params = ivImage.getLayoutParams();
            params.height = (int) DisplayUtil.convertDpToPixel(height, itemView.getContext());
            ivImage.setLayoutParams(params);
            Glide.with(ivImage.getContext())
                    .load(media.getUrl())
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

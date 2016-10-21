package vn.me.simplenewsstand.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by taq on 19/10/2016.
 */

public class Article implements Parcelable {

    public static final int NORMAL = 0;
    public static final int NO_IMAGE = 1;

    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("snippet")
    private String snippet;

    @SerializedName("multimedia")
    private List<Media> medias;

    protected Article(Parcel in) {
        webUrl = in.readString();
        snippet = in.readString();
        medias = in.createTypedArrayList(Media.CREATOR);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getWebUrl() {
        return webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public int getType() {
        if (getMedias() != null && !getMedias().isEmpty()) {
            return NORMAL;
        }
        return NO_IMAGE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(webUrl);
        dest.writeString(snippet);
        dest.writeTypedList(medias);
    }
}

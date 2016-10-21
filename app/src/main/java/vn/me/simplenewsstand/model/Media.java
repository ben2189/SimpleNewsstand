package vn.me.simplenewsstand.model;

import android.os.Parcel;
import android.os.Parcelable;

import vn.me.simplenewsstand.utils.Constants;

/**
 * Created by taq on 19/10/2016.
 */

public class Media implements Parcelable {

    private String type;
    private String url;
    private int width;
    private int height;

    protected Media(Parcel in) {
        type = in.readString();
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public int getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return Constants.IMAGE_URL + url;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}

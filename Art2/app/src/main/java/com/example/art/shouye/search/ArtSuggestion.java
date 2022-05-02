package com.example.art.shouye.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class ArtSuggestion implements SearchSuggestion {
    private String myText;
    private String myUrl;
    private String myCover;
    private boolean mIsHistory = false;

    public ArtSuggestion(Parcel source){
        this.myText = source.readString();
        this.myUrl = source.readString();
        this.myCover = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public ArtSuggestion(String name, String url, String cover) {
        this.myText = name.toLowerCase();
        this.myUrl = url;
        this.myCover = cover;
    }

    public static final Parcelable.Creator<ArtSuggestion> CREATOR = new Parcelable.Creator<ArtSuggestion>(){
        @Override
        public ArtSuggestion createFromParcel(Parcel parcel) {
            return new ArtSuggestion(parcel);
        }

        @Override
        public ArtSuggestion[] newArray(int i) {
            return new ArtSuggestion[i];
        }
    };

    @Override
    public String getBody() {
        return myText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(myText);
        parcel.writeString(myUrl);
        parcel.writeString(myCover);
        parcel.writeInt(mIsHistory ? 1 : 0);
    }

    public String getMyText() {
        return myText;
    }

    public void setMyText(String myText) {
        this.myText = myText;
    }

    public String getMyUrl() {
        return myUrl;
    }

    public void setMyUrl(String myUrl) {
        this.myUrl = myUrl;
    }

    public void setMyCover(String myCover) {
        this.myCover = myCover;
    }

    public boolean ismIsHistory() {
        return mIsHistory;
    }

    public void setmIsHistory(boolean mIsHistory) {
        this.mIsHistory = mIsHistory;
    }

    public String getMyCover() {
        return myCover;
    }
}

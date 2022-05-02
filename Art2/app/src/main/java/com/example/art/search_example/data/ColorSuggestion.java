package com.example.art.search_example.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class ColorSuggestion implements SearchSuggestion {

    private String myText;
    private boolean mIsHistory = false;

    public ColorSuggestion(String suggestion) {
        this.myText = suggestion.toLowerCase();
    }

    public ColorSuggestion(Parcel source) {
        this.myText = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return myText;
    }

    public static final Parcelable.Creator<ColorSuggestion> CREATOR = new Parcelable.Creator<ColorSuggestion>() {
        @Override
        public ColorSuggestion createFromParcel(Parcel in) {
            return new ColorSuggestion(in);
        }

        @Override
        public ColorSuggestion[] newArray(int size) {
            return new ColorSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(myText);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
package com.example.android.miwok;

/**
 * Created by rkesavan on 4/5/2017.
 */

public class Word {
    private String mOrigWord;
    private String mTranslatedWord;
    private int mImgID = NO_IMAGE;
    private int mAudioID;

    private static final int NO_IMAGE = -1;

    public Word(String origWord, String translatedWord, int audioID) {
        mOrigWord = origWord;
        mTranslatedWord = translatedWord;
        mAudioID = audioID;
    }

    public Word(String origWord, String translatedWord, int imgID, int audioID) {

        mOrigWord = origWord;
        mTranslatedWord = translatedWord;
        mImgID = imgID;
        mAudioID = audioID;
    }

    public String getOrigWord() {
        return mOrigWord;
    }

    public String getTranslatedWord() {
        return mTranslatedWord;
    }

    public int getmImgID() {
        return mImgID;
    }

    public int getmAudioID() { return mAudioID; }

    public boolean hasImage() {
        return mImgID != NO_IMAGE;
    }
}

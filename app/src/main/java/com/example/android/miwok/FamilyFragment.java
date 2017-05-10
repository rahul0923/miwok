package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        releaseMediaPlayer();
                    } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mediaPlayer.start();
                    }
                }
            };

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        int placeHolderImgID = R.mipmap.ic_launcher;
        int bgColor = R.color.category_family;

        ArrayList<Word> familyList = new ArrayList<Word>();

        familyList.add(new Word("Father", "Pitha",  R.drawable.family_father, R.raw.one));
        familyList.add(new Word("Mother", "Maa",  R.drawable.family_mother, R.raw.one));
        familyList.add(new Word("Brother", "Bhai",  R.drawable.family_son, R.raw.one));
        familyList.add(new Word("Sister", "Behain",  R.drawable.family_daughter, R.raw.one));
        familyList.add(new Word("Son", "Beta",  R.drawable.family_older_brother, R.raw.one));
        familyList.add(new Word("Daughter", "Beti",  R.drawable.family_younger_brother, R.raw.one));
        familyList.add(new Word("Husband", "Paati",  R.drawable.family_older_sister, R.raw.one));
        familyList.add(new Word("Wife", "Patni",  R.drawable.family_younger_sister, R.raw.one));
        familyList.add(new Word("Grandson", "Poota",  R.drawable.family_grandmother, R.raw.one));
        familyList.add(new Word("Granddaughter", "Pooti",  R.drawable.family_grandfather, R.raw.one));

        WordsAdapter wordsAdapter = new WordsAdapter(getActivity(), familyList, R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(wordsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = (Word)adapterView.getItemAtPosition(i);

                int res = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    releaseMediaPlayer();
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioID());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(myCompletionListener);
                }
            }
        });

        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();
        this.releaseMediaPlayer();
    }

    private  void releaseMediaPlayer() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    private MediaPlayer.OnCompletionListener myCompletionListener = new MediaPlayer.OnCompletionListener() {

        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
}

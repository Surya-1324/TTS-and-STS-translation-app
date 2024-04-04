package com.example.myapplication;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

import rm.com.audiowave.AudioWaveView;

public class ButtonHandler {

    private Texttospeech texttospeech;
    private ImageButton pauseButton, stopButton, playButton;
    private MediaPlayer mediaPlayer;
    private AudioWaveView waveView;
    private ObjectAnimator progressAnimation;
    private TextView durationTextView;

    public ButtonHandler(Texttospeech activity) {
        this.texttospeech = activity;
        initializeMediaPlayer();
        initializeButtons();
        setInitialButtonState();
        initializeAudioWaveView();
        durationTextView = texttospeech.findViewById(R.id.durationTextView);
    }

    private void initializeAudioWaveView() {
        int audioDuration = mediaPlayer.getDuration();
        waveView = texttospeech.findViewById(R.id.wave);
        progressAnimation = ObjectAnimator.ofFloat(waveView, "progress", 0F, 100F);
        progressAnimation.setInterpolator(new LinearInterpolator());
        progressAnimation.setDuration(audioDuration);
    }

    void initializeButtons() {
        playButton = texttospeech.findViewById(R.id.playButton);
        pauseButton = texttospeech.findViewById(R.id.pauseButton);
        stopButton = texttospeech.findViewById(R.id.stopButton);

        if (playButton != null) {
            playButton.setOnClickListener(view -> {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    progressAnimation.start();
                    playButton.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.VISIBLE);
                    stopButton.setVisibility(View.GONE);
                } else {
                    playButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.GONE);
                    stopButton.setVisibility(View.GONE);
                }
            });
        }

        if (pauseButton != null) {
            pauseButton.setOnClickListener(view -> {
                mediaPlayer.pause();
                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.GONE);
                progressAnimation.pause();
            });
        }
        if (stopButton != null) {
            stopButton.setOnClickListener(view -> {
                mediaPlayer.stop();
                releaseMediaPlayer();
                progressAnimation.end();
                handleStopButtonClick();
                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.GONE);
            });
        }
        startUpdateDurationTask();
    }
    private void initializeMediaPlayer() {
        mediaPlayer = MediaPlayer.create(texttospeech, R.raw.sample);
        mediaPlayer.setOnCompletionListener(mediaPlayer -> handleStopButtonClick());
    }

    private void setInitialButtonState() {
        try {
            if (playButton != null && pauseButton != null && stopButton != null) {
                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.GONE);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void handleStopButtonClick() {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            initializeMediaPlayer();
            progressAnimation.end();
            updateDurationTextView();
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.GONE);
            stopButton.setVisibility(View.GONE);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void updateDurationTextView() {
        if (durationTextView != null && mediaPlayer != null) {
            int currentPosition = mediaPlayer.getCurrentPosition() / 1000;
            durationTextView.setText(formatDuration(currentPosition));
        }
    }

    private String formatDuration(int durationInSeconds) {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void startUpdateDurationTask() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                texttospeech.runOnUiThread(() -> updateDurationTextView());
            }
        }, 0, 1000);
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

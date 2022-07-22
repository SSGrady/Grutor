package com.example.grutor.Utility;

import android.view.View;

public class OnboardingItem {

    private int image;
    private String title;
    private String description;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void preventTwoClick(final View view) {
        view.setEnabled(false);
        view.postDelayed(()-> view.setEnabled(true), 4810);
    }
}
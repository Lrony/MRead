package com.lrony.mread.data.bean;

/**
 * Created by Lrony on 18-4-24.
 */
public class Status {

    private String text;

    public Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

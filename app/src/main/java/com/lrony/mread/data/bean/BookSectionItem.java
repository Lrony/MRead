package com.lrony.mread.data.bean;

/**
 * Created by Lrony on 18-5-15.
 */
public class BookSectionItem {

    private int sectionIndex;
    private String sectionName;
    private String sectionLink;

    public BookSectionItem(int sectionIndex, String sectionName, String sectionId) {
        this.sectionIndex = sectionIndex;
        this.sectionName = sectionName;
        this.sectionLink = sectionId;
    }

    public int getSectionIndex() {
        return sectionIndex;
    }

    public void setSectionIndex(int sectionIndex) {
        this.sectionIndex = sectionIndex;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionLink() {
        return sectionLink;
    }

    public void setSectionLink(String sectionLink) {
        this.sectionLink = sectionLink;
    }
}

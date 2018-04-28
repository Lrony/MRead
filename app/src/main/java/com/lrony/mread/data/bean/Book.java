package com.lrony.mread.data.bean;

import android.telecom.Call;

/**
 * Created by Lrony on 18-4-24.
 */
public class Book {

    /*"books": [{
        "_id": "5702151c1c8e4db9483762d6",
                "title": "放开那个女巫",
                "author": "二目",
                "shortIntro": "程岩原以为穿越到了欧洲中世纪，成为了一位光荣的王子。但这世界似乎跟自己想的不太一样？女巫真实存在，而且还真具有魔力？女巫种田文，将种田进行到底。",
                "cover": "/agent/http://img.1391.com/api/v1/bookcenter/cover/1/1130743/_1130743_505316.jpg/",
                "site": "zhuishuvip",
                "majorCate": "奇幻",
                "minorCate": "西方奇幻",
                "sizetype": -1,
                "superscript": "",
                "contentType": "txt",
                "allowMonthly": false,
                "banned": 0,
                "latelyFollower": 30449,
                "retentionRatio": 59.82,
                "lastChapter": "第1108章 绰绰有余的对手",
                "tags": []*/

    private String id;
    private String title;
    private String author;
    private String shortIntro;
    private String cover;
    private String site;
    private String majorCate;
    private String minorCate;
    private String contentType;
    private boolean allowMonthly;
    private int banned;
    private int latelyFollower;
    private double retentionRatio;
    private String lastChapter;

    // 需要另外获取
    private String updated;
    // isSerial 是否还在更新
    private boolean isFinished;
    private long wordCount;
    private long postCount;
    private int chaptersCount;

    public Book(String id, String title, String author, String shortIntro, String cover, String site, String majorCate, String minorCate, String contentType, boolean allowMonthly, int banned, int latelyFollower, double retentionRatio, String lastChapter, String updated, boolean isFinished, long wordCount, long postCount, int chaptersCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.shortIntro = shortIntro;
        this.cover = cover;
        this.site = site;
        this.majorCate = majorCate;
        this.minorCate = minorCate;
        this.contentType = contentType;
        this.allowMonthly = allowMonthly;
        this.banned = banned;
        this.latelyFollower = latelyFollower;
        this.retentionRatio = retentionRatio;
        this.lastChapter = lastChapter;
        this.updated = updated;
        this.isFinished = isFinished;
        this.wordCount = wordCount;
        this.postCount = postCount;
        this.chaptersCount = chaptersCount;
    }

    public Book(String id, String title, String author, String shortIntro, String cover, String site, String majorCate, String minorCate, String contentType, boolean allowMonthly, int banned, int latelyFollower, double retentionRatio, String lastChapter) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.shortIntro = shortIntro;
        this.cover = cover;
        this.site = site;
        this.majorCate = majorCate;
        this.minorCate = minorCate;
        this.contentType = contentType;
        this.allowMonthly = allowMonthly;
        this.banned = banned;
        this.latelyFollower = latelyFollower;
        this.retentionRatio = retentionRatio;
        this.lastChapter = lastChapter;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMajorCate() {
        return majorCate;
    }

    public void setMajorCate(String majorCate) {
        this.majorCate = majorCate;
    }

    public String getMinorCate() {
        return minorCate;
    }

    public void setMinorCate(String minorCate) {
        this.minorCate = minorCate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isAllowMonthly() {
        return allowMonthly;
    }

    public void setAllowMonthly(boolean allowMonthly) {
        this.allowMonthly = allowMonthly;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public double getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(double retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public long getWordCount() {
        return wordCount;
    }

    public void setWordCount(long wordCount) {
        this.wordCount = wordCount;
    }

    public long getPostCount() {
        return postCount;
    }

    public void setPostCount(long postCount) {
        this.postCount = postCount;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }
}

package com.lrony.mread.data.bean;

import java.util.List;

/**
 * Created by Lrony on 18-4-8.
 */
public class RecommendBooksPackage extends BaseBean {

    private List<BooksBean> books;

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean {
        /**
         * _id : 50a0520aea1ebb6f5b00018e
         * title : 赘婿
         * author : 愤怒的香蕉
         * site : zhuishuvip
         * cover : /agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F42275%2F_42275_510325.jpg%2F
         * shortIntro : 一个受够了勾心斗角、生死打拼的金融界巨头回到了古代，进入一商贾之家最没地位的赘婿身体后的休闲故事。家国天下事，本已不欲去碰的他，却又如何能避得过了。
         “有人曾站在金字塔高点
         最廉价数不清妒忌与羡艳
         走过了这段万人簇拥路
         逃不过墓碑下那孤独的长眠”——finale《命悬一线》
         PS：赘（zhui第四声）婿，入赘累赘，非（ao第二声）婿。
         PS2：本文属TVB休闲剧，而非央视正剧，一切看起来与历史有涉之处，都请不要当真。
         * lastChapter : 第902章 煮海（四）
         * retentionRatio : 46.23
         * latelyFollower : 26849
         * majorCate : 历史
         * minorCate : 架空历史
         * allowMonthly : false
         */

        private String _id;
        private String title;
        private String author;
        private String site;
        private String cover;
        private String shortIntro;
        private String lastChapter;
        private double retentionRatio;
        private int latelyFollower;
        private String majorCate;
        private String minorCate;
        private boolean allowMonthly;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getShortIntro() {
            return shortIntro;
        }

        public void setShortIntro(String shortIntro) {
            this.shortIntro = shortIntro;
        }

        public String getLastChapter() {
            return lastChapter;
        }

        public void setLastChapter(String lastChapter) {
            this.lastChapter = lastChapter;
        }

        public double getRetentionRatio() {
            return retentionRatio;
        }

        public void setRetentionRatio(double retentionRatio) {
            this.retentionRatio = retentionRatio;
        }

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
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

        public boolean isAllowMonthly() {
            return allowMonthly;
        }

        public void setAllowMonthly(boolean allowMonthly) {
            this.allowMonthly = allowMonthly;
        }
    }
}

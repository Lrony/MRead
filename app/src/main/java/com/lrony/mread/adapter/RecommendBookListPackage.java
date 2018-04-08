package com.lrony.mread.adapter;

import com.lrony.mread.data.bean.BaseBean;

import java.util.List;

/**
 * Created by Lrony on 18-4-8.
 */
public class RecommendBookListPackage extends BaseBean{

    private List<BooklistsBean> booklists;

    public List<BooklistsBean> getBooklists() {
        return booklists;
    }

    public void setBooklists(List<BooklistsBean> booklists) {
        this.booklists = booklists;
    }

    public static class BooklistsBean {
        /**
         * id : 5454ccfdbd7c68e31be5577e
         * title : 潜力神作！储粮草备书荒
         * author : 赤戟
         * desc : 微信公众号：chijiread，主要是近几年值得一看的新书，非小白，较冷门，有特点，文笔不会太差的作品。量大类广不细言，仅关键词点评。
         书单主会定期添加口碑新书，有什么想法和建议可以到个人公众号留言：chijiread
         * bookCount : 176
         * collectorCount : 112982
         * cover : /agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2214168%2F2214168_e2092fc8e3084102b909f023cf0e8e67.jpg%2F
         */

        private String id;
        private String title;
        private String author;
        private String desc;
        private int bookCount;
        private int collectorCount;
        private String cover;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getCollectorCount() {
            return collectorCount;
        }

        public void setCollectorCount(int collectorCount) {
            this.collectorCount = collectorCount;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}

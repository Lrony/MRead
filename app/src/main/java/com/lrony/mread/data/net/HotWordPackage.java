package com.lrony.mread.data.net;

import java.util.List;

/**
 * Created by Lrony on 18-4-27.
 */
public class HotWordPackage {
    /**
     * hotWords : ["报告总裁，你老婆又跑了","不良宠婚","重生之最强商女","纯情丫头休想逃","殿下太凶猛：王妃，求不跑！","修真四万年","邪王盛宠：逆天狂妃狠火辣","傲娇总裁爆宠妻","邪王霸宠：狂妃妖娆宠上瘾","天才萌宝鬼医娘亲","名门军婚：首长老公夜夜宠！"]
     * newHotWords : [{"word":"报告总裁，你老婆又跑了","book":"59c231a112934c9d55591e6d"},{"word":"不良宠婚","book":"59e5920fa06f53c37c4305f8"},{"word":"重生之最强商女","book":"58378f77d693216707c64dec"},{"word":"纯情丫头休想逃","book":"5968945b5fbdd9d23b440260"},{"word":"殿下太凶猛：王妃，求不跑！","book":"5902e039c7639f295d98ec40"},{"word":"修真四万年","book":"5952843ebd5e3c3341dadaf6"},{"word":"邪王盛宠：逆天狂妃狠火辣","book":"588448227a7e4d367acf03a0"},{"word":"傲娇总裁爆宠妻","book":"59a8e660d6d9255c62abc5a6"},{"word":"邪王霸宠：狂妃妖娆宠上瘾","book":"5a54773d8408f11c495aa85b"},{"word":"天才萌宝鬼医娘亲","book":"582d43e475b75cee63e3dc82"},{"word":"名门军婚：首长老公夜夜宠！","book":"5830654b9af8962070d2449d"}]
     * ok : true
     */

    private boolean ok;
    private List<String> hotWords;
    private List<NewHotWordsBean> newHotWords;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<String> getHotWords() {
        return hotWords;
    }

    public void setHotWords(List<String> hotWords) {
        this.hotWords = hotWords;
    }

    public List<NewHotWordsBean> getNewHotWords() {
        return newHotWords;
    }

    public void setNewHotWords(List<NewHotWordsBean> newHotWords) {
        this.newHotWords = newHotWords;
    }

    public static class NewHotWordsBean {
        /**
         * word : 报告总裁，你老婆又跑了
         * book : 59c231a112934c9d55591e6d
         */

        private String word;
        private String book;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }
    }
}

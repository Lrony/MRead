package com.lrony.mread.data.bean;

import java.util.List;

/**
 * Created by lrony on 2018/4/7.
 */
public class HotWordPackage {
    /**
     * hotWords : ["Boss凶猛：纯情丫头被升级","总裁追妻：夫人往哪儿跑","远大前程（影视同名小说）","三国机密（上）：龙难日","婚宠NO.1：总裁天价宝贝妻","三国机密（下）：潜龙在渊","蜜爱似火：总裁，吃定你！","独宠小萌妻","妃要成仙：霸道妖王求宠爱","恶魔总裁专宠妻","总裁在上","总裁大人的强制爱","傻子王爷：天下第一妾","嫡公主归来：无赖王爷快走开"]
     * newHotWords : [{"word":"Boss凶猛：纯情丫头被升级","book":"583cdd7333d582703b9bc2d8"},{"word":"总裁追妻：夫人往哪儿跑","book":"5a6592f626dfa479c0f464bb"},{"word":"远大前程（影视同名小说）","book":"5a154234d0d11e2341a1c31d"},{"word":"三国机密（上）：龙难日","book":"57bad2f403650d4213a34e05"},{"word":"婚宠NO.1：总裁天价宝贝妻","book":"5a683fbadd6a9379889759b0"},{"word":"三国机密（下）：潜龙在渊","book":"5799bce62095ccb92eb04c26"},{"word":"蜜爱似火：总裁，吃定你！","book":"5a73d5ff3cd1967a55040568"},{"word":"独宠小萌妻","book":"5a7c1e09a397974655d9cf96"},{"word":"妃要成仙：霸道妖王求宠爱","book":"5575bd770f6460457c55d6c6"},{"word":"恶魔总裁专宠妻","book":"5ab1f90de02fb07a94480cee"},{"word":"总裁在上","book":"5a14d50ca384aa1d129fcfa4"},{"word":"总裁大人的强制爱","book":"5a6592f6b96be87a1087b897"},{"word":"傻子王爷：天下第一妾","book":"5462ed3bea37d02a1a4e0a4e"},{"word":"嫡公主归来：无赖王爷快走开","book":"5a5492db329c64187e0df451"}]
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
         * word : Boss凶猛：纯情丫头被升级
         * book : 583cdd7333d582703b9bc2d8
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

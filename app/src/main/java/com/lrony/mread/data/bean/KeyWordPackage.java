package com.lrony.mread.data.bean;

import java.util.List;

/**
 * Created by lrony on 2018/4/7.
 */
public class KeyWordPackage {
    /**
     * keywords : ["执魔","执笔天涯","执掌天劫","执掌轮回","执剑长老","执掌龙宫","执掌乾坤","执笔乱红尘","执灯夜行","执掌无限"]
     * ok : true
     */

    private boolean ok;
    private List<String> keywords;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}

package com.lrony.mread.data.net;

import com.lrony.mread.data.bean.BookSortPackage;
import com.lrony.mread.data.bean.SortBookPackage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lrony on 18-4-24.
 */
public interface BookApi {

    /*******************************API***************************************/
    // 获取分类： http://api.zhuishushenqi.com/cats/lv2/statistics
    // 按分类获取书籍列表： http://api.zhuishushenqi.com/book/by-categories?gender=male&type=hot&major=%E5%A5%87%E5%B9%BB&minor=&start=&limit=50


    /*******************************分类***************************************/
    /**
     * 获取分类
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Call<BookSortPackage> getBookSortPackage();

    /**
     * 按分类获取书籍列表
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     * @return
     */
    @GET("/book/by-categories")
    Call<SortBookPackage> getSortBookPackage(@Query("gender") String gender, @Query("type") String type, @Query("major") String major, @Query("minor") String minor, @Query("start") int start, @Query("limit") int limit);
}

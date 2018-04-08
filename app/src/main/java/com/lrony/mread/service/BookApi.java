package com.lrony.mread.service;

import com.lrony.mread.adapter.RecommendBookListPackage;
import com.lrony.mread.data.bean.BookDetailPackage;
import com.lrony.mread.data.bean.BookSortPackage;
import com.lrony.mread.data.bean.BookSubSortPackage;
import com.lrony.mread.data.bean.HotWordPackage;
import com.lrony.mread.data.bean.KeyWordPackage;
import com.lrony.mread.data.bean.RecommendBooksPackage;
import com.lrony.mread.data.bean.SearchBookPackage;
import com.lrony.mread.data.bean.SortBookPackage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lrony on 2018/4/7.
 */
public interface BookApi {

    /*******************************分类***************************************/
    /**
     * 获取分类
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Call<BookSortPackage> getBookSortPackage();

    /**
     * 获取二级分类
     *
     * @return
     */
    @GET("/cats/lv2")
    Call<BookSubSortPackage> getBookSubSortPackage();

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

    /************************************搜索书籍******************************************************/
    @GET("/book/hot-word")
    Call<HotWordPackage> getHotWordPackage();

    /**
     * 关键字自动补全
     *
     * @param query
     * @return
     */
    @GET("/book/auto-complete")
    Call<KeyWordPackage> getKeyWordPacakge(@Query("query") String query);

    /**
     * 书籍查询
     *
     * @param query:作者名或者书名
     * @return
     */
    @GET("/book/fuzzy-search")
    Call<SearchBookPackage> getSearchBookPackage(@Query("query") String query);

    /*************************书籍详情**********************************/

    /**
     * 书籍推荐书籍
     *
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}/recommend")
    Call<RecommendBooksPackage> getRecommendBooksPackage(@Path("bookId") String bookId);

    /**
     * 书籍推荐书单
     *
     * @param bookId
     * @param limit
     * @return
     */
    @GET("/book-list/{bookId}/recommend")
    Call<RecommendBookListPackage> getRecommendBookListPackage(@Path("bookId") String bookId, @Query("limit") String limit);

    /**
     * 书籍详情
     *
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}")
    Call<BookDetailPackage> getBookDetail(@Path("bookId") String bookId);
}

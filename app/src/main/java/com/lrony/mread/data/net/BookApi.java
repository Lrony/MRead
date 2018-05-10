package com.lrony.mread.data.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lrony on 18-4-24.
 */
public interface BookApi {

    /*******************************API***************************************/
    // 获取分类： http://api.zhuishushenqi.com/cats/lv2/statistics
    // 按分类获取书籍列表： http://api.zhuishushenqi.com/book/by-categories?gender=male&type=hot&major=%E5%A5%87%E5%B9%BB&minor=&start=&limit=50
    // 书籍详情: http://api.zhuishushenqi.com/book/59ba0dbb017336e411085a4e
    // 书籍推荐书籍: http://api.zhuishushenqi.com/book/59ba0dbb017336e411085a4e/recommend
    // 书籍章节: http://api.zhuishushenqi.com/mix-atoc/59ba0dbb017336e411085a4e?view=chapters

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


    /*************************书籍详情**********************************/

    /**
     * 书籍详情
     *
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}")
    Call<BookDetailPackage> getBookDetail(@Path("bookId") String bookId);

    /**
     * 书籍推荐书籍
     *
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}/recommend")
    Call<RecommendBooksPackage> getRecommendBooksPackage(@Path("bookId") String bookId);

    /**
     * 获取书籍的章节总列表
     * @param bookId
     * @param view 默认参数为:chapters
     * @return
     */
    @GET("/mix-atoc/{bookId}")
    Call<BookChapterPackage> getBookChapterPackage(@Path("bookId") String bookId, @Query("view") String view);


    /************************************搜索书籍******************************************************/

    /**
     * 热门搜索
     * @return
     */
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

}

package com.lrony.mread.util;

import android.support.annotation.Nullable;

import com.lrony.mread.data.bean.Book;
import com.lrony.mread.data.db.table.BookTb;

/**
 * Created by Lrony on 18-4-28.
 */
public class DataConvertUtil {

    public static BookTb book2BookTb(Book book, @Nullable BookTb bookTb) {
        if (bookTb == null) {
            bookTb = new BookTb();
        }
        bookTb.setId(book.getId());
        bookTb.setAuthor(book.getAuthor());
        bookTb.setCoverUrl(book.getCover());
        bookTb.setDescribe(book.getShortIntro());
        bookTb.setIsFinished(book.isFinished());
        bookTb.setName(book.getTitle());
        bookTb.setSectionCount(book.getChaptersCount());
        return bookTb;
    }

    public static Book bookTb2Book(BookTb bookTb) {
        Book book = new Book();
        book.setId(bookTb.getId());
        book.setAuthor(bookTb.getAuthor());
        book.setCover(bookTb.getCoverUrl());
        book.setShortIntro(bookTb.getDescribe());
        book.setFinished(bookTb.getIsFinished());
        book.setChaptersCount(bookTb.getSectionCount());
        book.setTitle(bookTb.getName());
        return book;
    }
}

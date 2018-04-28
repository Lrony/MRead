package com.lrony.mread.event;

import com.lrony.mread.data.db.table.BookTb;

/**
 * Created by Lrony on 18-4-28.
 */
public class AddBook2BookcaseEvent {

    public AddBook2BookcaseEvent(BookTb bookTb) {
        this.bookTb = bookTb;
    }

    public BookTb bookTb;
}

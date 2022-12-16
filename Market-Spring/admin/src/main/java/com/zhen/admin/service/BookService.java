package com.zhen.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhen.admin.domain.Book;
import com.zhen.common.domain.AjaxResult;


public interface BookService extends IService<Book> {

    public AjaxResult addBook(Book book);

    public AjaxResult updateBook(Book book);

    public AjaxResult deleteBook(Long id);
}

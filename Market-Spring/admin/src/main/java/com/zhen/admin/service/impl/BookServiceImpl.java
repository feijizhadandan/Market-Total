package com.zhen.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhen.admin.domain.Book;
import com.zhen.admin.mapper.BookMapper;
import com.zhen.admin.service.BookService;
import com.zhen.common.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public AjaxResult addBook(Book book) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getName, book.getName());
        if(bookMapper.selectOne(wrapper) != null) return AjaxResult.error("已存在同名书籍");

        int flag = bookMapper.insert(book);
        if(flag == 0) return AjaxResult.error("添加失败");
        else {
            return AjaxResult.success();
        }
    }

    @Override
    public AjaxResult updateBook(Book book) {
        // 检查是否有同名书本
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getName, book.getName()).ne(Book::getId, book.getId());
        if(bookMapper.selectOne(wrapper) != null) return AjaxResult.error("已存在同名书籍");

        // 获取旧版version
        Long oldVersion = bookMapper.selectById(book).getVersion();
        book.setVersion(oldVersion);

        int flag = bookMapper.updateById(book);
        if(flag == 0) return AjaxResult.error("修改失败");
        else {
            return AjaxResult.success();
        }
    }

    @Override
    public AjaxResult deleteBook(Long id) {
        int i = bookMapper.deleteById(id);
        if(i == 0) return AjaxResult.error("删除失败");
        else {
            return AjaxResult.success();
        }
    }
}

package com.zhen.admin.controller;

import com.zhen.admin.domain.Book;
import com.zhen.admin.service.BookService;
import com.zhen.common.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Slf4j
@Api(tags = "test-book-controller")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation("获取所有图书信息")
    @PreAuthorize("hasAuthority('system:buy')")
    @GetMapping()
    public AjaxResult getAllBook() {
        List<Book> list = bookService.list();
        return AjaxResult.success(list);
    }

    @ApiOperation("增加图书")
    @PostMapping()
    public AjaxResult addBook(@RequestBody Book book) {
        AjaxResult ajaxResult = bookService.addBook(book);
        return ajaxResult;
    }

    @ApiOperation("修改图书信息")
    @PutMapping()
    public AjaxResult updateBook(@RequestBody Book book) {
        AjaxResult ajaxResult = bookService.updateBook(book);
        return ajaxResult;
    }

    @ApiOperation("删除图书信息")
    @DeleteMapping("/{id}")
    public AjaxResult deleteBook(@PathVariable Long id) {
        AjaxResult ajaxResult = bookService.deleteBook(id);
        return ajaxResult;
    }

}

package com.zhen.admin;

import com.zhen.admin.domain.Book;
import com.zhen.admin.service.BookService;
import com.zhen.framework.config.MinioConfig;
import com.zhen.framework.security.domain.User;
import com.zhen.framework.security.mapper.SysMenuMapper;
import com.zhen.framework.security.mapper.UserMapper;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    
    @Autowired
    private MinioClient minioClient;
    
    @Autowired
    private MinioConfig minioConfig;

    @Test
    void contextLoads() {
    }

    @Test
    public void testBCryptPasswordEncoder() {
        Book book = new Book();
        book.setName("月球");
        book.setCount(9);
        book.setType("科幻");
        bookService.addBook(book);
    }

    @Test
    public void testMenuMapper() {
        List<String> strings = sysMenuMapper.selectPermsByUserId(1548959247104294913L);
        System.out.println(strings);
    }
    
    @Test
    public void testMinioUpload() {
        
    }
}

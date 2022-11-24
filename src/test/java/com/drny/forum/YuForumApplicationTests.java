package com.drny.forum;

import com.drny.forum.controller.Result;
import com.drny.forum.pojo.Comment;
import com.drny.forum.service.PostService;
import com.drny.forum.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class YuForumApplicationTests {

    @Autowired
    private PostService postService;

    @Test
    void contextLoads() {

        Result<List<Comment>> comments = postService.getComments(444276747);

        List<Comment> data = comments.getData();

        for (Comment datum : data) {
            System.out.println(datum);
        }

        //String s = JsonUtils.objectToString(data);


    }

    @Test
    public void strTest() {
        String str = "123";
        String str2 = "123";

        String str3 = new String("123");

        boolean b = str3 == str2;
        System.out.println("b = " + b);
    }

}

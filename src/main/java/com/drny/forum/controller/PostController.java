package com.drny.forum.controller;

import com.drny.forum.pojo.Comment;
import com.drny.forum.pojo.Post;
import com.drny.forum.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Resource
    private PostService postService;
    @PostMapping
    public Result<Void> addPost(Post post, HttpServletRequest request) throws IOException {
        return postService.addPost(post, request);
    }

    @GetMapping("/{page}")
    public Result<List<Post>> getPosts(@PathVariable int page) {
        return postService.getPosts(page);
    }

    @GetMapping("/comment/{id}")
    public Result<List<Comment>> getComments(@PathVariable int id) {
        return postService.getComments(id);
    }

}

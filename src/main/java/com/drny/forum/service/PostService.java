package com.drny.forum.service;

import com.drny.forum.controller.Result;
import com.drny.forum.pojo.Comment;
import com.drny.forum.pojo.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostService  {

    Result<Void> addPost(Post post, HttpServletRequest request);

    Result<List<Post>> getPosts(int page);

    Result<List<Comment>> getComments(int id);

}

package com.drny.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drny.forum.controller.Result;
import com.drny.forum.mapper.PostMapper;
import com.drny.forum.pojo.Comment;
import com.drny.forum.pojo.Post;
import com.drny.forum.pojo.User;
import com.drny.forum.service.PostService;
import com.drny.forum.util.CookieUtil;
import com.drny.forum.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public Result<Void> addPost(Post post, HttpServletRequest request) {

        User user = CookieUtil.getUser(request);

        if (user == null) {
            return ResponseUtil.error("用户未登录");
        }

        String content = post.getContent();
        if (content.trim().isEmpty()) {
            return ResponseUtil.error("输入内容不能为空");
        }

        Integer userId = user.getId();
        post.setUid(userId);
        System.out.println("post: " + post);

        try {
            int row = postMapper.insert(post);
            if (row != 1) {
                return ResponseUtil.error("出现错误");
            }
        } catch (Exception e) {
            return ResponseUtil.error("出错了");
        }

        return ResponseUtil.success("发帖成功了,请下拉刷新查看");
    }

    @Override
    public Result<List<Post>> getPosts(int page) {

        if (page < 1) {
            return ResponseUtil.errorBy("页数错误");
        }

        int current = (page - 1) * 10;

        List<Post> posts = postMapper.getPosts(current);
        //System.out.println(posts);

        return ResponseUtil.success(posts);
    }

    @Override
    public Result<List<Comment>> getComments(int id) {
        return ResponseUtil.success(postMapper.getComment(id));
    }
}

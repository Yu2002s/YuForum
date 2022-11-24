package com.drny.forum.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.drny.forum.controller.handler.ListToTextTypeHandler;
import lombok.Data;

import java.util.List;

/**
* 评论表
*/
@Data
public class Comment {

    /**
    * 评论ID
    */
    private Integer id;
    /**
    * 用户ID
    */
    private Integer uid;
    private Integer pid;
    /**
    * 评论内容
    */
    private String content;
    /**
    * 评论图片
    */
    @TableField(typeHandler = ListToTextTypeHandler.class)
    private List<String> images;
    /**
    * 回复对象ID
    */
    private Integer cid;
    /**
    * 发布时间
    */
    @TableField("createAt")
    private String createAt;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private List<Comment> comments;

}

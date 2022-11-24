package com.drny.forum.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.drny.forum.controller.handler.ListToTextTypeHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 帖子表
 *
 * @TableName post
 */
@Data
@TableName(autoResultMap = true)
public class Post {

    /**
     * 帖子唯一ID
     */
    @TableId(type = IdType.AUTO)
    private Integer pid;
    /**
     * 发帖用户ID
     */
    private Integer uid;
    /**
     * 用户信息
     */
    @TableField(exist = false)
    private User user;
    /**
     * 发帖内容
     */
    private String content;
    /**
     * 上传的图片
     */
    @TableField(typeHandler = ListToTextTypeHandler.class, jdbcType = JdbcType.VARCHAR)
    private List<String> images;
    /**
     * 构建时间
     */
    @TableField(value = "createAt", fill = FieldFill.INSERT)
    private Long createAt;

}

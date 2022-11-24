package com.drny.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drny.forum.pojo.Comment;
import com.drny.forum.pojo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    List<Post> getPosts(int current);

    List<Comment> getComment(@Param("id") int id);

}

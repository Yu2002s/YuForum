package com.drny.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drny.forum.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

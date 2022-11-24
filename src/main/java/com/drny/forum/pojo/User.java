package com.drny.forum.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String icon;
    private String username;
    private String password;
    private String email;
    @TableField(value = "createAt", fill = FieldFill.INSERT)
    private Long createAt;

   /* @Version
    private Integer version;

    @TableLogic(value = "0", delval = "1")
    private Integer deleted;*/

}

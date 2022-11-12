package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 文件实体类
 * 和数据库相互对应
 */
@Data
@TableName("sys_file")
public class Files {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String type;

    private Long size;

    private String url;


    private Boolean isDelete;

    private Boolean enable;

    private String md5;
}

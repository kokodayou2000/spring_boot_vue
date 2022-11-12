package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import com.example.demo.common.Result;
import com.example.demo.mapper.FileMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @GetMapping("/example")
    public Result get(){

        Map<String,Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));

        return Result.success(map);
    }

    /**
     * 返回每个季度创建用户的人数
     * @return
     */

    @Resource
    private FileMapper fileMapper;

    @GetMapping("/members")
    public Result members(){
        int one = fileMapper.getOne();
        int two = fileMapper.getTwo();
        int three = fileMapper.getThree();
        int four = fileMapper.getFour();
        Map<String,Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("第一季度", "第二季度", "第三季度", "第四季度"));
        map.put("y",CollUtil.newArrayList(one,two,three,four));

        return Result.success(map);
    }

}

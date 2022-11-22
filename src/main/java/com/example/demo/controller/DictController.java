package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.IDictService;
import com.example.demo.entity.Dict;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author deng
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private IDictService dictService;
    @GetMapping("/getIcons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        List<Dict> list = dictService.list(queryWrapper);
        System.out.println(list);
        return Result.success(list);
    }
}

package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.IMenuService;
import com.example.demo.entity.Menu;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author deng
 * @since 2022-11-12
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @PostMapping
    public Boolean save(@RequestBody Menu menu) {
        return menuService.saveOrUpdate(menu);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return menuService.removeById(id);
    }


    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return menuService.removeByIds(ids);
    }

    @GetMapping
    public List<Menu> findAll() {
        return menuService.list();
    }

    @GetMapping("/{id}")
    public Menu findOne(@PathVariable Integer id) {
        return menuService.getById(id);
    }

    @GetMapping("/page")
    public Page<Menu> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String name
    ) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");

        return menuService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }
}

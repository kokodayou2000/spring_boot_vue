package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /*
    @GetMapping
    public Map<Integer,List<Menu>> findAll() {
        List<Menu> list = menuService.list();
        //一级菜单
        List<Menu> first_level = list.stream().filter(menu -> (menu.getPid() == 0)).collect(Collectors.toList());
        Map<Integer,List<Menu>> map = new HashMap<>();
        map.put(0,first_level);
        for (Menu menu : first_level) {
            //对子菜单进行归并 getId是一定存在的所以使用,getId放到equals前面
            List<Menu> menus = list.stream().filter(o -> menu.getId().equals(o.getPid())).collect(Collectors.toList());
            map.put(menu.getId(),menus);
        }
        System.out.println(map);
        return map;
    }
     */
    @GetMapping("/findAll")
    public Result findAll(@RequestParam(defaultValue = "") String name){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name",name);
        List<Menu> list = menuService.list(queryWrapper);

        List<Menu> parentNode = list.stream().filter(o->o.getPid().equals(0)).collect(Collectors.toList());
        for (Menu menu : parentNode) {
            menu.setChildren(list.stream().filter(o->menu.getId().equals(o.getPid())).collect(Collectors.toList()));
        }

        return Result.success(parentNode);
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

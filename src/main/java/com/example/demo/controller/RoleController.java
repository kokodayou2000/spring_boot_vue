package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.service.impl.RoleServiceImpl;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.IRoleService;
import com.example.demo.entity.Role;


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
@RequestMapping("/role")
public class RoleController {


    @Resource
    private IRoleService roleService;

    @PostMapping
    public Boolean save(@RequestBody Role role) {
        return roleService.saveOrUpdate(role);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
    }


    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return roleService.removeByIds(ids);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.list();
    }

    @GetMapping("/{id}")
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @GetMapping("/page")
    public Page<Role> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String name

    ) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name",name);

        queryWrapper.orderByDesc("id");

        return roleService.page(new Page<>(pageNum, pageSize),queryWrapper);

    }
}

package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.IRoleMenuService;
import com.example.demo.entity.RoleMenu;


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
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Resource
    private IRoleMenuService roleMenuService;

    @PostMapping("/{roleId}")
    public Result save(@PathVariable Integer roleId, @RequestBody List<Integer> ids ) {
        roleMenuService.setRoleMenu(roleId,ids);
        return Result.success();
    }

    @GetMapping("/getIds/{roleId}")
    public Result getIds(@PathVariable Integer roleId){

        List<Integer> menus = roleMenuService.getRoleMenu(roleId);

        return Result.success(menus);
    }



}




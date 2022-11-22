package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.RoleMenu;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author deng
 * @since 2022-11-13
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> ids) {
//        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role_Id",roleId);
//        //删除旧的
//        roleMenuMapper.delete(queryWrapper);
        roleMenuMapper.deleteByRoleId(roleId);

        //添加新的
        for (Integer menuId : ids) {
            RoleMenu menu = new RoleMenu(roleId,menuId);
            roleMenuMapper.insert(menu);
        }

    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}

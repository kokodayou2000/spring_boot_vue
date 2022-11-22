package com.example.demo.service;

import com.example.demo.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author deng
 * @since 2022-11-13
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 删除当前roleId已经绑定的id
     * 然后在重新根据新的ids列表来创建新表
     * @param roleId 角色id
     * @param ids 角色id对应的菜单ids
     */

    void setRoleMenu(Integer roleId, List<Integer> ids);

    List<Integer> getRoleMenu(Integer roleId);
}

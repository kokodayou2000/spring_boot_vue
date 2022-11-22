package com.example.demo.mapper;

import com.example.demo.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author deng
 * @since 2022-11-13
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {


    int deleteByRoleId(@Param("roleId") Integer roleId);

    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}

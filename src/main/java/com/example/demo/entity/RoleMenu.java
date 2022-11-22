package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author deng
 * @since 2022-11-13
 */
@Getter
@Setter
@AllArgsConstructor
@TableName("sys_role_menu")
@ApiModel(value = "RoleMenu对象", description = "")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("角色id")
        private Integer roleId;

      @ApiModelProperty("菜单id")
      private Integer menuId;


}

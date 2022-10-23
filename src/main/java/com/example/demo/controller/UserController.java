package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//RestController表示只返回数据
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/findAll")
    public List<User> getAll(){
        System.out.println("heee");
        return userService.findAll();
    }

    @PostMapping()
    public Integer save(@RequestBody User user){
        //@RequestBody 表示前台传入json对象的时候，将json对象转换为user对象
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userService.deleteById(id);
    }

    @GetMapping("/page")
    //@RequestParam 就是将 :8081/user/page?pageNum=1&pageSize=10后面的数据读取出来
    // limit(args1,args2)  args1 = (pageNum - 1) * pageSize ; args2 = pageSize
    public Map<String, Object> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        pageNum = (pageNum - 1)*pageSize;
        Integer total = userService.selectTotal();
        List<User> data = userService.selectPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }


    @GetMapping("/pageByUsername")
    //@RequestParam 就是将 :8081/user/page?pageNum=1&pageSize=10后面的数据读取出来
    // limit(args1,args2)  args1 = (pageNum - 1) * pageSize ; args2 = pageSize
    public Map<String, Object> findPageByUsername(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String username){
        pageNum = (pageNum - 1)*pageSize;
        Integer total = userService.selectTotalByName(username);
        List<User> data = userService.selectPageByUserName(pageNum, pageSize,username);
        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }

    @GetMapping("/pageByEmail")
    //@RequestParam 就是将 :8081/user/page?pageNum=1&pageSize=10后面的数据读取出来
    // limit(args1,args2)  args1 = (pageNum - 1) * pageSize ; args2 = pageSize
    public Map<String, Object> findPageByEmail(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String email){
        pageNum = (pageNum - 1)*pageSize;
        Integer total = userService.selectTotalByEmail(email);
        List<User> data = userService.selectPageByEmail(pageNum, pageSize,email);
        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }

    @GetMapping("/searchByUsernameAndEmail")
    //@RequestParam 就是将 :8081/user/page?pageNum=1&pageSize=10后面的数据读取出来
    // limit(args1,args2)  args1 = (pageNum - 1) * pageSize ; args2 = pageSize
    public Map<String, Object> findPageByUsernameAndEmail(@RequestParam Integer pageNum,
                                               @RequestParam Integer pageSize,
                                               @RequestParam String username,
                                               @RequestParam String email){
        pageNum = (pageNum - 1)*pageSize;
        Integer total = userService.selectTotalByNameAndEmail(username,email);
        List<User> data = userService.searchPageByUsernameAndEmail(pageNum, pageSize,username,email);
        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }


}

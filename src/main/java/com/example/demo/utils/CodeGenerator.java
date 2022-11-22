package com.example.demo.utils;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

//代码生成器
public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }
    private static void generator(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/vue?useUnicode=true", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("deng") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\BaiduNetdiskWorkspace\\springBoot_vue\\spring_boot\\demo\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.demo") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "D:\\BaiduNetdiskWorkspace\\springBoot_vue\\spring_boot\\demo\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok(); //使用lombok 放弃 getter setter
//                    builder.mapperBuilder().enableMapperAnnotation().build(); //添加mapper注解，但是我们的MybatisPlushConfig上添加了 @MapperScan(path)就不需要添加了
                    builder.addInclude("sys_role_menu") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                    builder.controllerBuilder().enableHyphenStyle()  //将 sys_user 转化成为 sysUser
                            .enableRestStyle(); //将Controller转换为 RestController

                    //如果不设置，sys_ 生成的类就会变成 Sys_User 而不是 User
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();



    }
}

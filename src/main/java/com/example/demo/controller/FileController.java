package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Files;
import com.example.demo.entity.User;
import com.example.demo.mapper.FileMapper;
import com.example.demo.utils.TokenUtils;
import io.swagger.models.auth.In;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件处理相关接口
 */
@RequestMapping("/file")
@RestController
public class FileController {

    private final Log log = Log.get();

    @Resource
    private FileMapper fileMapper;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    /**
     * 文件上传接口
     * @param file 前端传入的文件
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String extName = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //将文件存储到磁盘中
        File uploadParentFile = new File(fileUploadPath);
        if (!uploadParentFile.exists()){
            //如果不存在该目录，就创建
            boolean mkdirs = uploadParentFile.mkdirs();
            if (mkdirs){
                log.info("创建文件成功");
            }else{
                log.error("创建文件失败");
            }
        }

        //创建一个文件唯一的标识位
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid+ StrUtil.DOT +extName;
        File uploadFile = new File(fileUploadPath+fileUuid);


        String url;
        String md5;

        //由于获取md5必须要上传文件
        //上传文件是必须的

        //文件上传
        file.transferTo(uploadFile);
        //得到md5
        md5 = SecureUtil.md5(uploadFile);
        System.out.println("文件获取md5"+md5);
        Files f = getFilesByMd5(md5);
        System.out.println("通过md5查询文件"+f);
        if (null == f){
            //不存在该文件
            //将文件上传
            System.out.println("uploadFile"+uploadFile);

//            file.transferTo(uploadFile);
//            FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);


            url = "http://localhost:8081/file/"+fileUuid;

        }else{
            //存在相同的md5，之前上传过该文件
            //获取url
            url = f.getUrl();
            //删除该文件
            uploadFile.delete();
        }

        //创建和数据库对应的对象
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(extName);
        saveFile.setSize(size/2014);
        saveFile.setMd5(md5);
        saveFile.setUrl(url);
        //将该对象插入到数据库中
        fileMapper.insert(saveFile);

        return url;


    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath+fileUUID);

        ServletOutputStream os = response.getOutputStream();
        //写出格式
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");

        //使用hutool工具类来读取文件
        os.write(FileUtil.readBytes(uploadFile));

        os.flush();
        os.close();
    }

    /**
     * 判断数据库中是否已经存在相同的md5
     * @param md5
     * @return
     */
    private Files getFilesByMd5(String md5){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ?  null : filesList.get(0);
    }


    //更新一行字段
    @PostMapping("/update")
    public Boolean update(@RequestBody Files file) {
        fileMapper.updateById(file);
        return true;
    }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);

        Files files = fileMapper.selectOne(queryWrapper);
        files.setIsDelete(true);
        fileMapper.updateById(files);
        return true;

    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        // select *from file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);

        List<Files> filesList = fileMapper.selectList(queryWrapper);
        for (Files files : filesList) {
            files.setIsDelete(true);
            fileMapper.updateById(files);
        }
        return true;

    }




    @GetMapping("/page")
    public Page<Files> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String name)
                               {
        //设置page的页码和当前页
        //这个Page是IPage Page的接口，输出的结果需要进行类型转换
        IPage<Files> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        //查询未删除的记录
        queryWrapper.eq("is_delete",false);

        //倒序排列
        queryWrapper.orderByDesc("id");
        //SELECT id,username,password,nickname,email,phone,address FROM sys_user WHERE (username LIKE ? AND (email LIKE ?)) LIMIT ?
        //username like #{username} and email like #{email}

        //进行非空校验
        if (!("".equals(name))){
            queryWrapper.like("name",name);
        }


        return (Page<Files>)fileMapper.selectPage(page, queryWrapper);
    }
}

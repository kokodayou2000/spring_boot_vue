package com.example.demo;

import com.example.demo.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class Main {

    @Resource
    FileMapper fileMapper;



}

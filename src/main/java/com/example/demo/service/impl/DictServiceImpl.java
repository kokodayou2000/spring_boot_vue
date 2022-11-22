package com.example.demo.service.impl;

import com.example.demo.entity.Dict;
import com.example.demo.mapper.DictMapper;
import com.example.demo.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author deng
 * @since 2022-11-13
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}

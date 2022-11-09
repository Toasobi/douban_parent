package com.douban.celebritesservice.service.impl;

import com.douban.celebritesservice.entity.Celebrities;
import com.douban.celebritesservice.mapper.CelebritiesMapper;
import com.douban.celebritesservice.service.CelebritiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Toasobi
 * @since 2022-11-06
 */
@Service
public class CelebritiesServiceImpl extends ServiceImpl<CelebritiesMapper, Celebrities> implements CelebritiesService {

}

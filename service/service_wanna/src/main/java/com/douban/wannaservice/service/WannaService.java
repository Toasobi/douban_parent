package com.douban.wannaservice.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.douban.commonutils.Result;
import com.douban.wannaservice.entity.Wanna;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Toasobi
 * @since 2022-11-06
 */
public interface WannaService extends IService<Wanna> {

    Result addWanna(HttpServletRequest request, String film_identity);

    Result addDWanna(HttpServletRequest request, String film_identity);
}

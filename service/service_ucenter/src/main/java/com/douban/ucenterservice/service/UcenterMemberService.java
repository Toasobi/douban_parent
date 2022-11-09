package com.douban.ucenterservice.service;

import com.douban.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.douban.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Toasobi
 * @since 2022-10-29
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录的方法
    String login(UcenterMember member) throws Exception;

    void register(RegisterVo registerVo) throws Exception;

    UcenterMember getOpenIdMember(String open_id);
}

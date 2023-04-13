package com.bcheng.chat.config.satoken;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.dmpp.bbd.entities.po.TblMenu;
import com.dmpp.bbd.mapper.TblMenuMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private TblMenuMapper menuMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get(SaConst.PERMISSIONS__ID,()->{
            List<String> roleList = getRoleList(loginId, loginType);

            List<TblMenu> menus = new ArrayList<>();
            roleList.forEach(roleId-> menus.addAll(menuMapper.selectByRoleId(Integer.parseInt(roleId))));
            return menus.stream().map(TblMenu::getIdentifier).collect(Collectors.toList());
        });
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        //登录接口存储
        return Collections.singletonList(String.valueOf(session.get(SaConst.ROLE__ID)));
    }
}

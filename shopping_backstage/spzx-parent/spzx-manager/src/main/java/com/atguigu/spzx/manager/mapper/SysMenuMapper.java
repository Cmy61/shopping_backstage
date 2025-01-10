package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/9 12:06
 * @version: 1.0
 */
@Mapper
public interface SysMenuMapper {
    public List<SysMenu> findAll() ;
    //添加
    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void delete(Long id);

    int selectCountById(Long id);


    List<SysMenu> selectListByUserId(Long userId);
}

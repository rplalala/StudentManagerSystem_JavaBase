package com.ding.dao;

import com.ding.entity.AdminEntity;

import java.util.List;
import java.util.Map;

//管理员接口，定义功能
public interface AdminDao {
    boolean isLogin(AdminEntity admin);

    List<Map<String, String>> queryAdmin();

    int countAdmin();

    boolean updateAdmin(AdminEntity admin, String userName);

    boolean deleteAdmin(String userName);

    boolean addAdmin(AdminEntity admin);
}

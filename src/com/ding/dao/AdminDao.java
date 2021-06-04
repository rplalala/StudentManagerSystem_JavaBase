package com.ding.dao;

import com.ding.entity.AdminEntity;

//管理员接口，定义功能
public interface AdminDao {
	boolean isLogin(AdminEntity admin);

}

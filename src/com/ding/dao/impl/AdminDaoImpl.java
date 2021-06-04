package com.ding.dao.impl;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.ding.dao.AdminDao;
import com.ding.entity.AdminEntity;
import com.ding.utils.XMLUtils;

//实现管理员接口，实现功能
public class AdminDaoImpl implements AdminDao{
	
	XMLUtils xmlUtils = new XMLUtils("src/Admins.xml");

	@Override 
	/**
	 * 
	 * 判断输入的管理员信息 是否在xml文件中
	 * */
	public boolean isLogin(AdminEntity admin) {
		boolean judge = false;//判断是否存在输入的管理员账号
		Document document = xmlUtils.xmlParse();
		List<Map<String,String>> list = xmlUtils.xmlRead(document);//读取xml文件的数据,返回数据集合
		for(Map<String,String> map : list) {
			if(map.get("userName").equals(admin.getUserName()) && 
					map.get("userPassword").equals(admin.getUserPassword())) {
				judge = true;
				break;
			}
		}
		return judge;
	}
	
	/**
	 * 
	 * 得到存在xml文件中的管理员信息
	 * */
	public List<Map<String,String>> queryAdmin(){
		Document document = xmlUtils.xmlParse();
		return xmlUtils.xmlRead(document);//读取xml文件的数据,返回数据集合
	}
	
	/**
	 * 
	 * 得到存在xml文件中的管理员个数
	 * */
	public int countAdmin() {
		List<Map<String, String>> list = queryAdmin();
		return list.size();
	}
	
	/**
	 * 修改xml文件中的管理员数据
	 * @param admin：修改后的管理员信息
	 * @param userName：需要进行修改的管理员用户名
	 * @return 是否修改成功
	 */
	public boolean updateAdmin(AdminEntity admin,String userName) {
		Document document = xmlUtils.xmlParse();
		String[] values = {admin.getUserName(),admin.getUserPassword()};
		return xmlUtils.xmlUpdate(document, values, userName);
	}
	
	/**
	 * 
	 * @param userName：需要删除的 管理员的用户名
	 * @return 是否删除成功
	 */
	public boolean deleteAdmin(String userName) {
		Document document = xmlUtils.xmlParse();
		return xmlUtils.xmlDelete(document, userName);
	}
	
	public boolean addAdmin(AdminEntity admin) {
		Document document = xmlUtils.xmlParse();
		String[] values = {admin.getUserName(),admin.getUserPassword()};
		return xmlUtils.xmlAdd(document, values);
	}
}

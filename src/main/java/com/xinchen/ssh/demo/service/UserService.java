package com.xinchen.ssh.demo.service;

import java.util.List;

import com.xinchen.ssh.demo.entity.AcctUser;

/**
 * @Description:
 * @author xinchen
 * @date 2016年10月23日 下午8:14:22
 * @version V1.0
 */
public interface UserService {
	AcctUser load(String id);

	AcctUser get(String id);

	List<AcctUser> findAll();

	void persist(AcctUser entity);

	String save(AcctUser entity);

	void saveOrUpdate(AcctUser entity);

	void delete(String id);

	void flush();
}

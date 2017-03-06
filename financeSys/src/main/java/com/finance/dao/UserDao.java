package com.finance.dao;

import org.springframework.stereotype.Repository;

import com.finance.entity.User;

@Repository
public interface UserDao {

	/**
	 * 添加用户
	 *
	 * @author Liugang
	 * @time 2017-2-22
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 根据用户名查询用户
	 *
	 * @author Liugang
	 * @time 2017-2-22
	 * @param user
	 * @return
	 */
	public User findUserByUsername(User user);
	
	/**
	 * 根据用户ID查询用户
	 *
	 * @author Liugang
	 * @time 2017-2-22
	 * @param user
	 * @return
	 */
	public User findUserById(User user);
}

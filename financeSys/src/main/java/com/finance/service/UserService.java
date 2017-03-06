package com.finance.service;

import com.finance.entity.User;

public interface UserService {
	
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
	public User getUserByUsername(User user);
	
	/**
	 * 根据用户ID查询用户
	 *
	 * @author Liugang
	 * @time 2017-2-22
	 * @param user
	 * @return
	 */
	public User getUserById(User user);
	
}

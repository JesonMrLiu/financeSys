package com.finance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.UserDao;
import com.finance.entity.User;
import com.finance.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	
	@Override
	public void addUser(User user) {
		dao.addUser(user);
	}

	@Override
	public User getUserByUsername(User user) {
		return dao.findUserByUsername(user);
	}

	@Override
	public User getUserById(User user) {
		return dao.findUserById(user);
	}

}

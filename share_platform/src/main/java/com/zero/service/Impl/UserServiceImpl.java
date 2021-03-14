package com.zero.service.Impl;


import com.zero.dao.UserDao;
import com.zero.domain.User;
import com.zero.service.UserService;
import com.zero.utiles.MD5Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户业务层接口实现类
 * @Author: ONESTAR
 * @Date: Created in 11:04 2020/3/26
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utiles.code(password));
        return user;
    }
}
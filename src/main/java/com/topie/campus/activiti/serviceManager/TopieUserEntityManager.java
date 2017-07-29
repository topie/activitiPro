package com.topie.campus.activiti.serviceManager;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.topie.campus.security.dao.UserMapper;
import com.topie.campus.security.model.User;

public class TopieUserEntityManager extends UserEntityManager{

	@Autowired
    private UserMapper userMapper;//自己实现的获取用户数据的Service

    @Override
    public UserEntity findUserById(final String userCode) {
        if (userCode == null)
            return null;

        try {
            UserEntity userEntity = null;
           User user = userMapper.selectByPrimaryKey(Integer.valueOf(userCode));
            userEntity = toUserEntity(user);
            return userEntity;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
   
   @Override
   public List<Group> findGroupsByUser(final String userCode) {
       return null;
   }

   /*@Override
   public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
       throw new RuntimeException("not implement method.");
   }*/

   @Override
   public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId,
                                                        String key) {
       throw new RuntimeException("not implement method.");
   }

   @Override
   public List<String> findUserInfoKeysByUserIdAndType(String userId,
                                                       String type) {
       throw new RuntimeException("not implement method.");
   }

   @Override
   public long findUserCountByQueryCriteria(UserQueryImpl query) {
       throw new RuntimeException("not implement method.");
   }


    private UserEntity toUserEntity(User user)
    {
    	UserEntity ue = new UserEntity();
    	ue.setEmail(user.getEmail());
    	ue.setId(user.getId().toString());
    	ue.setFirstName(user.getDisplayName());
    	return ue;
    }
	
}

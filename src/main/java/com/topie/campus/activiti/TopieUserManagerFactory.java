package com.topie.campus.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.topie.campus.activiti.serviceManager.TopieUserEntityManager;

public class TopieUserManagerFactory implements SessionFactory{

	    private UserEntityManager userEntityManager;

	    @Autowired
	    public void setUserEntityManager(UserEntityManager userEntityManager) {
	        this.userEntityManager = userEntityManager;
	    }

	    public Class<?> getSessionType() {
	        // 返回原始的UserManager类型
	        return UserEntityManager.class;
	    }
	    public Session openSession() {
	        // 返回自定义的UserManager实例
	        return userEntityManager;
	    }
}

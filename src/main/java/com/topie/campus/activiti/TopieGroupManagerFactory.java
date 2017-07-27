package com.topie.campus.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.topie.campus.activiti.serviceManage.TopieGroupEntityManager;

public class TopieGroupManagerFactory implements SessionFactory
{

private GroupEntityManager groupEntityManager;

@Autowired
public void setGroupEntityManager(GroupEntityManager groupEntityManager) {
    this.groupEntityManager = groupEntityManager;
}

public Class<?> getSessionType() {
    // 返回原始的GroupEntityManager类型
    return GroupEntityManager.class;
}

@Override
public Session openSession() {
	// TODO Auto-generated method stub
	return groupEntityManager;
}

}
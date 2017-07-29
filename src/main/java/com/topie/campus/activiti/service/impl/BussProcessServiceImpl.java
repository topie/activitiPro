package com.topie.campus.activiti.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.campus.activiti.dao.BussProcessMapper;
import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.activiti.service.BussProcessService;
import com.topie.campus.basedao.service.impl.BaseService;
import com.topie.campus.security.model.Role;
import com.topie.campus.security.model.User;

@Service
public class BussProcessServiceImpl extends BaseService<BussProcess> implements BussProcessService{

@Inject
BussProcessMapper bussMapper;

public PageInfo<BussProcess> findByPage(int pageNum, int pageSize, BussProcess bussProcess) {
	// TODO Auto-generated method stub
	 PageHelper.startPage(pageNum, pageSize);
	List<BussProcess> list =  bussMapper.selectAll();
	PageInfo<BussProcess> page = new PageInfo<BussProcess>(list);
	return page;
}
}

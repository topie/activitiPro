package com.topie.campus.activiti.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.campus.activiti.dao.BpmNodeMapper;
import com.topie.campus.activiti.model.BpmNode;
import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.activiti.service.BpmNodeService;
import com.topie.campus.basedao.service.impl.BaseService;

@Service
public class BpmNodeServiceImpl extends BaseService<BpmNode> implements BpmNodeService{

	
	@Inject
	BpmNodeMapper bpmNodeMapper;
	
	@Override
	public PageInfo<BpmNode> findByPage(int pageNum, int pageSize,
			BpmNode bpmNode) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<BpmNode> nodes = bpmNodeMapper.findByBpmNode(bpmNode);
		PageInfo<BpmNode> pageInfo = new PageInfo<BpmNode>(nodes);
		return pageInfo;
	}

}

package com.topie.campus.activiti.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.topie.campus.activiti.dao.BpmConfNodeMapper;
import com.topie.campus.activiti.model.BpmConfNode;
import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.activiti.service.BpmConfNodeService;
import com.topie.campus.basedao.service.impl.BaseService;

@Service
public class BpmConfNodeServiceImpl  extends BaseService<BpmConfNode> implements BpmConfNodeService {

	@Inject
	BpmConfNodeMapper bpmConfNodeMapper;
	
	@Override
	public BpmConfNode selectByUnionKey(String processId, String flowElementId,
			String userId) {
		// TODO Auto-generated method stub
		return bpmConfNodeMapper.selectByUnionKey(processId,flowElementId,userId);
	}


}

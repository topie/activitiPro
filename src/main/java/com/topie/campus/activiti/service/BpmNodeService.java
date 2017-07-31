package com.topie.campus.activiti.service;

import com.github.pagehelper.PageInfo;
import com.topie.campus.activiti.model.BpmNode;
import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.basedao.service.IService;

public interface BpmNodeService extends IService<BpmNode>{

	PageInfo<BpmNode> findByPage(int pageNum, int pageSize, BpmNode bpmNode);

}

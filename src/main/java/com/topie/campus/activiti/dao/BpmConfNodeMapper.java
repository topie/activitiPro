package com.topie.campus.activiti.dao;

import com.topie.campus.activiti.model.BpmConfNode;

import tk.mybatis.mapper.common.Mapper;

public interface BpmConfNodeMapper extends Mapper<BpmConfNode> {

	BpmConfNode selectByUnionKey(String processId, String flowElementId, String userId);
}
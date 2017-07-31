package com.topie.campus.activiti.dao;

import java.util.List;

import com.topie.campus.activiti.model.BpmNode;

import tk.mybatis.mapper.common.Mapper;

public interface BpmNodeMapper extends Mapper<BpmNode> {

	List<BpmNode> findByBpmNode(BpmNode bpmNode);
}
package com.topie.campus.activiti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.core.model.Attachment;

public interface BussProcessMapper extends Mapper<BussProcess>{

	List<BussProcess> findByPage(@Param("pageOffset") int pageOffset, @Param("pageSize") int pageSize, BussProcess bussProcess);
}
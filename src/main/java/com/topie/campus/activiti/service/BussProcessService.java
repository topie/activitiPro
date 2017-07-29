package com.topie.campus.activiti.service;

import com.github.pagehelper.PageInfo;
import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.basedao.service.IService;

public interface BussProcessService extends IService<BussProcess>{

	PageInfo<BussProcess> findByPage(int pageNum, int pageSize,
			BussProcess bussProcess);


}

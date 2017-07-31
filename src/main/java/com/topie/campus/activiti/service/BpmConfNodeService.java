package com.topie.campus.activiti.service;

import com.topie.campus.activiti.model.BpmConfNode;
import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.basedao.service.IService;

public interface BpmConfNodeService  extends IService<BpmConfNode>{

	BpmConfNode selectByUnionKey(String processId, String flowElementId, String userId);


}

package com.topie.campus.activiti.api;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.topie.campus.activiti.model.BpmConfNode;
import com.topie.campus.activiti.model.BpmNode;
import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.activiti.service.BpmConfNodeService;
import com.topie.campus.activiti.service.BpmNodeService;
import com.topie.campus.common.utils.PageConvertUtil;
import com.topie.campus.common.utils.ResponseUtil;
import com.topie.campus.common.utils.Result;
import com.topie.campus.security.model.User;
import com.topie.campus.security.service.UserService;

@Controller
@RequestMapping("/bpmNode")
public class BpmConfNodeController {

	@Autowired
	private RepositoryService repositoryService;
	
	@Inject
	BpmConfNodeService bpmConfNodeService;
	
	@Inject
	UserService userService;
	
	@Inject
	TaskService taskService;
	
	@Inject
	BpmNodeService bpmNodeService;
	
	@RequestMapping("/node-conf")
	@ResponseBody
	public Result list(String flowElementId,String processId,String userIds,int type)
	{
		byte[] bytes = repositoryService.getModelEditorSource(processId);
    	
    	JsonNode modelNode = null;
		try {
			modelNode = (JsonNode) new ObjectMapper().readTree(bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        
    	FlowElement f = model.getMainProcess().getFlowElement(flowElementId);
	    	if(f instanceof UserTask)
	    	{
	    		UserTask task = (UserTask)f;
	    		String userIdArray[] = userIds.split(",");
                if(type==1)  
                {
                task.setAssignee(userIdArray[0]);
                bpmConfNodeService.insert(convertFromFlowElement(f, processId, userIdArray[0], type));
                }
                else if(type==2)
                {
                	 List<String> caUsers =  task.getCandidateUsers();
                     caUsers.addAll(Arrays.asList(userIdArray));
                     task.setCandidateUsers(caUsers);
	                for(String userId:userIdArray)	
	                {
	                bpmConfNodeService.insert(convertFromFlowElement(f, processId, userId, type));
	                }
                }
	    	}
	    	
		return ResponseUtil.success("配置成功！");
	}
	
	@RequestMapping("/node-conf-delete")
	@ResponseBody
	public Result delete(String flowElementId,String processId, String userId,int type)
	{
		byte[] bytes = repositoryService.getModelEditorSource(processId);
    	
    	JsonNode modelNode = null;
		try {
			modelNode = (JsonNode) new ObjectMapper().readTree(bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        
    	FlowElement f = model.getMainProcess().getFlowElement(flowElementId);
	    	if(f instanceof UserTask)
	    	{
	    		UserTask task = (UserTask)f;
                if(type==1)  
                {
	    		task.setAssignee("");
                }
                else if(type==2)
                {
                List<String> caUsers =  task.getCandidateUsers();
                caUsers.remove(userId);
                task.setCandidateUsers(caUsers);
                }
                BpmConfNode node =  bpmConfNodeService.selectByUnionKey(processId,flowElementId,userId);
                bpmConfNodeService.deleteEntity(node);
	    	}
	    	
		return ResponseUtil.success("删除成功！");
	}
	
	private BpmConfNode convertFromFlowElement(FlowElement f,String processId,String userId,Integer type)
	{
		BpmConfNode nodeConf = new BpmConfNode();
        nodeConf.setNodeId(f.getId());
        nodeConf.setNodeName(f.getName());
        nodeConf.setProcessId(processId);
        nodeConf.setUserId(userId);
        nodeConf.setUserType(type);
        User user = userService.selectByKey(Integer.valueOf(userId));
        nodeConf.setUserName(user.getDisplayName());
        return nodeConf;
	}

	@RequestMapping("/list")
	@ResponseBody
	public Result list(Integer id,@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,BpmNode bpmNode)
	{
		PageInfo<BpmNode> pageInfo =  bpmNodeService.findByPage(pageNum,pageSize,bpmNode);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}	
}

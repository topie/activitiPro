package com.topie.campus.modeler.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.history.handler.UserTaskAssignmentHandler;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.topie.campus.common.Option;
import com.topie.campus.common.SimplePageInfo;
import com.topie.campus.common.utils.PageConvertUtil;
import com.topie.campus.common.utils.ResponseUtil;
import com.topie.campus.common.utils.Result;
import com.topie.campus.security.model.User;

@Controller
@RequestMapping("/modeler")
public class ModelController {

	  @Autowired
	  private RepositoryService repositoryService;
	  
	  @Inject
	  ObjectMapper objectMapper;
	  
	  @Inject
	  ManagementService managementService;
	
	 @RequestMapping("/list")
	 @ResponseBody
	    public Result list(org.springframework.ui.Model model,
	    		@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
	        List<Model> models = repositoryService.createModelQuery().listPage((pageNum-1)*pageSize, pageNum*pageSize);
	        Long total = repositoryService.createModelQuery().count();
	        SimplePageInfo<Model> page = new SimplePageInfo<Model>(pageNum,pageSize,total,models);
	        return ResponseUtil.success(PageConvertUtil.grid(page));
	    }
	 
	 @RequestMapping("/listAll")
	 @ResponseBody
	    public List<Option> listAll(org.springframework.ui.Model model) {
	        List<Model> models = repositoryService.createModelQuery().list();
	        List<Option> options = new ArrayList<>();
	        for(Model m :models)
	        {
	        	Option o = new Option();
	        	o.setText(m.getName());
	        	o.setValue(m.getId());
	        	options.add(o);
	        	
	        }
	        return options;
	    }
	 
	 @RequestMapping("modeler-open")
	    public String open(@RequestParam(value = "id", required = false) String id)
	            throws Exception {
	        Model model = repositoryService.getModel(id);

	        if (model == null) {
	            model = repositoryService.newModel();
	            repositoryService.saveModel(model);
	            id = model.getId();
	        }

	        // return "redirect:/widgets/modeler/editor.html?id=" + id;
	        return "redirect:/modeler.html?modelId=" + id;
	    }
	 
		/**
	     * 创建模型
	     */
	    @RequestMapping("/create")
	    public void create(HttpServletRequest request, HttpServletResponse response) {
	        try {
	        	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	        	 
	        	RepositoryService repositoryService = processEngine.getRepositoryService();
	        	 
	            objectMapper = new ObjectMapper();
	            ObjectNode editorNode = objectMapper.createObjectNode();
	            editorNode.put("id", "canvas");
	            editorNode.put("resourceId", "canvas");
	            ObjectNode stencilSetNode = objectMapper.createObjectNode();
	            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
	            editorNode.put("stencilset", stencilSetNode);
	            Model modelData = repositoryService.newModel();

	            ObjectNode modelObjectNode = objectMapper.createObjectNode();
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "name");
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
	            String description = "lutiannan---";
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
	            modelData.setMetaInfo(modelObjectNode.toString());
	            modelData.setName("name");
	            modelData.setKey("12313123");

	            //保存模型
	            repositoryService.saveModel(modelData);
	            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
	            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
	        } catch (Exception e) {
	            System.out.println("创建模型失败：");
	        }
	    }
	    
	    @RequestMapping("/modeler-deploy")
	    @ResponseBody
	    public Result deploy(@RequestParam("id") String id,
	            org.springframework.ui.Model theModel) throws Exception {
	        Model modelData = repositoryService.getModel(id);
	        byte[] bytes = repositoryService
	                .getModelEditorSource(modelData.getId());

	        if (bytes == null) {
	            theModel.addAttribute("message", "模型数据为空，请先设计流程并成功保存，再进行发布。");

	            return ResponseUtil.error();
	        }

	        JsonNode modelNode = (JsonNode) new ObjectMapper().readTree(bytes);
	        byte[] bpmnBytes = null;

	        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
	        bpmnBytes = new BpmnXMLConverter().convertToXML(model);

	        String processName = modelData.getName() + ".bpmn20.xml";
	        Deployment deployment = repositoryService.createDeployment()
	                .name(modelData.getName())
	                .addString(processName,new String(bpmnBytes, "UTF-8"))
	                .deploy();
	        modelData.setDeploymentId(deployment.getId());
	        repositoryService.saveModel(modelData);

	        List<ProcessDefinition> processDefinitions = repositoryService
	                .createProcessDefinitionQuery()
	                .deploymentId(deployment.getId()).list();

	        System.out.println(new String(bpmnBytes, "UTF-8"));
	        
	        return ResponseUtil.success("发布成功！");
	    }
	    
	    @RequestMapping("/modeler-remove")
	    @ResponseBody
	    public Result remove(@RequestParam("id") String id) {
	    	repositoryService.deleteModel(id);
	        return ResponseUtil.success("删除成功！");
	    }
	    
	    @RequestMapping("/node-list")
	    @ResponseBody
	    public Result getNodeList(@RequestParam("id") String id,
	    		@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) throws JsonProcessingException, IOException {
	    	
	    	byte[] bytes = repositoryService
	                .getModelEditorSource(id);
	    	
	    	JsonNode modelNode = null;
			try {
				modelNode = (JsonNode) new ObjectMapper().readTree(bytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
	        
	    	Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements(); 
	    	
	    	SimplePageInfo<FlowElement> page = new SimplePageInfo<FlowElement>(pageNum,pageSize,flowElements.size(), new ArrayList<FlowElement>(flowElements));
	       
	    	return ResponseUtil.success(ResponseUtil.success(PageConvertUtil.grid(page)));
	    }
	    
}

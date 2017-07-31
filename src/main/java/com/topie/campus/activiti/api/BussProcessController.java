package com.topie.campus.activiti.api;

import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.topie.campus.activiti.model.BussProcess;
import com.topie.campus.activiti.service.BussProcessService;
import com.topie.campus.activiti.service.impl.BussProcessServiceImpl;
import com.topie.campus.common.utils.PageConvertUtil;
import com.topie.campus.common.utils.ResponseUtil;
import com.topie.campus.common.utils.Result;

@Controller
@RequestMapping("/bussProcess")
public class BussProcessController {

	@Inject
	BussProcessService bussProcessService;
	
	@Autowired
	  private RepositoryService repositoryService;
	
	@RequestMapping("/save")
	@ResponseBody
	public Result save(BussProcess bussProcess)
	{
		if(bussProcess.getId()==null)
		bussProcessService.insert(bussProcess);
		else
		bussProcessService.updateSelective(bussProcess);
		return ResponseUtil.success("保存成功！");
	}	
	
	@RequestMapping("/load")
	@ResponseBody
	public Result load(int id)
	{
		BussProcess bussProcess = bussProcessService.selectByKey(id);
		return ResponseUtil.success(bussProcess);
	}	
	
	@RequestMapping("/update")
	@ResponseBody
	public Result update(BussProcess bussProcess)
	{
		bussProcessService.updateSelective(bussProcess);
		return ResponseUtil.success("更新成功！");
	}	
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result update(Integer id)
	{
		bussProcessService.delete(bussProcessService.selectByKey(id));
		return ResponseUtil.success("删除成功！");
	}	
	
	@RequestMapping("/list")
	@ResponseBody
	public Result list(Integer id,@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,BussProcess bussProcess)
	{
		PageInfo<BussProcess> pageInfo =  bussProcessService.findByPage(pageNum,pageSize,bussProcess);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}	
	
}
	

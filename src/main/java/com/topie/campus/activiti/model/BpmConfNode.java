package com.topie.campus.activiti.model;

import javax.persistence.*;

@Table(name = "bpm_conf_node")
public class BpmConfNode {
    @Id
    @Column(name = "node_id")
    private String nodeId;

    @Id
    @Column(name = "process_id")
    private String processId;

    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 1用户2候选人3用户组
     */
    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "form_id")
    private String formId;

    @Column(name = "node_name")
    private String nodeName;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "user_name")
    private String userName;

    /**
     * @return node_id
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * @param nodeId
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * @return process_id
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * @param processId
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取1用户2候选人3用户组
     *
     * @return user_type - 1用户2候选人3用户组
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置1用户2候选人3用户组
     *
     * @param userType 1用户2候选人3用户组
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * @return form_id
     */
    public String getFormId() {
        return formId;
    }

    /**
     * @param formId
     */
    public void setFormId(String formId) {
        this.formId = formId;
    }

    /**
     * @return node_name
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @param nodeName
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return group_id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
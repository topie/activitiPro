package com.topie.campus.activiti.model;

import javax.persistence.*;

@Table(name = "bpm_node")
public class BpmNode {
    @Id
    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "node_name")
    private String nodeName;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "node_type")
    private String nodeType;

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
     * @return node_type
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * @param nodeType
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}
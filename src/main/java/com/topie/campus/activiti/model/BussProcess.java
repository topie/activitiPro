package com.topie.campus.activiti.model;

import javax.persistence.*;

@Table(name = "buss_process")
public class BussProcess {
    @Id
    private Integer id;

    private String name;

    private String describtion;

    @Column(name = "process_id")
    private String processId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return describtion
     */
    public String getDescribtion() {
        return describtion;
    }

    /**
     * @param describtion
     */
    public void setDescribtion(String describtion) {
        this.describtion = describtion;
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
}
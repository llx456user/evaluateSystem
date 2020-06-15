package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "project_info")
public class ProjectInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 项目版本
     */
    @Column(name = "project_version")
    private String projectVersion;

    /**
     * 项目分类
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 指标配置
     */
    @Column(name = "index_data")
    private String indexData;
    /**
     * 创建人ID
     */
    @Column(name = "create_uid")
    private Integer createUid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人ID
     */
    @Column(name = "update_uid")
    private Integer updateUid;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    
    @Transient
    private String updateTimeStr;

    /**
     * 是否删除【0:否1：是】
     */
    private Integer isdelete;

    /**
     * 项目备注
     */
    @Column(name = "project_content")
    private String projectContent;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取项目名称
     *
     * @return project_name - 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置项目名称
     *
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 获取项目版本
     *
     * @return project_version - 项目版本
     */
    public String getProjectVersion() {
        return projectVersion;
    }

    /**
     * 设置项目版本
     *
     * @param projectVersion 项目版本
     */
    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    /**
     * 获取项目分类
     *
     * @return category_id - 项目分类
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置项目分类
     *
     * @param categoryId 项目分类
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 模板id
     */
    @Column(name = "template_id")
    private Integer templateId;

    /**
     * 获取创建人ID
     *
     * @return create_uid - 创建人ID
     */
    public Integer getCreateUid() {
        return createUid;
    }

    /**
     * 设置创建人ID
     *
     * @param createUid 创建人ID
     */
    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人ID
     *
     * @return update_uid - 更新人ID
     */
    public Integer getUpdateUid() {
        return updateUid;
    }

    /**
     * 设置更新人ID
     *
     * @param updateUid 更新人ID
     */
    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 获取模板id
     *
     * @return template_id - 模板id
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 设置模板id
     *
     * @param templateId 模板id
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否删除【0:否1：是】
     *
     * @return isdelete - 是否删除【0:否1：是】
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * 设置是否删除【0:否1：是】
     *
     * @param isdelete 是否删除【0:否1：是】
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * 获取项目备注
     *
     * @return project_content - 项目备注
     */
    public String getProjectContent() {
        return projectContent;
    }

    /**
     * 设置项目备注
     *
     * @param projectContent 项目备注
     */
    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectName=").append(projectName);
        sb.append(", projectVersion=").append(projectVersion);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", templateId=").append(templateId);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", projectContent=").append(projectContent);
        sb.append("]");
        return sb.toString();
    }

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getIndexData() {
		return indexData;
	}

	public void setIndexData(String indexData) {
		this.indexData = indexData;
	}
}
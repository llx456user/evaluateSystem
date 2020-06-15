package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "model_info")
public class ModelInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 模型分类id
     */
    @Column(name = "model_categoryid")
    private Integer modelCategoryid;

    /**
     * 模型状态(1--未上传，2-已上传)
     */
    @Column(name = "model_status")
    private Integer modelStatus;

    /**
     * 模型名称
     */
    @Column(name = "model_name")
    private String modelName;

    /**
     * 模型版本号
     */
    @Column(name = "model_version")
    private String modelVersion;

    /**
     * 模型内容
     */
    @Column(name = "model_content")
    private String modelContent;

    /**
     * dll路径
     */
    @Column(name = "dll_path")
    private String dllPath;

    /**
     * xml路径
     */
    @Column(name = "xml_path")
    private String xmlPath;

    /**
     * dll名称
     */
    private String dllname;

    /**
     * xml名称
     */
    private String xmlname;

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

    /**
     * 是否删除【0:否1：是】
     */
    private Integer isdelete;

    /**
     * 是否单变量函数0：否，1：是，默认为0.
     */
    @Column(name = "is_single_fun")
    private Boolean isSingleFun;

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
     * 获取模型分类id
     *
     * @return model_categoryid - 模型分类id
     */
    public Integer getModelCategoryid() {
        return modelCategoryid;
    }

    /**
     * 设置模型分类id
     *
     * @param modelCategoryid 模型分类id
     */
    public void setModelCategoryid(Integer modelCategoryid) {
        this.modelCategoryid = modelCategoryid;
    }

    /**
     * 获取模型状态(1--未上传，2-已上传)
     *
     * @return model_status - 模型状态(1--未上传，2-已上传)
     */
    public Integer getModelStatus() {
        return modelStatus;
    }

    /**
     * 设置模型状态(1--未上传，2-已上传)
     *
     * @param modelStatus 模型状态(1--未上传，2-已上传)
     */
    public void setModelStatus(Integer modelStatus) {
        this.modelStatus = modelStatus;
    }

    /**
     * 获取模型名称
     *
     * @return model_name - 模型名称
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * 设置模型名称
     *
     * @param modelName 模型名称
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * 获取模型版本号
     *
     * @return model_version - 模型版本号
     */
    public String getModelVersion() {
        return modelVersion;
    }

    /**
     * 设置模型版本号
     *
     * @param modelVersion 模型版本号
     */
    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    /**
     * 获取模型内容
     *
     * @return model_content - 模型内容
     */
    public String getModelContent() {
        return modelContent;
    }

    /**
     * 设置模型内容
     *
     * @param modelContent 模型内容
     */
    public void setModelContent(String modelContent) {
        this.modelContent = modelContent;
    }

    /**
     * 获取dll路径
     *
     * @return dll_path - dll路径
     */
    public String getDllPath() {
        return dllPath;
    }

    /**
     * 设置dll路径
     *
     * @param dllPath dll路径
     */
    public void setDllPath(String dllPath) {
        this.dllPath = dllPath;
    }

    /**
     * 获取xml路径
     *
     * @return xml_path - xml路径
     */
    public String getXmlPath() {
        return xmlPath;
    }

    /**
     * 设置xml路径
     *
     * @param xmlPath xml路径
     */
    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    /**
     * 获取dll名称
     *
     * @return dllname - dll名称
     */
    public String getDllname() {
        return dllname;
    }

    /**
     * 设置dll名称
     *
     * @param dllname dll名称
     */
    public void setDllname(String dllname) {
        this.dllname = dllname;
    }

    /**
     * 获取xml名称
     *
     * @return xmlname - xml名称
     */
    public String getXmlname() {
        return xmlname;
    }

    /**
     * 设置xml名称
     *
     * @param xmlname xml名称
     */
    public void setXmlname(String xmlname) {
        this.xmlname = xmlname;
    }

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
     * 获取是否单变量函数0：否，1：是，默认为0.
     *
     * @return is_single_fun - 是否单变量函数0：否，1：是，默认为0.
     */
    public Boolean getIsSingleFun() {
        return isSingleFun;
    }

    /**
     * 设置是否单变量函数0：否，1：是，默认为0.
     *
     * @param isSingleFun 是否单变量函数0：否，1：是，默认为0.
     */
    public void setIsSingleFun(Boolean isSingleFun) {
        this.isSingleFun = isSingleFun;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", modelCategoryid=").append(modelCategoryid);
        sb.append(", modelStatus=").append(modelStatus);
        sb.append(", modelName=").append(modelName);
        sb.append(", modelVersion=").append(modelVersion);
        sb.append(", modelContent=").append(modelContent);
        sb.append(", dllPath=").append(dllPath);
        sb.append(", xmlPath=").append(xmlPath);
        sb.append(", dllname=").append(dllname);
        sb.append(", xmlname=").append(xmlname);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", isSingleFun=").append(isSingleFun);
        sb.append("]");
        return sb.toString();
    }
}
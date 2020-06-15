package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "datasource_file")
public class DatasourceFile {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件版本
     */
    @Column(name = "file_version")
    private String fileVersion;

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
     * 指标或模型id
     */
    @Column(name = "index_or_model")
    private Integer indexOrModel;

    /**
     * 父文件
     */
    @Column(name = "parent_file")
    private String parentFile;

    /**
     * 评估ID
     */
    @Column(name = "assess_id")
    private Integer assessId;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 文件尺寸
     */
    @Column(name = "file_size")
    private String fileSize;

    /**
     * 文件分类ID
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 文件描述内容
     */
    @Column(name = "file_content")
    private String fileContent;

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
     * 获取文件名
     *
     * @return file_name - 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名
     *
     * @param fileName 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件版本
     *
     * @return file_version - 文件版本
     */
    public String getFileVersion() {
        return fileVersion;
    }

    /**
     * 设置文件版本
     *
     * @param fileVersion 文件版本
     */
    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
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
     * 获取指标或模型id
     *
     * @return index_or_model - 指标或模型id
     */
    public Integer getIndexOrModel() {
        return indexOrModel;
    }

    /**
     * 设置指标或模型id
     *
     * @param indexOrModel 指标或模型id
     */
    public void setIndexOrModel(Integer indexOrModel) {
        this.indexOrModel = indexOrModel;
    }

    /**
     * 获取父文件
     *
     * @return parent_file - 父文件
     */
    public String getParentFile() {
        return parentFile;
    }

    /**
     * 设置父文件
     *
     * @param parentFile 父文件
     */
    public void setParentFile(String parentFile) {
        this.parentFile = parentFile;
    }

    /**
     * 获取评估ID
     *
     * @return assess_id - 评估ID
     */
    public Integer getAssessId() {
        return assessId;
    }

    /**
     * 设置评估ID
     *
     * @param assessId 评估ID
     */
    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    /**
     * 获取文件路径
     *
     * @return file_path - 文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取文件尺寸
     *
     * @return file_size - 文件尺寸
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件尺寸
     *
     * @param fileSize 文件尺寸
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取文件分类ID
     *
     * @return category_id - 文件分类ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置文件分类ID
     *
     * @param categoryId 文件分类ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取文件描述内容
     *
     * @return file_content - 文件描述内容
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * 设置文件描述内容
     *
     * @param fileContent 文件描述内容
     */
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileVersion=").append(fileVersion);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", indexOrModel=").append(indexOrModel);
        sb.append(", parentFile=").append(parentFile);
        sb.append(", assessId=").append(assessId);
        sb.append(", filePath=").append(filePath);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", fileContent=").append(fileContent);
        sb.append("]");
        return sb.toString();
    }
}
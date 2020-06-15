package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "word_template")
public class WordTemplate {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 模板名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 模板路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 模板分类id
     */
    @Column(name = "category_id")
    private Integer categoryId;

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
     * 获取模板名称
     *
     * @return file_name - 模板名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置模板名称
     *
     * @param fileName 模板名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取模板路径
     *
     * @return file_path - 模板路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置模板路径
     *
     * @param filePath 模板路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取模板分类id
     *
     * @return category_id - 模板分类id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置模板分类id
     *
     * @param categoryId 模板分类id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
     * @return file_content
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * @param fileContent
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
        sb.append(", filePath=").append(filePath);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", fileContent=").append(fileContent);
        sb.append("]");
        return sb.toString();
    }
}
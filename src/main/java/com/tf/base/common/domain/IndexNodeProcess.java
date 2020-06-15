package com.tf.base.common.domain;

import com.tf.base.common.utils.StringUtil;
import com.tf.base.go.util.InparamterUtil;

import javax.persistence.*;
import java.util.Date;

@Table(name = "index_node_process")
public class IndexNodeProcess {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 指标执行ID
     */
    @Column(name = "index_process_id")
    private Integer indexProcessId;

    /**
     * 节点状态[0:未执行;1：执行成功;2:执行失败]
     */
    @Column(name = "node_status")
    private Integer nodeStatus;

    /**
     * 节点的key，获取定义的信息
     */
    @Column(name = "node_key")
    private Integer nodeKey;

    /**
     * 文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

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

    @Column(name = "node_input_param")
    private String nodeInputParam;

    @Column(name = "node_output_param")
    private String nodeOutputParam;

    /**
     * 是否删除【0:否1：是】
     */
    private Integer isdelete;

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
     * 获取指标执行ID
     *
     * @return index_process_id - 指标执行ID
     */
    public Integer getIndexProcessId() {
        return indexProcessId;
    }

    /**
     * 设置指标执行ID
     *
     * @param indexProcessId 指标执行ID
     */
    public void setIndexProcessId(Integer indexProcessId) {
        this.indexProcessId = indexProcessId;
    }

    /**
     * 获取节点状态[0:未执行;1：执行成功;2:执行失败3:条件否，不执行]
     *
     * @return node_status - 节点状态[0:未执行;1：执行成功;2:执行失败3:条件否，不执行]
     */
    public Integer getNodeStatus() {
        return nodeStatus;
    }

    /**
     * 设置节点状态[0:未执行;1：执行成功;2:执行失败；3:条件否，不执行]
     *
     * @param nodeStatus 节点状态[0:未执行;1：执行成功;2:执行失败;3:条件否，不执行]
     */
    public void setNodeStatus(Integer nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    /**
     * 获取节点的key，获取定义的信息
     *
     * @return node_key - 节点的key，获取定义的信息
     */
    public Integer getNodeKey() {
        return nodeKey;
    }

    /**
     * 设置节点的key，获取定义的信息
     *
     * @param nodeKey 节点的key，获取定义的信息
     */
    public void setNodeKey(Integer nodeKey) {
        this.nodeKey = nodeKey;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", indexProcessId=").append(indexProcessId);
        sb.append(", nodeStatus=").append(nodeStatus);
        sb.append(", nodeKey=").append(nodeKey);
        sb.append(", fileName=").append(fileName);
        sb.append(", filePath=").append(filePath);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }

	public String getNodeInputParam() {
		return nodeInputParam;
	}

	public void setNodeInputParam(String nodeInputParam) {
		this.nodeInputParam = nodeInputParam;
	}

	public String getNodeOutputParam() {
		return nodeOutputParam;
	}

	public void setNodeOutputParam(String nodeOutputParam) {
		this.nodeOutputParam = nodeOutputParam;
	}

    @Transient
    public String getNodeInputParamExp() {
        if(!StringUtil.isEmpty(nodeInputParam)){
            return  InparamterUtil.readString(nodeInputParam);
        }
        return nodeInputParam;
    }

    @Transient
    public void setNodeInputParamExp(String nodeInputParam) {
        if(!StringUtil.isEmpty(nodeInputParam)){
            this.nodeInputParam  = InparamterUtil.createFileName();
            InparamterUtil.writerString(this.nodeInputParam,nodeInputParam);
        }else{
            this.nodeInputParam = nodeInputParam;
        }
    }

    @Transient
    public String getNodeOutputParamExp() {
        if(!StringUtil.isEmpty(nodeOutputParam)){
           return  InparamterUtil.readString(nodeOutputParam);
        }
        return nodeOutputParam;
    }

    @Transient
    public void setNodeOutputParamExp(String nodeOutputParam) {
        if(!StringUtil.isEmpty(nodeOutputParam)){
            this.nodeOutputParam  = InparamterUtil.createFileName();
            InparamterUtil.writerString(this.nodeOutputParam,nodeOutputParam);
        }else{
            this.nodeOutputParam = nodeOutputParam;
        }
    }
}

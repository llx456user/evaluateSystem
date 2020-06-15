package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "data_file")
public class DataFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 有效模块 
     */
    private String model;
    
    /**
     * 业务模块表id
     */
    @Column(name = "model_tb_id")
    private String modelTbId;

    /**
     * 类型：1.默认
     */
    private String type;
    
    /**
     * 上传时文件名
     */
    private String uploadfilename;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件大小
     */
    private String filesize;

    /**
     * 文件路径
     */
    private String pathname;

    @Column(name = "upload_time")
    private Date uploadTime;

    private String uploader;

    /**
     * 状态 1.有效 2.无效
     */
    private String status;

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
     *
     * @return model - 有效模块
     */
    public String getModel() {
        return model;
    }

    /**
     *
     * @param model 有效模块 
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取类型：1.默认
     *
     * @return type - 类型：1.默认
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型：1.默认
     *
     * @param type 类型：1.默认
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取文件名称
     *
     * @return filename - 文件名称
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 设置文件名称
     *
     * @param filename 文件名称
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 获取文件大小
     *
     * @return filesize - 文件大小
     */
    public String getFilesize() {
        return filesize;
    }

    /**
     * 设置文件大小
     *
     * @param filesize 文件大小
     */
    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    /**
     * 获取文件路径
     *
     * @return pathname - 文件路径
     */
    public String getPathname() {
        return pathname;
    }

    /**
     * 设置文件路径
     *
     * @param pathname 文件路径
     */
    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    /**
     * @return upload_time
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * @param uploadTime
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * @return uploader
     */
    public String getUploader() {
        return uploader;
    }

    /**
     * @param uploader
     */
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    /**
     * 获取状态 1.有效 2.无效
     *
     * @return status - 状态 1.有效 2.无效
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 1.有效 2.无效
     *
     * @param status 状态 1.有效 2.无效
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", model=").append(model);
        sb.append(", type=").append(type);
        sb.append(", filename=").append(filename);
        sb.append(", filesize=").append(filesize);
        sb.append(", pathname=").append(pathname);
        sb.append(", uploadTime=").append(uploadTime);
        sb.append(", uploader=").append(uploader);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }

	public String getModelTbId() {
		return modelTbId;
	}

	public void setModelTbId(String modelTbId) {
		this.modelTbId = modelTbId;
	}

	public String getUploadfilename() {
		return uploadfilename;
	}

	public void setUploadfilename(String uploadfilename) {
		this.uploadfilename = uploadfilename;
	}
}
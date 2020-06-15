package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "model_parmeter")
public class ModelParmeter {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 模型id
     */
    @Column(name = "model_id")
    private Integer modelId;

    /**
     * 参数名称
     */
    @Column(name = "parmeter_name")
    private String parmeterName;

    /**
     * 参数单位
     */
    @Column(name = "parmeter_unit")
    private String parmeterUnit;

    /**
     * 参数扩展单位
     */
    @Column(name = "parmeter_unit_ex")
    private String parmeterUnitEx;

    /**
     * 参数源
     */
    @Column(name = "parmeter_source")
    private String parmeterSource;

    /**
     * 参数类型
     */
    @Column(name = "parmeter_type")
    private String parmeterType;

    /**
     * 参数默认
     */
    @Column(name = "parmeter_default")
    private String parmeterDefault;

    /**
     * 公式顺序
     */
    @Column(name = "formula_order")
    private Integer formulaOrder;

    /**
     * 输入输出类型【0：输入,1：输出,2：结构体输入】
     */
    @Column(name = "inout_type")
    private Integer inoutType;

    /**
     * 父节点id
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 默认值
     */
    private Double defaultvalue;

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
     * 是否数组0：否，1：是，默认为0.
     */
    @Column(name = "is_array")
    private Boolean isArray;

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
     * 获取模型id
     *
     * @return model_id - 模型id
     */
    public Integer getModelId() {
        return modelId;
    }

    /**
     * 设置模型id
     *
     * @param modelId 模型id
     */
    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    /**
     * 获取参数名称
     *
     * @return parmeter_name - 参数名称
     */
    public String getParmeterName() {
        return parmeterName;
    }

    /**
     * 设置参数名称
     *
     * @param parmeterName 参数名称
     */
    public void setParmeterName(String parmeterName) {
        this.parmeterName = parmeterName;
    }

    /**
     * 获取参数单位
     *
     * @return parmeter_unit - 参数单位
     */
    public String getParmeterUnit() {
        return parmeterUnit;
    }

    /**
     * 设置参数单位
     *
     * @param parmeterUnit 参数单位
     */
    public void setParmeterUnit(String parmeterUnit) {
        this.parmeterUnit = parmeterUnit;
    }

    /**
     * 获取参数扩展单位
     *
     * @return parmeter_unit_ex - 参数扩展单位
     */
    public String getParmeterUnitEx() {
        return parmeterUnitEx;
    }

    /**
     * 设置参数扩展单位
     *
     * @param parmeterUnitEx 参数扩展单位
     */
    public void setParmeterUnitEx(String parmeterUnitEx) {
        this.parmeterUnitEx = parmeterUnitEx;
    }

    /**
     * 获取参数源
     *
     * @return parmeter_source - 参数源
     */
    public String getParmeterSource() {
        return parmeterSource;
    }

    /**
     * 设置参数源
     *
     * @param parmeterSource 参数源
     */
    public void setParmeterSource(String parmeterSource) {
        this.parmeterSource = parmeterSource;
    }

    /**
     * 获取参数类型
     *
     * @return parmeter_type - 参数类型
     */
    public String getParmeterType() {
        return parmeterType;
    }

    /**
     * 设置参数类型
     *
     * @param parmeterType 参数类型
     */
    public void setParmeterType(String parmeterType) {
        this.parmeterType = parmeterType;
    }

    /**
     * 获取参数默认
     *
     * @return parmeter_default - 参数默认
     */
    public String getParmeterDefault() {
        return parmeterDefault;
    }

    /**
     * 设置参数默认
     *
     * @param parmeterDefault 参数默认
     */
    public void setParmeterDefault(String parmeterDefault) {
        this.parmeterDefault = parmeterDefault;
    }

    /**
     * 获取公式顺序
     *
     * @return formula_order - 公式顺序
     */
    public Integer getFormulaOrder() {
        return formulaOrder;
    }

    /**
     * 设置公式顺序
     *
     * @param formulaOrder 公式顺序
     */
    public void setFormulaOrder(Integer formulaOrder) {
        this.formulaOrder = formulaOrder;
    }

    /**
     * 获取输入输出类型【0：输入,1：输出,2：结构体输入】
     *
     * @return inout_type - 输入输出类型【0：输入,1：输出,2：结构体输入】
     */
    public Integer getInoutType() {
        return inoutType;
    }

    /**
     * 设置输入输出类型【0：输入,1：输出,2：结构体输入】
     *
     * @param inoutType 输入输出类型【0：输入,1：输出,2：结构体输入】
     */
    public void setInoutType(Integer inoutType) {
        this.inoutType = inoutType;
    }

    /**
     * 获取父节点id
     *
     * @return parent_id - 父节点id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父节点id
     *
     * @param parentId 父节点id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取默认值
     *
     * @return defaultvalue - 默认值
     */
    public Double getDefaultvalue() {
        return defaultvalue;
    }

    /**
     * 设置默认值
     *
     * @param defaultvalue 默认值
     */
    public void setDefaultvalue(Double defaultvalue) {
        this.defaultvalue = defaultvalue;
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
     * 获取是否数组0：否，1：是，默认为0.
     *
     * @return is_array - 是否数组0：否，1：是，默认为0.
     */
    public Boolean getIsArray() {
        return isArray;
    }

    /**
     * 设置是否数组0：否，1：是，默认为0.
     *
     * @param isArray 是否数组0：否，1：是，默认为0.
     */
    public void setIsArray(Boolean isArray) {
        this.isArray = isArray;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", modelId=").append(modelId);
        sb.append(", parmeterName=").append(parmeterName);
        sb.append(", parmeterUnit=").append(parmeterUnit);
        sb.append(", parmeterUnitEx=").append(parmeterUnitEx);
        sb.append(", parmeterSource=").append(parmeterSource);
        sb.append(", parmeterType=").append(parmeterType);
        sb.append(", parmeterDefault=").append(parmeterDefault);
        sb.append(", formulaOrder=").append(formulaOrder);
        sb.append(", inoutType=").append(inoutType);
        sb.append(", parentId=").append(parentId);
        sb.append(", defaultvalue=").append(defaultvalue);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", isArray=").append(isArray);
        sb.append("]");
        return sb.toString();
    }
}
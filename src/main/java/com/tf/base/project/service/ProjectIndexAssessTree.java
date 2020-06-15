package com.tf.base.project.service;

import com.tf.base.common.domain.ProjectIndexAssess;
import com.tf.base.project.domain.ProjectIndexAssessBean;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ProjectIndexAssessTree {
    private List<ProjectIndexAssess> projectIndexs;

    private List<ProjectIndexAssessBean> projectIndexAssessBeans= new ArrayList<>();

    public ProjectIndexAssessTree(List<ProjectIndexAssess> projectIndexs) {
         this.projectIndexs=projectIndexs;
        buildTree();
    }

    private void buildTree() {
        if(this.projectIndexs==null|| this.projectIndexs.size()==0){
            return;
        }
        if(this.projectIndexs.size()==1){
            ProjectIndexAssessBean bean = new ProjectIndexAssessBean();
            BeanUtils.copyProperties(projectIndexs.get(0), bean);
            bean.setNo("1");
            projectIndexAssessBeans.add(bean);
            return;
        }
        ProjectIndexAssess projectIndexAssess= getRoot();
        ProjectIndexAssessBean bean1 = new ProjectIndexAssessBean();
        bean1.setNo("1");
        BeanUtils.copyProperties(projectIndexs.get(0), bean1);
        projectIndexAssessBeans.add(bean1);
        tree(projectIndexAssess,"1");
    }

    /**
     * 获取根节点
     * @return
     */
    private  ProjectIndexAssess getRoot(){
        for(ProjectIndexAssess projectIndexAssess:this.projectIndexs){
            List<ProjectIndexAssess> list = getParent(projectIndexAssess.getParentid());
            if(list.size()==0){
                return projectIndexAssess;
            }
        }
        return  null ;
    }

    /**
     * 获取父节点
     * @param parentId
     * @return
     */
    private  List<ProjectIndexAssess> getParent(Integer parentId){
        List<ProjectIndexAssess> list = new ArrayList<>();
        for(ProjectIndexAssess projectIndexAssess:this.projectIndexs){
            if(projectIndexAssess.getIndexId()==parentId){
                list.add(projectIndexAssess);
            }
        }
        return list;
    }

    private void tree(ProjectIndexAssess projectIndexAssess, String no) {
        List<ProjectIndexAssess> childList = getChild(projectIndexAssess.getProjectIndexId());
        if (childList.size() == 0) {//没有子节点
        //不做处理
        }else{
            int i=1;
            for(ProjectIndexAssess projectIndexAssess1:childList){
                ProjectIndexAssessBean bean = new ProjectIndexAssessBean();
                BeanUtils.copyProperties(projectIndexAssess1, bean);
                bean.setNo(getChildNo(no,i));
                i++;
                projectIndexAssessBeans.add(bean);
                tree(projectIndexAssess1,bean.getNo());
            }
        }
    }


    /**
     * 获取子编号
     * @param no
     * @param i
     * @return
     */
    private String getChildNo(String no,int i){
        return  no+"."+i ;
    }
    /**
     * 获取子节点
     * @param id
     * @return
     */
    private List<ProjectIndexAssess> getChild(Integer id) {
        List<ProjectIndexAssess> list = new ArrayList<>();
        for (ProjectIndexAssess projectIndexAssess : projectIndexs) {
            if (projectIndexAssess.getParentid().intValue() == id.intValue()) {
                list.add(projectIndexAssess);
            }
        }
        return list;
    }

    /**
     * 获取构造好排序后的树形结构
     * @return
     */
    public  List<ProjectIndexAssessBean> getpProjectIndexAssessBeans(){
        return  this.projectIndexAssessBeans;
    }
}

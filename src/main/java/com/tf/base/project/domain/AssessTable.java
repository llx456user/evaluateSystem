package com.tf.base.project.domain;

import com.tf.base.common.domain.ProjectIndexAssess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessTable {

    private Map<Integer,List<ProjectIndexAssess>> map = new HashMap<>();

    private Map<Integer,List<ProjectIndexAssess>> mapChildren = new HashMap<>();

    private  int rows=0 ;

    private  int columns=0 ;

    /**
     * 构建结构
     * @param projectIndexAssess
     */
    public   void addProjectIndexAssess(ProjectIndexAssess projectIndexAssess){
        if(projectIndexAssess.getIndexLevel()>columns){
            columns=projectIndexAssess.getIndexLevel();
        }
        if(map.containsKey(projectIndexAssess.getIndexLevel())){
            map.get(projectIndexAssess.getIndexLevel()).add(projectIndexAssess);
        }else{
            List<ProjectIndexAssess> list = new ArrayList<>();
            list.add(projectIndexAssess);
            map.put(projectIndexAssess.getIndexLevel(),list);
        }
        if(mapChildren.containsKey(projectIndexAssess.getParentid())){
            mapChildren.get(projectIndexAssess.getParentid()).add(projectIndexAssess);
        }else{
            List<ProjectIndexAssess> list = new ArrayList<>();
            list.add(projectIndexAssess);
            mapChildren.put(projectIndexAssess.getParentid(),list);
        }
        //保证所有的节点ID都在子节点列表中
        if(!mapChildren.containsKey(projectIndexAssess.getProjectIndexId())){
            List<ProjectIndexAssess> list = new ArrayList<>();
            mapChildren.put(projectIndexAssess.getProjectIndexId(),list);
        }
    }

    public int getRows() {
        rows=0 ;
        for(Integer key :mapChildren.keySet()){
            if(mapChildren.get(key).size()==0){
                rows++;
            }
        }
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * 获取行列值
     * @param row
     * @param column
     * @return
     */
    public ProjectIndexAssess  getValueByRowAndColumn(int row,int column){
        ProjectIndexAssess projectIndexAssess =null ;
        int rowNum = 0 ;
        List<ProjectIndexAssess> list = map.get(column+1);
        for(ProjectIndexAssess p :list){
            int cNum = getChildrenNum(p);
            if(cNum==0){
                cNum=1;
            }
            rowNum=rowNum+cNum;
//            if(row<=rowNum){
                projectIndexAssess=p;
                break;
//            }
        }
        return  projectIndexAssess ;
    }

    public List<ProjectIndexAssess> getListByColumn(int Column){
        return  map.get(Column);
    }

    /**
     * 获取节点数量
     * @param projectIndexAssess
     * @return
     */
    public  int getChildrenNum(ProjectIndexAssess projectIndexAssess){
        return  mapChildren.get(projectIndexAssess.getProjectIndexId()).size() ;
    }

}

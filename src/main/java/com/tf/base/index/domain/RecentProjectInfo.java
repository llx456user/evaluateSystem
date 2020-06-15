package com.tf.base.index.domain;


import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RecentProjectInfo {
    private String id;
    private String project_name;
    private String project_content;



    private String index_id;
    private String index_name;
    private String assess_name;



    private String create_time;
    private String update_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date data = formatter.parse(create_time);
            this.create_time = formatter.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_content() {
        return project_content;
    }

    public void setProject_content(String project_content) {
        String p_content = project_content;
        if (project_content.length() > 15) {
            p_content = project_content.substring(0, 15) + "...";
        }
        this.project_content = p_content;
    }


    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date data = formatter.parse(update_time);
            this.update_time = formatter.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    public String getIndex_name() {
        return index_name;
    }

    public void setIndex_name(String index_name) {
        this.index_name = index_name;
    }

    public String getAssess_name() {
        return assess_name;
    }

    public void setAssess_name(String assess_name) {
        this.assess_name = assess_name;
    }
}

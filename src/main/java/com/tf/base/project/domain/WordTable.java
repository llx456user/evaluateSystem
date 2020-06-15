package com.tf.base.project.domain;

import java.util.List;
import java.util.Map;

public class WordTable {

    private  String title;
    private String[] headers;
    private  List<Map<String, Object>> dataset;

    public WordTable(String title,String[] headers, List<Map<String, Object>> dataset) {
        this.title=title;
        this.headers=headers;
        this.dataset=dataset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<Map<String, Object>> getDataset() {
        return dataset;
    }

    public void setDataset(List<Map<String, Object>> dataset) {
        this.dataset = dataset;
    }
}

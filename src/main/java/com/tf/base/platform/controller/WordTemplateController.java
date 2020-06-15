package com.tf.base.platform.controller;

import com.tf.base.common.domain.*;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.service.CommonService;
import com.tf.base.platform.domain.*;
import com.tf.base.platform.service.WordTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/platform/")
public class WordTemplateController {

    @Autowired
    private WordTemplateService wordTemplateService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "docTemplateList", method = RequestMethod.GET)
    public String indexList(Model model) {
        return "platform/docTemplateList";
    }

    /**
     * 查询word模板信息
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "docTemplateList", method = RequestMethod.POST)
    public Map<String, Object> docTemplateList(WordTemplateParams params) {
        Map<String, Object> result = new HashMap<String, Object>();
        int total = wordTemplateService.queryCount(params);
        result.put("total", total);
        if (total == 0) {
            result.put("rows", new ArrayList<WordTemplate>());
            return result;
        } else {
            int start = (params.getPage() - 1) * params.getRows();
            List<WordTemplateQueryResult> rows = wordTemplateService.queryList(params, start);
            result.put("rows", rows);
            return result;
        }
    }

    /**
     * 查询模板分类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "wordCategoryList", method = RequestMethod.POST)
    public Object wordCategoryList() {
        return wordTemplateService.wordCategoryList();
    }

    /**
     * 新增模板分类
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "wordCategoryCreate", method = RequestMethod.POST)
    public String wordCategoryCreate(WordCategory category) {
        category.setIsdelete(0);
        category.setCreateTime(new Date());
        category.setCreateUid(Integer.parseInt(baseService.getUserId()));
        wordTemplateService.wordCategoryCreate(category);
        return "success";
    }

    /**
     * 删除模板分类
     * @param categoryId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "wordCategoryRemove", method = RequestMethod.POST)
    public String wordCategoryRemove(Integer categoryId) {
        wordTemplateService.wordCategoryRemove(categoryId);
        return "success";
    }

    /**
     * 编辑模板分类
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "wordCategoryUpdate", method = RequestMethod.POST)
    public String wordCategoryUpdate(WordCategory category) {
        category.setUpdateTime(new Date());
        category.setUpdateUid(Integer.parseInt(baseService.getUserId()));
        wordTemplateService.wordCategoryUpdate(category);
        return "success";
    }

    /**
     * 模板编辑
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "wordFileEdit", method = RequestMethod.GET)
    public String fileEdit(Model model,String id) {
        WordTemplate df = wordTemplateService.findById(id);
        model.addAttribute("categoryId", df.getCategoryId());
        model.addAttribute("df", df);
        return "platform/wordTemplateAdd";
    }

    /**
     * 删除表格模板
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteWordIndex", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteWordIndex(String id) {
        int i = wordTemplateService.deleteWordIndex(id);
        return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
    }

    /**
     * 上传模板
     * @param model
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "wordTemplateAdd", method = RequestMethod.GET)
    public String fileAdd(Model model,String categoryId) {
        model.addAttribute("categoryId", categoryId);
        return "platform/wordTemplateAdd";
    }

    /**
     * 保存模板
     * @param df
     * @param cover
     * @return
     */
    @RequestMapping(value = "wordFileSave")
    @ResponseBody
    public Map<String, Object> fileSave(WordTemplate df,String cover) {

        int i = wordTemplateService.wordFileSave(df,cover);
        if(i == -1){
            return commonService.returnMsg(-1, "模板文件名已存在");
        }
        return i > 0 ? commonService.returnMsg(1, "模板保存成功") : commonService.returnMsg(0, "模板保存失败");
    }

}
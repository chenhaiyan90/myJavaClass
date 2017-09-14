package com.spring.common.web.vo;

import com.alibaba.fastjson.JSON;

import com.spring.common.config.PageSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 对分页的基本数据进行一个简单的封装
 * @User: ambitor_luo
 * @Date: 2015/6/23
 */
@JsonSerialize(using = PageSerializer.class)
public class Page<T> extends ArrayList<T> {
    private static final long serialVersionUID = 5299464453357532822L;

    private int pageNum = 1;// 页码，默认是第一页
    private int pageSize = 10;// 每页显示的记录数，默认是15
    private int totalCount;// 总记录数
    private int totalPage;// 总页数
    private List<T> results;// 对应的当前页记录
    private boolean isFirstPage;// 是否为第一页
    private boolean isLastPage;// 是否为最后一页
    private boolean filterNullForJson = true;// 转json时候，是否忽略为null的属性

    public Page() {
    }

    public Page(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageNum >= 1) {
            this.pageNum = pageNum;
        }

        if (pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public boolean isFilterNullForJson() {
        return filterNullForJson;
    }

    public void setFilterNullForJson(boolean filterNullForJson) {
        this.filterNullForJson = filterNullForJson;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;

        int totalPage = ((totalCount % pageSize) == 0) ? (totalCount / pageSize) : ((totalCount / pageSize) + 1);
        this.setTotalPage(totalPage);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getMysqlOffset() {
        return (getPageNum() - 1) * getPageSize();
    }

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNum", this.getPageNum());
        map.put("pageSize", this.getPageSize());
        map.put("totalCount", this.getTotalCount());
        map.put("totalPage", this.getTotalPage());
        map.put("isFirstPage", this.isFirstPage());
        map.put("isLastPage", this.isLastPage());
        map.put("results", this.getResults());

        return JSON.toJSONString(map);
    }
}

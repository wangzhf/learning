package com.wangzhf.common.domain;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 定义分页变量
 */
public class Page implements Serializable {

    // 每页大小
    @NotBlank(message = "每页大小不能为空")
    private Integer pageSize;
    // 当前第几页
    @NotBlank(message = "当前页数不能为空")
    private Integer currentPage;
    // 从第几条开始
    private Integer start;
    // 总记录数
    private Integer total;

    public void calcStart() {
        if (this.pageSize != null && this.currentPage != null ){
            this.start = this.pageSize * (this.currentPage - 1);
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.calcStart();
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.calcStart();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}

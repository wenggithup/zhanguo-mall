package club.banyuan.mall.mgt.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * ums_resource_category
 * @author 
 */
public class UmsResourceCategory implements Serializable {
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
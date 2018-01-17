/**
 * @fileName :     BaseObject
 * @author :       zeeker
 * @date :         11/01/2018 11:59
 * @description :
 */

package com.zeeker.keychain.model;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseObject implements Serializable{

    private static final long serialVersionUID = -8053015777606040943L;

    private Long id;

    private Date createTime;

    private Date updateTime;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

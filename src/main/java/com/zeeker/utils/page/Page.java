/**
 * @fileName :     Page
 * @author :       zeeker
 * @date :         17/01/2018 16:23
 * @description :  分页
 */

package com.zeeker.utils.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = -4148043914907667068L;

    private final List<T> content = new ArrayList<>();
    private Pageable pageable;

    public Page(List<T> content, Pageable pageable) {
        this.content.addAll(content);
        this.pageable = pageable;
    }

    public List<T> getContent() {
        return content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setContent(List<T> content){
        this.content.addAll(content);
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}

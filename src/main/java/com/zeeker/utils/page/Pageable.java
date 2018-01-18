/**
 * @fileName :     Pageable
 * @author :       zeeker
 * @date :         17/01/2018 16:22
 * @description :  分页信息
 */

package com.zeeker.utils.page;
import java.io.Serializable;

public class Pageable implements Serializable{
    private static final long serialVersionUID = 1219135216749677487L;

    // 默认页码
    private static final int DEFAULT_PAGE_NUMBER = 1;
    // 默认每页记录数
    private static final int DEFAULT_PAGE_SIZE = 10;
    // 每页最大记录数
    private static final int MAX_PAGE_SIZE = 3000;
    // 默认导航条显示的页数
    private static final int DEFAULT_PAGE_BAR_COUNT = 13;

    // 当前页码
    private Integer pageIndex;
    // 每页记录数
    private Integer pageSize;
    // 总页数
    private Integer totalPage;
    // 导航条
    private Integer[] pageBar;


    /**
     * 创建分页信息
     * @param pageIndex
     * @param totalCount
     */
    public Pageable(Integer pageIndex, Integer totalCount){
        this(pageIndex, totalCount, DEFAULT_PAGE_SIZE, DEFAULT_PAGE_BAR_COUNT);
    }

    /**
     * 创建分页信息
     * @param pageIndex
     * @param totalCount
     * @param pageSize
     */
    public Pageable(Integer pageIndex, Integer totalCount, Integer pageSize) {
        this(pageIndex, totalCount, pageSize, DEFAULT_PAGE_BAR_COUNT);
    }

    /**
     * 创建分页信息
     * @param pageIndex
     * @param totalCount
     * @param pageSize 每页记录数
     * @param barCount 导航条长度
     */
    public Pageable(Integer pageIndex, Integer totalCount, Integer pageSize, Integer barCount) {
        // 顺序不能乱
        setPageIndex(pageIndex);
        setPageSize(pageSize);
        setTotalPage(totalCount);
        setPageBar(barCount);
    }

    private void setPageIndex(Integer pageIndex){
        if (pageIndex != null && pageIndex > 0){
            this.pageIndex = pageIndex ;
        }else {
            this.pageIndex = DEFAULT_PAGE_NUMBER;
        }
    }

    private void setPageSize(Integer pageSize){
        if (pageSize != null && pageSize > 0 && pageSize <= MAX_PAGE_SIZE){
            this.pageSize = pageSize;
        }else {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
    }

    private void setTotalPage(Integer totalCount){
        if (totalCount != null && totalCount > 0){
            this.totalPage = (int)Math.ceil( (double)totalCount / (double)this.pageSize );
        }else {
            this.totalPage = 1;
        }
    }

    /**
     * 设置导航栏
     * @param barLength 导航栏长度
     */
    private void setPageBar(Integer barLength){
        barLength = ( barLength != null || barLength > 0 ) ? barLength : DEFAULT_PAGE_BAR_COUNT;
        int headValue = 0;
        int tailValue = 0;
        if (totalPage <= barLength){
            headValue = 1;
            tailValue = totalPage;
        }else {
            headValue = pageIndex - barLength / 2 > 0 ? pageIndex - barLength / 2 : 1;
            tailValue = headValue + ( barLength - 1 );
            if (tailValue >= totalPage){
                tailValue = totalPage;
                headValue = tailValue - ( barLength - 1 );
            }
        }
        int pageBarLength = tailValue - headValue + 1;
        this.pageBar = new Integer[pageBarLength];
        for (int i = 0; i < pageBarLength; i++){
            this.pageBar[i] = headValue + i;
        }
    }

    public Integer[] getPageBar(){
        return pageBar;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }
}

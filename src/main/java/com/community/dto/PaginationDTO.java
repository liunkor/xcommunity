package com.community.dto;

import com.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        /**
         * set the pagination data:
         * page - the current page
         * pages - the page number will show
         * showPrevious/showNext - if show the privious or next page button
         * showFirstPage/showEndPage - if show the first Page or end Page button
         */
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        //set the current page
        this.page = page;
        //set the page number will be show
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //if show the Previous page
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //if show the Next page
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //if show the first page index
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        // if show the last page index
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}

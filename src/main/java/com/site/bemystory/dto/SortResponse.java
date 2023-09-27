package com.site.bemystory.dto;

import org.springframework.data.domain.Sort;

public class SortResponse {
    public boolean sorted;
    public String direction;
    public String orderProperty;
    public SortResponse(Sort sort){
        this.sorted=true;
        this.direction="DESC";
        this.orderProperty="BookId";
    }
}

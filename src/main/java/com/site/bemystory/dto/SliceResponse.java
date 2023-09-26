package com.site.bemystory.dto;

import com.site.bemystory.domain.Book;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;
public class SliceResponse<T> {
    private final List<T> content;
    private final SortResponse sort;
    private final int currentPage;
    private final int size;
    private final boolean first;
    private final boolean last;


    public SliceResponse(Slice<T> sliceContent){
        this.content=sliceContent.getContent();
        this.sort=new SortResponse(sliceContent.getSort());
        this.currentPage=sliceContent.getNumber();
        this.size=sliceContent.getSize();
        this.first=sliceContent.isFirst();
        this.last=sliceContent.isLast();
    }


}

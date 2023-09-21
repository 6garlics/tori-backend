package com.site.bemystory.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

//TODO: access가 무엇인지 알아보기
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollPaginationCollection<T> {
    // 현재 스크롤의 요소 + 다음 스크롤의 요소 1개 (다음 스크롤이 있는지 확인을 위한)
    private final List<T> itemWithNextCursor;
    private final int countPerScroll;   //스크롤 1회에 조회할 데이터의 개수

    public static <T> ScrollPaginationCollection<T> of(List<T> itemWithNextCursor, int size) {
        return new ScrollPaginationCollection<>(itemWithNextCursor, size);
    }

    /**
     * 현재 스크롤이 마지막 스크롤인지 확인하기 위한 메소드
     * 데이터가 countPerScroll 의 숫자 이하로 조회되면 마지막 스크롤이라고 판단
     */
    public boolean isLastScroll() {
        return this.itemWithNextCursor.size() <= countPerScroll;
    }

    /**
     * 마지막 스크롤-> itemsWithNextCursor 를 return 하고
     * 마지막 스크롤 x -> 다음 스크롤의 데이터 1개를 제외하고 return
     */
    public List<T> getCurrentScrollItems() {
        if (isLastScroll()) {
            return this.itemWithNextCursor;
        }
        return this.itemWithNextCursor.subList(0, countPerScroll);
    }

    /**
     * 현재 스크롤의 데이터 중 마지막 데이터를 cursor로 사용하고 이를 return
     */
    public T getNextCursor() {
        return itemWithNextCursor.get(countPerScroll - 1);
    }
}

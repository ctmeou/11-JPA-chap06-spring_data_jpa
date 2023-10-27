package com.ohgiraffers.springdatajpa.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor //모든 매개변수를 전달받는 Constructor
//데이터를 저장할 객체의 형태
public class PagingButtonInfo {

    private int currentPage;
    private int startPage;
    private int endPage;

}

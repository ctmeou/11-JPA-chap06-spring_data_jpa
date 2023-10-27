package com.ohgiraffers.springdatajpa.menu.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_category")
@Getter @Setter @ToString
public class Category {

    @Id        //컬럼명과 동일하지 않으면 mapping되지 않는다.
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;

}

package com.ohgiraffers.springdatajpa.menu.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //엔티티를 여러 번 사용하지 않을 거라 name을 따로 지정하지 않음 = 클래스명을 따름
@Table(name = "tbl_menu")
@Getter @Setter @ToString
public class Menu {

    @Id
    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode;
    private String orderableStatus;

    public Menu(){}

}

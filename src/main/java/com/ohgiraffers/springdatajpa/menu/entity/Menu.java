package com.ohgiraffers.springdatajpa.menu.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity //엔티티를 여러 번 사용하지 않을 거라 name을 따로 지정하지 않음 = 클래스명을 따름
@Table(name = "tbl_menu")
@SequenceGenerator( //메뉴 등록 시 DB와 비교해서 시퀀스 자동 생성된다.(insert를 입력한 것을 제외하고)
        name = "seq_menu_code_generator",
        sequenceName = "seq_menu_code",
        allocationSize = 1 //증가값, DB에 작성해두어도 비워두면 에러 발생해서 작성해야 한다.
)
@Getter @Setter @ToString
public class Menu {

    @Id
    @GeneratedValue (
            strategy = GenerationType.SEQUENCE,
            generator = "seq_menu_code_generator"
    )
    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode;
    private String orderableStatus;

    public Menu(){}

}

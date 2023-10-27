package com.ohgiraffers.springdatajpa.menu.respository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//DB와 연동하는 구문
//MenuRepository 스프링의 기능을 이용하기 위해 정해진 JPA를 상속
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    //이름만 작성해서 사용할 수 있다.
    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 가격 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 오름차순으로 조회
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 내림차순으로 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);

}

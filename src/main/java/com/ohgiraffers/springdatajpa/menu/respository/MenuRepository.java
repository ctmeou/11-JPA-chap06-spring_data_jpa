package com.ohgiraffers.springdatajpa.menu.respository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

//DB와 연동하는 구문
//MenuRepository 스프링의 기능을 이용하기 위해 정해진 JPA를 상속
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}

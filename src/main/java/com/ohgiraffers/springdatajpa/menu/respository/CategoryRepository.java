package com.ohgiraffers.springdatajpa.menu.respository;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

//JPA 사용하기 위한 인터페이스이고 extends JpaRepository 작성해줘야 한다.
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /* Category 테이블의 모든 entity를 조회하기 위해서 별도의 JPQL 정의는 필요하징 않지만
    * 예제를 만들기 위해서 작성해본다. (findAll 기능으로 조회 가능) */  //실제는 findAll만 작성해도 된다.


}

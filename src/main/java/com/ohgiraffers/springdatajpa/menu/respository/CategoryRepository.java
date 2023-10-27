package com.ohgiraffers.springdatajpa.menu.respository;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//JPA 사용하기 위한 인터페이스이고 extends JpaRepository 작성해줘야 한다.
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /* Category 테이블의 모든 entity를 조회하기 위해서
    별도의 JPQL 정의는 필요하지 않지만 예제를 만들기 위해서 작성해본다. (findAll 기능으로 조회 가능) */

    /* JPQL */
    //1.메소드 정의 2.@Query 작성 3.value에 원하는
    // @Query(value = "SELECT c FROM Category c ORDER BY c.categoryCode") //from절에 엔티티명(작성하지 않으면 클래스명)
    /* Native */
    //3.구분 자체를 sql구문으로 작성해야 하고 nativeQuery를 true로 작성해줘야 한다.
    //nativeQuery를 엔티티로 반환받고 싶으면 모든 컬럼을 작성해야 한다.
    @Query(value = "SELECT CATEGORY_CODE, CATEGORY_NAME, REF_CATEGORY_CODE FROM TBL_CATEGORY ORDER BY CATEGORY_CODE",
            nativeQuery = true)
    public List<Category> findAllCategory();

}

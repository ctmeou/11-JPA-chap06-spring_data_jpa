package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.respository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service //유의점 1. 빈 등록
public class MenuService {

    //유의점 2. 사용하고자 하는 객체 의존성 주입
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper; //등록한 bean을 사용하기 위해

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.modelMapper =modelMapper;
    }

    //반환 타입을 엔티티라고 적지 않고 DTO라고 적음
    //엔티티는 영속성 컨텍스트에서 관리되는 객체인데 변경이 일어나면 변경이 감지되어 데이터베이스 쪽에서 flush가 된다.
    //조회만 하기 때문에 변경이 될 일은 없지만 혹시나 하는 경우에 있어, 변경이 되는 경우를 막고 싶어서
    //비영속 객체로 취급해서 활용하기 위해 작성한다.
    //서비스레이어에서 조회한 것을 비영속 객체로 반환하는 것
    public MenuDTO findMenuByCode(int menuCode) {

        // Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해서 반환한다.
        //Entity로 조회하는 동작은 menuRepository를 통해서 한다.
        //findById에서 Id는 PK 고유값을 의미한다.
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new); //조회하려는 것이 menu entity

        return modelMapper.map(menu, MenuDTO.class);

    }

}

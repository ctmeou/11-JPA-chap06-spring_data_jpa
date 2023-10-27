package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.respository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    /* 1. id로 entity 조회 : findById */
    public MenuDTO findMenuByCode(int menuCode) {

        // Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해서 반환한다.
        //Entity로 조회하는 동작은 menuRepository를 통해서 한다.
        //findById에서 Id는 PK 고유값을 의미한다.
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new); //조회하려는 것이 menu entity

        return modelMapper.map(menu, MenuDTO.class);

    }

    /* 2-1. 모든 entity 조회 : findAll */
    public List<MenuDTO> findMenuList() {

        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending()); //정렬 기준 작성 시 필드명을 작성해야 한다.

        //return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
        //menuList를 stream으로 바꾸고 싶을 때 stream메소드 사용
        //사용 이유 : 배열이든 컬렉션이든 여러 가지 데이터를 동일한 방식으로 취급하기 위해
        //배열이나 컬렉션의 요소를 순회하거나 가공할 때 다양한 메소드가 있음
        //stream화 시키면 stream이 된다.
        //modelMapper를 통해
        //반환하고 싶은 것은 List<Menu>이기 때문에 collect하고 최종적으로 반환하는 것은 List타입으로 반환이 된다.

        return  menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());

    }

    /* 2-2. 페이징된 entity 조회 : findALl(Pageable) */
    public Page<MenuDTO> findMenuList(Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));

    }
}

package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.Pagenation;
import com.ohgiraffers.springdatajpa.common.PagingButtonInfo;
import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    //값을 뽑아오기 위해 서비스에 요청
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{menuCode}")    //menuCode를 전달하면서 빈등록 @PathVariable : URL 경로에 변수를 넣고 싶을 때, 이 변수의 값을 컨트롤러 메소드의 파라미터로 받아와야 할 때 사용한다.
    public String findMenuByCode(@PathVariable int menuCode, Model model) {

        MenuDTO menu = menuService.findMenuByCode(menuCode);
        model.addAttribute("menu", menu);

        return "menu/detail";

    }

    /* 페이징 이전 버전 */
   /* @GetMapping("/list")    //뷰에 담기 위해 Model객체 사용
    public String findMenuList(Model model) {

        List<MenuDTO> menuList = menuService.findMenuList();
        model.addAttribute("menuList", menuList);

        return "menu/list";

    } */

    /* 페이징 이후 버전 */
    @GetMapping("/list")                    //Pageable 객체 이용
    public String findMenuList(@PageableDefault Pageable pageable, Model model) {

        /* page -> number, size, sort 파라미터가 Pageable 객체에 담긴다. */
        log.info("pageable : {}", pageable);

        //MenuDTO : 컨텐츠  + 다 가지고 있음
        Page<MenuDTO> menuList = menuService.findMenuList(pageable);
        //List가 아닌 Page가 가지고 있는 정보 ↓

        /* Page 객체가 담고 있는 정보 */
        log.info("조회한 내용 목록 : {}", menuList.getContent()); //반환 타입 : List<MenuDTO>
        log.info("총 페이지 수 : {}", menuList.getTotalPages()); //max페이지를 알 수 있다.
        log.info("총 메뉴 수 : {}", menuList.getTotalElements()); //조회될 수 있는 메뉴가 몇 개 있는지 확인 가능하다.
        log.info("해당 페이지에 표시될 요소 수 : {}", menuList.getSize()); //pageable에서 설정한 size가 그대로 담긴다.(몇 개씩 표현할 건지)
        log.info("해당 페이지에 실제 요수 수 : {}", menuList.getNumberOfElements()); //실제로 조회된 요소가 몇 개인지 확인 가능하다.
        log.info("첫 페이지 여부 : {}", menuList.isFirst()); //첫 번째 페이지인지 확인 가능하다.
        log.info("마지막 페이지 여부 : {}", menuList.isLast()); //마지막 페이지인지 확인 가능하다.
        log.info("정렬 방식 : {}", menuList.getSort()); //정렬 방식 확인 가능하다.
        log.info("여러 페이지 중 현재 인덱스 : {}", menuList.getNumber()); //pageable에서 설정한 number가 그대로 담긴다.

        //페이징 바를 만들기 위한 로직 추가
        PagingButtonInfo paging = Pagenation.getPaginButtonInfo(menuList);

        model.addAttribute("menuList", menuList);
        model.addAttribute("paging", paging);

        return "menu/list";

    }

    @GetMapping("/querymethod")
    public void queryMethodPage(){}

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model) {

        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);
        model.addAttribute("menuList", menuList);

        return "menu/searchResult";

    }

    @GetMapping("/regist")
    public void registPage(){}

    @GetMapping("/category")
    @ResponseBody
    public List<CategoryDTO> findCategoryList() {

        //데이터를 바로 보냄(리턴값은 문자열을 사용하고 문자열은 view를 응답, 현재 여기서는 데이터 응답 List<CategoryDTO>가 응답값)
        return menuService.findAllCategory();

    }

    @PostMapping("/regist")
    public String registMenu(MenuDTO menu) {

        menuService.registNewMenu(menu);

        return "redirect:/menu/list"; //저장이 되면 등록이 되었는지 확인하기 위해 menu/list로 이동

    }

    @GetMapping("/modify")
    public void modifyPage(){}

    @PostMapping("/modify") //수정할 내용 메뉴 코드와 메뉴 이름을 MenuDTO로 받는다.
    public String modifyMenu(MenuDTO menu) {

        menuService.modifyMenu(menu);

        return "redirect:/menu/" + menu.getMenuCode(); //수정하고자 하는 메뉴의 코드를 전달 받아왔을 때 그 값을 이용해서 redirect해서 수정된 결과 처리 조회

    }

    @GetMapping("/delete")
    public void deletePage(){}

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam Integer menuCode) {

        menuService.deleteMenu(menuCode);

        return "redirect:/menu/list";

    }


    /*************************************** 과제 ***************************************/

    //1. 메뉴 이름으로 조회하기
    @GetMapping("/searchMenu")
    public void searchMenuPage(){}

    @GetMapping("/searchByName")        //html 내 name 설정값
    public String searchByName(@RequestParam String menuSearch, Model model){
        //void로 작성했기에 /menu/searchByName으로 간다.(템플릿이랑 경로가 일치해야 하고, 일치하지 않을 경우 String으로 작성하고 반환 값 템플릿 위치 작성해야 한다.)

        List<MenuDTO> menuList = menuService.findMenuByName(menuSearch);
        log.info("menuList : {}", menuList);

        model.addAttribute("menuList", menuList);
        //addAttribute는 key(String값)와 value형태(Object)로 전달인자를 보내고, 타임리프에서 사용하는 attribute의 key 값으로 객체를 찾아올 수 있다.

        return "menu/searchResultView";

    }

    //2. 원하는 가격으로 메뉴 조회하기
    @GetMapping("/searchPrice")
    public void searchPricePage(){}

    @GetMapping("/searchByPrice")
    public String searchByPrice(@RequestParam Integer menuPrice1, @RequestParam Integer menuPrice2, Model model) {

        List<MenuDTO> menuList = menuService.findMenuByPriceBetween(menuPrice1, menuPrice2);

        model.addAttribute("menuList", menuList);

        return "menu/searchResultView";

    }

    //3. 전체 메뉴 조회하기 - jpql
    @GetMapping("/searchResultView")
    public String MenuListPage(Model model) {

        List<MenuDTO> menuList = menuService.selectMenuList();

        model.addAttribute("menuList", menuList);

        return "menu/searchResultView";

    }


}

package hello.cafemate.service;

import hello.cafemate.domain.Menu;
import hello.cafemate.web.dto.menu.MenuDto;
import hello.cafemate.dto.update_dto.MenuUpdateDto;
import hello.cafemate.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public MenuDto registerMenu(MenuDto menuDto){
        Menu menu = dtoToEntity(menuDto);
        menuRepository.save(menu);
        return entityToDto(menu);
    }

    public List<MenuDto> findAll(){
        List<Menu> menuList = menuRepository.findAll();
        List<MenuDto> menuDtoList = menuList.stream()
                .map(this::entityToDto).collect(Collectors.toList());

        return menuDtoList;
    }

    public void updateOne(MenuDto menuDto, MenuUpdateDto updateParam){
        Optional<Menu> result = menuRepository.findByName(menuDto.getProduct_name());
        if(result.isEmpty()){
            throw new IllegalStateException("수정하려는 메뉴가 존재하지 않습니다.");
        }

        Menu menu = result.get();
        menuRepository.update(menu.getId(), updateParam);
    }

    public void deleteOne(MenuDto menuDto){
        Optional<Menu> result = menuRepository.findByName(menuDto.getProduct_name());
        if(result.isEmpty()){
            throw new IllegalStateException("삭제하려는 메뉴가 존재하지 않습니다.");
        }

        Menu menu = result.get();
        menuRepository.deleteById(menu.getId());
    }

    //id를 넘겨 제거합니다. id는 view단에서도 추가되어야해서 id도 dto에서 관리하도록 함. 나중에 피드백 부탁
    //Connection is read-only. Queries leading to data modification are not allowed
    //위의 오류떠서 트랜잭션 어노테이션 추가및 false로 바꿨음 원래 default가 false인데 왜그러지; 암튼 이거 추가하고 오류 안생김
    @Transactional(readOnly = false)
    public void deleteById(Long id){

        menuRepository.deleteById(id);
    }

    private Menu dtoToEntity(MenuDto menuDto){
        return new Menu(
                menuDto.getProduct_name(),
                menuDto.getCategory(),
                menuDto.getPrice(),
                menuDto.getOn_sale(),
                menuDto.getRegistrationDate()
        );
    }

    private MenuDto entityToDto(Menu menu){
        return new MenuDto(
                menu.getId(),
                menu.getName(),
                menu.getCategory(),
                menu.getPrice(),
                menu.isOnSale(),
                menu.getRegistrationDate()
        );
    }

}

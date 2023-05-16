package hello.cafemate.service;

import hello.cafemate.domain.Menu;
import hello.cafemate.dto.simple_dto.MenuDto;
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
        Optional<Menu> result = menuRepository.findByName(menuDto.getName());
        if(result.isEmpty()){
            throw new IllegalStateException("수정하려는 메뉴가 존재하지 않습니다.");
        }

        Menu menu = result.get();
        menuRepository.update(menu.getId(), updateParam);
    }

    public void deleteOne(MenuDto menuDto){
        Optional<Menu> result = menuRepository.findByName(menuDto.getName());
        if(result.isEmpty()){
            throw new IllegalStateException("삭제하려는 메뉴가 존재하지 않습니다.");
        }

        Menu menu = result.get();
        menuRepository.deleteById(menu.getId());
    }


    private Menu dtoToEntity(MenuDto menuDto){
        return new Menu(
                menuDto.getName(),
                menuDto.getCategory(),
                menuDto.getPrice(),
                menuDto.isOnSale(),
                menuDto.getRegistrationDate()
        );
    }

    private MenuDto entityToDto(Menu menu){
        return new MenuDto(
                menu.getName(),
                menu.getCategory(),
                menu.getPrice(),
                menu.isOnSale(),
                menu.getRegistrationDate()
        );
    }

}

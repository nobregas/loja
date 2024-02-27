package com.gabnob.shoppingapi.services;

import com.gabnob.shoppingapi.entities.Shop;
import com.gabnob.shoppingapi.repositories.ShopRepository;
import com.gabnob.shoppingclient.dtos.ItemDTO;
import com.gabnob.shoppingclient.dtos.ProductDTO;
import com.gabnob.shoppingclient.dtos.ShopDTO;
import com.gabnob.shoppingclient.dtos.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @InjectMocks
    private ShopService shopService;

    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;
    @Mock
    private ShopRepository shopRepository;

    @Test
    @DisplayName("Objeto criado deve ser igual ao salvo")
    public void test_saveShop() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProductIdentifier("123");
        itemDTO.setPrice(100F);

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setUserIdentifier("123");
        shopDTO.setItems(new ArrayList<>());
        shopDTO.getItems().add(itemDTO);
        shopDTO.setTotal(100F);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductIdentifier("123");
        productDTO.setPreco(100F);

        Mockito.when(userService.getUserByCpf("123", "123"))
                .thenReturn(new UserDTO());
        Mockito.when(productService.getProductByIdentifier("123"))
                .thenReturn(productDTO);
        Mockito.when(shopRepository.save(Mockito.any()))
                .thenReturn(Shop.convert(shopDTO));

        shopDTO = shopService.save(shopDTO, "123");
        Assertions.assertEquals(100F, shopDTO.getTotal());
        Assertions.assertEquals(1, shopDTO.getItems().size());
        Mockito.verify(shopRepository, Mockito.times(1)).save(Mockito.any());


    }

}
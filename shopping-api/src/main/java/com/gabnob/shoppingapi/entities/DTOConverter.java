package com.gabnob.shoppingapi.entities;

import com.gabnob.shoppingclient.dtos.ItemDTO;
import com.gabnob.shoppingclient.dtos.ShopDTO;

import java.util.stream.Collectors;

public class DTOConverter {
    public static ItemDTO convert(Item item) {
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setProductIdentifier(
                item.getProductIdentifier()
        );
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }

    public static ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();

        shopDTO.setUserIdentifier(shop.getUserIdentifier());
        shopDTO.setTotal(shop.getTotal());
        shopDTO.setDate(shop.getDate());
        shopDTO.setItems(
                shop.getItems()
                        .stream().
                        map(DTOConverter::convert).
                        collect(Collectors.toList()));
        return shopDTO;
    }

}

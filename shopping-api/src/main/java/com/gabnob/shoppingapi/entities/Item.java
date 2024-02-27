package com.gabnob.shoppingapi.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.gabnob.shoppingclient.dtos.ItemDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Item {

    private String productIdentifier;
    private Float price;


    public static Item convert(ItemDTO itemDTO) {
        Item item = new Item();

        item.setProductIdentifier(itemDTO.getProductIdentifier());
        item.setPrice(itemDTO.getPrice());
        return item;
    }
}

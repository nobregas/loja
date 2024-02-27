package com.gabnob.productapi.entities;


import com.gabnob.shoppingclient.dtos.ProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private Float preco;
    private String descricao;
    private String productIdentifier;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public static Product convert(ProductDTO productDTO) {
        Product product = new Product();

        product.setNome(productDTO.getNome());
        product.setPreco(productDTO.getPreco());
        product.setDescricao(productDTO.getDescricao());
        product.setProductIdentifier(productDTO.getProductIdentifier());

        if (productDTO.getCategory() != null) {
            product.setCategory(
                    Category.convert(productDTO.getCategory())
            );
        }
        return product;
    }



}

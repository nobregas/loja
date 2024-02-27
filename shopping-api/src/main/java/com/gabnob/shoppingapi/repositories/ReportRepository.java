package com.gabnob.shoppingapi.repositories;


import com.gabnob.shoppingapi.entities.Shop;
import com.gabnob.shoppingclient.dtos.ShopReportDTO;


import java.time.LocalDate;
import java.util.List;

public interface ReportRepository {
    public List<Shop> getShopByFilters(
            LocalDate dataInicio,
            LocalDate dataFim,
            Float valorMinimo
    );

    public ShopReportDTO getReportByDate(
            LocalDate dataInicio,
            LocalDate dataFim
    );

}

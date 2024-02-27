package com.gabnob.shoppingapi.services;

import com.gabnob.shoppingapi.entities.DTOConverter;
import com.gabnob.shoppingapi.entities.Shop;
import com.gabnob.shoppingapi.repositories.ReportRepository;
import com.gabnob.shoppingclient.dtos.ShopDTO;
import com.gabnob.shoppingclient.dtos.ShopReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public List<ShopDTO> getShopsByFilter(
            LocalDate dataInicio,
            LocalDate dataFim,
            Float valorMinimo
    ) {
        List<Shop> shops = reportRepository
                .getShopByFilters(dataInicio, dataFim, valorMinimo);

        return shops.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public ShopReportDTO getReportByDate(
            LocalDate dataInicio,
            LocalDate dataFim
    ) {
        return reportRepository.getReportByDate(dataInicio, dataFim);
    }

}

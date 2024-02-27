package com.gabnob.shoppingapi.controllers;

import com.gabnob.shoppingapi.services.ReportService;
import com.gabnob.shoppingapi.services.ShopService;
import com.gabnob.shoppingclient.dtos.ShopDTO;
import com.gabnob.shoppingclient.dtos.ShopReportDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shopping")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final ReportService reportService;

    @GetMapping
    public List<ShopDTO> getShops() {
        return shopService.getAll();
    }

    @GetMapping("/shopByUser/{userIdentifier}")
    public List<ShopDTO> getShops(@PathVariable String userIdentifier) {
        return shopService.getByUser(userIdentifier);
    }

    @GetMapping("/shopByDate")
    public List<ShopDTO> getShops(@RequestBody ShopDTO shopDTO) {
        return shopService.getByDate(shopDTO);
    }

    @GetMapping("/{id}")
    public ShopDTO findById(@PathVariable Long id) {
        return shopService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopDTO newShop(
            @RequestHeader(name="key", required = true) String key,
            @Valid @RequestBody ShopDTO shopDTO) {
        return shopService.save(shopDTO, key);
    }

    @GetMapping("/search")
    public List<ShopDTO> getShopsByFilter(
            @RequestParam(name= "dataInicio", required = true)
            @DateTimeFormat(pattern = "dd/MM/yyyy")LocalDate dataInicio,

            @RequestParam(name= "dataFim", required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy")LocalDate dataFim,

            @RequestParam(name = "valorMinimo", required = false) Float valorMinimo)   {

        return reportService.getShopsByFilter(dataInicio, dataFim, valorMinimo);
    }

    @GetMapping("/report")
    public ShopReportDTO getReportByDate(
            @RequestParam(name= "dataInicio", required = true)
            @DateTimeFormat(pattern = "dd/MM/yyyy")LocalDate dataInicio,

            @RequestParam(name= "dataFim", required = true)
            @DateTimeFormat(pattern = "dd/MM/yyyy")LocalDate dataFim
    ) {
        return reportService.getReportByDate(dataInicio, dataFim);
    }










}

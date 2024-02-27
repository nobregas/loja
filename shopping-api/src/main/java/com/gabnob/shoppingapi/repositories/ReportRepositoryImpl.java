package com.gabnob.shoppingapi.repositories;

import com.gabnob.shoppingapi.entities.Shop;
import com.gabnob.shoppingclient.dtos.ShopReportDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public String createSql(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {
        StringBuilder sb = new StringBuilder();
        sb.append("select s ");
        sb.append("from shop s ");
        sb.append("where s.date >= :dataInicio ");

        if (dataFim != null) {
            sb.append("and s.date <= :dataFim ");
        }
        if (valorMinimo != null){
            sb.append("and s.total <= :valorMinimo ");
        }
        return sb.toString();
    }

    public Query createQuery(String sql, LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {
        Query query = entityManager.createQuery(sql);
        query.setParameter("dataInicio", dataInicio.atTime(0, 0));

        if (dataFim != null) {
            query.setParameter("dataFim", dataFim.atTime(23, 59));
        }

        if (valorMinimo != null) {
            query.setParameter("valorMinimo", valorMinimo);
        }
        return query;
    }

    @Override
    public List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {
        String sql = createSql(dataInicio, dataFim, valorMinimo);
        Query query = createQuery(sql, dataInicio, dataFim, valorMinimo);

        return query.getResultList();
    }

    public ShopReportDTO shopRepDtoBuilder(Object[] result) {
        ShopReportDTO shopReportDTO = new ShopReportDTO();

        shopReportDTO.setCount((Long) result[0]);
        shopReportDTO.setTotal((Double) result[1]);
        shopReportDTO.setMean((Double) result[2]);

        return shopReportDTO;
    }

    @Override
    public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(sp.id), sum(sp.total), avg(sp.total) ");
        sb.append("from shopping.shop sp ");
        sb.append("where sp.date >= :dataInicio ");
        sb.append("and sp.date <= :dataFim ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("dataInicio", dataInicio.atTime(0, 0));
        query.setParameter("dataFim", dataFim.atTime(23, 59));

        Object[] result = (Object[]) query.getSingleResult();
        return shopRepDtoBuilder(result);

    }
}

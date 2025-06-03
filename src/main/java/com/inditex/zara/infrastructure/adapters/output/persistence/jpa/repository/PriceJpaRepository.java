package com.inditex.zara.infrastructure.adapters.output.persistence.jpa.repository;

import com.inditex.zara.infrastructure.adapters.output.persistence.jpa.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para operaciones de persistencia de precios.
 * Nombre más claro y métodos simplificados.
 */
@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Encuentra el precio aplicable con mayor prioridad para los criterios dados.
     * Utiliza ORDER BY y LIMIT para obtener directamente el precio de mayor prioridad.
     */
    @Query("""
        SELECT p FROM PriceEntity p 
        WHERE p.brandId = :brandId 
        AND p.productId = :productId 
        AND :date BETWEEN p.startDate AND p.endDate
        ORDER BY p.priority DESC
        LIMIT 1
        """)
    Optional<PriceEntity> findApplicablePrice(
            @Param("brandId") Integer brandId,
            @Param("productId") Long productId,
            @Param("date") LocalDateTime date
    );

    /**
     * Encuentra todos los precios aplicables para los criterios dados.
     * Útil cuando se necesita aplicar lógica de negocio adicional.
     */
    @Query("""
        SELECT p FROM PriceEntity p 
        WHERE p.brandId = :brandId 
        AND p.productId = :productId 
        AND :date BETWEEN p.startDate AND p.endDate
        ORDER BY p.priority DESC
        """)
    List<PriceEntity> findAllApplicablePrices(
            @Param("brandId") Integer brandId,
            @Param("productId") Long productId,
            @Param("date") LocalDateTime date
    );
} 
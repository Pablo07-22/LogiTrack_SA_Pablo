package com.Proyecto.LogiTrack.SA.Validaciones;

import com.Proyecto.LogiTrack.SA.Exception.BussinesRuleException;
import com.Proyecto.LogiTrack.SA.Model.Bodega;
import com.Proyecto.LogiTrack.SA.Model.InventarioBodega;
import com.Proyecto.LogiTrack.SA.Repository.InventarioBodegaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Validaciones {

    private final InventarioBodegaRepository inventarioBodegaRepository;

    // Obtiene la cantidad total almacenada en una bodega.
    public long obtenerStockTotal(Long bodegaId) {
        Long total = inventarioBodegaRepository.sumarStockPorBodega(bodegaId);
        return total == null ? 0L : total;
    }

    // Valida que no se reduzca la capacidad por debajo del stock existente.
    public void validarCapacidadAlActualizarBodega(
            Bodega bodega,
            BigDecimal nuevaCapacidad
    ) {
        long stockActual = obtenerStockTotal(bodega.getId());
        if (BigDecimal.valueOf(stockActual).compareTo(nuevaCapacidad) > 0) {
            throw new BussinesRuleException("No se puede reducir la capacidad. La bodega tiene " + stockActual + " unidades almacenadas.");
        }
    }

    // Valida entradas y transferencias hacia una bodega.
    public void validarCapacidadDisponible(Bodega bodega, long unidadesAIngresar) {
        if (unidadesAIngresar <= 0) {
            throw new BussinesRuleException("Las unidades que se desean ingresar deben ser mayores que cero.");
        }
        long stockActual = obtenerStockTotal(bodega.getId());
        long stockProyectado = stockActual + unidadesAIngresar;
        if (BigDecimal.valueOf(stockProyectado).compareTo(bodega.getCapacidad()) > 0) {
            BigDecimal disponible = bodega.getCapacidad().subtract(BigDecimal.valueOf(stockActual));
            throw new BussinesRuleException("Capacidad excedida. Capacidad: " + bodega.getCapacidad()
                            + ", ocupado: " + stockActual + ", disponible: " + disponible.max(BigDecimal.ZERO) + ", unidades solicitadas: " + unidadesAIngresar
            );
        }
    }

    // Valida creación y actualización directa de inventarios.
    public void validarCapacidadAlGuardarInventario(Bodega nuevaBodega, InventarioBodega inventarioActual, int nuevoStock) {
        if (nuevoStock < 0) {
            throw new BussinesRuleException("El stock no puede ser negativo.");
        }
        long stockActual = obtenerStockTotal(nuevaBodega.getId());
        // Si se actualiza un inventario existente en la misma bodega,
        // primero se descuenta su stock anterior.
        if (inventarioActual != null && inventarioActual.getBodega() != null && inventarioActual.getBodega().getId().equals(nuevaBodega.getId())) {
            stockActual -= inventarioActual.getStock();
        }
        long stockProyectado = stockActual + nuevoStock;
        if (BigDecimal.valueOf(stockProyectado).compareTo(nuevaBodega.getCapacidad()) > 0) {
            throw new BussinesRuleException("No se puede guardar el inventario porque supera " + "la capacidad de la bodega. Capacidad: "
                            + nuevaBodega.getCapacidad() + ", stock proyectado: " + stockProyectado
            );
        }
    }
}

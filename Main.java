package com.company;

import java.util.*;
import java.util.stream.Collectors;

class TiendaDeRopa {

    List<Venta> ventas;

    double gananciaDelDia(Date fecha) {
       return ventasDelDia(fecha).stream().mapToDouble(venta -> venta.precioTotal()).sum();
    }

    List<Venta> ventasDelDia(java.util.Date fecha) {
        return ventas.stream().filter(venta -> venta.esDelDia(fecha)).collect(Collectors.toList());
    }

    void registrarVenta(Venta venta) {
        ventas.add(venta);
    }

    List<Venta> ventas() {
        return ventas;
    }
}

class Venta {

    java.util.Date fecha = new Date();
    List<Prenda> prendas;
    TipoDePago pago;

    Venta(List<Prenda> prendas, TipoDePago pago) {
        this.prendas = prendas;
        this.pago = pago;
    }

    double precioParcial() {
        return prendas.stream().mapToDouble(prenda -> prenda.getPrecio()).sum();
    }

    double precioTotal() {
        return pago.precioFinal(this.precioParcial());
    }

    boolean esDelDia(Date fecha) {
        return this.fecha.equals(fecha);
    }

}

interface TipoDePago {
    double precioFinal(double precio);
}

class Efectivo implements TipoDePago {

    @Override
    public double precioFinal(double precio) {
        return 0;
    }

}

class Tarjeta implements TipoDePago {

    int cantidadDeCuotas;
    double coeficiente;

    Tarjeta(int cuotas, double coef) {
        cantidadDeCuotas = cuotas;
        coeficiente = coef;
    }

    @Override
    public double precioFinal(double precio) {
        return cantidadDeCuotas * coeficiente + 0.01 * precio;
    }

}

class Prenda {

    String tipo;
    double precio;
    Estado estado;

    Prenda( String tipo, double precio, Estado estado) {
        this.precio = precio;
        this.tipo = tipo;
        this.estado = estado;
    }

    double getPrecio() {
        return precio - estado.descuento(precio);
    }

}

interface Estado {
    double descuento(double precio);
}

class Nueva implements Estado {

    @Override
    public double descuento(double precio) {
        return 0;
    }
}

class Promocion implements Estado {

    double valorPromo;

    Promocion(double promo) {
        valorPromo = promo;
    }

    @Override
    public double descuento(double precio) {
        return valorPromo;
    }
}

class Liquidacion implements Estado {

    @Override
    public double descuento(double precio) {
        return precio * 0.5;
    }
}
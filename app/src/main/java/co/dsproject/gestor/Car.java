package co.dsproject.gestor;

import java.time.LocalDate;
import java.util.Date;

public class Car implements Comparable<Car> {

    private String owner;
    private String placa;
    private String marca;
    private String linea;
    private int modelo;
    private LocalDate ulltimo_mantenimiento;
    private int frecuencia_mantenimiento;
    private LocalDate soat;
    private LocalDate rtm;
    private LocalDate poliza;
    private LocalDate impuesto;

    public Car(String owner, String placa, String marca, String linea, int modelo, LocalDate ulltimo_mantenimiento,
        int frecuencia_mantenimiento, LocalDate soat, LocalDate rtm, LocalDate poliza, LocalDate impuesto){
        this.owner = owner;
        this.placa = placa;
        this.marca = marca;
        this.linea = linea;
        this.modelo = modelo;
        this.ulltimo_mantenimiento = ulltimo_mantenimiento;
        this.frecuencia_mantenimiento = frecuencia_mantenimiento;
        this.soat = soat;
        this.rtm = rtm;
        this.poliza = poliza;
        this.impuesto = impuesto;
    }


    @Override
    public int compareTo(Car o) {
        return this.placa.compareTo(o.placa);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public LocalDate getUlltimo_mantenimiento() {
        return ulltimo_mantenimiento;
    }

    public void setUlltimo_mantenimiento(LocalDate ulltimo_mantenimiento) {
        this.ulltimo_mantenimiento = ulltimo_mantenimiento;
    }

    public int getFrecuencia_mantenimiento() {
        return frecuencia_mantenimiento;
    }

    public void setFrecuencia_mantenimiento(int frecuencia_mantenimiento) {
        this.frecuencia_mantenimiento = frecuencia_mantenimiento;
    }

    public LocalDate getSoat() {
        return soat;
    }

    public void setSoat(LocalDate soat) {
        this.soat = soat;
    }

    public LocalDate getRtm() {
        return rtm;
    }

    public void setRtm(LocalDate rtm) {
        this.rtm = rtm;
    }

    public LocalDate getPoliza() {
        return poliza;
    }

    public void setPoliza(LocalDate poliza) {
        this.poliza = poliza;
    }

    public LocalDate getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(LocalDate impuesto) {
        this.impuesto = impuesto;
    }
}

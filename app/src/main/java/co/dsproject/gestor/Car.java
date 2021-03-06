package co.dsproject.gestor;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Car implements Comparable<Car> {

    private String owner;
    private String placa;
    private String marca;
    private String linea;
    private int modelo;
    private String ultimo_mantenimiento;
    private int frecuencia_mantenimiento;
    private String soat;
    private String rtm;
    private String poliza;
    private String impuesto;

    public Car(String placa) {
        this.placa = placa;
    }

    public Car() {
    }

    public Car(String owner, String placa, String marca, String linea, int modelo, String ultimo_mantenimiento, int frecuencia_mantenimiento, String soat, String rtm, String poliza, String impuesto) {
        this.owner = owner;
        this.placa = placa;
        this.marca = marca;
        this.linea = linea;
        this.modelo = modelo;
        this.ultimo_mantenimiento = ultimo_mantenimiento;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(Objects.hash((Car) obj) == Objects.hash(this)) {
            return placa.equals((((Car) obj).placa));
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        for(int i = 0; i < placa.length(); i++) {
            hashcode = hashcode + placa.charAt(i);
        }
        return hashcode;
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

    public String getUltimo_mantenimiento() {
        return ultimo_mantenimiento;
    }

    public void setUltimo_mantenimiento(String ultimo_mantenimiento) {
        this.ultimo_mantenimiento = ultimo_mantenimiento;
    }


    public int getFrecuencia_mantenimiento() {
        return frecuencia_mantenimiento;
    }

    public void setFrecuencia_mantenimiento(int frecuencia_mantenimiento) {
        this.frecuencia_mantenimiento = frecuencia_mantenimiento;
    }

    public String getSoat() {
        return soat;
    }

    public void setSoat(String soat) {
        this.soat = soat;
    }

    public String getRtm() {
        return rtm;
    }

    public void setRtm(String rtm) {
        this.rtm = rtm;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }
}

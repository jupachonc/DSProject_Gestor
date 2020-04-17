package co.dsproject.gestor;

import java.util.Date;

public class Car implements Comparable<Car> {

    private String owner;
    private String placa;
    private String marca;
    private String linea;
    private int modelo;
    private Date ulltimo_mantenimiento;
    private int frecuencia_mantenimiento;
    private Date soat;
    private Date rtm;
    private Date poliza;
    private Date impuesto;

    public Car(String owner, String placa, String marca, String linea, int modelo, Date ulltimo_mantenimiento,
        int frecuencia_mantenimiento, Date soat, Date rtm, Date poliza, Date impuesto){
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
}

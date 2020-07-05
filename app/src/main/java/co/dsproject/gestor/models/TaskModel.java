package co.dsproject.gestor.models;

import java.time.LocalDate;
import java.util.Objects;

public class TaskModel implements Comparable<TaskModel> {

    int tipo;
    String placa;
    String fecha;

    public TaskModel(int tipo, String placa, LocalDate fecha) {
        this.tipo = tipo;
        this.placa = placa;
        this.fecha = fecha.toString();
    }

    public TaskModel() {
    }

    @Override
    public int compareTo(TaskModel o) {
        return this.fecha.compareTo(o.fecha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskModel taskModel = (TaskModel) o;
        return tipo == taskModel.tipo &&
                Objects.equals(placa, taskModel.placa) &&
                Objects.equals(fecha, taskModel.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, placa, fecha);
    }

    public int getTipo() {
        return tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getFecha() {
        return fecha;
    }
}

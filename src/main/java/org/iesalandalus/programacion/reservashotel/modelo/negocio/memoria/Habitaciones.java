package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;

public class Habitaciones {
    private ArrayList<Habitacion> coleccionHabitaciones = new ArrayList<>();

    public Habitaciones() {

    }

    public ArrayList<Habitacion> get() {
        return copiaProfundaHabitaciones();
    }

    private ArrayList<Habitacion> copiaProfundaHabitaciones() {
        ArrayList<Habitacion> copiaHabitaciones = new ArrayList<>();
        Iterator<Habitacion> copiaHabitacionIterador = coleccionHabitaciones.iterator();
        while(copiaHabitacionIterador.hasNext()) {
            Habitacion habitacion = new Habitacion(copiaHabitacionIterador.next());
            copiaHabitaciones.add(habitacion);
        }
        return copiaHabitaciones;
    }

    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion) {

        ArrayList<Habitacion> listaTipoHabitacion = new ArrayList<>();
        Iterator<Habitacion> listaTipoHabitacionIterador = get().iterator();
        while(listaTipoHabitacionIterador.hasNext()) {
            Habitacion habitacion = listaTipoHabitacionIterador.next();
            if(habitacion.getTipoHabitacion().equals(tipoHabitacion)) {
                listaTipoHabitacion.add(habitacion);
            }
        }
        /*
        for(int i = 0;i < get().size(); i++) {
            if(coleccionHabitaciones.get(i).getTipoHabitacion().equals(tipoHabitacion)) {
                listaTipoHabitacion.add(coleccionHabitaciones.get(i));
            }
        }

         */
        return listaTipoHabitacion;
    }




    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if(coleccionHabitaciones.contains(habitacion)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }
        if(coleccionHabitaciones.contains(habitacion)) {
            Iterator<Habitacion> iteradorHabitacion = coleccionHabitaciones.iterator();
            while(iteradorHabitacion.hasNext()) {
                if(habitacion.equals(iteradorHabitacion.next())) {
                    return new Habitacion(habitacion);
                }
            }
        }
        return null;
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }

        if (!coleccionHabitaciones.contains(habitacion)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
        coleccionHabitaciones.remove(habitacion);
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }
}

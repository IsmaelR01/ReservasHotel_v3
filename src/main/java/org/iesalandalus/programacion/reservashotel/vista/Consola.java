package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.iesalandalus.programacion.reservashotel.modelo.Modelo.*;

import static org.iesalandalus.programacion.reservashotel.modelo.Modelo.habitaciones;
import static org.iesalandalus.programacion.reservashotel.modelo.Modelo.huespedes;


public class Consola {
    private static Vista vista;
    private static Controlador controlador;
    private Consola() {

    }

    public static void mostrarMenu() {
        System.out.println("Menu de Opciones");
        Opcion[] opciones = Opcion.values();
        for (int i = 0; i < opciones.length; i++) {
            System.out.println(opciones[i].toString());
        }
    }

    public static Opcion elegirOpcion() {
        int opcionEscogida;
        do {
            System.out.println("Elija  una opción válida");
            opcionEscogida = Entrada.entero();
            if (opcionEscogida < 1 || opcionEscogida > Opcion.values().length) {
                System.out.println("Opción incorrecta, inténtalo de nuevo.");
            }
        } while (opcionEscogida < 1 || opcionEscogida > Opcion.values().length);
        return Opcion.values()[opcionEscogida - 1];
    }

    public static Huesped leerHuesped() {
        System.out.println("Introduce los datos del huésped");
        System.out.println("Nombre");
        String nombre = Entrada.cadena();
        System.out.println("DNI");
        String dni = Entrada.cadena();
        System.out.println("Correo electrónico");
        String correo = Entrada.cadena();
        System.out.println("Número de teléfono");
        String telefono = Entrada.cadena();
        return new Huesped(nombre, dni, correo, telefono, leerfecha("Fecha de nacimiento formato (dd/MM/YYYY)"));
    }

    public static Huesped getHuespedPorDni() {
        System.out.println("Introduce el dni del huésped");
        String dni = Entrada.cadena();
        ArrayList<Huesped> busquedaHuesped = huespedes.get(); // Obtener el array de huéspedes
        for (int i = 0; i < busquedaHuesped.size(); i++) {
            Huesped huesped = busquedaHuesped.get(i);
            if (huesped.getDni().equals(dni)) {
                return new Huesped(huesped.getNombre(), dni, huesped.getCorreo(), huesped.getTelefono(), huesped.getFechaNacimiento());
            }
        }
        return new Huesped("Spiderman Rodríguez Cabrera",dni,"spidermanr46@gmail.com","675679876",LocalDate.of(2001,5,23));
    }


    public static LocalDate leerfecha(String mensaje) {
        LocalDate fechaFinal = null;
        String fecha;
        do {
            System.out.println(mensaje);
            fecha = Entrada.cadena();
            try {
                fechaFinal = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido. Por favor, introduzca la fecha  formato (dd/MM/yyyy)");
            }
        } while (fechaFinal == null);
        return fechaFinal;
    }

    public static LocalDateTime leerFechaHora(String mensaje) {
        String fecha;
        LocalDateTime fechaFinal = null;
        do {
            System.out.println(mensaje);
            fecha = Entrada.cadena();
            try {
                fechaFinal = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha y hora no válido. Por favor, introduzca la fecha y hora formato (dd/MM/yyyy HH:mm:ss)");
            }
        } while (fechaFinal == null);
        return fechaFinal;
    }


    public static Habitacion leerHabitacion() {

        System.out.println("Introduce los datos de la habitación");
        System.out.println("Planta");
        int planta = Entrada.entero();
        System.out.println("Puerta");
        int puerta = Entrada.entero();
        System.out.println("Precio");
        double precio = Entrada.realDoble();
        return new Habitacion(planta, puerta, precio, leerTipoHabitacion());
    }


    public static Habitacion leerHabitacionPorIdentificador() {
            System.out.println("Introduce el numero de planta");
            int planta = Entrada.entero();
            System.out.println("Introduce el número de puerta");
            int puerta = Entrada.entero();
            String identificador = "" + planta + puerta;
        ArrayList<Habitacion> busquedaHabitacion = habitaciones.get();
        for (int i = 0; i < busquedaHabitacion.size(); i++) {
            Habitacion habitacion = busquedaHabitacion.get(i);
            if(habitacion.getIdentificador().equals(identificador)) {
                return new Habitacion(planta,puerta,habitacion.getPrecio(),habitacion.getTipoHabitacion());
            }
        }
        return new Habitacion(planta,puerta,145,TipoHabitacion.DOBLE);
    }

    public static TipoHabitacion leerTipoHabitacion() {
        int opcionEscogida;
        do {
            System.out.println("Menu de Opciones Tipos de Habitación");
            TipoHabitacion[] tipoHabitacion = TipoHabitacion.values();
            for (int i = 0; i < tipoHabitacion.length; i++) {
                System.out.println(tipoHabitacion[i].toString());
            }
            System.out.println("Elija un tipo de habitación");
            opcionEscogida = Entrada.entero();
            if (opcionEscogida < 1 || opcionEscogida > TipoHabitacion.values().length) {
                System.out.println("Opción incorrecta, inténtalo de nuevo.");
            }
        } while (opcionEscogida < 1 || opcionEscogida > TipoHabitacion.values().length);
        return TipoHabitacion.values()[opcionEscogida - 1];
    }


    public static Regimen leerRegimen() {
        int opcionEscogida;
        do {
            System.out.println("Menu de Opciones Régimen");
            Regimen[] regimen = Regimen.values();
            for (int i = 0; i < regimen.length; i++) {
                System.out.println(regimen[i].toString());
            }
            System.out.println("Elija un Régimen");
            opcionEscogida = Entrada.entero();
            if (opcionEscogida < 1 || opcionEscogida > Regimen.values().length) {
                System.out.println("Opción incorrecta, inténtalo de nuevo.");
            }
        } while (opcionEscogida < 1 || opcionEscogida > Regimen.values().length);
        return Regimen.values()[opcionEscogida - 1];
    }

    public static Reserva leerReserva() {
        System.out.println("Introduce el número de personas");
        int numPersonas = Entrada.entero();
        return new Reserva(getHuespedPorDni(), leerHabitacionPorIdentificador(), leerRegimen(), leerfecha("Introduce fecha inicio reserva formato (dd/MM/YYYY)"), leerfecha("Introduce la fecha de fin de la reserva formato (dd/MM/YYYY) "),numPersonas);
    }
}




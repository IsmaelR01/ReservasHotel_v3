package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Vista {
    private static Controlador controlador;



    public void setControlador(Controlador controlador) {
        if(controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            ejecutarOpcion(opcion = Consola.elegirOpcion());
        }while(opcion != Opcion.SALIR);
    }

    public void terminar() {
        controlador.terminar();
    }

    private static void ejecutarOpcion(Opcion opcion) {
        switch (opcion) {
            case SALIR:
                System.out.println("Adios, hasta luego");
                break;
            case INSERTAR_HUESPED:
                insertarHuesped();
                break;
            case BUSCAR_HUESPED:
                buscarHuesped();
                break;
            case BORRAR_HUESPED:
                borrarHuesped();
                break;
            case MOSTRAR_HUESPEDES:
                mostrarHuespedes();
                break;
            case INSERTAR_HABITACION:
                insertarHabitacion();
                break;
            case BUSCAR_HABITACION:
                buscarHabitacion();
                break;
            case BORRAR_HABITACION:
                borrarHabitacion();
                break;
            case MOSTRAR_HABITACIONES:
                mostrarHabitaciones();
                break;
            case INSERTAR_RESERVA:
                insertarReserva();
                break;
            case ANULAR_RESERVA:
                anularReserva();
                break;
            case MOSTRAR_RESERVAS:
                mostrarReservas();
                break;
            case CONSULTAR_DISPONIBILIDAD:
                consultarDisponibilidad(Consola.leerTipoHabitacion(),Consola.leerfecha("Introduce fecha inicio reserva formato (dd/MM/yyyy)"),Consola.leerfecha("Introduce fecha fin reserva formato (dd/MM/yyyy)"));
                break;
            case REALIZAR_CHECKIN:
                realizarCheckIn();
                break;
            case REALIZAR_CHECKOUT:
                realizarCheckOut();

        }

    }



    private static void insertarHuesped() {
        try {
            controlador.insertar(Consola.leerHuesped());
            System.out.println("Hu�sped insertado correctamente.");
        }catch(NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    private static void buscarHuesped() {
        try {
            Huesped huesped = controlador.buscar(Consola.getHuespedPorDni());
            if(huesped == null) {
                System.out.println("El huésped introducido no existe.");
            }else {
                System.out.println(huesped);
            }
        }catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    private static void borrarHuesped() {
        try {
            controlador.borrar(Consola.getHuespedPorDni());
            System.out.println("Huesped borrado correctamente.");
        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    private static void mostrarHuespedes() {
        if(controlador.getHuespedes().isEmpty()) {
            System.out.println("No hay hu�spedes para mostrar.");
        }else{
            ArrayList<Huesped> huespedesOrdenados = controlador.getHuespedes();
            Collections.sort(huespedesOrdenados,Comparator.comparing(Huesped::getNombre));
            Iterator<Huesped> huespedesIterador = huespedesOrdenados.iterator();
            while(huespedesIterador.hasNext()) {
                Huesped huesped = huespedesIterador.next();
                System.out.println(huesped.toString());
            }
            /*
            for(int i = 0; i<controlador.getHuespedes().length;i++) {
                System.out.println(controlador.getHuespedes()[i].toString());
            }

             */
        }
    }

    private static void insertarHabitacion() {
        try {
            controlador.insertar(Consola.leerHabitacion());
            System.out.println("Habitaci�n insertada correctamente");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }
    private static void buscarHabitacion() {
        try {
            Habitacion habitacion = controlador.buscar(Consola.leerHabitacionPorIdentificador());
            if(habitacion == null) {
                System.out.println("La habitación introducida no existe.");
            }else {
                System.out.println(habitacion);
            }
        }catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    private static void borrarHabitacion() {
        try {
            controlador.borrar(Consola.leerHabitacionPorIdentificador());
            System.out.println("Habitaci�n borrada correctamente.");
        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    private static void mostrarHabitaciones() {
        if(controlador.getHabitaciones().isEmpty()) {
            System.out.println("No hay habitaciones para mostrar.");
        }else{
            ArrayList<Habitacion> habitacionesOrdenadas = controlador.getHabitaciones();
            Collections.sort(habitacionesOrdenadas,Comparator.comparing(Habitacion::getIdentificador));
            Iterator<Habitacion> habitacionesIterador = habitacionesOrdenadas.iterator();
            while(habitacionesIterador.hasNext()) {
                Habitacion habitacion = habitacionesIterador.next();
                System.out.println(habitacion.toString());
            }
            /*
            for(int i = 0; i<controlador.getHabitaciones().length;i++) {
                System.out.println(controlador.getHabitaciones()[i].toString());
            }

             */
        }
    }

    private static void insertarReserva() {
        try {
            Reserva reserva = Consola.leerReserva();
            System.out.println("Ahora consulta disponibilidad");
            if(!controlador.getReservas().isEmpty()) {
                if(consultarDisponibilidad(reserva.getHabitacion().getTipoHabitacion(),reserva.getFechaInicioReserva(),reserva.getFechaFinReserva()) == null) {
                    System.out.println("La habitaci�n no est� disponible");
                }else{
                    System.out.println("Hay habitaciones disponibles, se puede insertar la reserva.");
                    controlador.insertar(reserva);
                    System.out.println("Reserva insertada correctamente.");
                }

            }else{
                controlador.insertar(reserva);
                System.out.println("Reserva insertada correctamente.");
            }
        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException  e) {
            System.out.println("-" + e.getMessage());
        }
    }

    private static void listarReservas(Huesped huesped) {
        if(huesped == null) {
            System.out.println("El hu�sped no puede ser nulo.");
        }
        try {
            ArrayList<Reserva> reservasHuesped = controlador.getReservas(huesped);
            if (reservasHuesped.isEmpty()) {
                System.out.println("El hu�sped no tiene reservas.");
            } else {
                System.out.println("Reservas del hu�sped que has buscado:");
                Comparator<Habitacion> habitacionComparator = Comparator.comparing(Habitacion::getIdentificador);
                Collections.sort(reservasHuesped,Comparator.comparing(Reserva::getFechaInicioReserva).thenComparing(Reserva::getHabitacion,habitacionComparator));
                int contador = 1;
                Iterator<Reserva> reservasHuespedIterador = reservasHuesped.iterator();
                while(reservasHuespedIterador.hasNext()) {
                     Reserva reserva = reservasHuespedIterador.next();
                    System.out.println(contador +".- " + reserva.toString());
                    contador++;
                }
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    private static void listarReservas(TipoHabitacion tipoHabitacion) {
        if(tipoHabitacion == null) {
            System.out.println("El tipo de habitaci�n no puede ser nulo");
        }
        try {
            ArrayList<Reserva> reservasTipoHabitacion = controlador.getReservas(tipoHabitacion);
            if (reservasTipoHabitacion.isEmpty()) {
                System.out.println("No hay reservas para el tipo de habitaci�n " + tipoHabitacion + ".");
            } else {
                System.out.println("Reservas de habitaciones tipo " + tipoHabitacion + ":");
                Comparator<Huesped> huespedComparator = Comparator.comparing(Huesped::getNombre);
                Collections.sort(reservasTipoHabitacion,Comparator.comparing(Reserva::getFechaInicioReserva).thenComparing(Reserva::getHuesped,huespedComparator));
                Iterator<Reserva> reservasTipoHabitacionIterador = reservasTipoHabitacion.iterator();
                while(reservasTipoHabitacionIterador.hasNext()) {
                    Reserva reserva = reservasTipoHabitacionIterador.next();
                    System.out.println(reserva.toString());
                }
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private static ArrayList<Reserva> getReservasAnulables(ArrayList<Reserva> reservaAAnular) {
        ArrayList<Reserva> reservasAnulables = new ArrayList<>();
        Iterator<Reserva> reservasAnulablesIterador = reservaAAnular.iterator();
        while(reservasAnulablesIterador.hasNext()) {
            Reserva reserva = reservasAnulablesIterador.next();
            if(reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasAnulables.add(reserva);
            }
        }
        /*
        int contadorReservasAnulables = 0;

        for (int i = 0; i < reservaAAnular.length; i++) {
            Reserva reserva = reservaAAnular[i];
            if (reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                contadorReservasAnulables++;
            }
        }

        Reserva[] reservasAnulables = new Reserva[contadorReservasAnulables];
        int contador = 0;

        for (int i = 0; i < reservasAnulables.length; i++) {
            Reserva reserva = reservaAAnular[i];
            if (reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasAnulables[contador++] = reserva;
            }
        }

         */
        return reservasAnulables;
    }

    private static void anularReserva() {
        try {
            Huesped huesped = Consola.getHuespedPorDni();

            ArrayList<Reserva> reservasAnulables = getReservasAnulables(controlador.getReservas(huesped));
            if (reservasAnulables.isEmpty()) {
                System.out.println("El hu�sped no tiene reservas anulables.");
            }else {
                System.out.println("Seleccione la reserva que desea anular:");
                Iterator<Reserva> reservasAnulablesIterador = reservasAnulables.iterator();
                while(reservasAnulablesIterador.hasNext()) {
                    Reserva reservaAnulable = reservasAnulablesIterador.next();
                    System.out.println(reservaAnulable.toString());
                }
            /*
            for (int i = 0; i < reservasAnulables.length; i++) {
                System.out.println((i + 1) + ".-" + reservasAnulables[i].toString());
            }

             */

                int opcion;
                do {
                    System.out.println("Ingrese el n�mero de la reserva a anular:");
                    opcion = Entrada.entero();
                    if (opcion < 1 || opcion > reservasAnulables.size()) {
                        System.out.println("Opci�n incorrecta, int�ntalo de nuevo.");
                    }
                } while (opcion < 1 || opcion > reservasAnulables.size());


                char respuesta;
                do {
                    System.out.println("�Est� seguro de que desea anular la reserva? (s/n)");
                    respuesta = Character.toLowerCase(Entrada.caracter());
                    if (respuesta != 's' && respuesta != 'n') {
                        System.out.println("Respuesta incorrecta, por favor ingrese 's' o 'n'.");
                    }
                } while (respuesta != 's' && respuesta != 'n');

                if (respuesta == 's') {
                    controlador.borrar(reservasAnulables.get(opcion - 1));
                    System.out.println("Reserva anulada correctamente.");
                } else {
                    System.out.println("Anulaci�n de reserva cancelada.");
                }
            }

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("-" + e.getMessage());
        }
    }

    private static void mostrarReservas() {
        int opcion;
        do{
            System.out.println("Elija una opción.");
            System.out.println("1.- Mostrar todas las reservas.");
            System.out.println("2.- Mostrar las reservas por huésped.");
            System.out.println("3.- Mostrar las reservas por tipo de habitación.");
            opcion = Entrada.entero();
            if(opcion < 1 || opcion > 3) {
                System.out.println("Opción no válida, inténtelo de nuevo.");
            }
        }while(opcion < 1 || opcion > 3);
        switch (opcion) {
            case 1:
                ArrayList<Reserva> reservasTotales = controlador.getReservas();
                if(reservasTotales.isEmpty()) {
                    System.out.println("No hay reservas para mostrar.");
                }else{
                    Comparator<Habitacion> habitacionComparator = Comparator.comparing(Habitacion::getIdentificador);
                    Collections.sort(reservasTotales,Comparator.comparing(Reserva::getFechaInicioReserva).thenComparing(Reserva::getHabitacion,habitacionComparator));
                    Iterator<Reserva> todasReservasIterador = reservasTotales.iterator();
                    while(todasReservasIterador.hasNext()) {
                        System.out.println(todasReservasIterador.next().toString());
                    }
                    /*
                    for(int i = 0; i<controlador.getReservas().length;i++) {
                        System.out.println(controlador.getReservas()[i].toString());
                    }

                     */
                }
                break;
            case 2:
                try {
                    listarReservas(Consola.getHuespedPorDni());
                }catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println("-" + e.getMessage());
                }
                break;
            case 3:
                listarReservas(Consola.leerTipoHabitacion());

        }

    }


    private static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        ArrayList<Habitacion> habitacionesTipoSolicitado= controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.size() && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado.get(i)!=null)
            {
                ArrayList<Reserva> reservasFuturas = controlador.getReservasFuturas(habitacionesTipoSolicitado.get(i));
                numElementos=getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que est� disponible.
                    habitacionDisponible=new Habitacion(habitacionesTipoSolicitado.get(i));
                    tipoHabitacionEncontrada=true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posici�n 0), quiere decir que la habitaci�n est� disponible en las fechas indicadas.

                    Collections.sort(reservasFuturas,Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                    mostrar(reservasFuturas);*/

                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())) {
                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posici�n 0), quiere decir que la habitaci�n est� disponible en las fechas indicadas.

                        Collections.sort(reservasFuturas,Comparator.comparing(Reserva::getFechaInicioReserva));

                        /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                        mostrar(reservasFuturas);*/

                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas est�n alg�n hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.size() && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas.get(j)!=null && reservasFuturas.get(j - 1)!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas.get(j - 1).getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {

                                    habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }


                }
            }
        }

        return habitacionDisponible;
    }

    private static int getNumElementosNoNulos(ArrayList<Reserva> reservasFuturas) {
        Iterator<Reserva> reservasFuturasIterador = reservasFuturas.iterator();
        int contador = 0;
        while(reservasFuturasIterador.hasNext()) {
            contador++;
            reservasFuturasIterador.next();

        }
        /*
        int contador = 0;
        for(int i = 0; i < reservasFuturas.size(); i++) {
            if(reservasFuturas.get(i)!= null) {
                contador++;
            }
        }

         */
        return contador;
    }

    private static void realizarCheckIn() {
        try {
            Huesped huesped = Consola.getHuespedPorDni();
            ArrayList<Reserva> reservas = controlador.getReservas(huesped);
            if(reservas.isEmpty()) {
                System.out.println("No hay ninguna reserva, por lo que no se puede hacer el check-in");
            }else {
                listarReservas(huesped);


                int opcion;
                do {
                    System.out.println("Inserte Opción");
                    opcion = Entrada.entero();
                } while (opcion < 1 || opcion > reservas.size());
                Reserva reservaFinal = reservas.get(opcion - 1);
                if (reservaFinal.getCheckIn() == null) {
                    controlador.realizarCheckIn(reservaFinal,Consola.leerFechaHora("Introduce la fecha y hora del check-in formato(dd/MM/yyyy hh:mm:ss)"));
                    System.out.println("Check-In actualizado correctamente.");
                } else {
                    System.out.println("Esta reserva ya tiene realizado el Check-In.");
                }
            }
        }catch (NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.println("-"+ e.getMessage());
        }

    }

    private static void realizarCheckOut() {
        try {
            Huesped huesped = Consola.getHuespedPorDni();
            ArrayList<Reserva> reservas = controlador.getReservas(huesped);
            if(reservas.isEmpty()) {
                System.out.println("No hay ninguna reserva, por lo que no se puede hacer el check-out");
            }else {
                listarReservas(huesped);

                int opcion;
                do {
                    System.out.println("Inserte Opción");
                    opcion = Entrada.entero();
                } while (opcion < 1 || opcion > reservas.size());
                Reserva reservaFinal = reservas.get(opcion - 1);
                if (reservaFinal.getCheckOut() == null) {
                    controlador.realizarCheckOut(reservaFinal,Consola.leerFechaHora("Introduce la fecha y hora del check-out formato(dd/MM/yyyy hh:mm:ss)"));
                } else {
                    System.out.println("Esta reserva ya tiene realizado el Check-Out.");
                }
            }
        }catch(NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.println("-"+ e.getMessage());
        }

    }

}



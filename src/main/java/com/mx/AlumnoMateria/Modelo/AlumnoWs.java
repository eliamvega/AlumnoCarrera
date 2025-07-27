package com.mx.AlumnoMateria.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "ALUMNO_WS")
public class AlumnoWs {

    @Id
    @Column(name = "ID_ALUMNO")
    private Long idAlumno;

    @Column(name = "NOMBRE", columnDefinition = "NVARCHAR2(20)")
    private String nombre;

    @Column(name = "APELLIDO", columnDefinition = "NVARCHAR2(20)")
    private String apellido;

    @Column(name = "EDAD", columnDefinition = "NUMBER")
    private int edad;

    @ManyToOne
    @JoinColumn(name = "CARRERA_ID")
    private CarreraWS carrera;

    public AlumnoWs() {}

    public AlumnoWs(Long idAlumno, String nombre, String apellido, int edad, CarreraWS carrera) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.carrera = carrera;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public CarreraWS getCarrera() {
        return carrera;
    }

    public void setCarrera(CarreraWS carrera) {
        this.carrera = carrera;
    }
}


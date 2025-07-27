package com.mx.AlumnoMateria.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "CARRERA_WS")
public class CarreraWS {

    @Id
    @Column(name = "ID_CARRERA")
    private Long idCarrera;

    @Column(name = "NOMBRE", columnDefinition = "NVARCHAR2(20)")
    private String nombre;

    @Column(name = "DURACION", columnDefinition = "NUMBER")
    private int duracion;

    @Column(name = "MODALIDAD", columnDefinition = "NVARCHAR2(20)")
    private String modalidad;

    @Column(name = "NIVEL_ACADEMICO", columnDefinition = "NVARCHAR2(20)")
    private String nivelAcademico;

    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<AlumnoWs> alumnos;

    public CarreraWS() {}

    public CarreraWS(Long idCarrera, String nombre, int duracion, String modalidad, String nivelAcademico) {
        this.idCarrera = idCarrera;
        this.nombre = nombre;
        this.duracion = duracion;
        this.modalidad = modalidad;
        this.nivelAcademico = nivelAcademico;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public List<AlumnoWs> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<AlumnoWs> alumnos) {
        this.alumnos = alumnos;
    }
}

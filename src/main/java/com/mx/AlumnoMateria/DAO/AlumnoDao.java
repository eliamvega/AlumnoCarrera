package com.mx.AlumnoMateria.DAO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.AlumnoMateria.Modelo.AlumnoWs;


public interface AlumnoDao extends JpaRepository<AlumnoWs, Long> {
	List<AlumnoWs> findByNombre(String nombre);
}

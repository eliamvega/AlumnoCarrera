package com.mx.AlumnoMateria.DAO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.AlumnoMateria.Modelo.CarreraWS;


public interface CarreraDao extends JpaRepository<CarreraWS, Long> {
	List<CarreraWS> findByModalidad(String modalidad);
}

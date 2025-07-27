package com.mx.AlumnoMateria.Service;

import java.util.List;


	
	public interface IMetodos<T, ID> {

	public    void guardar(T entidad);

	public     void editar(T entidad);

	public     void eliminar(T entidad);

	public     List<T> mostrar();

	public     T buscar(ID id);
	}

		



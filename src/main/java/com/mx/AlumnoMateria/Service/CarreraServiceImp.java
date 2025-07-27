package com.mx.AlumnoMateria.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.AlumnoMateria.DAO.CarreraDao;
import com.mx.AlumnoMateria.Modelo.CarreraWS;

@Service
public class CarreraServiceImp implements IMetodos<CarreraWS, Long> {

    @Autowired
    private CarreraDao dao;

    @Override
    public void guardar(CarreraWS c) {
        dao.save(c);
    }

    @Override
    public void editar(CarreraWS c) {
        dao.save(c);
    }

    @Override
    public void eliminar(CarreraWS c) {
        dao.delete(c);
    }

    @Override
    public List<CarreraWS> mostrar() {
        return dao.findAll();
    }

    @Override
    public CarreraWS buscar(Long id) {
        return dao.findById(id).orElse(null);
    }

    // Método adicional específico (no afecta la interfaz)
    public List<CarreraWS> buscarPorModalidad(String modalidad) {
        return dao.findByModalidad(modalidad);
    }
}


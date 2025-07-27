package com.mx.AlumnoMateria.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.AlumnoMateria.DAO.AlumnoDao;
import com.mx.AlumnoMateria.Modelo.AlumnoWs;

@Service
public class AlumnoServiceImp implements IMetodos<AlumnoWs, Long> {

    @Autowired
    private AlumnoDao dao;

    @Override
    public void guardar(AlumnoWs a) {
        dao.save(a);
    }

    @Override
    public void editar(AlumnoWs a) {
        dao.save(a);
    }

    @Override
    public void eliminar(AlumnoWs a) {
        dao.delete(a);
    }

    @Override
    public List<AlumnoWs> mostrar() {
        return dao.findAll();
    }

    @Override
    public AlumnoWs buscar(Long id) {
        return dao.findById(id).orElse(null);
    }

    // Método adicional específico (opcional)
    public List<AlumnoWs> buscarPorNombre(String nombre) {
        return dao.findByNombre(nombre);
    }
}

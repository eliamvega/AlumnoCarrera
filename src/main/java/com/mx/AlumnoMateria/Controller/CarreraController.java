package com.mx.AlumnoMateria.Controller;

import com.mx.AlumnoMateria.Modelo.CarreraWS;
import com.mx.AlumnoMateria.Service.IMetodos;
import com.mx.AlumnoMateria.DAO.CarreraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Sort;
import java.util.List;

@RestController
@RequestMapping("/api/carrera")
@CrossOrigin(origins = "*")
public class CarreraController {

    @Autowired
    IMetodos<CarreraWS, Long> metodos;

    @Autowired
    CarreraDao dao;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardar(@RequestBody CarreraWS carrera) {
        try {
           
            if (dao.existsById(carrera.getIdCarrera())) {
                throw new IllegalArgumentException("EL ID DE LA CARRERA YA EXISTE.");
            }

            //OBTENEMOS TODAS LAS CARRERAS PARA VALIDAR SI YA EXISTE EL MISMO NOMBRE
            
            List<CarreraWS> lista = dao.findAll();
            for (CarreraWS c : lista) {
                if (c.getNombre().equalsIgnoreCase(carrera.getNombre())) {
                    throw new IllegalArgumentException("YA EXISTE UNA CARRERA CON EL MISMO NOMBRE.");
                }
            }

         // SI PASA LAS VALIDACIONES, GUARDAMOS LA CARRERA
            metodos.guardar(carrera);
            return ResponseEntity.ok("CARRERA GUARDADA CORRECTAMENTE.");

        } catch (IllegalArgumentException e) {
        	 // CAPTURA EXCEPCIONES DE VALIDACION PARA ENVIAR MENSAJE ESPECIFICO AL CLIENTE
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
        	// CAPTURA CUALQUIER OTRA EXCEPCION INESPERADA
            return ResponseEntity.badRequest().body("ERROR AL GUARDAR LA CARRERA.");
        }
    }


    @PutMapping("/editar")
    public ResponseEntity<String> editar(@RequestBody CarreraWS carrera) {
        try {
            CarreraWS existente = metodos.buscar(carrera.getIdCarrera());
            if (existente == null) {
                return ResponseEntity.status(404).body("CARRERA NO ENCONTRADA PARA EDITAR.");
            }

            String nuevoNombre = carrera.getNombre();

            // VALIDAMOS SI EL NUEVO NOMBRE YA ESTA EN USO EN OTRA CARRERA
            List<CarreraWS> lista = dao.findAll();
            for (CarreraWS c : lista) {
                if (!c.getIdCarrera().equals(carrera.getIdCarrera()) &&
                    c.getNombre().equalsIgnoreCase(nuevoNombre)) {
                    return ResponseEntity.badRequest().body("YA EXISTE UNA CARRERA CON ESE NOMBRE.");
                }
            }

            // SOLO SI PASO LA VALIDACION, SE APLICAN LOS CAMBIOS AL OBJETO EXISTENTE
            existente.setNombre(carrera.getNombre());
            existente.setDuracion(carrera.getDuracion());
            existente.setModalidad(carrera.getModalidad());
            existente.setNivelAcademico(carrera.getNivelAcademico());

            metodos.editar(existente);

            return ResponseEntity.ok("CARRERA EDITADA CORRECTAMENTE.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR AL EDITAR LA CARRERA.");
        }
    }


    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminar(@RequestBody CarreraWS carrera) {
        try {
            // VERIFICAMOS SI LA CARRERA EXISTE EN LA BASE DE DATOS
            if (!dao.existsById(carrera.getIdCarrera())) {
                return ResponseEntity.status(404).body("LA CARRERA CON ID " + carrera.getIdCarrera() + " NO EXISTE.");
            }

            // SI EXISTE, INTENTAMOS ELIMINAR
            metodos.eliminar(carrera);
            return ResponseEntity.ok("CARRERA ELIMINADA CORRECTAMENTE.");

        } catch (Exception e) {
        	// CAPTURA CUALQUIER OTRA EXCEPCION INESPERADA
            return ResponseEntity.badRequest().body("ERROR AL ELIMINAR LA CARRERA.");
        }
    }


    @GetMapping("/mostrar")
    public ResponseEntity<?> mostrar() {
        List<CarreraWS> lista = dao.findAll(Sort.by("idCarrera").ascending());
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body("NO HAY CARRERAS REGISTRADAS.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        CarreraWS carrera = metodos.buscar(id);
        if (carrera != null) {
            return ResponseEntity.ok(carrera);
        } else {
            return ResponseEntity.status(404).body("CARRERA NO ENCONTRADA CON ID: " + id);
        }
    }

    @GetMapping("/modalidad/{modalidad}")
    public ResponseEntity<?> buscarPorModalidad(@PathVariable String modalidad) {
        List<CarreraWS> carreras = dao.findByModalidad(modalidad);
        if (carreras.isEmpty()) {
            return ResponseEntity.status(404).body("NO SE ENCONTRARON CARRERAS CON MODALIDAD: " + modalidad);
        }
        return ResponseEntity.ok(carreras);
    }
}


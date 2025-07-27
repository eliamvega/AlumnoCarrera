package com.mx.AlumnoMateria.Controller;

import com.mx.AlumnoMateria.Modelo.AlumnoWs;
import com.mx.AlumnoMateria.Service.IMetodos;
import com.mx.AlumnoMateria.DAO.AlumnoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Sort;
import java.util.List;

@RestController
@RequestMapping("/api/alumno")
@CrossOrigin(origins = "*")
public class AlumnoController {

    @Autowired
    IMetodos<AlumnoWs, Long> metodos;

    @Autowired
    AlumnoDao dao;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardar(@RequestBody AlumnoWs alumno) {
        try {
           
            // VALIDAMOS QUE NO EXISTA UN ALUMNO CON EL MISMO ID
            if (dao.existsById(alumno.getIdAlumno())) {
                throw new IllegalArgumentException("EL ID DEL ALUMNO YA EXISTE.");
            }

            // OBTENEMOS TODOS LOS ALUMNOS PARA VALIDAR SI YA EXISTE EL MISMO NOMBRE
            List<AlumnoWs> lista = dao.findAll();
            for (AlumnoWs a : lista) {
                if (a.getNombre().equalsIgnoreCase(alumno.getNombre())) {
                    throw new IllegalArgumentException("YA EXISTE UN ALUMNO CON EL MISMO NOMBRE.");
                }
            }

            //SI PASA LAS VALIDACIONES, GUARDAMOS EL ALUMNO
            metodos.guardar(alumno);
            return ResponseEntity.ok("ALUMNO GUARDADO CORRECTAMENTE.");

        } catch (IllegalArgumentException e) {
            // CAPTURA EXCEPCIONES DE VALIDACION PARA ENVIAR MENSAJE ESPECIFICO AL CLIENTE
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            // CAPTURA CUALQUIER OTRA EXCEPCION INESPERADA
            return ResponseEntity.badRequest().body("ERROR AL GUARDAR EL ALUMNO.");
        }
    }



    @PutMapping("/editar")
    public ResponseEntity<String> editar(@RequestBody AlumnoWs alumno) {
        try {
            AlumnoWs existente = metodos.buscar(alumno.getIdAlumno());
            if (existente == null) {
                return ResponseEntity.status(404).body("ALUMNO NO ENCONTRADO PARA EDITAR..");
            }

            String nuevoNombre = alumno.getNombre().trim();

            alumno.setNombre(nuevoNombre);
            metodos.editar(alumno);
            return ResponseEntity.ok("ALUMNO EDITADO CORRECTAMENTE.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR AL EDITAR EL ALUMNO.");
        }
    }


    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminar(@RequestBody AlumnoWs alumno) {
        try {
            // Buscar el alumno por ID
            AlumnoWs existente = metodos.buscar(alumno.getIdAlumno());

            if (existente == null) {
                return ResponseEntity.status(404).body("EL ALUMNO CON ID " + alumno.getIdAlumno() + " NO EXISTE.");
            }

            // Eliminar el alumno, sin importar si tiene carrera asignada
            metodos.eliminar(existente);
            return ResponseEntity.ok("ALUMNO ELIMINADO CORRECTAMENTE.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR AL ELIMINAR EL ALUMNO.");
        }
    }



    @GetMapping("/mostrar")
    public ResponseEntity<?> mostrar() {
        List<AlumnoWs> lista = dao.findAll(Sort.by("idAlumno").ascending());
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body("NO HAY ALUMNOS REGISTRADOS.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        AlumnoWs alumno = metodos.buscar(id);

        if (alumno == null) {
            return ResponseEntity.status(404)
                    .body("ALUMNO NO ENCONTRADO CON ID: " + id);
        }

        return ResponseEntity.ok(alumno);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        List<AlumnoWs> lista = dao.findByNombre(nombre);
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body("NO SE ENCONTRARON ALUMNOS CON EL NOMBRE: " + nombre);
        }
        return ResponseEntity.ok(lista);
    }
}

package com.formacionbdi.microservicios.app.cursos.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	@Query("select c from Curso c join fetch c.cursoAlumnos a where a.alumnoId=?1")
	public Curso findCursoByAlumnoId(Long id);
	
	@Modifying()
	@Query("delete from CursoAlumno ca where ca.alumnoId=?1 ")
	public void eliminarCursoAlumnoPorId(Long id);
}

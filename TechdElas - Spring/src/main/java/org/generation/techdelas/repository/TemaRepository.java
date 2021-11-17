package org.generation.techdelas.repository;

import java.util.List;

import org.generation.techdelas.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TemaRepository extends JpaRepository <Tema, Long> {
	
	public List<Tema> findAllByNomeTemaContainingIgnoreCase (String nomeTema);


	public List<Tema> findAllBySobreContainingIgnoreCase(String sobre);


	/*public List <Tema> findAllByImgDevsContainingIgnoreCase(String imgDevs);

                                                                                //COMENTADO PARA FUTURAS IMPLEMENTAÇÕES!
	public List <Tema> findByNomeTema(String nometema);*/                          
	}
	
		

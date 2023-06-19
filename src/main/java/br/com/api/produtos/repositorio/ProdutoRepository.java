package br.com.api.produtos.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.api.produtos.modelos.ProdutoModel;

@Repository
//produtomodel = tipo de variavel de repositorio, long =  tipo de chave primaria
public interface ProdutoRepository extends CrudRepository<ProdutoModel, Long>{
  
}

package br.com.api.produtos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.produtos.modelos.ProdutoModel;
import br.com.api.produtos.modelos.RespostaModel;
import br.com.api.produtos.repositorio.ProdutoRepository;

@Service
public class ProdutoService {
  
  @Autowired
  //
  private ProdutoRepository pr;

  @Autowired
  //variavel feedback se está faltando algo da classe model
  private RespostaModel rm;

  //metodo para listar todos os produtos
  public Iterable<ProdutoModel> listar(){
    return pr.findAll();
  }

  //metodo para checar se as variaveis estao sendo cadastradas ou alterados
  //importante criar mais metodos de verificacao para cada variavel.
  public ResponseEntity<?> cadastrarAlterar(ProdutoModel pm, String acao){

    if(pm.getNome().equals("")){
      rm.setMensagem("O nome do produto é obrigatório");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);

    } else if(pm.getMarca().equals("")){
      rm.setMensagem("O nome da marca é obrigatória");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);

    } else{
      //se for uma alteracao, o status http é ok, se for um cadastro, o status é criado
      if(acao.equals("cadastrar")){
          //o repositorio pr possui uma função save
        return new ResponseEntity<ProdutoModel>(pr.save(pm), HttpStatus.CREATED);
      }else{
        return new ResponseEntity<ProdutoModel>(pr.save(pm), HttpStatus.OK);
      }
    }
  }


  //metodo para remover produtos
  public ResponseEntity <RespostaModel> remover(long codigo){
    pr.deleteById(codigo);
    rm.setMensagem("O produto foi removido"); 

    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }
  

  

}

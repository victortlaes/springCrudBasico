package br.com.api.produtos.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.produtos.servico.ProdutoService;
import br.com.api.produtos.modelos.ProdutoModel;
import br.com.api.produtos.modelos.RespostaModel;

@RestController
//liberar o acesso a porta 3000(react)
@CrossOrigin(origins = "http://localhost:3000")
public class ProdutoControl {

  @Autowired
  private ProdutoService ps;

  @GetMapping("/listar")
  public Iterable<ProdutoModel> listar(){
    return ps.listar();
  }

  @PostMapping("/cadastrar")
  public ResponseEntity<?> cadastrar(@RequestBody ProdutoModel pm){
    return ps.cadastrarAlterar(pm, "cadastrar");
  }

  @PutMapping("/alterar")
  public ResponseEntity<?> alterar(@RequestBody ProdutoModel pm){
    return ps.cadastrarAlterar(pm, "alterar");
  }

  @DeleteMapping("/remover/{codigo}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long codigo){
    return ps.remover(codigo);
  }

  @GetMapping("/")
  public String rota(){
    return "API de produtos funcionando";
  }
}

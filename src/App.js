import { useEffect, useState } from 'react';
import './App.css';
import Formulario from './Formulario';
import Tabela from './Tabela';

function App() {

  //Objeto produto
  const produto = {
    codigo: 0,
    nome: '',
    marca:''
  }

  //useState
  const [btnCadastrar, setBtnCadastrar] = useState(true);
  const [produtos, setProdutos] = useState([]);
  const [objProduto, setObjProduto] = useState(produto);

  //useEffect
  useEffect(()=>{
    fetch("http://localhost:8080/listar")
    //transforma para json
    .then(retorno => retorno.json())
    .then(retorno_convertido => setProdutos(retorno_convertido))
  },[]);

  //OBTENDO DADOS DO FORMULARIO
  const aoDigitar = (e) =>{
    setObjProduto({...objProduto, [e.target.name]:e.target.value});

  }

  //CADASTRAR PRODUTO
  const cadastrar = () =>{
    fetch('http://localhost:8080/cadastrar',{
      method:'post',
      body:JSON.stringify(objProduto),
      headers:{
        'Content-type':'application/json',
        'Accept':'application/json'
      }
    })
    .then(retorno => retorno.json())
    .then(retorno_convertido => {
      if(retorno_convertido.mensagem !==undefined){
        alert(retorno_convertido.mensagem);
      }else{
        setProdutos([...produtos, retorno_convertido]);
        alert('Produto cadastrado com sucesso')
        limpar();
      }
     
    })
    
  }

  //REMOVER PRODUTO
   
  const remover = () =>{
    fetch('http://localhost:8080/remover/'+objProduto.codigo,{
      method:'delete',
      headers:{
        'Content-type':'application/json',
        'Accept':'application/json'
      }
    })
    .then(retorno => retorno.json())
    .then(retorno_convertido => {
      alert('Produto removido com sucesso');

      //copia de vetor de produtos, porem atualizado
      let vetorTemp = [...produtos];
      //indice
      let indice = vetorTemp.findIndex((p) =>{
        return p.codigo === objProduto.codigo;
      });

      //remover produto do vetortemp
      vetorTemp.splice(indice, 1);

      //atualizar vetor de produtos
      setProdutos(vetorTemp);
      
      //apos remover, limpar formulario
      limpar();
    })
      
  }

  //ALTERAR PRODUTO 
  const alterar = () =>{
    fetch('http://localhost:8080/alterar',{
      method:'put',
      body:JSON.stringify(objProduto),
      headers:{
        'Content-type':'application/json',
        'Accept':'application/json'
      }
    })
    .then(retorno => retorno.json())
    .then(retorno_convertido => {
      if(retorno_convertido.mensagem !==undefined){
        alert(retorno_convertido.mensagem);
      }else{
        //mensagem
        alert('Produto alterado com sucesso');

        //copia de vetor de produtos, porem atualizado
        let vetorTemp = [...produtos];
        //indice
        let indice = vetorTemp.findIndex((p) =>{
          return p.codigo === objProduto.codigo;
        });

        //alterar produto do vetortemp
        vetorTemp[indice] = objProduto;

        //atualizar vetor de produtos
        setProdutos(vetorTemp);

        //limpar formulario
        limpar();

      }
     
    })
    
  }


  //LIMPAR CAMPOS DO FORMULARIO 
  const limpar = () =>{
    setObjProduto(produto);
    //cancelar produto
    setBtnCadastrar(true);
  }

  //Selecionar produto
  const selecionarProduto = (indice) => {
    setObjProduto(produtos[indice]);
    setBtnCadastrar(false);
  }


  //retorno
  return (
    <div>
      <Formulario botao={btnCadastrar} eventoTeclado={aoDigitar} cadastrar = {cadastrar} obj={objProduto} cancelar = {limpar} remover = {remover} alterar = {alterar}/>
      <Tabela vetor={produtos} selecionar = {selecionarProduto}/>
    </div>
  );
}

export default App;

package br.edu.ifpr.treinamento.fxbeans.converters;

import br.edu.ifpr.treinamento.fxbeans.EnderecoFXBean;
import br.edu.ifpr.treinamento.modelo.Endereco;

public class EnderecoEntityConverter
             implements EntityConverter<Endereco, EnderecoFXBean> {
   @Override
   public Endereco toEntity(EnderecoFXBean f) {
      Endereco ender = new Endereco();

      ender.setLogradouro(f.logradouroProperty().get());
      ender.setNumero(f.numeroProperty().get());
      ender.setComplemento(f.complementoProperty().get());
      ender.setBairro(f.bairroProperty().get());
      ender.setCep(f.cepProperty().get());
      ender.setCidade(f.cidadeProperty().get());
      ender.setEstado(f.estadoProperty().get());
      ender.setTipo(f.tipoProperty().get());

      return ender;
   }

   @Override
   public EnderecoFXBean fromEntity(Endereco e) {
      EnderecoFXBean fxbEndereco = new EnderecoFXBean();

      fxbEndereco.logradouroProperty().set(e.getLogradouro());
      fxbEndereco.numeroProperty().set(e.getNumero());
      fxbEndereco.complementoProperty().set(e.getComplemento());
      fxbEndereco.bairroProperty().set(e.getBairro());
      fxbEndereco.cepProperty().set(e.getCep());
      fxbEndereco.cidadeProperty().set(e.getCidade());
      fxbEndereco.estadoProperty().set(e.getEstado());
      fxbEndereco.tipoProperty().set(e.getTipo());

      return fxbEndereco;
   }
}

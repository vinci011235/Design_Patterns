package br.edu.ifpr.treinamento.fxbeans.converters;

import br.edu.ifpr.treinamento.fxbeans.TelefoneFXBean;
import br.edu.ifpr.treinamento.modelo.Telefone;

public class TelefoneEntityConverter
             implements EntityConverter<Telefone, TelefoneFXBean> {
   @Override
   public Telefone toEntity(TelefoneFXBean f) {
      Telefone fone = new Telefone();

      fone.setDdd(f.dddProperty().get());
      fone.setNumero(f.numeroProperty().get());
      fone.setTipo(f.tipoProperty().get());

      return fone;
   }

   @Override
   public TelefoneFXBean fromEntity(Telefone e) {
      TelefoneFXBean fxbFone = new TelefoneFXBean();

      fxbFone.dddProperty().set(e.getDdd());
      fxbFone.numeroProperty().set(e.getNumero());
      fxbFone.tipoProperty().set(e.getTipo());

      return fxbFone;
   }
}

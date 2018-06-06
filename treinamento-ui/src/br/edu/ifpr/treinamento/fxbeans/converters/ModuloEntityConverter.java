package br.edu.ifpr.treinamento.fxbeans.converters;

import br.edu.ifpr.treinamento.fxbeans.ModuloFXBean;
import br.edu.ifpr.treinamento.modelo.Modulo;
import br.edu.ifpr.utils.date.DateTimeUtils;

public class ModuloEntityConverter
             implements EntityConverter<Modulo, ModuloFXBean> {
   @Override
   public Modulo toEntity(ModuloFXBean f) {
      InstrutorEntityConverter conv = new InstrutorEntityConverter();

      Modulo modulo = new Modulo();

      modulo.setNome(f.nomeProperty().get());
      modulo.setDuracao(f.duracaoProperty().get());
      modulo.setInicio(DateTimeUtils.localDateToDate(f.inicioProperty().get()));
      modulo.setInstrutor(conv.toEntity(f.instrutorProperty().get()));

      conv = null;

      return modulo;
   }

   @Override
   public ModuloFXBean fromEntity(Modulo e) {
      InstrutorEntityConverter conv = new InstrutorEntityConverter();

      ModuloFXBean fxbModulo = new ModuloFXBean();

      fxbModulo.nomeProperty().set(e.getNome());
      fxbModulo.duracaoProperty().set(e.getDuracao());
      fxbModulo.inicioProperty().set(
                                  DateTimeUtils.dateToLocalDate(e.getInicio()));
      fxbModulo.instrutorProperty().set(conv.fromEntity(e.getInstrutor()));

      conv = null;

      return fxbModulo;
   }
}

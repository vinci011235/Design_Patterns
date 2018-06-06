package br.edu.ifpr.treinamento.fxbeans.converters;

import br.edu.ifpr.treinamento.fxbeans.CursoFXBean;
import br.edu.ifpr.treinamento.fxbeans.ModuloFXBean;
import br.edu.ifpr.treinamento.modelo.Curso;
import br.edu.ifpr.treinamento.modelo.Modulo;
import br.edu.ifpr.utils.date.DateTimeUtils;

public class CursoEntityConverter
             implements EntityConverter<Curso, CursoFXBean> {
   @Override
   public Curso toEntity(CursoFXBean f) {
      ModuloEntityConverter convM = new ModuloEntityConverter();

      Curso curso = new Curso();

      curso.setCodigo(f.codigoProperty().get());
      curso.setNome(f.nomeProperty().get());
      curso.setSituacao(f.situacaoProperty().get());
      curso.setDuracao(f.duracaoProperty().get());
      curso.setInicio(DateTimeUtils.localDateToDate(f.inicioProperty().get()));

      for (ModuloFXBean fxbModulo : f.modulosProperty())
         curso.getModulos().add(convM.toEntity(fxbModulo));

      // NÃO CONVERTE MATRÍCULAS

      convM = null;

      return curso;
   }

   @Override
   public CursoFXBean fromEntity(Curso e) {
      ModuloEntityConverter convM = new ModuloEntityConverter();

      CursoFXBean fxbCurso = new CursoFXBean();

      fxbCurso.codigoProperty().set(e.getCodigo());
      fxbCurso.nomeProperty().set(e.getNome());
      fxbCurso.situacaoProperty().set(e.getSituacao());
      fxbCurso.duracaoProperty().set(e.getDuracao());
      fxbCurso.inicioProperty().set(
                                  DateTimeUtils.dateToLocalDate(e.getInicio()));

      for (Modulo modulo : e.getModulos())
         fxbCurso.modulosProperty().add(convM.fromEntity(modulo));

      // NÃO CONVERTE MATRÍCULAS

      convM = null;

      return fxbCurso;
   }

}

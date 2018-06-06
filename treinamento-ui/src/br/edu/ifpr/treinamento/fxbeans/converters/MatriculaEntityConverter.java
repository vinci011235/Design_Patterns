package br.edu.ifpr.treinamento.fxbeans.converters;

import br.edu.ifpr.treinamento.fxbeans.MatriculaFXBean;
import br.edu.ifpr.treinamento.modelo.Matricula;
import br.edu.ifpr.utils.date.DateTimeUtils;

public class MatriculaEntityConverter
             implements EntityConverter<Matricula, MatriculaFXBean> {
   @Override
   public Matricula toEntity(MatriculaFXBean f) {
      CursoEntityConverter convC = new CursoEntityConverter();
      AlunoEntityConverter convA = new AlunoEntityConverter();

      Matricula matricula = new Matricula();

      matricula.setCurso(convC.toEntity(f.cursoProperty().get()));
      matricula.setAluno(convA.toEntity(f.alunoProperty().get()));
      matricula.setDataMatricula(
                DateTimeUtils.localDateToDate(f.dataMatriculaProperty().get()));

      convA = null;
      convC = null;

      return matricula;
   }

/*
   private ObjectProperty<CursoFXBean>     curso;
   private ObjectProperty<AlunoFXBean>     aluno;
   private ObjectProperty<LocalDate> dataMatricula;
*/
   @Override
   public MatriculaFXBean fromEntity(Matricula e) {
      CursoEntityConverter convC = new CursoEntityConverter();
      AlunoEntityConverter convA = new AlunoEntityConverter();

      MatriculaFXBean fxbMatricula = new MatriculaFXBean();

      fxbMatricula.cursoProperty().set(convC.fromEntity(e.getCurso()));
      fxbMatricula.alunoProperty().set(convA.fromEntity(e.getAluno()));
      fxbMatricula.dataMatriculaProperty().set(
                           DateTimeUtils.dateToLocalDate(e.getDataMatricula()));

      convA = null;
      convC = null;

      return fxbMatricula;
   }
}

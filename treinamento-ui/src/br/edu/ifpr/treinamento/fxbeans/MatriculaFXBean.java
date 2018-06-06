package br.edu.ifpr.treinamento.fxbeans;

import java.time.LocalDate;
import java.util.Date;

import br.edu.ifpr.treinamento.utils.date.DateTimeUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MatriculaFXBean {
   private ObjectProperty<CursoFXBean> curso;
   private ObjectProperty<AlunoFXBean> aluno;
   private ObjectProperty<LocalDate>   dataMatricula;

   public MatriculaFXBean() {}
   public MatriculaFXBean(CursoFXBean curso, AlunoFXBean aluno, Date dataMatricula) {
      this(curso,aluno,DateTimeUtils.dateToLocalDate(dataMatricula));
   }
   public MatriculaFXBean(CursoFXBean curso, AlunoFXBean aluno, LocalDate dataMatricula) {
      this.curso         = new SimpleObjectProperty<>(curso);
      this.aluno         = new SimpleObjectProperty<>(aluno);
      this.dataMatricula = new SimpleObjectProperty<>(dataMatricula);
   }

   public CursoFXBean getCurso() { return curso.get(); }
   public void setCurso(CursoFXBean curso) { this.curso.set(curso); }
   public ObjectProperty<CursoFXBean> cursoProperty() { return this.curso; }

   public AlunoFXBean getAluno() { return aluno.get(); }
   public void setAluno(AlunoFXBean aluno) { this.aluno.set(aluno); }
   public ObjectProperty<AlunoFXBean> alunoProperty() { return this.aluno; }

   public LocalDate getDataMatricula() { return dataMatricula.get(); }
   public void setDataMatricula(LocalDate dataMatricula) {
      this.dataMatricula.set(dataMatricula);
   }
   public void setDataMatricula(Date dataMatricula) {
      this.dataMatricula.set(DateTimeUtils.dateToLocalDate(dataMatricula));
   }
   public ObjectProperty<LocalDate> dataMatriculaProperty() {
      return this.dataMatricula;
   }

   @Override
   public String toString() {
      return "MatriculaFXBean[curso=" + curso + ", aluno=" +
             aluno + ", [dataMatricula=" + dataMatricula + "]";
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      MatriculaFXBean other = (MatriculaFXBean) obj;
      if (dataMatricula == null) {
         if (other.dataMatricula != null) return false;
      }
      else
         if (!dataMatricula.equals(other.dataMatricula)) return false;
      if (aluno == null) {
         if (other.aluno != null) return false;
      }
      else
         if (!aluno.equals(other.aluno)) return false;
      if (curso == null) {
         if (other.curso != null) return false;
      }
      else
         if (!curso.equals(other.curso)) return false;
      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
               + ((dataMatricula == null) ? 0 : dataMatricula.hashCode());
      result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
      result = prime * result + ((curso == null) ? 0 : curso.hashCode());
      return result;
   }
}

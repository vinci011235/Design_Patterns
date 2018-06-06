package br.edu.ifpr.treinamento.fxbeans;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import br.edu.ifpr.treinamento.modelo.types.PessoaType;
import br.edu.ifpr.treinamento.modelo.types.SexoType;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class AlunoFXBean extends PessoaFXBean {
   private StringProperty                registro;
   private ListProperty<MatriculaFXBean> matriculas;

   public AlunoFXBean() {
      super();
      this.tipo.set(PessoaType.ALUNO);
      this.matriculas = new SimpleListProperty<>(
                                           FXCollections.observableArrayList());
   }
   // todos os atributos e 1 telefone, exceto 'email'
   public AlunoFXBean(String cpf, String nome, String rg, LocalDate nascimento,
                SexoType sexo, EnderecoFXBean endereco, TelefoneFXBean fone,
                String registro) {
      this(cpf,nome,rg,nascimento,sexo,"",endereco,Arrays.asList(fone),
           registro);
   }
   // todos os atributos e 1 telefone
   public AlunoFXBean(String cpf, String nome, String rg, LocalDate nascimento,
                SexoType sexo, String email, EnderecoFXBean endereco, TelefoneFXBean fone,
                String registro) {
      this(cpf,nome,rg,nascimento,sexo,email,endereco,Arrays.asList(fone),
           registro);
   }
   // todos os atributos
   public AlunoFXBean(String cpf, String nome, String rg, LocalDate nascimento,
                SexoType sexo, String email, EnderecoFXBean endereco,
                List<TelefoneFXBean> fones, String registro) {
      super(cpf,nome,rg,nascimento,sexo,PessoaType.ALUNO,email,endereco,fones);
      this.registro   = new SimpleStringProperty(registro);
      this.matriculas = new SimpleListProperty<>(
                                           FXCollections.observableArrayList());
   }

   public String getRegistro() { return registro.get(); }
   public void setRegistro(String registro) { this.registro.set(registro); }
   public StringProperty registroProperty() { return this.registro; }

   public List<MatriculaFXBean> getMatriculas() { return this.matriculas; }
   public void setMatriculas(List<MatriculaFXBean> matriculas) {
      this.matriculas.clear();
      this.matriculas.addAll(FXCollections.observableArrayList(matriculas));
   }
   public ListProperty<MatriculaFXBean> matriculasProperty() {
      return this.matriculas;
   }
//
//   // ==========================================================================
//   // === MATRÍCULAS - INÍCIO ==================================================
//   // ==========================================================================
//   public boolean addMatricula(MatriculaFXBean matricula) {
//      if (matricula == null)
//         throw new IllegalArgumentException("addMatricula: Matrícula inválida");
//
//      return matriculas.add(matricula);
//   }
//
//   public boolean removeMatricula(MatriculaFXBean matricula) {
//      if (matricula == null)
//         throw new IllegalArgumentException("removeMatricula: Matrícula " +
//                                            "inválida");
//
//      return matriculas.remove(matricula);
//   }
//
//   public MatriculaFXBean updateMatricula() {
//      return null;
//   }
//
//   public MatriculaFXBean getMatricula(CursoFXBean curso) {
//      if (curso == null)
//         throw new IllegalArgumentException("getMatricula: CursoFXBean " +
//                                            "inválida");
//
//      for (MatriculaFXBean matricula : matriculas)
//         if (matricula.getCurso().equals(curso))
//            return matricula;
//
//      return null;
//   }
//
//   public MatriculaFXBean getMatricula(Date dataMatricula) {
//      if (dataMatricula == null)
//         throw new IllegalArgumentException("getMatricula: Data de Matrícula " +
//                                            "inválida");
//
//      for (MatriculaFXBean matricula : matriculas)
//         if (matricula.getDataMatricula().equals(dataMatricula))
//            return matricula;
//
//      return null;
//   }
//   // ==========================================================================
//   // === MATRÍCULAS - FIM =====================================================
//   // ==========================================================================

   @Override
   public String toString() {
      return "AlunoFXBean[registro=" + registro +
             ", matriculas=" + matriculas.size() + ", " + super.toString() +"]";
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!super.equals(obj)) return false;
      if (getClass() != obj.getClass()) return false;
      AlunoFXBean other = (AlunoFXBean) obj;
      if (matriculas == null) {
         if (other.matriculas != null) return false;
      }
      else
         if (!matriculas.equals(other.matriculas)) return false;
      if (registro == null) {
         if (other.registro != null) return false;
      }
      else
         if (!registro.equals(other.registro)) return false;
      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result
               + ((matriculas == null) ? 0 : matriculas.hashCode());
      result = prime * result
               + ((registro == null) ? 0 : registro.hashCode());
      return result;
   }
}

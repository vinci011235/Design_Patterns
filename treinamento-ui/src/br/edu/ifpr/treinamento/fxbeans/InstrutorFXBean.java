package br.edu.ifpr.treinamento.fxbeans;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import br.edu.ifpr.treinamento.modelo.types.PessoaType;
import br.edu.ifpr.treinamento.modelo.types.SexoType;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class InstrutorFXBean extends PessoaFXBean {
   private StringProperty             codigo;
   private ListProperty<ModuloFXBean> modulos;

   public InstrutorFXBean() {
      super();
      this.tipo.set(PessoaType.INSTRUTOR);
      this.codigo  = new SimpleStringProperty("");
      this.modulos = new SimpleListProperty<>(
                                           FXCollections.observableArrayList());
   }
   // todos os atributos e 1 telefone
   public InstrutorFXBean(String cpf, String nome, String rg,
                          LocalDate nascimento, SexoType sexo, String email,
                          EnderecoFXBean endereco, TelefoneFXBean fone,
                          String codigo) {
      this(cpf,nome,rg,nascimento,sexo,email,endereco,Arrays.asList(fone),
           codigo);
   }
   // todos os atributos exceto 'email', e 1 telefone
   public InstrutorFXBean(String cpf, String nome, String rg,
                          LocalDate nascimento, SexoType sexo,
                          EnderecoFXBean endereco, TelefoneFXBean fone,
                          String codigo) {
      this(cpf,nome,rg,nascimento,sexo,"",endereco,Arrays.asList(fone),
           codigo);
   }
   // todos os atributos
   public InstrutorFXBean(String cpf, String nome, String rg,
                          LocalDate nascimento, SexoType sexo, String email,
                          EnderecoFXBean endereco, List<TelefoneFXBean> fones,
                          String codigo) {
      super(cpf,nome,rg,nascimento,sexo,PessoaType.INSTRUTOR,email,endereco,
            fones);
      this.codigo  = new SimpleStringProperty(codigo);
      this.modulos = new SimpleListProperty<>(
                                           FXCollections.observableArrayList());
   }

   public String getCodigo() { return codigo.get(); }
   public void setCodigo(String codigo) { this.codigo.set(codigo); }
   public StringProperty codigoProperty() { return this.codigo; }

   public List<ModuloFXBean> getModulos() { return modulos; }
   public void setModulos(List<ModuloFXBean> modulos) {
      this.modulos.clear();
      this.modulos.addAll(FXCollections.observableArrayList(modulos));
   }
   public ListProperty<ModuloFXBean> modulosProperty() {
      return this.modulos;
   }
//
//   // ==========================================================================
//   // === MÓDULOS - INÍCIO =====================================================
//   // ==========================================================================
//   public boolean addModulo(ModuloFXBean modulo) {
//      if (modulo == null)
//         throw new IllegalArgumentException("addModulo: Módulo inválido");
//
//      return modulos.add(modulo);
//   }
//
//   public ModuloFXBean updateModulo() {
//      return null;
//   }
//
//   public boolean removeModulo(ModuloFXBean modulo) {
//      if (modulo == null)
//         throw new IllegalArgumentException("removeModulo: Módulo inválido");
//
//      return modulos.remove(modulo);
//   }
//
//   public ModuloFXBean getModulo(String nome) {
//      if (nome == null)
//         throw new IllegalArgumentException("getModulo: Nome inválido");
//
//      for (ModuloFXBean modulo : modulos)
//         if (modulo.getNome().equals(nome))
//            return modulo;
//
//      return null;
//   }
//   // ==========================================================================
//   // === MÓDULOS - FIM ========================================================
//   // ==========================================================================

   @Override
   public String toString() {
      return getClass().getSimpleName() + "[codigo=" + codigo + ", modulos=" +
             modulos.size() + "; " + super.toString() + "]";
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!super.equals(obj)) return false;
      if (getClass() != obj.getClass()) return false;
      InstrutorFXBean other = (InstrutorFXBean) obj;
      if (codigo == null) {
         if (other.codigo != null) return false;
      }
      else
         if (!codigo.equals(other.codigo)) return false;
      if (modulos == null) {
         if (other.modulos != null) return false;
      }
      else
         if (!modulos.equals(other.modulos)) return false;
      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((modulos == null) ? 0 : modulos.hashCode());
      result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
      return result;
   }

   public static Builder builder() { return new InstrutorFXBean.Builder(); }

   public static class Builder {
      private InstrutorFXBean instance = new InstrutorFXBean();

      private Builder() {
         instance.tipo.set(PessoaType.INSTRUTOR);
      }

      public Builder cpf(String cpf) {
         instance.cpf.set(cpf);

         return this;
      }
      public Builder nome(String nome) {
         instance.nome.set(nome);

         return this;
      }
      public Builder rg(String rg) {
         instance.rg.set(rg);

         return this;
      }
      public Builder nascimento(LocalDate nascimento) {
         instance.nascimento.set(nascimento);

         return this;
      }
      public Builder sexo(SexoType sexo) {
         instance.sexo.set(sexo);

         return this;
      }
      public Builder email(String email) {
         instance.email.set(email);

         return this;
      }
      public Builder endereco(EnderecoFXBean endereco) {
         instance.endereco.set(endereco);

         return this;
      }
      public Builder fones(TelefoneFXBean fone) {
         instance.fones.add(fone);

         return this;
      }
      public Builder fones(Collection<TelefoneFXBean> fones) {
         return fones(new SimpleListProperty<>(
                                     FXCollections.observableArrayList(fones)));
      }
      public Builder fones(ListProperty<TelefoneFXBean> fones) {
         instance.fones.clear();
         instance.fones.set(fones);

         return this;
      }
      public Builder codigo(String codigo) {
         instance.codigo.set(codigo);

         return this;
      }
      public Builder modulos(ModuloFXBean modulo) {
         instance.modulos.add(modulo);

         return this;
      }
      public Builder modulos(Collection<ModuloFXBean> modulos) {
         return modulos(new SimpleListProperty<>(
                                   FXCollections.observableArrayList(modulos)));
      }
      public Builder modulos(ListProperty<ModuloFXBean> modulos) {
         instance.modulos.clear();
         instance.modulos.set(modulos);

         return this;
      }

      public InstrutorFXBean build() { return instance; }
   }
}

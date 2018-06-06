package br.edu.ifpr.treinamento.fxbeans;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import br.edu.ifpr.treinamento.modelo.types.PessoaType;
import br.edu.ifpr.treinamento.modelo.types.SexoType;
import br.edu.ifpr.treinamento.utils.date.DateTimeUtils;
import br.org.romualdo.validators.formatter.CpfFormatter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public abstract class PessoaFXBean {
   protected StringProperty                 cpf;
   protected StringProperty                 nome;
   protected StringProperty                 rg;
   protected ObjectProperty<LocalDate>      nascimento;
   protected ObjectProperty<SexoType>       sexo;
   protected ObjectProperty<PessoaType>     tipo;
   protected StringProperty                 email;
   protected ObjectProperty<EnderecoFXBean> endereco;
   protected ListProperty<TelefoneFXBean>   fones;

   public PessoaFXBean() {
      this("","","",null,SexoType.MASCULINO,PessoaType.ALUNO,"",null,
           FXCollections.observableArrayList());
   }
   // todos os atributos e 1 telefone
   public PessoaFXBean(String cpf, String nome, String rg, LocalDate nascimento,
                 SexoType sexo, PessoaType tipo, String email,
                 EnderecoFXBean endereco, TelefoneFXBean fone) {
      this(cpf,nome,rg,nascimento,sexo,tipo,email,endereco,Arrays.asList(fone));
   }
   // todos os atributos e 1 telefone, exceto 'email'
   public PessoaFXBean(String cpf, String nome, String rg, LocalDate nascimento,
                 SexoType sexo, PessoaType tipo, EnderecoFXBean endereco,
                 TelefoneFXBean fone) {
      this(cpf,nome,rg,nascimento,sexo,tipo,"",endereco,Arrays.asList(fone));
   }
   // todos os atributos
   public PessoaFXBean(String cpf, String nome, String rg, LocalDate nascimento,
                 SexoType sexo, PessoaType tipo, String email,
                 EnderecoFXBean endereco, List<TelefoneFXBean> fones) {
      this.cpf        = new SimpleStringProperty(cpf);
      this.nome       = new SimpleStringProperty(nome);
      this.rg         = new SimpleStringProperty(rg);
      this.nascimento = new SimpleObjectProperty<>(nascimento);
      this.sexo       = new SimpleObjectProperty<>(sexo);
      this.tipo       = new SimpleObjectProperty<>(tipo);
      this.email      = new SimpleStringProperty(email);
      this.endereco   = new SimpleObjectProperty<>(endereco);
      this.fones      = new SimpleListProperty<>(
                                      FXCollections.observableArrayList(fones));
   }

   public String getNome() { return nome.get(); }
   public void setNome(String nome) { this.nome.set(nome); }
   public StringProperty nomeProperty() { return this.nome; }

   public String getCpf() { return cpf.get(); }
   public void setCpf(String cpf) { this.cpf.set(cpf); }
   public StringProperty cpfProperty() { return this.cpf; }

   public String getRg() { return rg.get(); }
   public void setRg(String rg) { this.rg.set(rg); }
   public StringProperty rgProperty() { return this.rg; }

   public LocalDate getNascimento() { return nascimento.get(); }
   public void setNascimento(LocalDate nascimento) {
      this.nascimento.set(nascimento);
   }
   public ObjectProperty<LocalDate> nascimentoProperty() {
      return this.nascimento;
   }

   public SexoType getSexo() { return sexo.get(); }
   public void setSexo(SexoType sexo) { this.sexo.set(sexo); }
   public ObjectProperty<SexoType> sexoProperty() { return this.sexo; }

   public PessoaType getTipo() { return tipo.get(); }
   public void setTipo(PessoaType tipo) { this.tipo.set(tipo); }
   public ObjectProperty<PessoaType> tipoProperty() { return this.tipo; }

   public String getEmail() { return email.get(); }
   public void setEmail(String email) { this.email.set(email); }
   public StringProperty emailProperty() { return this.email; }

   public EnderecoFXBean getEndereco() { return endereco.get(); }
   public void setEndereco(EnderecoFXBean endereco) { this.endereco.set(endereco); }
   public ObjectProperty<EnderecoFXBean> enderecoProperty() { return this.endereco; }

   public List<TelefoneFXBean> getFones() { return fones; }
   public void setFones(List<TelefoneFXBean> fones) {
      this.fones.clear();
      this.fones.addAll(FXCollections.observableArrayList(fones));
   }
   public ListProperty<TelefoneFXBean> fonesProperty() {
      return this.fones;
   }
//
//   // ==========================================================================
//   // === TelefoneFXBean - INÍCIO ====================================================
//   // ==========================================================================
//   public void addTelefone(TelefoneFXBean telefone) {
//      if ((telefone.getDdd() == null || telefone.getDdd().isEmpty()) ||
//          (telefone.getNumero() == null || telefone.getNumero().isEmpty()) ||
//          (telefone.getTipo() == null)
//         )
//         throw new IllegalArgumentException("PessoaFXBean.addTelefone: " +
//                                            "TelefoneFXBean inválido");
//
//           this.fones.add(telefone);
//   }
//
//   public void addTelefone(String ddd, String numero, TelefoneType tipo) {
//      if ((ddd == null || ddd.isEmpty()) ||
//          (numero == null || numero.isEmpty()) ||
//          (tipo == null)
//         )
//         throw new IllegalArgumentException("PessoaFXBean.addTelefone: " +
//                                            "TelefoneFXBean inválido");
//
//      this.fones.add(new TelefoneFXBean(ddd,numero,tipo));
//   }
//
//   public void updateTelefone(TelefoneFXBean fone) {
//      if (fone == null)
//         throw new IllegalArgumentException("PessoaFXBean.updateTelefone: " +
//                                            "TelefoneFXBean inválido");
//
//      // ?????
//   }
//
//   public boolean deleteTelefone(TelefoneFXBean fone) {
//      if (fone == null)
//         throw new IllegalArgumentException("PessoaFXBean.addTelefone: " +
//                                            "TelefoneFXBean inválido");
//
//      return  this.fones.remove(fone);
//   }
//
//   public List<TelefoneFXBean> getFonesByDdd(final String ddd) {
//      List<TelefoneFXBean> temp = new ArrayList<>();
//
//      for (TelefoneFXBean fone : this.fones) {
//         if (fone.getDdd().equals(ddd))
//            temp.add(new TelefoneFXBean(fone));
//      }
//      return temp;
//   }
//
//   public List<TelefoneFXBean> getFonesByTipo(final TelefoneType tipo) {
//      List<TelefoneFXBean> temp = new ArrayList<>();
//
//      for (TelefoneFXBean fone : this.fones) {
//         if (fone.getTipo().equals(tipo))
//            temp.add(new TelefoneFXBean(fone));
//      }
//      return temp;
//   }
//   // ==========================================================================
//   // === TelefoneFXBean - FIM =======================================================
//   // ==========================================================================

   @Override
   public String toString() {
      return "PessoaFXBean[cpf=" + CpfFormatter.formatCpf(cpf.get())+
             ", nome=" + nome + ", rg=" + rg + ", nascimento=" +
             DateTimeUtils.formatLocalDate(nascimento.get()) +
             ", sexo=" + sexo + ", tipo=" + tipo + ", email=" + email +
             ", endereco=" + endereco + ", fones=" + fones + "]";
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      PessoaFXBean other = (PessoaFXBean) obj;
      if (cpf == null) {
         if (other.cpf != null) return false;
      }
      else
         if (!cpf.equals(other.cpf)) return false;
      if (email == null) {
         if (other.email != null) return false;
      }
      else
         if (!email.equals(other.email)) return false;
      if (endereco == null) {
         if (other.endereco != null) return false;
      }
      else
         if (!endereco.equals(other.endereco)) return false;
      if (fones == null) {
         if (other.fones != null) return false;
      }
      else
         if (!fones.equals(other.fones)) return false;
      if (nascimento == null) {
         if (other.nascimento != null) return false;
      }
      else
         if (!nascimento.equals(other.nascimento)) return false;
      if (nome == null) {
         if (other.nome != null) return false;
      }
      else
         if (!nome.equals(other.nome)) return false;
      if (rg == null) {
         if (other.rg != null) return false;
      }
      else
         if (!rg.equals(other.rg)) return false;
      if (sexo != other.sexo) return false;
      if (tipo != other.tipo) return false;
      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
      result = prime * result + ((email == null) ? 0 : email.hashCode());
      result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
      result = prime * result + ((fones == null) ? 0 : fones.hashCode());
      result = prime * result
               + ((nascimento == null) ? 0 : nascimento.hashCode());
      result = prime * result + ((nome == null) ? 0 : nome.hashCode());
      result = prime * result + ((rg == null) ? 0 : rg.hashCode());
      result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
      result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
      return result;
   }
}

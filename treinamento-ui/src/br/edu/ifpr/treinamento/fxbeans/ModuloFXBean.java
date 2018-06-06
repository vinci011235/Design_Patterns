package br.edu.ifpr.treinamento.fxbeans;

import java.time.LocalDate;
import java.util.Date;

import br.edu.ifpr.treinamento.utils.date.DateTimeUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModuloFXBean {
   private StringProperty                  nome;
   private IntegerProperty                 duracao;
   private ObjectProperty<LocalDate>       inicio;
   private ObjectProperty<InstrutorFXBean> instrutor;

   public ModuloFXBean() {
      this("",LocalDate.now(),0,null);
   }
   public ModuloFXBean(String nome, Date inicio) { this(nome,inicio,null); }
   public ModuloFXBean(String nome, LocalDate inicio) {
      this(nome,inicio,0,null);
   }
   public ModuloFXBean(String nome, Date inicio, Integer duracao) {
      this(nome,inicio,duracao,null);
   }
   public ModuloFXBean(String nome, LocalDate inicio, Integer duracao) {
      this(nome,inicio,duracao,null);
   }
   public ModuloFXBean(String nome, Date inicio, Integer duracao,
                       InstrutorFXBean instrutor) {
      this(nome,DateTimeUtils.dateToLocalDate(inicio),duracao,instrutor);
   }
   public ModuloFXBean(String nome, LocalDate inicio, Integer duracao,
                       InstrutorFXBean instrutor) {
      this.nome        = new SimpleStringProperty(nome);
      this.duracao     = new SimpleIntegerProperty(duracao);
      this.inicio      = new SimpleObjectProperty<>(inicio);
      this.instrutor   = new SimpleObjectProperty<>(instrutor);
   }

   public String getNome() { return nome.get(); }
   public void setNome(String nome) { this.nome.set(nome); }
   public StringProperty nomeProperty() { return this.nome; }

   public Integer getDuracao() { return duracao.get(); }
   public void setDuracao(Integer duracao) { this.duracao.set(duracao); }
   public IntegerProperty duracaoProperty() { return this.duracao; }

   public LocalDate getInicio() { return inicio.get(); }
   public void setInicio(LocalDate inicio) { this.inicio.set(inicio); }
   public ObjectProperty<LocalDate> inicioProperty() { return this.inicio; }

   public InstrutorFXBean getInstrutor() { return instrutor.get(); }
   public void setInstrutor(InstrutorFXBean instrutor) {
      this.instrutor.set(instrutor);
   }
   public ObjectProperty<InstrutorFXBean> instrutorProperty() {
      return this.instrutor;
   }

   @Override
   public String toString() {
      return getClass().getSimpleName() + "[nome=" + nome + ", duracao=" +
             duracao + ", inicio=" + DateTimeUtils.formatLocalDate(inicio.get())
             + ", instrutor=" + instrutor +"]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
      result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
      result = prime * result
               + ((instrutor == null) ? 0 : instrutor.hashCode());
      result = prime * result + ((nome == null) ? 0 : nome.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      ModuloFXBean other = (ModuloFXBean) obj;
      if (inicio == null) {
         if (other.inicio != null) return false;
      }
      else
         if (!inicio.equals(other.inicio)) return false;
      if (duracao == null) {
         if (other.duracao != null) return false;
      }
      else
         if (!duracao.equals(other.duracao)) return false;
      if (instrutor == null) {
         if (other.instrutor != null) return false;
      }
      else
         if (!instrutor.equals(other.instrutor)) return false;
      if (nome == null) {
         if (other.nome != null) return false;
      }
      else
         if (!nome.equals(other.nome)) return false;
      return true;
   }

}

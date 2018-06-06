package br.edu.ifpr.treinamento.fxbeans;

import java.time.LocalDate;
import java.util.Date;

import br.edu.ifpr.treinamento.modelo.types.CursoSituacaoType;
import br.edu.ifpr.treinamento.utils.date.DateTimeUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CursoFXBean {
   private StringProperty                    codigo;
   private StringProperty                    nome;
   private ObjectProperty<CursoSituacaoType> situacao;
   private IntegerProperty                   duracao;
   private ObjectProperty<LocalDate>         inicio;
   private ListProperty<ModuloFXBean>        modulos;
   private ListProperty<MatriculaFXBean>     matriculas;

   public CursoFXBean() {
      this("","",0,DateTimeUtils.getNow());
   }
   public CursoFXBean(CursoFXBean curso) {
      this(curso.codigo.get(),curso.nome.get(),curso.duracao.get(),
           curso.inicio.get());
      this.situacao = curso.situacao;
      // coleções criadas na chamada ao construtor (início)
      this.modulos.addAll(curso.modulos);
      this.matriculas.addAll(curso.matriculas);
   }
   public CursoFXBean(String codigo, String nome, Date inicio) {
      this(codigo,nome,0,DateTimeUtils.dateToLocalDate(inicio));
   }
   public CursoFXBean(String codigo, String nome, LocalDate inicio) {
      this(codigo,nome,0,inicio);
   }
   public CursoFXBean(String codigo, String nome, int duracao, Date inicio) {
      this(codigo,nome,duracao,DateTimeUtils.dateToLocalDate(inicio));
   }
   public CursoFXBean(String codigo, String nome, int duracao, LocalDate inicio) {
      this.codigo     = new SimpleStringProperty(codigo);
      this.nome       = new SimpleStringProperty(nome);
      this.situacao   = new SimpleObjectProperty<>(CursoSituacaoType.ABERTO);
      this.duracao    = new SimpleIntegerProperty(duracao);
      this.inicio     = new SimpleObjectProperty<>(inicio);
      this.modulos    = new SimpleListProperty<>(
                                           FXCollections.observableArrayList());
      this.matriculas = new SimpleListProperty<>(
                                           FXCollections.observableArrayList());
   }

   public String getCodigo() { return codigo.get(); }
   public void setCodigo(String codigo) { this.codigo.set(codigo); }
   public StringProperty codigoProperty() { return codigo; }

   public String getNome() { return nome.get(); }
   public void setNome(String nome) { this.nome.set(nome); }
   public StringProperty nomeProperty() { return nome; }

   public CursoSituacaoType getSituacao() { return situacao.get(); }
   public void setSituacao(CursoSituacaoType situacao) {
      this.situacao.set(situacao);
   }
   public ObjectProperty<CursoSituacaoType> situacaoProperty() {
      return situacao;
   }

   public Integer getDuracao() { return duracao.get(); }
   public void setDuracao(Integer duracao) { this.duracao.set(duracao); }
   public IntegerProperty duracaoProperty() { return duracao; }

   public LocalDate getInicio() { return inicio.get(); }
   public void setInicio(LocalDate inicio) { this.inicio.set(inicio); }
   public ObjectProperty<LocalDate> inicioProperty() { return inicio; }

   public ObservableList<ModuloFXBean> getModulos() { return modulos.get(); }
   public void setModulos(ObservableList<ModuloFXBean> modulos) {
      this.modulos.set(modulos);
   }
   public ListProperty<ModuloFXBean> modulosProperty() { return modulos; }

   public ObservableList<MatriculaFXBean> getMatriculas() { return matriculas.get(); }
   public void setMatriculas(ObservableList<MatriculaFXBean> matriculas) {
      this.matriculas.addAll(matriculas);
   }
   public ListProperty<MatriculaFXBean> matriculasProperty() { return matriculas; }

   @Override
   public String toString() {
      return "CursoFXBean [codigo=" + codigo + ", nome=" +
             nome + ", situacao=" + situacao + ", duracao=" + duracao +
             ", inicio=" + DateTimeUtils.formatLocalDate(inicio.get()) +
             ", modulos=" + modulos.size() + ", matriculas=" +
             matriculas.size() + "]";
   }
}

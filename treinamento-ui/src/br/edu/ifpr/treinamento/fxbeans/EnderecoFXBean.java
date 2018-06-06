package br.edu.ifpr.treinamento.fxbeans;

import br.edu.ifpr.treinamento.modelo.types.EnderecoType;
import br.org.romualdo.validators.formatter.CepFormatter;
import br.org.romualdo.validators.types.civil.UF;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EnderecoFXBean {
   private StringProperty               logradouro;
   private IntegerProperty              numero;
   private StringProperty               complemento;
   private StringProperty               bairro;
   private StringProperty               cep;
   private StringProperty               cidade;
   private ObjectProperty<UF>           estado;
   private ObjectProperty<EnderecoType> tipo;

   public EnderecoFXBean() {
      this("",0,"","","","",UF.ACRE,EnderecoType.COMERCIAL);
   }
   // sem "complemento"
   public EnderecoFXBean(String logradouro, int numero, String bairro,
                         String cep, String cidade, UF estado,
                         EnderecoType tipo) {
      this(logradouro,numero,"",bairro,cep,cidade,estado,tipo);
   }
   public EnderecoFXBean(String logradouro, int numero, String complemento,
                         String bairro, String cep, String cidade, UF estado,
                         EnderecoType tipo) {
      this.logradouro  = new SimpleStringProperty(logradouro);
      this.numero      = new SimpleIntegerProperty(numero);
      this.complemento = new SimpleStringProperty(complemento);
      this.bairro      = new SimpleStringProperty(bairro);
      this.cep         = new SimpleStringProperty(cep);
      this.cidade      = new SimpleStringProperty(cidade);
      this.estado      = new SimpleObjectProperty<>(estado);
      this.tipo        = new SimpleObjectProperty<>(tipo);
   }

   public String getLogradouro() { return logradouro.get(); }
   public void setLogradouro(String logradouro) {
      this.logradouro.set(logradouro);
   }
   public StringProperty logradouroProperty() { return this.logradouro; }

   public int getNumero() { return numero.get(); }
   public void setNumero(int numero) { this.numero.set(numero); }
   public IntegerProperty numeroProperty() { return this.numero; }

   public String getComplemento() { return complemento.get(); }
   public void setComplemento(String complemento) {
      this.complemento.set(complemento);
   }
   public StringProperty complementoProperty() {
      return this.complemento;
   }

   public String getBairro() { return bairro.get(); }
   public void setBairro(String bairro) { this.bairro.set(bairro); }
   public StringProperty bairroProperty() { return this.bairro; }

   public String getCep() { return cep.get(); }
   public void setCep(String cep) { this.cep.set(cep); }
   public StringProperty cepProperty() { return this.cep; }

   public String getCidade() { return cidade.get(); }
   public void setCidade(String cidade) { this.cidade.set(cidade); }
   public StringProperty cidadeProperty() { return this.cidade; }

   public UF getEstado() { return estado.get(); }
   public void setEstado(UF estado) { this.estado.set(estado); }
   public ObjectProperty<UF> estadoProperty() { return this.estado; }

   public EnderecoType getTipo() { return tipo.get(); }
   public void setTipo(EnderecoType tipo) { this.tipo.set(tipo); }
   public ObjectProperty<EnderecoType> tipoProperty() {
      return this.tipo;
   }

   @Override
   public String toString() {
      return getClass().getSimpleName() + "[logradouro=" + logradouro +
             ", numero=" + numero + ", complemento=" + complemento +
             ", bairro=" + bairro +
             ", cep=" + CepFormatter.formatCep(cep.get()) +
             ", cidade=" + cidade + ", estado=" + estado.toString() +
             ", tipo=" + tipo.toString() + "]";
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      EnderecoFXBean other = (EnderecoFXBean) obj;
      if (tipo == null) {
         if (other.tipo != null) return false;
      }
      else
         if (!tipo.equals(other.tipo)) return false;
      if (bairro == null) {
         if (other.bairro != null) return false;
      }
      else
         if (!bairro.equals(other.bairro)) return false;
      if (cep == null) {
         if (other.cep != null) return false;
      }
      else
         if (!cep.equals(other.cep)) return false;
      if (cidade == null) {
         if (other.cidade != null) return false;
      }
      else
         if (!cidade.equals(other.cidade)) return false;
      if (complemento == null) {
         if (other.complemento != null) return false;
      }
      else
         if (!complemento.equals(other.complemento)) return false;
      if (estado == null) {
         if (other.estado != null) return false;
      }
      else
         if (!estado.equals(other.estado)) return false;
      if (logradouro == null) {
         if (other.logradouro != null) return false;
      }
      else
         if (!logradouro.equals(other.logradouro)) return false;
      if (numero != other.numero) return false;
      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
      result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
      result = prime * result + ((cep == null) ? 0 : cep.hashCode());
      result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
      result = prime * result
               + ((complemento == null) ? 0 : complemento.hashCode());
      result = prime * result + ((estado == null) ? 0 : estado.hashCode());
      result = prime * result
               + ((logradouro == null) ? 0 : logradouro.hashCode());
      result = prime * result + ((numero == null) ? 0 : numero.hashCode());
      return result;
   }
}

package br.edu.ifpr.treinamento.fxbeans;

import br.edu.ifpr.treinamento.modelo.types.TelefoneType;
import br.edu.ifpr.utils.formatter.TelefoneFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TelefoneFXBean {
   private StringProperty               ddd;
   private StringProperty               numero;
   private ObjectProperty<TelefoneType> tipo;

   public TelefoneFXBean() {
      this("","",TelefoneType.CELULAR);
   }
   public TelefoneFXBean(TelefoneFXBean fone) {
      this(fone.ddd.get(),fone.numero.get(),fone.tipo.get());
   }
   public TelefoneFXBean(String ddd, String numero, TelefoneType tipo) {
      this.ddd    = new SimpleStringProperty(ddd);
      this.numero = new SimpleStringProperty(numero);
      this.tipo   = new SimpleObjectProperty<>(tipo);
   }

   public String getDdd() { return ddd.get(); }
   public void setDdd(String ddd) { this.ddd.set(ddd); }
   public StringProperty dddProperty() { return this.ddd; }

   public String getNumero() { return numero.get(); }
   public void setNumero(String numero) { this.numero.set(numero); }
   public StringProperty numeroProperty() { return this.numero; }

   public TelefoneType getTipo() { return tipo.get(); }
   public void setTipo(TelefoneType tipo) { this.tipo.set(tipo); }
   public ObjectProperty<TelefoneType> tipoProperty() { return this.tipo; }

   @Override
   public String toString() {
//      return getClass().getSimpleName() + "[ddd=" + ddd.get() +
//             ", numero=" + numero.get() +
//             ", tipo=" + tipo.get() + "]";
      String formattedFone = TelefoneFormatter.formatTelefone(ddd.get(),
                                                              Boolean.FALSE,
                                                              numero.get() +
                                                             " (" + tipo + ")");

      return getClass().getSimpleName() + "[" + formattedFone + "]";
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      TelefoneFXBean other = (TelefoneFXBean) obj;
      if (ddd == null) {
         if (other.ddd != null) return false;
      }
      else
         if (!ddd.equals(other.ddd)) return false;
      if (numero == null) {
         if (other.numero != null) return false;
      }
      else
         if (!numero.equals(other.numero)) return false;
      if (tipo != other.tipo) return false;
      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((ddd == null) ? 0 : ddd.hashCode());
      result = prime * result + ((numero == null) ? 0 : numero.hashCode());
      result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
      return result;
   }
}

package br.edu.ifpr.treinamento.fxbeans.converters.jfx;

import javafx.util.StringConverter;

public class IntegerConverterFX extends StringConverter<Number> {
   @Override
   public String toString(Number object) {
      if (object == null) return null;

      return object.toString();
   }

   @Override
   public Integer fromString(String string) {
      Integer temp = Integer.valueOf(0);

      if (string != null && !string.isEmpty())
         try { temp = Integer.parseInt(string); }
         catch (NumberFormatException ex) {
            ; // não precisa tratamento, pois retornará 0 (zero)
         }
      return temp;
   }
}

package br.edu.ifpr.treinamento.fxbeans.converters;

public interface EntityConverter<E, F> {
   E toEntity(F f);
   F fromEntity(E e);
}

package br.edu.ifpr.treinamento.fxbeans.converters;

import br.edu.ifpr.treinamento.fxbeans.EnderecoFXBean;
import br.edu.ifpr.treinamento.fxbeans.InstrutorFXBean;
import br.edu.ifpr.treinamento.fxbeans.TelefoneFXBean;
import br.edu.ifpr.treinamento.modelo.Instrutor;
import br.edu.ifpr.treinamento.modelo.Telefone;
import br.edu.ifpr.treinamento.utils.date.DateTimeUtils;

public class InstrutorEntityConverter
             implements EntityConverter<Instrutor, InstrutorFXBean> {
   @Override
   public Instrutor toEntity(InstrutorFXBean f) {
      EnderecoEntityConverter convE = new EnderecoEntityConverter();
      TelefoneEntityConverter convT = new TelefoneEntityConverter();

      Instrutor instr = new Instrutor();

      instr.setCpf(f.cpfProperty().get());
      instr.setNome(f.nomeProperty().get());
      instr.setRg(f.rgProperty().get());
      instr.setNascimento(
                   DateTimeUtils.localDateToDate(f.nascimentoProperty().get()));
      instr.setSexo(f.sexoProperty().get());
      instr.setEmail(f.emailProperty().get());
      instr.setEndereco(convE.toEntity(f.enderecoProperty().get()));

      for (TelefoneFXBean fxbFone : f.fonesProperty())
         instr.getFones().add(convT.toEntity(fxbFone));

      instr.setCodigo(f.codigoProperty().get());

      // NÃO CONVERTE MÓDULOS

      convT = null;
      convE = null;

      return instr;
   }

   @Override
   public InstrutorFXBean fromEntity(Instrutor e) {
      EnderecoEntityConverter convE = new EnderecoEntityConverter();
      TelefoneEntityConverter convT = new TelefoneEntityConverter();

      EnderecoFXBean fxbEndereco = convE.fromEntity(e.getEndereco());

      InstrutorFXBean fxbInstrutor = InstrutorFXBean.builder()
                                                    .cpf(e.getCpf())
                                                    .nome(e.getNome())
                                                    .rg(e.getRg())
                   .nascimento(DateTimeUtils.dateToLocalDate(e.getNascimento()))
                                                    .sexo(e.getSexo())
                                                    .email(e.getEmail())
                                                    .endereco(fxbEndereco)
                                                    .codigo(e.getCodigo())
                                                    .build();

      for (Telefone f : e.getFones())
         fxbInstrutor.fonesProperty().add(convT.fromEntity(f));

      // NÃO CONVERTE MÓDULOS

      convT = null;
      convE = null;

      return fxbInstrutor;
   }
}

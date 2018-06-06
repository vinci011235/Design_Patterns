package br.edu.ifpr.treinamento.fxbeans.converters;

import br.edu.ifpr.treinamento.fxbeans.AlunoFXBean;
import br.edu.ifpr.treinamento.fxbeans.EnderecoFXBean;
import br.edu.ifpr.treinamento.fxbeans.TelefoneFXBean;
import br.edu.ifpr.treinamento.modelo.Aluno;
import br.edu.ifpr.treinamento.modelo.Telefone;
import br.edu.ifpr.treinamento.utils.date.DateTimeUtils;

public class AlunoEntityConverter
             implements EntityConverter<Aluno, AlunoFXBean> {
   @Override
   public Aluno toEntity(AlunoFXBean f) {
      EnderecoEntityConverter convE = new EnderecoEntityConverter();
      TelefoneEntityConverter convT = new TelefoneEntityConverter();

      Aluno aluno = new Aluno();

      aluno.setCpf(f.cpfProperty().get());
      aluno.setNome(f.nomeProperty().get());
      aluno.setRg(f.rgProperty().get());
      aluno.setNascimento(
                   DateTimeUtils.localDateToDate(f.nascimentoProperty().get()));
      aluno.setSexo(f.sexoProperty().get());
      aluno.setEmail(f.emailProperty().get());
      aluno.setEndereco(convE.toEntity(f.enderecoProperty().get()));

      for (TelefoneFXBean fxbFone : f.fonesProperty())
         aluno.getFones().add(convT.toEntity(fxbFone));

      aluno.setRegistro(f.registroProperty().get());

      // NÃO CONVERTE MATRÍCULAS

      convT = null;
      convE = null;

      return aluno;
   }

   @Override
   public AlunoFXBean fromEntity(Aluno e) {
      EnderecoEntityConverter convE = new EnderecoEntityConverter();
      TelefoneEntityConverter convT = new TelefoneEntityConverter();

      EnderecoFXBean fxbEndereco = convE.fromEntity(e.getEndereco());

      AlunoFXBean fxbAluno = new AlunoFXBean();

      fxbAluno.cpfProperty().set(e.getCpf());
      fxbAluno.nomeProperty().set(e.getNome());
      fxbAluno.rgProperty().set(e.getRg());
      fxbAluno.nascimentoProperty().set(
                              DateTimeUtils.dateToLocalDate(e.getNascimento()));
      fxbAluno.sexoProperty().set(e.getSexo());
      fxbAluno.emailProperty().set(e.getEmail());
      fxbAluno.enderecoProperty().set(fxbEndereco);

      for (Telefone f : e.getFones())
         fxbAluno.fonesProperty().add(convT.fromEntity(f));

      fxbAluno.registroProperty().set(e.getRegistro());

      // NÃO CONVERTE MATRÍCULAS

      convT = null;
      convE = null;

      return fxbAluno;
   }
}

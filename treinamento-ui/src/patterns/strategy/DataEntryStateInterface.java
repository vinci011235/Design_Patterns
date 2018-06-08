package patterns.strategy;

import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroCursoController;
import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroModuloController;

public interface DataEntryStateInterface {
	public void changeState(CadastroCursoController ccc);
	public void changeState(CadastroModuloController cmc);
}

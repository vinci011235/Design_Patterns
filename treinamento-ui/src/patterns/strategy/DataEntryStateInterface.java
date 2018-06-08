package patterns.strategy;

import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroModuloController;
import patterns.template.CadastroController;

public interface DataEntryStateInterface {
	public void changeState(CadastroController cc);
}

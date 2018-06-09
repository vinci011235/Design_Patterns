package patterns.strategy;

import patterns.template.CadastroController;

public interface DataEntryStateInterface {
	public void changeState(CadastroController cc);
}

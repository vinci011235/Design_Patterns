package patterns.state;

import patterns.template.CadastroController;

public interface DataEntryStateInterface {
	public void changeState(CadastroController cc);
}

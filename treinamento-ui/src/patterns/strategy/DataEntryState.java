package patterns.strategy;

import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroCursoController;
import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroModuloController;

public enum DataEntryState implements DataEntryStateInterface {
	INIT {
		@Override
		public void changeState(CadastroCursoController ccc) {
			ccc.changeStateInit();
			System.out.println("Teste_1s");
		}

		@Override
		public void changeState(CadastroModuloController cmc) {
			cmc.changeStateInit();
		}
	},
	VIEW {
		@Override
		public void changeState(CadastroCursoController ccc) {
			ccc.changeStateView();
		}

		@Override
		public void changeState(CadastroModuloController cmc) {
			cmc.changeStateView();
			
		}
	},
	INSERT {
		@Override
		public void changeState(CadastroCursoController ccc) {
			ccc.changeStateInsert();
		}

		@Override
		public void changeState(CadastroModuloController cmc) {
			cmc.changeStateInsert();
			
		}
	},
	EDIT {
		@Override
		public void changeState(CadastroCursoController ccc) {
			ccc.changeStateUpdate();
		}

		@Override
		public void changeState(CadastroModuloController cmc) {
			cmc.changeStateUpdate();
			
		}
	},
	DELETE {
		@Override
		public void changeState(CadastroCursoController ccc) {
			ccc.changeStateDelete();
		}

		@Override
		public void changeState(CadastroModuloController cmc) {
			cmc.changeStateDelete();
			
		}
	},
	SAVE {
		@Override
		public void changeState(CadastroCursoController ccc) {
			ccc.changeStateSave();
		}

		@Override
		public void changeState(CadastroModuloController cmc) {
			cmc.changeStateSave();
			
		}
	},
	CANCEL {
		@Override
		public void changeState(CadastroCursoController ccc) {
			ccc.changeStateCancel();
		}

		@Override
		public void changeState(CadastroModuloController cmc) {
			cmc.changeStateCancel();
			
		}
	},
	SEARCH {
		@Override
		public void changeState(CadastroCursoController ccc) {
			ccc.changeStateFind();
		}

		@Override
		public void changeState(CadastroModuloController cmc) {
			cmc.changeStateFind();
			
		}
	};
}
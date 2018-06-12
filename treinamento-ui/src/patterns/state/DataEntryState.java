package patterns.state;

import patterns.template.CadastroController;

public enum DataEntryState implements DataEntryStateInterface {
	INIT {
		@Override
		public void changeState(CadastroController cc) {
			cc.changeStateInit();
		}
	},
	VIEW {
		@Override
		public void changeState(CadastroController cc) {
			cc.changeStateView();
		}
	},
	INSERT {
		@Override
		public void changeState(CadastroController cc) {
			cc.changeStateInsert();
		}
	},
	EDIT {
		@Override
		public void changeState(CadastroController cc) {
			cc.changeStateUpdate();
		}
	},
	DELETE {
		@Override
		public void changeState(CadastroController cc) {
			cc.changeStateDelete();
		}
	},
	SAVE {
		@Override
		public void changeState(CadastroController cc) {
			cc.changeStateSave();
		}
	},
	CANCEL {
		@Override
		public void changeState(CadastroController cc) {
			cc.changeStateCancel();
		}
	},
	SEARCH {
		@Override
		public void changeState(CadastroController cc) {
			cc.changeStateFind();
		}
	};
}
package patterns.template;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public abstract class CadastroController {

	// Pattern 'Template' to class 'CadastroCursoController' and
	// 'CadastroModuloController'
	@FXML	private MenuItem miIncluir;
	@FXML	private MenuItem miAlterar;
	@FXML	private MenuItem miExcluir;
	@FXML	private MenuItem miSalvar;
	@FXML	private MenuItem miCancelar;
	@FXML	private MenuItem miProcurar;
	@FXML	private MenuItem miSair;

	@FXML	private Button btnIncluir;
	@FXML	private Button btnAlterar;
	@FXML	private Button btnExcluir;
	@FXML	private Button btnSalvar;
	@FXML	private Button btnCancelar;
	@FXML	private Button btnProcurar;

	public CadastroController() {
	}
	
	@FXML
	private void onMenuButtonBarAction(ActionEvent ev) {
		Object component = ev.getSource();

		if (component == miIncluir || component == btnIncluir)
			insert();
		else if (component == miAlterar || component == btnAlterar)
			update();
		else if (component == miExcluir || component == btnExcluir)
			delete();
		else if (component == miSalvar || component == btnSalvar)
			save();
		else if (component == miCancelar || component == btnCancelar)
			cancel();
		else if (component == miProcurar || component == btnProcurar)
			search();
		else if (component == miSair)
			exit();
	}

	public void insert() {
		doInsert();
	}

	public void update() {
		doUpdate();
	}

	public void delete() {
		doDelete();
	}

	public void save() {
		doSave();
	}

	public void cancel() {
		doCancel();
	}

	public void search() {
		doFind();
	}

	public void exit() {
		doExit();
	}
	public void insertFocus() {
		doInsertFocus();
	}
	
	public void viewFocus() {
		doViewFocus();
	}
	
	public void updateFocus() {
		doShowData();
		doUpdateFocus();
	}
	
	public void showData() {
		doShowData();
	}
	
	public void clearData() {
		doClearData();
	}

	public abstract void doInsert();

	public abstract void doUpdate();

	public abstract void doDelete();

	public abstract void doSave();

	public abstract void doCancel();

	public abstract void doFind();

	public abstract void doExit();
	
	public abstract void doInsertFocus();
	
	public abstract void doViewFocus();
	
	public abstract void doUpdateFocus();
	
	public abstract void doShowData();
	
	public abstract void doClearData();

	// Bindings agora realizados por metodos gets da classe 'CadastroController'
	public void initBindings() {
		btnIncluir.disableProperty().bind(miIncluir.disableProperty());
		btnAlterar.disableProperty().bind(miAlterar.disableProperty());
		btnExcluir.disableProperty().bind(miExcluir.disableProperty());
		btnSalvar.disableProperty().bind(miSalvar.disableProperty());
		btnCancelar.disableProperty().bind(miCancelar.disableProperty());
		btnProcurar.disableProperty().bind(miProcurar.disableProperty());
	}

	public void changeStateInit() {
		miIncluir.disableProperty().set(false);
		miAlterar.disableProperty().set(true);
		miExcluir.disableProperty().set(true);
		miSalvar.disableProperty().set(true);
		miCancelar.disableProperty().set(true);
		miProcurar.disableProperty().set(false);
		// inicializações adicionais
		btnIncluir.requestFocus();
	}

	public void changeStateView() {
		miIncluir.disableProperty().set(false);
		miAlterar.disableProperty().set(false);
		miExcluir.disableProperty().set(false);
		miSalvar.disableProperty().set(true);
		miCancelar.disableProperty().set(true);
		miProcurar.disableProperty().set(false);
	}

	public void changeStateInsert() {
		miIncluir.disableProperty().set(true);
		miAlterar.disableProperty().set(true);
		miExcluir.disableProperty().set(true);
		miSalvar.disableProperty().set(false);
		miCancelar.disableProperty().set(false);
		miProcurar.disableProperty().set(true);
		// ajusta a interface para inclusão
		insertFocus();
	}

	public void changeStateUpdate() {
		miIncluir.disableProperty().set(true);
		miAlterar.disableProperty().set(true);
		miExcluir.disableProperty().set(true);
		miSalvar.disableProperty().set(false);
		miCancelar.disableProperty().set(false);
		miProcurar.disableProperty().set(false);
		// ajusta a entrada de dados para alteração
		updateFocus();
	}

	public void changeStateDelete() {
		// menu
		miIncluir.disableProperty().set(true);
		miAlterar.disableProperty().set(true);
		miExcluir.disableProperty().set(false);
		miSalvar.disableProperty().set(true);
		miCancelar.disableProperty().set(true);
		miProcurar.disableProperty().set(true);
	}

	public void changeStateSave() {
		;
	}

	public void changeStateCancel() {
		;
	}

	public void changeStateFind() {
		;
	}

}

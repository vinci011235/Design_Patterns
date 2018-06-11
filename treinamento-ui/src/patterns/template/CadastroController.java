package patterns.template;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public abstract class CadastroController {
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
	
	public void initComponents() {
		miIncluir.setOnAction(e -> {
			doInsert();
		});
		btnIncluir.setOnAction(e -> {
			doInsert();
		});
		miAlterar.setOnAction(e -> {
			doUpdate();
		});
		btnAlterar.setOnAction(e -> {
			doUpdate();
		});
		miExcluir.setOnAction(e -> {
			doDelete();
		});
		btnExcluir.setOnAction(e -> {
			doDelete();
		});
		miSalvar.setOnAction(e -> {
			doSave();
		});
		btnSalvar.setOnAction(e -> {
			doSave();
		});
		miCancelar.setOnAction(e -> {
			doCancel();
		});
		btnCancelar.setOnAction(e -> {
			doCancel();
		});
		miProcurar.setOnAction(e -> {
			doFind();
		});
		btnProcurar.setOnAction(e -> {
			doFind();
		});
		miSair.setOnAction(e -> {
			doExit();
		});
	}
	
	@FXML
	private void onMenuButtonBarAction(ActionEvent ev) {
		initComponents();
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
		doInsertFocus();
	}

	public void changeStateUpdate() {
		miIncluir.disableProperty().set(true);
		miAlterar.disableProperty().set(true);
		miExcluir.disableProperty().set(true);
		miSalvar.disableProperty().set(false);
		miCancelar.disableProperty().set(false);
		miProcurar.disableProperty().set(false);
		// ajusta a entrada de dados para alteração
		doUpdateFocus();
	}

	public void changeStateDelete() {
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

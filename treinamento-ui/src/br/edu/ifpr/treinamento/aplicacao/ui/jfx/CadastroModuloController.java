package br.edu.ifpr.treinamento.aplicacao.ui.jfx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.fxbeans.InstrutorFXBean;
import br.edu.ifpr.treinamento.fxbeans.ModuloFXBean;
import br.edu.ifpr.treinamento.fxbeans.converters.InstrutorEntityConverter;
import br.edu.ifpr.treinamento.fxbeans.converters.jfx.IntegerConverterFX;
import br.edu.ifpr.treinamento.modelo.Instrutor;
import br.edu.ifpr.treinamento.modelo.Modulo;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import br.edu.ifpr.treinamento.modelo.service.command.JpaPersistenceDAOType;
import br.edu.ifpr.treinamento.modelo.service.command.impl.InstrutorPersistenceDAO;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import patterns.strategy.DataEntryState;
import patterns.template.CadastroController;

public class CadastroModuloController extends CadastroController {
	@FXML	private GridPane gpDados;
	@FXML	private TextField txfNome;
	@FXML	private TextField txfDuração;
	@FXML	private DatePicker dpkInício;
	@FXML	private ComboBox<InstrutorFXBean> cbxInstrutor;

	private ScreenManager screenManager;
	private InstrutorPersistenceDAO instrutorDao;

	private ModuloFXBean fxbModulo;
	private Modulo modulo;

	private ModuloFXBean lastFXBModulo;

	private List<InstrutorFXBean> fxbInstrutores;

	private BooleanProperty dataEntryActiveProperty;
	private BooleanProperty dataEntryInsertingProperty;
	private BooleanProperty direta;

	private DataEntryState lastState;
	private DataEntryState state;
	private Node lastFocused;

	// template CadastroController
	// received 'this' because in this class extends 'CadastroController'
	CadastroController cc = this;

	public void initState(ScreenManager screenManager, JpaService jpaService) {
		initState(DataEntryState.INIT, screenManager, jpaService);
	}

	public void initState(DataEntryState state, ScreenManager screenManager, JpaService jpaService) {
		this.direta = new SimpleBooleanProperty(state == DataEntryState.INIT);
		this.state = state;
		this.lastFocused = null;
		this.screenManager = screenManager;
		this.instrutorDao = (InstrutorPersistenceDAO) jpaService
				.persistenceCommandFactory(JpaPersistenceDAOType.INSTRUTOR);

		this.fxbInstrutores = new ArrayList<>();
		populateInstrutorOList();

		this.fxbModulo = new ModuloFXBean();
		this.lastFXBModulo = new ModuloFXBean();
		this.modulo = new Modulo();

		dataEntryActiveProperty = new SimpleBooleanProperty(false);
		dataEntryInsertingProperty = new SimpleBooleanProperty(false);

		initBindings();
		initBindingDataEntry();

		changeState(state);

		txfDuração.setAlignment(Pos.CENTER_RIGHT);
		init();
	}

	private void init() {
		doClearData();

		dataEntryActiveProperty.set(false);
		dataEntryInsertingProperty.set(false);
	}

	public FocusPropertyChangeListener getFocusPropertyChangeListener() {
		return new FocusPropertyChangeListener();
	}


	private void initBindingDataEntry() {
		gpDados.disableProperty().bind(dataEntryActiveProperty.not());

		txfNome.textProperty().bindBidirectional(fxbModulo.nomeProperty());
		Bindings.bindBidirectional(txfDuração.textProperty(), fxbModulo.duracaoProperty(), new IntegerConverterFX());
		dpkInício.valueProperty().bindBidirectional(fxbModulo.inicioProperty());
		Bindings.bindBidirectional(cbxInstrutor.valueProperty(),
				fxbModulo.instrutorProperty());
		
		initBindingInstrutores();
	}

	private void initBindingInstrutores() {
		cbxInstrutor.itemsProperty().get().addAll(fxbInstrutores);
		cbxInstrutor.buttonCellProperty().set(new InstrutorComboBoxListCell());
		cbxInstrutor.cellFactoryProperty().set(new InstrutorComboBoxCallback());
	}

	private void changeState(DataEntryState newState) {
		this.lastState = this.state;
		this.state = newState;
		state.changeState(this);

		// SWITCH CASE REMOVED
		// DESIGN PATTERN STRATEGY
	}

	@Override
	public void doInsert() {

	}

	@Override
	public void doUpdate() {

	}

	@Override
	public void doDelete() {
		if (showAlert(AlertType.CONFIRMATION, "Pedido de Exclusão", "Deseja realmente excluir os dados???")) {
			//String key = modulo.getNome();

			init();
			changeState(DataEntryState.INIT);
		}
	}

	@Override
	public void doSave() {
		boolean dadosValidos = true;
		if (dadosValidos) {
			// ModuloEntityConverter conv = new ModuloEntityConverter();
			//
			// toEntity();
			// if (state == DataEntryState.INSERT)
			// moduloDao.insert(modulo);
			// else
			// moduloDao.update(modulo);
			if (!direta.get())
				screenManager.previous();
		} else {

		}
		// if (state == DataEntryState.INSERT) {
		// changeState(DataEntryState.INIT);
		// if (cadastro != null)
		// cadastro.initState();
		// }
		// else {
		changeState(DataEntryState.VIEW);
		doViewFocus();
		// }
	}

	@Override
	public void doCancel() {
		if (showAlert(AlertType.CONFIRMATION, "Pedido de Cancelamento", "Deseja realmente cancelar a operação?")) {
			if (!direta.get())
				screenManager.previous();
			else {
				if (lastState == DataEntryState.INIT || lastState == DataEntryState.INSERT) {
					changeState(DataEntryState.INIT);
					init();
				} else {
					copy(fxbModulo, lastFXBModulo);
					changeState(DataEntryState.VIEW);
					doViewFocus();
				}
			}
		}
	}

	@Override
	public void doFind() {
		//final String CURSOS[] = { "AAA", "BBB", "CCC", "DDD", "EEE" };
		//Random r = new Random();
		//String codigo = CURSOS[r.nextInt(CURSOS.length)];

		if (modulo != null) {
			copy(lastFXBModulo, fxbModulo);

			doViewFocus();
			changeState(DataEntryState.VIEW);
		}
	}

	@Override
	public void doExit() {
		if (showAlert(AlertType.CONFIRMATION, "Pedido de Fechamento", "Deseja realmente Sair?"))
			screenManager.previous();
	}
	
	@Override
	public void doInsertFocus() {
		doClearData();

		dataEntryActiveProperty.set(true);
		dataEntryInsertingProperty.set(true);

		txfNome.requestFocus();
	}

	@Override
	public void doViewFocus() {
		dataEntryActiveProperty.set(false);
		dataEntryInsertingProperty.set(false);
	}

	@Override
	public void doUpdateFocus() {
		doShowData();

		dataEntryActiveProperty.set(true);
		dataEntryInsertingProperty.set(false);
		txfNome.requestFocus();
	}

	@Override
	public void doShowData() {
		;
	}

	@Override
	public void doClearData() {
		txfNome.clear();
		txfDuração.textProperty().set("0");
		dpkInício.valueProperty().set(LocalDate.now());

		cbxInstrutor.selectionModelProperty().get().clearSelection();
	}

	private void copy(ModuloFXBean target, ModuloFXBean source) {
		source.nomeProperty().set(target.nomeProperty().get());
		source.duracaoProperty().set(target.duracaoProperty().get());
		source.inicioProperty().set(target.inicioProperty().get());
		source.instrutorProperty().set(target.instrutorProperty().get());
	}

	private void populateInstrutorOList() {
		// objeto converter do Modelo de Objetos para o JavaFX Bean
		InstrutorEntityConverter conv = new InstrutorEntityConverter();
		// recupera os instrutores da fonte de dados
		Collection<Instrutor> temp = instrutorDao.select();
		// converte de 'Instrutor' para 'InstrutorFXBean'
		for (Instrutor i : temp) {
			fxbInstrutores.add(conv.fromEntity(i));
		}

		temp = null;
		conv = null;
	}

	private boolean showAlert(AlertType alertType, String title, String content) {
		return showAlert(alertType, title, null, content);
	}

	private boolean showAlert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);

		alert.titleProperty().set(title);
		alert.headerTextProperty().set(header);
		alert.contentTextProperty().set(content);

		Button button = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
		button.setDefaultButton(false);

		button = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
		button.setDefaultButton(true);

		ButtonType btn = alert.showAndWait().get();

		if (btn == ButtonType.CANCEL)
			if (lastFocused != null)
				lastFocused.requestFocus();

		return btn == ButtonType.OK;
	}

	// ==========================================================================
	// === CLASSES DE SUPORTE - INÍCIO ==========================================
	// ==========================================================================
	private class FocusPropertyChangeListener implements ChangeListener<Node> {
		@Override
		public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
			lastFocused = oldValue;
		}
	}

	private class InstrutorComboBoxCallback implements Callback<ListView<InstrutorFXBean>, ListCell<InstrutorFXBean>> {
		@Override
		public ListCell<InstrutorFXBean> call(ListView<InstrutorFXBean> param) {
			return new InstrutorComboBoxListCell();
		}
	}

	private class InstrutorComboBoxListCell extends ListCell<InstrutorFXBean> {
		@Override
		public void updateItem(InstrutorFXBean item, boolean empty) {
			super.updateItem(item, empty);

			if (item == null)
				textProperty().set(null);
			else
				textProperty().set(item.nomeProperty().get());
		}
	}
}

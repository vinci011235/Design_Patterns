package br.edu.ifpr.treinamento.aplicacao.ui.jfx;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.fxbeans.CursoFXBean;
import br.edu.ifpr.treinamento.fxbeans.EnderecoFXBean;
import br.edu.ifpr.treinamento.fxbeans.InstrutorFXBean;
import br.edu.ifpr.treinamento.fxbeans.ModuloFXBean;
import br.edu.ifpr.treinamento.fxbeans.TelefoneFXBean;
import br.edu.ifpr.treinamento.modelo.Curso;
import br.edu.ifpr.treinamento.modelo.Endereco;
import br.edu.ifpr.treinamento.modelo.Instrutor;
import br.edu.ifpr.treinamento.modelo.Modulo;
import br.edu.ifpr.treinamento.modelo.Telefone;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import br.edu.ifpr.treinamento.modelo.service.command.JpaPersistenceDAOType;
import br.edu.ifpr.treinamento.modelo.service.command.impl.CursoPersistenceDAO;
import br.edu.ifpr.treinamento.modelo.types.CursoSituacaoType;
import br.edu.ifpr.treinamento.utils.date.DateTimeUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import patterns.command.Command;
import patterns.command.factory.CommandFactory;
import patterns.command.factory.CommandFactoryProperties;
import patterns.state.DataEntryState;
import patterns.template.CadastroController;

public class CadastroCursoController extends CadastroController {
	private static final String FXML_PATH_NAME = "/br/edu/ifpr/treinamento/aplicacao/ui/jfx/";
	private static final String CADASTRO_MÓDULO_FXML = "CadastroModulo.fxml";

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final List<String> CURSO_SITUACOES = Arrays.asList(CursoSituacaoType.ABERTO.toString(),
			CursoSituacaoType.ANDAMENTO.toString(), CursoSituacaoType.ENCERRADO.toString(),
			CursoSituacaoType.CANCELADO.toString());
	
	@FXML	private GridPane gpDados;
	@FXML	private TextField txfCodigo;
	@FXML	private TextField txfNome;
	@FXML	private ChoiceBox<String> chbSituacao;
	@FXML	private DatePicker dpkInicio;

	@FXML	private Label lbModulos;
	@FXML	private TableView<ModuloFXBean> tvwModulos;
	@FXML	private TableColumn<ModuloFXBean, LocalDate> tclInicio;
	@FXML	private TableColumn<ModuloFXBean, Integer> tclDuracao;
	@FXML	private TableColumn<ModuloFXBean, String> tclNome;

	@FXML	private Button btnIncluirModulo;
	@FXML	private Button btnAlterarModulo;
	@FXML	private Button btnExcluirModulo;
	@FXML	private Button btnProcurarModulo;
	@FXML	private Button btnSelecionarTodosModulo;
	@FXML	private Button btnLimparSelecaoModulo;

	// INCLUSÃO OU ALTERAÇÃO: true; VISUALIZAR: false
	private BooleanProperty dataEntryActiveProperty;
	// INCLUSÃO: true; ALTERAÇÃO: false
	private BooleanProperty dataEntryInsertingProperty;

	private CursoPersistenceDAO dao;
	private CursoFXBean fxbCurso;
	private Curso curso;
	private CursoFXBean lastFXBCurso;

	private ScreenManager sceneManager;
	private JpaService jpaService;

	private DataEntryState lastState;
	private DataEntryState state;
	private Node lastFocused;
	
	@FXML
	private void onModulosButtonAction(ActionEvent ev) throws IOException {
		btnIncluirModulo.setUserData(CommandFactory.INCLUIR_MODULO);
		btnAlterarModulo.setUserData(CommandFactory.ALTERAR_MODULO);
		btnExcluirModulo.setUserData(CommandFactory.EXCLUIR_MODULO);
		btnProcurarModulo.setUserData(CommandFactory.PROCURAR_MODULO);
		btnSelecionarTodosModulo.setUserData(CommandFactory.SELECIONAR_TODOS);
		btnLimparSelecaoModulo.setUserData(CommandFactory.LIMPAR_SELECAO);
	}

	public void initState(ScreenManager sceneManager, JpaService jpaService) {
		this.jpaService = jpaService;

		this.sceneManager = sceneManager;
		this.lastFocused = null;

		initBindings();

		fxbCurso = new CursoFXBean();
		lastFXBCurso = new CursoFXBean();

		changeState(DataEntryState.INIT);

		dataEntryActiveProperty = new SimpleBooleanProperty(false);
		dataEntryInsertingProperty = new SimpleBooleanProperty(false);
		init();

		this.dao = (CursoPersistenceDAO) jpaService.persistenceCommandFactory(JpaPersistenceDAOType.CURSO);

		initBindingDataEntry();
		initBindingModulos();
	}

	private void init() {
		doClearData();

		dataEntryActiveProperty.set(false);
		dataEntryInsertingProperty.set(false);
	}

	@Override
	public void doInsert() {
		changeState(DataEntryState.INSERT);
	}
	
	@Override
	public void doUpdate() {
		changeState(DataEntryState.EDIT);
	}

	@Override
	public void doDelete() {
		if (showAlert(AlertType.CONFIRMATION, "Pedido de Exclusão", "Deseja realmente excluir os dados???")) {
			// EXCLUIR DADOS
			String key = curso.getCodigo();
			int deleted = dao.delete(key);
			// LIMPAR ENTRADA DE DADOS
			// ALTERAR ESTADO "INICIAL" (os dados são "limpos" aqui)
			init();
			changeState(DataEntryState.INIT);
		}
	}
	
	@Override
	public void doSave() {
		boolean dadosValidos = true;
		if (dadosValidos) {
			toEntity();
			if (state == DataEntryState.INSERT)
				dao.insert(curso);
			else
				dao.update(curso);
		}

		changeState(DataEntryState.VIEW);
		doViewFocus();
	}
	
	@Override
	public void doCancel() {
		if (showAlert(AlertType.CONFIRMATION, "Pedido de Cancelamento", "Deseja realmente cancelar a operação?")) {
			if (lastState == DataEntryState.INIT || lastState == DataEntryState.INSERT) {
				changeState(DataEntryState.INIT);
				init();
			} else {
				// COPIAR 'lastFXBCurso' PARA 'fxbCurso'
				copy(fxbCurso, lastFXBCurso);
				changeState(DataEntryState.VIEW);
				doViewFocus();
			}
		}
	}
	
	@Override
	public void doFind() {
		final String CURSOS[] = { "AAA", "BBB", "CCC", "DDD", "EEE" };
		Random r = new Random();
		String codigo = CURSOS[r.nextInt(CURSOS.length)];
		// ENTRAR NO MODO DE BUSCA
		// ALTERAR ESTADO "VISUALIZAÇÃO"
		curso = dao.select(codigo);
		if (curso != null) {
			fromEntity();

			// COPIAR 'fxbCurso' PARA 'lastFXBCurso'
			copy(lastFXBCurso, fxbCurso);

			doViewFocus();
			changeState(DataEntryState.VIEW);
		}
	}

	@Override
	public void doExit() {
		if (showAlert(AlertType.CONFIRMATION, "Pedido de Fechamento", "Deseja realmente Sair?"))
			sceneManager.previous();
	}

	private void changeState(DataEntryState newState) {
		this.lastState = state;
		this.state = newState;
		// SWITCH CASE REMOVED
		// Design Pattern Strategy
		state.changeState(this);
	}

	@Override
	public void doInsertFocus() {
		doClearData();

		dataEntryActiveProperty.set(true);
		dataEntryInsertingProperty.set(true);
		// o foco deve ir ao primeiro componente gráfico para entrada de dados
		txfCodigo.requestFocus();
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
		// PARA TESTE "PROCURAR"
		// fxbCurso.codigoProperty().set("AAA");
		// fxbCurso.nomeProperty().set("CursoFXBean AAA");
		// fxbCurso.situacaoProperty().set(CursoSituacaoType.ABERTO);
		// fxbCurso.inicioProperty().set(DateTimeUtils.localDateNowPlusDays(10));
		//
		// fxbCurso.modulosProperty().clear();
		// fxbCurso.modulosProperty().add(
		// new ModuloFXBean("MÓDULO AAA",DateTimeUtils.getDate()));
		// fxbCurso.modulosProperty().add(
		// new ModuloFXBean("MÓDULO BBB",DateTimeUtils.localDateNowPlusDays(2)));
		// fxbCurso.modulosProperty().add(
		// new ModuloFXBean("MÓDULO CCC",DateTimeUtils.localDateNowPlusDays(4)));
		// fxbCurso.modulosProperty().add(
		// new ModuloFXBean("MÓDULO DDD",DateTimeUtils.localDateNowPlusDays(6)));
		// fxbCurso.modulosProperty().add(
		// new ModuloFXBean("MÓDULO EEE",DateTimeUtils.localDateNowPlusDays(8)));
	}
	
	@Override
	public void doClearData() {
		txfCodigo.clear();
		txfNome.clear();

		initChoiceBoxSituacao();

		dpkInicio.valueProperty().set(LocalDate.now());
		tvwModulos.itemsProperty().get().clear();
	}

	private void toEntity() {
		if (state == DataEntryState.INSERT)
			curso = new Curso();

		curso.setCodigo(fxbCurso.codigoProperty().get());
		curso.setNome(fxbCurso.nomeProperty().get());
		curso.setSituacao(fxbCurso.situacaoProperty().get());
		curso.setInicio(DateTimeUtils.localDateToDate(fxbCurso.inicioProperty().get()));
		// Módulos do Curso
		if (curso.getModulos().size() > 0)
			curso.getModulos().clear();

		for (ModuloFXBean mbean : fxbCurso.modulosProperty()) {
			Modulo m = new Modulo();

			m.setNome(mbean.nomeProperty().get());
			m.setDuracao(mbean.duracaoProperty().get());
			m.setInicio(DateTimeUtils.localDateToDate(mbean.inicioProperty().get()));
			// Instrutor do Módulo
			Instrutor i = new Instrutor();
			InstrutorFXBean ibean = mbean.getInstrutor();

			i.setCpf(ibean.cpfProperty().get());
			i.setNome(ibean.nomeProperty().get());
			i.setRg(ibean.rgProperty().get());
			i.setNascimento(DateTimeUtils.localDateToDate(ibean.nascimentoProperty().get()));
			i.setSexo(ibean.sexoProperty().get());
			i.setEmail(ibean.emailProperty().get());
			i.setCodigo(ibean.codigoProperty().get());

			// Endereço do Instrutor
			Endereco e = new Endereco();
			EnderecoFXBean ebean = ibean.getEndereco();

			e.setLogradouro(ebean.logradouroProperty().get());
			e.setNumero(ebean.numeroProperty().get());
			e.setComplemento(ebean.complementoProperty().get());
			e.setBairro(ebean.bairroProperty().get());
			e.setCep(ebean.cepProperty().get());
			e.setCidade(ebean.cidadeProperty().get());
			e.setEstado(ebean.estadoProperty().get());
			e.setTipo(ebean.tipoProperty().get());
			// acrescenta o Endereco ao Instrutor
			i.setEndereco(e);

			i.getFones().clear();
			// Telefones do Instrutor
			for (TelefoneFXBean fbean : ibean.getFones()) {
				Telefone f = new Telefone();

				f.setDdd(fbean.dddProperty().get());
				f.setNumero(fbean.numeroProperty().get());
				f.setTipo(fbean.tipoProperty().get());
				// acrescenta Telefone ao Instrutor
				i.getFones().add(f);
			}
			// acrescenta o Instrutor ao Modulo
			m.setInstrutor(i);

			curso.getModulos().add(m);
		}
		// "Matrículas" não são necessárias neste contexto
	}

	private void fromEntity() {
		fxbCurso.codigoProperty().set(curso.getCodigo());
		fxbCurso.nomeProperty().set(curso.getNome());
		fxbCurso.situacaoProperty().set(curso.getSituacao());
		fxbCurso.duracaoProperty().set(curso.getDuracao());
		fxbCurso.inicioProperty().set(DateTimeUtils.dateToLocalDate(curso.getInicio()));
		fxbCurso.modulosProperty().clear();
		// Módulos do Curso
		for (Modulo m : curso.getModulos()) {
			ModuloFXBean mbean = new ModuloFXBean();
			// Modulo
			mbean.nomeProperty().set(m.getNome());
			mbean.duracaoProperty().set(m.getDuracao());
			mbean.inicioProperty().set(DateTimeUtils.dateToLocalDate(m.getInicio()));

			// Instrutor do Módulo
			InstrutorFXBean ibean = new InstrutorFXBean();
			Instrutor i = m.getInstrutor();

			ibean.cpfProperty().set(i.getCpf());
			ibean.nomeProperty().set(i.getNome());
			ibean.rgProperty().set(i.getRg());
			ibean.nascimentoProperty().set(DateTimeUtils.dateToLocalDate(i.getNascimento()));
			ibean.sexoProperty().set(i.getSexo());
			ibean.emailProperty().set(i.getEmail());
			ibean.codigoProperty().set(i.getCodigo());

			// Endereço do Instrutor
			EnderecoFXBean ebean = new EnderecoFXBean();
			Endereco e = i.getEndereco();

			ebean.logradouroProperty().set(e.getLogradouro());
			ebean.numeroProperty().set(e.getNumero());
			ebean.complementoProperty().set(e.getComplemento());
			ebean.bairroProperty().set(e.getBairro());
			ebean.cepProperty().set(e.getCep());
			ebean.cidadeProperty().set(e.getCidade());
			ebean.estadoProperty().set(e.getEstado());
			ebean.tipoProperty().set(e.getTipo());
			// acrescenta o EnderecoFXBean ao InstrutorFXBean
			ibean.enderecoProperty().set(ebean);

			ibean.fonesProperty().clear();
			// Telefones do Instrutor
			for (Telefone f : i.getFones()) {
				TelefoneFXBean fbean = new TelefoneFXBean();

				fbean.dddProperty().set(f.getDdd());
				fbean.numeroProperty().set(f.getNumero());
				fbean.tipoProperty().set(f.getTipo());
				// acrescenta TelefoneFXBean ao InstrutorFXBean
				ibean.fonesProperty().get().add(fbean);
			}
			// acrescenta o InstrutorFXBean ao ModuloFXBean
			mbean.instrutorProperty().set(ibean);
			// acrescenta o ModuloFXBean ao CursoFXBean
			fxbCurso.modulosProperty().get().add(mbean);
		}
		// "Matrículas" não são necessárias neste contexto
	}

	private void copy(CursoFXBean target, CursoFXBean source) {
		target.codigoProperty().set(source.codigoProperty().get());
		target.nomeProperty().set(source.nomeProperty().get());
		target.situacaoProperty().set(source.situacaoProperty().get());
		target.duracaoProperty().set(source.duracaoProperty().get());
		target.inicioProperty().set(source.inicioProperty().get());

		target.modulosProperty().clear();
		target.modulosProperty().addAll(source.modulosProperty().get());

		target.matriculasProperty().clear();
		target.matriculasProperty().addAll(source.matriculasProperty());
	}

	private boolean showAlert(AlertType alertType, String title, String content) {
		return showAlert(alertType, title, null, content);
	}

	private boolean showAlert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);

		alert.titleProperty().set(title);
		alert.headerTextProperty().set(header);
		alert.contentTextProperty().set(content);

		// mudar o botão padrão
		// 1:
		// Deactivate Defaultbehavior for yes-Button:
		Button button = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
		button.setDefaultButton(false);

		// Activate Defaultbehavior for no-Button:
		button = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
		button.setDefaultButton(true);

		ButtonType btn = alert.showAndWait().get();

		if (btn == ButtonType.CANCEL)
			if (lastFocused != null)
				lastFocused.requestFocus();

		return btn == ButtonType.OK;
	}

	public FocusPropertyChangeListener getFocusPropertyChangeListener() {
		return new FocusPropertyChangeListener();
	}

	private void initBindingDataEntry() {
		gpDados.disableProperty().bind(dataEntryActiveProperty.not());
		// vincula TextField <-> objeto do modelo
		txfCodigo.textProperty().bindBidirectional(fxbCurso.codigoProperty());
		txfCodigo.disableProperty().bind(dataEntryInsertingProperty.not());
		txfCodigo.textProperty().addListener(new CodigoChangeListener());

		// vincula TextField <-> objeto do modelo
		txfNome.textProperty().bindBidirectional(fxbCurso.nomeProperty());
		txfNome.textProperty().addListener(new NomeChangeListener());

		// vincula TextField <-> objeto do modelo
		Bindings.bindBidirectional(chbSituacao.valueProperty(), fxbCurso.situacaoProperty(),
				new CursoSituacaoConverter());

		dpkInicio.valueProperty().bindBidirectional(fxbCurso.inicioProperty());
		dpkInicio.valueProperty().addListener(new InicioChangeListener());

		tvwModulos.itemsProperty().bindBidirectional(fxbCurso.modulosProperty());

		tclInicio.cellValueFactoryProperty().set(new PropertyValueFactory<ModuloFXBean, LocalDate>("inicio"));
		tclInicio.cellFactoryProperty().set(new InicioFormatterCallback());

		tclDuracao.cellValueFactoryProperty().set(new PropertyValueFactory<ModuloFXBean, Integer>("duracao"));
		tclNome.cellValueFactoryProperty().set(new PropertyValueFactory<ModuloFXBean, String>("nome"));
	}

	private void initBindingModulos() {
		// permite múltiplas seleções e o botão "Selecionar Todos"
		tvwModulos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// habilitar grid somente se INCLUSÃO ou ALTERAÇÃO
		tvwModulos.disableProperty().bind(gpDados.disableProperty());

		btnIncluirModulo.disableProperty().bind(gpDados.disableProperty());

		ObservableList<ModuloFXBean> items = tvwModulos.itemsProperty().get();

		BooleanBinding disableIfEmpty = gpDados.disableProperty().or(Bindings.size(items).isEqualTo(0));
		btnAlterarModulo.disableProperty().bind(gpDados.disableProperty().or(disableIfEmpty));
		btnExcluirModulo.disableProperty().bind(gpDados.disableProperty().or(disableIfEmpty));
		btnProcurarModulo.disableProperty().bind(gpDados.disableProperty().or(disableIfEmpty));

		BooleanBinding noItems = Bindings.size(items).isEqualTo(0);
		btnSelecionarTodosModulo.disableProperty().bind(gpDados.disableProperty().or(noItems));

		ReadOnlyIntegerProperty selectedIndex = tvwModulos.selectionModelProperty().get().selectedIndexProperty();
		BooleanBinding selected = selectedIndex.greaterThan(-1);
		btnLimparSelecaoModulo.disableProperty().bind(selected.not());
	}

	private void initChoiceBoxSituacao() {
		chbSituacao.itemsProperty().get().clear();
		chbSituacao.itemsProperty().get().addAll(CURSO_SITUACOES);
		chbSituacao.selectionModelProperty().get().selectFirst();
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

	private class CodigoChangeListener implements ChangeListener<String> {
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			;
		}
	}

	private class NomeChangeListener implements ChangeListener<String> {
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			;
		}
	}

	private class InicioChangeListener implements ChangeListener<LocalDate> {
		@Override
		public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
			;
		}
	}

	private class CursoSituacaoConverter extends StringConverter<CursoSituacaoType> {
		@Override
		public String toString(CursoSituacaoType object) {
			if (object == null)
				return null;

			return object.toString();
		}

		@Override
		public CursoSituacaoType fromString(String string) {
			if (string == null || string.isEmpty())
				return null;

			return CursoSituacaoType.valueOf(string.toUpperCase());
		}
	}

	private class InicioFormatterCallback
			implements Callback<TableColumn<ModuloFXBean, LocalDate>, TableCell<ModuloFXBean, LocalDate>> {
		@Override
		public TableCell<ModuloFXBean, LocalDate> call(TableColumn<ModuloFXBean, LocalDate> cell) {
			return new InicioFormatterTableCell();
		}
	}

	private class InicioFormatterTableCell extends TableCell<ModuloFXBean, LocalDate> {
		@Override
		protected void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);

			if (item == null)
				setText(null);
			else
				setText(DATE_FORMATTER.format(item));
		}
	}
}

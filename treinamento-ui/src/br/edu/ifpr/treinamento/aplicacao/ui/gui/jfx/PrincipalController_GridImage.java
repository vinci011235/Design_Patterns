package br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx;

import java.io.IOException;
import java.net.URL;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroCursoController;
import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroModuloController;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import patterns.singleton.JpaConfig;

public class PrincipalController_GridImage {
	private static final String FXML_PATH_NAME = "/br/edu/ifpr/treinamento/aplicacao/ui/jfx/";

	private static final String CADASTRO_CURSO_FXML = "CadastroCurso.fxml";
	private static final String CADASTRO_MÓDULO_FXML = "CadastroModulo.fxml";

	private static final KeyCombination CURSOS_KEY = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
	private static final KeyCombination MÓDULOS_KEY = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
	private static final KeyCombination MATRÍCULAS_KEY = new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN);
	private static final KeyCombination ALUNOS_KEY = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
	private static final KeyCombination INSTRUTORES_KEY = new KeyCodeCombination(KeyCode.I,
			KeyCombination.CONTROL_DOWN);
	private static final KeyCombination RELATÓRIOS_KEY = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
	private static final KeyCombination SERVIÇOS_KEY = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
	private static final KeyCombination SAIR_KEY = new KeyCodeCombination(KeyCode.X, KeyCombination.ALT_DOWN);

	@FXML
	private GridPane gpPrincipal;

	@FXML
	private ImageView ivCursos;
	@FXML
	private Label lbCursos;
	@FXML
	private ImageView ivMódulos;
	@FXML
	private Label lbMódulos;
	@FXML
	private ImageView ivMatriculas;
	@FXML
	private Label lbMatriculas;
	@FXML
	private ImageView ivAlunos;
	@FXML
	private Label lbAlunos;
	@FXML
	private ImageView ivInstrutores;
	@FXML
	private Label lbInstrutores;
	@FXML
	private ImageView ivRelatorios;
	@FXML
	private Label lbRelatorios;
	@FXML
	private ImageView ivServicos;
	@FXML
	private Label lbServicos;
	@FXML
	private ImageView ivSair;
	@FXML
	private Label lbSair;

	private ScreenManager sceneManager;

	private Label[] labels;
	private String imageViewOldStyle;
	private String labelOldStyle;

	//Alterado para Singleton
	private JpaService jpaService = JpaConfig.getInstance();

	private SceneKeyPressed sceneKeyPressed = new SceneKeyPressed();

	@FXML
	private void initialize() {
		jpaService.createEntityManagerFactory(true, false);

		labels = new Label[8];
		labels[0] = lbCursos;
		labels[1] = lbMódulos;
		labels[2] = lbMatriculas;
		labels[3] = lbAlunos;
		labels[4] = lbInstrutores;
		labels[5] = lbRelatorios;
		labels[6] = lbServicos;
		labels[7] = lbSair;

		// guarda os estilos padrão para serem restaurados após serem manipulados
		imageViewOldStyle = ivCursos.getStyle();
		labelOldStyle = lbCursos.getStyle();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				sceneManager.getStage().getScene().addEventFilter(KeyEvent.KEY_RELEASED, sceneKeyPressed);
			}
		});

		initTooltips();
	}

	@FXML
	private void onImageViewLabelMouseClicked(MouseEvent ev) throws IOException {
		changeView(ev.getSource());
	}

	@FXML
	private void onImageViewMouseEntered(MouseEvent ev) {
		changeLabelStyle((Node) ev.getSource(), "-fx-font-weight: bold; -fx-text-fill: blue");
	}

	@FXML
	private void onImageViewMouseExited(MouseEvent ev) {
		changeLabelStyle((Node) ev.getSource(), labelOldStyle);
	}

	@FXML
	private void onImageViewMousePressed(MouseEvent ev) {
		int rotate = (Math.random() < 0.5) ? -10 : 10;

		((Node) ev.getSource()).setStyle("-fx-rotate: " + rotate);
	}

	@FXML
	private void onImageViewMouseReleased(MouseEvent ev) {
		((Node) ev.getSource()).setStyle(imageViewOldStyle);
	}

	// ==========================================================================
	// === FUNÇÕES DE SUPORTE - INÍCIO ==========================================
	// ==========================================================================
	public void setSceneManager(ScreenManager sceneManager) {
		this.sceneManager = sceneManager;
	}

	private void changeView(Object obj) throws IOException {
		if (obj == ivSair || obj == lbSair) {
			// encerra a conexão com a base de dados
			jpaService.closeEntityManagerFactory();
			// encerra a aplicação
			sceneManager.getStage().close();
		} else {
			sceneManager.add(sceneManager.getStage().getScene().getRoot());
			BorderPane root = null;
			Scene scene = sceneManager.getStage().getScene();

			if (obj == ivCursos || obj == lbCursos) {
				String url = FXML_PATH_NAME + CADASTRO_CURSO_FXML;
				URL fxmlFile = getClass().getResource(url);
				FXMLLoader loader = new FXMLLoader(fxmlFile);

				root = loader.load();
				CadastroCursoController controller = loader.getController();

				controller.initState(sceneManager, jpaService);
				scene.focusOwnerProperty().addListener(controller.getFocusPropertyChangeListener());
			} else if (obj == ivMódulos || obj == lbMódulos) {
				String url = FXML_PATH_NAME + CADASTRO_MÓDULO_FXML;
				URL fxmlFile = getClass().getResource(url);
				FXMLLoader loader = new FXMLLoader(fxmlFile);

				root = loader.load();
				CadastroModuloController controller = loader.getController();

				controller.initState(sceneManager, jpaService);
				scene.focusOwnerProperty().addListener(controller.getFocusPropertyChangeListener());
			} else if (obj == ivMatriculas || obj == lbMatriculas) {
				;
			} else if (obj == ivAlunos || obj == lbAlunos) {
				;
			} else if (obj == ivInstrutores || obj == lbInstrutores) {
				;
			} else if (obj == ivRelatorios || obj == lbRelatorios) {
				;
			} else if (obj == ivServicos || obj == lbServicos) {
				;
			}
			if (root == null) {
				sceneManager.previous();
				return;
			}

			scene.setRoot(root);
			sceneManager.getStage().sizeToScene();
		}
	}

	private void changeLabelStyle(Node node, String style) {
		int index = getLabelIndexFromGrid(node);

		labels[index].setStyle(style);
	}

	private int getLabelIndexFromGrid(Node node) {
		// o grid possui 2 linhas para cada entrada (ImageView e Label), que
		// precisam ser "transformadas" em uma entrada, por isso dividir por 2.
		// o grid possui 3 colunas, por isso a multiplicação
		return (GridPane.getRowIndex(node) / 2) * 3 + GridPane.getColumnIndex(node);
	}

	private void initTooltips() {
		Tooltip ttpCursos = new Tooltip(String.format("Cursos (%s)", CURSOS_KEY.getDisplayText()));
		Tooltip ttpMódulos = new Tooltip(String.format("Módulos (%s)", MÓDULOS_KEY.getDisplayText()));
		Tooltip ttpMatriculas = new Tooltip(String.format("Matrículas (%s)", MATRÍCULAS_KEY.getDisplayText()));
		Tooltip ttpAlunos = new Tooltip(String.format("Alunos (%s)", ALUNOS_KEY.getDisplayText()));
		Tooltip ttpInstrutores = new Tooltip(String.format("Instrutores (%s)", INSTRUTORES_KEY.getDisplayText()));
		Tooltip ttpRelatorios = new Tooltip(String.format("Relatórios (%s)", RELATÓRIOS_KEY.getDisplayText()));
		Tooltip ttpServicos = new Tooltip(String.format("Serviços (%s)", SERVIÇOS_KEY.getDisplayText()));
		Tooltip ttpSair = new Tooltip(String.format("Sair (%s)", SAIR_KEY.getDisplayText()));

		Tooltip.install(ivCursos, ttpCursos);
		lbCursos.tooltipProperty().set(ttpCursos);

		Tooltip.install(ivMódulos, ttpMódulos);
		lbMódulos.tooltipProperty().set(ttpMódulos);

		Tooltip.install(ivMatriculas, ttpMatriculas);
		lbMatriculas.tooltipProperty().set(ttpMatriculas);

		Tooltip.install(ivAlunos, ttpAlunos);
		lbAlunos.tooltipProperty().set(ttpAlunos);

		Tooltip.install(ivInstrutores, ttpInstrutores);
		lbInstrutores.tooltipProperty().set(ttpInstrutores);

		Tooltip.install(ivRelatorios, ttpRelatorios);
		lbRelatorios.tooltipProperty().set(ttpRelatorios);

		Tooltip.install(ivServicos, ttpServicos);
		lbServicos.tooltipProperty().set(ttpServicos);

		Tooltip.install(ivSair, ttpSair);
		lbSair.tooltipProperty().set(ttpSair);
	}

	private class SceneKeyPressed implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent ev) {
			if (CURSOS_KEY.match(ev))
				try {
					changeView(lbCursos);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			else if (MÓDULOS_KEY.match(ev))
				try {
					changeView(lbMódulos);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			else if (SAIR_KEY.match(ev))
				try {
					changeView(lbSair);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
		}
	}
}

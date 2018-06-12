package patterns.command.impl;

import java.io.IOException;
import java.net.URL;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroModuloController;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import patterns.command.Command;
import patterns.state.DataEntryState;

public abstract class IncluirModulo implements Command {
	private static final String FXML_PATH_NAME = "/br/edu/ifpr/treinamento/aplicacao/ui/jfx/";
	private static final String CADASTRO_MÓDULO_FXML = "CadastroModulo.fxml";

	@Override
	public void execute(ScreenManager sceneManager, JpaService jpaService) {
		sceneManager.add(sceneManager.getStage().getScene().getRoot());

		BorderPane root = null;
		Scene scene = sceneManager.getStage().getScene();
		String url = FXML_PATH_NAME + CADASTRO_MÓDULO_FXML;
		URL fxmlFile = getClass().getResource(url);
		FXMLLoader loader = new FXMLLoader(fxmlFile);

		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CadastroModuloController controller = loader.getController();

		controller.initState(DataEntryState.INSERT, sceneManager, jpaService);
		scene.focusOwnerProperty().addListener(controller.getFocusPropertyChangeListener());
		if (root == null) {
			sceneManager.previous();
			return;
		}

		scene.setRoot(root);
		sceneManager.getStage().sizeToScene();
	}
}

package br.edu.ifpr.treinamento.aplicacao;

import java.util.logging.Logger;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.PrincipalController_GridImage;
import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class TreinamentoUIPrincipal extends Application {
	private static final Logger LOGGER = Logger.getLogger(TreinamentoUIPrincipal.class.getName());

	private static final String FXML_PATH_NAME = "/br/edu/ifpr/treinamento/aplicacao/ui/gui/jfx/";
	private static final String FXML_FILE_NAME = FXML_PATH_NAME + "Principal_GridImage.fxml";
	private static final String CSS_FILE_NAME = "/resources/css/application.css";

	private ScreenManager sceneManager;

	@Override
	public void start(Stage primaryStage) {
		sceneManager = new ScreenManager(primaryStage);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE_NAME));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			String css = getClass().getResource(CSS_FILE_NAME).toExternalForm();

			PrincipalController_GridImage controller = loader.getController();
			controller.setSceneManager(sceneManager);

			scene.getStylesheets().add(css);

			primaryStage.setScene(scene);
			primaryStage.resizableProperty().set(false);

			//primaryStage.initModality(Modality.APPLICATION_MODAL);
			//primaryStage.initStyle(StageStyle.TRANSPARENT);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			//primaryStage.initStyle(StageStyle.UTILITY);

			primaryStage.sizeToScene();

			// se o usuário:
			// 1) clicar no X da janela ou
			// 2) pressionar Alt=F4
			// o evento de fechamento é "consumido" sem fechar a janela
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent ev) {
					// o método "consome" o evento evitando que este seja propagado
					// de volta à aplicação
					ev.consume();
				}
			});
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package patterns.command;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.fxbeans.ModuloFXBean;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import javafx.scene.control.TableView;

public interface Command {
	void execute();
	void execute(ScreenManager sceneManager, JpaService jpaService);
	void execute(TableView<ModuloFXBean> tvwModulos);
}

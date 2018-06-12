package patterns.command.impl;

import br.edu.ifpr.treinamento.fxbeans.ModuloFXBean;
import javafx.scene.control.TableView;
import patterns.command.Command;

public abstract class LimparSelecaoModulo implements Command {

	@Override
	public void execute(TableView<ModuloFXBean> tvwModulos) {
		tvwModulos.getSelectionModel().clearSelection();
	}

}

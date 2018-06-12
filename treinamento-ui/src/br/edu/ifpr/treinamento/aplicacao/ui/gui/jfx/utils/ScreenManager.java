package br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.scene.Parent;
import javafx.stage.Stage;
import patterns.iterator.IteratorScreenManager;

public class ScreenManager implements IteratorScreenManager {
	private static final Logger LOGGER = Logger.getLogger(ScreenManager.class.getName());

	private Stage stage;
	private List<ScreenDefinition> roots;
	private int index;

	public ScreenManager(Stage stage) {
		this.stage = stage;
		this.roots = new ArrayList<>();
		this.index = -1;
	}

	public Stage getStage() {
		return this.stage;
	}

	public void add(Parent parent) {
		roots.add(new ScreenDefinition(parent));
		++index;
	}
	@Override
	public ScreenDefinition next() {
		if (!hasNext())
			throw new IllegalStateException("next(): Não há 'scene' disponível");
		
		ScreenDefinition sceneDefinition = roots.get(index);
		
		stage.getScene().setRoot(sceneDefinition.getParent());
		stage.sizeToScene();
		return sceneDefinition;
	}

	@Override
	public boolean hasNext() {
		return index > roots.size();
	}

	public void previous() {
		if (index < 0)
			throw new IllegalStateException("previous(): Não há 'scene' disponível");

		ScreenDefinition sceneDefinition = roots.get(index);
		--index;

		stage.getScene().setRoot(sceneDefinition.getParent());
		stage.sizeToScene();

	}


}

class ScreenDefinition {
	private Parent parent;

	public ScreenDefinition(Parent parent) {
		this.parent = parent;
	}

	public Parent getParent() {
		return this.parent;
	}
}
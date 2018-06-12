package patterns.command.factory;

import patterns.command.Command;

public interface CommandFactory {
	public static final String INCLUIR_MODULO = "IncluirModulo";
	public static final String ALTERAR_MODULO = "AlterarModulo";
	public static final String EXCLUIR_MODULO = "ExcluirModulo";
	public static final String PROCURAR_MODULO = "ProcurarModulo";
	public static final String SELECIONAR_TODOS = "SelecionarTodosModulo";
	public static final String LIMPAR_SELECAO = "LimparSelecaoModulo";

	public Command createCommandFactory(String command);

}

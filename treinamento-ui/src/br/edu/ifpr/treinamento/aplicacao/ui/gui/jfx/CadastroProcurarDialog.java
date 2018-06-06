package br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class CadastroProcurarDialog<T> {
   private static final Logger LOGGER =
            Logger.getLogger(CadastroCurso.class.getName());

   private static final Image RECUPERAR_IMAGE =
                                        new Image("/resources/images/tick.png");
   private static final Image CANCELAR_IMAGE  =
                                       new Image("/resources/images/cross.png");

   private Dialog<T>  dialog;
   private ButtonType recuperarButtonType;
   private ButtonType cancelarButtonType;

   private CadastroProcurar procurar;

   public CadastroProcurarDialog() {
      createDialog();
   }

   public T showAndWait() {
      Optional<T> result = dialog.showAndWait();
      T           dado   = null;

      if (result.isPresent())
         dado  = result.get();

      return dado;
   }
   // ==========================================================================
   // === MÉTODOS DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   private void createDialog() {
      procurar = new CadastroProcurar();
      dialog   = new Dialog<>();

      dialog.setTitle("Procurar");

      recuperarButtonType = new ButtonType("Recuperar",ButtonData.OK_DONE);
      cancelarButtonType  = new ButtonType("Cancelar",ButtonData.CANCEL_CLOSE);
      dialog.getDialogPane().getButtonTypes().addAll(recuperarButtonType,
                                                     cancelarButtonType);

      dialog.dialogPaneProperty().get().contentProperty().set(procurar);

      Button btnRecuperar = (Button) dialog.getDialogPane()
                                           .lookupButton(recuperarButtonType);
      Button btnCancelar  = (Button) dialog.getDialogPane()
                                           .lookupButton(cancelarButtonType);

      btnRecuperar.graphicProperty().set(new ImageView(RECUPERAR_IMAGE));
//      btnRecuperar.disableProperty().bind();

      btnCancelar.graphicProperty().set(new ImageView(CANCELAR_IMAGE));

//      Platform.runLater(new Runnable() {
//         @Override
//         public void run() { procurar.initialFocus(); }
//      });

      dialog.setResultConverter(new CadastroProcurarDialogCallback());
   }
   // ==========================================================================
   // === MÉTODOS DE SUPORTE - FIM =============================================
   // ==========================================================================

   // ==========================================================================
   // === CLASSES DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   private class CadastroProcurarDialogCallback
                 implements Callback<ButtonType, T> {
      @Override
      public T call(ButtonType param) {
         if (param == recuperarButtonType) {
            LOGGER.info("===> DIALOG::BOTÃO RECUPERAR <===");
            // CRIAR OBJETO "CURSO"
            T dado = null;
            // POPULAR OBJETO "CURSO" COM OS DADOS SELECIONADOS PELO USUÁRIO
            // RETORNAR OBJETO "CURSO"
         }
         return null;
      }
   }
   // ==========================================================================
   // === CLASSES DE SUPORTE - FIM =============================================
   // ==========================================================================
}

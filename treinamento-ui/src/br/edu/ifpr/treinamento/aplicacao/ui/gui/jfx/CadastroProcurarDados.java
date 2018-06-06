package br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CadastroProcurarDados extends AnchorPane implements Initializable {
   private static final String FXML_FILE_NAME = "CadastroProcurarDados.fxml";

   private static final Image ADD_CONECTIVO_IMAGE    =
                                          new Image("resources/images/add.png");
   private static final Image REMOVE_CONECTIVO_IMAGE =
                                       new Image("resources/images/delete.png");

   private static final String[] COMPARADORES = { /*0*/"é igual a",
                                                  /*1*/"é diferente de",
                                                  /*2*/"é menor que",
                                                  /*3*/"é maior que",
                                                  /*4*/"é menor ou igual a",
                                                  /*5*/"é maior ou igual a",
                                                  /*6*/"está entre",
                                                  /*7*/"não está entre",
                                                  /*8*/"contém",
                                                  /*9*/"não contém",
                                                  /*10*/"começa com",
                                                  /*11*/"termina com"
                                                };
   private static final int ESTÁ_ENTRE_INDEX     = 6;
   private static final int NÃO_ESTÁ_ENTRE_INDEX = 7;

   private static final String[] CONECTIVOS = { "E", "OU" };

   private static final Integer ADD_CONECTIVO_DATA    = new Integer(1);
   private static final Integer REMOVE_CONECTIVO_DATA = new Integer(2);

   @FXML private Label             lbProcurarPor;
   @FXML private ChoiceBox<String> chbProcurarPor;
   @FXML private Label             lbComparador;
   @FXML private ChoiceBox<String> chbComparador;
   @FXML private Label             lbValor;
   @FXML private TextField         txfValor1;
   @FXML private Label             lbE;
   @FXML private TextField         txfValor2;
   @FXML private Label             lbConectivo;
   @FXML private ChoiceBox<String> chbConectivo;
   @FXML private Button            btnRemoveConectivo;
   @FXML private Button            btnAddConectivo;

   private BooleanProperty haDadosProperty = new SimpleBooleanProperty(false);

   private CadastroProcurar cadastroProcurar;

   public CadastroProcurarDados(CadastroProcurar cadastroProcurar) {
      loadFXML();

      this.cadastroProcurar = cadastroProcurar;
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      initLabelFor();
      initChoiceBoxComparador();
      initChoiceBoxConectivo();
      initHaDadosProperty();
      initVisibleValor2();

      btnAddConectivo.setUserData(ADD_CONECTIVO_DATA);

//      btnAddConectivo.onMouseEnteredProperty()
//                           .set(new EventHandler<MouseEvent>() {
//         @Override
//         public void handle(MouseEvent ev) {
//            Button btn = (Button) ev.getSource();
//            int    pos = (Integer) btn.getUserData();
//
//            btn.setTooltip(new Tooltip("Posição " + pos));
//         }
//      });
//      btnAddConectivo.onMouseExitedProperty()
//                           .set(new EventHandler<MouseEvent>() {
//         @Override
//         public void handle(MouseEvent ev) {
//            ((Button) ev.getSource()).setTooltip(null);;
//         }
//      });

      btnAddConectivo.onActionProperty()
                           .set(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent ev) {
            Button btnAddRemove = (Button) ev.getSource();

            if (btnAddRemove.getUserData().equals(ADD_CONECTIVO_DATA)) {
               if (chbConectivo.selectionModelProperty()
                               .get().selectedIndexProperty().get() < 0) {
                  Alert alert = new Alert(AlertType.WARNING,
                                          "Selecione um conectivo",
                                          ButtonType.OK);

                  alert.titleProperty().set("AVISO");
                  alert.headerTextProperty().set("Conectivo faltando!");
                  alert.showAndWait();
   
                  chbConectivo.requestFocus();
               }
               else {
                  btnAddRemove.graphicProperty()
                              .set(new ImageView(REMOVE_CONECTIVO_IMAGE));
  
                  cadastroProcurar.addCadastroProcurarDados();
                  btnAddRemove.setUserData(REMOVE_CONECTIVO_DATA);
               }
            }
            else {
               if (!cadastroProcurar.removeCadastroProcurarDados(
                                                  CadastroProcurarDados.this)) {
                  Alert alert = new Alert(AlertType.WARNING,
                                         "Não é possível remover o último item",
                                          ButtonType.OK);

                  alert.titleProperty().set("AVISO");
//                  alert.headerTextProperty().set("Conectivo faltando!");
                  alert.showAndWait();
               }
            }
         }
      });
   }

   public double height() { return this.getHeight(); }

   public ReadOnlyBooleanProperty haDadosProperty() { return haDadosProperty; }

   public void initialFocus() { chbProcurarPor.requestFocus(); }

   // ==========================================================================
   // === MÉTODOS DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   private void loadFXML() {
      FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(FXML_FILE_NAME));

      loader.setRoot(this);
      loader.setController(this);
      
      try { loader.load(); }
      catch (IOException ex) {
         throw new RuntimeException(ex);
      }
   }

   private void initVisibleValor2() {
      // se Comparador igual a "está entre" ou "não está entre" o identity "E" e
      // o TextField para o segundo valor estarão visíveis
      BooleanBinding estaEntre    = chbComparador.selectionModelProperty()
                                                 .get().selectedIndexProperty()
                                                 .isEqualTo(ESTÁ_ENTRE_INDEX);
      BooleanBinding naoEstaEntre = chbComparador.selectionModelProperty().get()
                                                 .selectedIndexProperty()
                                               .isEqualTo(NÃO_ESTÁ_ENTRE_INDEX);

      lbE.visibleProperty().bind(estaEntre.or(naoEstaEntre));
      txfValor2.visibleProperty().bind(lbE.visibleProperty());
   }

   private void initLabelFor() {
      lbProcurarPor.labelForProperty().set(chbProcurarPor);
      lbComparador.labelForProperty().set(chbComparador);
      lbValor.labelForProperty().set(txfValor1);
      lbConectivo.labelForProperty().set(chbConectivo);
   }

   private void initChoiceBoxComparador() {
      chbComparador.itemsProperty().get().addAll(Arrays.asList(COMPARADORES));
   }

   private void initChoiceBoxConectivo() {
      chbConectivo.itemsProperty().get().addAll(Arrays.asList(CONECTIVOS));
   }

   private void initHaDadosProperty() {
      BooleanBinding haCampo = chbProcurarPor.selectionModelProperty()
                                             .get().selectedIndexProperty()
                                             .greaterThan(-1);
      BooleanBinding haComparador = chbComparador.selectionModelProperty()
                                                 .get().selectedIndexProperty()
                                                 .greaterThan(-1);
      BooleanBinding haValor1 = txfValor1.textProperty().isNotEmpty();
      BooleanBinding haValor2 = txfValor2.textProperty().isNotEmpty();
      BooleanBinding haDados = haCampo.and(haComparador.and(haValor1));
      // VERIFICAR, POIS DEVE SER DINÂMICO
      if (txfValor2.visibleProperty().get()) haDados.and(haValor2);

      haDadosProperty.bind(haDados);
   }
   // ==========================================================================
   // === MÉTODOS DE SUPORTE - FIM =============================================
   // ==========================================================================

   // ==========================================================================
   // === CLASSES DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   // ==========================================================================
   // === CLASSES DE SUPORTE - FIM =============================================
   // ==========================================================================
}

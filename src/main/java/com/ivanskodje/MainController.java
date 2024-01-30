package com.ivanskodje;

import com.ivanskodje.service.GlassesOverlay;
import com.ivanskodje.service.TestService;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

@Component
public class MainController implements Initializable {
    @Autowired
    TestService testService;

    DirectoryChooser dirChooser = new DirectoryChooser();

    @FXML
    private Text actiontarget;

    @FXML
    private ImageView image_origin;

    @FXML
    private Label label_origin_name;

    private Stage _stage;

    @FXML
    protected void handleStartButtonAction(ActionEvent event) {
        actiontarget.setText("Status: Run button pressed");
    }

    @FXML
    protected void handleStopButtonAction(ActionEvent event) {
        actiontarget.setText("Status: Stop button pressed");
    }

    @FXML
    protected void handlePauseButtonAction(ActionEvent event) {
        actiontarget.setText("Status: Pause button pressed");
    }

    @FXML
    protected void handlePrveButtonAction(ActionEvent event) {
        actiontarget.setText("Status: Export button pressed");
    }

    @FXML
    protected void handleOpenbuttonAction(ActionEvent event) {
        File selectedFolder = dirChooser.showDialog(null);
        dirChooser.setTitle("Please select the directory of Images.");
        if (selectedFolder != null) {
            System.out.println("Selected file: " + selectedFolder.getAbsolutePath());
            actiontarget.setText("Status: Selected file => " + selectedFolder.getAbsolutePath());
            File[] files = selectedFolder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String extension = "";

                        int i = file.getName().lastIndexOf('.');
                        if (i > 0) {
                            extension = file.getName().substring(i + 1);
                        }
                        if (extension.equals("jpg") || extension.equals("png") || extension.equals("PNG")
                                || extension.equals("JPG")) {

                            Image image = new Image(file.toURI().toString());
                            System.out.println(file.toURI().toString());
                            // image_origin = new ImageView(image);
                            image_origin.setImage(image);
                            actiontarget.setText("Status: Processing file ===> " + file.getName().toString());
                            label_origin_name.setText(file.getName().toString());

                            GlassesOverlay proc = new GlassesOverlay();
                            proc.getEyePoints(file.toURI().toString());
                            // System.out.println( "Restarting app!" );
                            // _stage.close();
                            // Platform.runLater( () -> new Main().start( new Stage() ) );

                        }
                    }
                }
            }
        }

    }

    public void setStageAndSetupListeners(Stage stage) {
        this._stage = stage;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("# MainController - initialize() start");
        testService.doSomething();
    }
}

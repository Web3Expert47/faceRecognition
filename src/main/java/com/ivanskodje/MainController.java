package com.ivanskodje;

import com.ivanskodje.service.TestService;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

@Component
public class MainController implements Initializable {
    @Autowired
    TestService testService;

    @FXML
    private Text actiontarget;

    @FXML
    protected void handleStartButtonAction(ActionEvent event) {
        actiontarget.setText("Run button pressed");
    }

    @FXML
    protected void handleStopButtonAction(ActionEvent event) {
        actiontarget.setText("Stop button pressed");
    }

    @FXML
    protected void handlePauseButtonAction(ActionEvent event) {
        actiontarget.setText("Pause button pressed");
    }

    @FXML
    protected void handlePrveButtonAction(ActionEvent event) {
        actiontarget.setText("Export button pressed");
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

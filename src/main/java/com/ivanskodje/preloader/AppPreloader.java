package com.ivanskodje.preloader;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class AppPreloader extends Preloader
{
    private ProgressBar progressBar;
    private Stage stage;

    public void start(Stage stage)
    {
        this.stage = stage;
        stage.setScene(buildScene());
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification)
    {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START)
        {
            stage.hide();
        }
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification preloaderNotification)
    {
        progressBar.setProgress(((ProgressNotification) preloaderNotification).getProgress());
    }

    private Scene buildScene()
    {
        String appPreloaderFxml = "/fxml/preloader/AppPreloader.fxml";
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(appPreloaderFxml));
            Parent root = loader.load();
            progressBar = (ProgressBar) loader.getNamespace().get("progressBar"); // Using the ProgressBar's "fx:id" to get it from AppPreloader.fxml
            return new Scene(root);
        }
        catch (Exception ex)
        {
            System.err.println("Unable to load FXML from '" + appPreloaderFxml + "'. Building a backup scene instead.");
            Label loadingLabel = new Label("Loading...");
            progressBar = new ProgressBar(0.0);
            BorderPane root = new BorderPane();
            root.setCenter(progressBar);
            root.setTop(loadingLabel);
            root.setAlignment(loadingLabel, Pos.CENTER);
            return new Scene(root, 300, 100);
        }
    }
}
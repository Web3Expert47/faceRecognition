package com.ivanskodje;

import com.ivanskodje.preloader.AppPreloader;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Main extends Application
{
    private ConfigurableApplicationContext configurableApplicationContext;

    @Override
    public void init() throws Exception
    {
        // TODO: Do preloading in this method
        // ...

        // Initialize Spring
        configurableApplicationContext = SpringApplication.run(Main.class);

        // (Simulation of heavy background work)
        int numberOfUpdates = 10;
        for (int i = 0; i < numberOfUpdates; i++)
        {
            // Gradually update the loading bar
            notifyPreloader(new Preloader.ProgressNotification((double) i / numberOfUpdates));
            Thread.sleep(3000L / numberOfUpdates);
        }
    }

    @Override
    public void start(Stage primaryStage)
    {
        String fxmlPath = "/fxml/Main.fxml";
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fxmlLoader.setControllerFactory(configurableApplicationContext::getBean);
            Parent root = fxmlLoader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setMaximized(true);
            primaryStage.setTitle("The Face Recognization Application");
            primaryStage.show();
        }
        catch (IOException ex)
        {
            System.err.println("Unable to load '" + fxmlPath + "'");
        }
    }

    @Override
    public void stop()
    {
        configurableApplicationContext.close();
    }

    public static void main(String[] args)
    {
        LauncherImpl.launchApplication(Main.class, AppPreloader.class, args);
    }
}

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Application extends javafx.application.Application implements EventHandler<ActionEvent>
{
    private TextField txt1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        primaryStage.setTitle("Auto Typer");
        Scene scene = new Scene(root, 300, 275);


        txt1 = new TextField();
        Button btn1 = new Button();
        btn1.setText("Paste");
        btn1.setOnAction(this);
        VBox vbox1 = new VBox();
        vbox1.getChildren().addAll(txt1, btn1);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.setSpacing(10);
        root.getChildren().add(vbox1);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event)
    {
        Thread t  = new Thread(new Paster(txt1.getText()));
        t.start();
    }



}

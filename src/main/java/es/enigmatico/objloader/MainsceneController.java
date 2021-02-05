/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.enigmatico.objloader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import es.enigmatico.objloader.objhandler.ObjHandler;
import es.enigmatico.objloader.objhandler.ObjModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/* 3D stuff */
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.geometry.Point3D;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;

/* Animation stuff */
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.CullFace;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author Enigmatico
 */
public class MainsceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String debugObjPath = "D:\\magnu\\Documents\\objtest\\Mario\\mario.obj";
    //private Window thisWindow;
    
    @FXML
    private Pane pane;
    @FXML
    private MeshView meshView;
    @FXML
    private PointLight pointLight;
    @FXML
    private CheckBox flipNormals;
    @FXML
    private CheckBox wireFrame;
    @FXML
    private ComboBox<String> cullFace;
    @FXML
    private HBox hBox;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Button loadObjBtn;
    
    public void loadObj(String path)
    {
        //thisWindow = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        ObjModel myModel = null;
        if(path.startsWith("/") || path.startsWith(".") || path.startsWith("jar:"))
        {
           myModel = ObjHandler.LoadModel(getClass().getResourceAsStream(path));
        }else{
            try
            {
                myModel = ObjHandler.LoadModel(new FileInputStream(path));
            }catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
        
        
        meshView.setMesh(myModel.getMesh());
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setCullFace(CullFace.FRONT);
        
        PhongMaterial mtrl = new PhongMaterial();
        mtrl.setDiffuseMap(new Image(getClass().getResourceAsStream("/es/enigmatico/objloader/uvs.png")));
        
        meshView.setMaterial(mtrl);
        meshView.setTranslateX(200.0);
        meshView.setTranslateY(150.0);
        //meshView.setTranslateZ(-200.0);
        meshView.setRotationAxis(new Point3D(0.0,0.0,1.0));
        meshView.setRotate(180.0);
        
        pointLight.setColor(Color.WHITE);
        
        Rotate rot = new Rotate(0, Rotate.Y_AXIS);
        meshView.getTransforms().add(rot);
        
        Timeline anim = new Timeline(
            new KeyFrame(Duration.seconds(0), new KeyValue(rot.angleProperty(), 0)),
            new KeyFrame(Duration.seconds(15), new KeyValue(rot.angleProperty(), 360))
        );
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        splitPane.prefHeightProperty().bind(pane.heightProperty());
        splitPane.prefWidthProperty().bind(pane.widthProperty());
        
        cullFace.getItems().addAll(
                "FRONT",
                "BACK",
                "NONE"
        );
        cullFace.setValue("FRONT");
        
        loadObj("/es/enigmatico/objloader/longcube.obj");
    }

    @FXML
    private void normalsChange(MouseEvent event) {
        
    }

    @FXML
    private void wireframeChange(MouseEvent event) {
        if(wireFrame.isSelected() == true)
        {
            meshView.setDrawMode(DrawMode.LINE);
        }else{
            meshView.setDrawMode(DrawMode.FILL);
        }
    }

    @FXML
    private void handleCullChange(ActionEvent event) 
    {
        if(cullFace.getValue().compareTo("FRONT") == 0)
        {
            meshView.setCullFace(CullFace.FRONT);
        }
        else if(cullFace.getValue().compareTo("BACK") == 0)
        {
            meshView.setCullFace(CullFace.BACK);
        }
        else
        {
            meshView.setCullFace(CullFace.NONE);
        }
    }

    @FXML
    private void doLoadObj(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open OBJ file...");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("OBJ files (*.obj)", "*.obj"));
        File objFile = fileChooser.showOpenDialog(pane.getScene().getWindow());
        
        if(objFile != null)
        {
            loadObj(objFile.getPath());
        }
    }
    
}

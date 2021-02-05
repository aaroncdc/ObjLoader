/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.enigmatico.objloader.objhandler;
import java.io.IOException;

import es.enigmatico.objloader.objhandler.ObjModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.scene.shape.VertexFormat;

/**
 *
 * @author Enigmatico
 */
public class ObjHandler {
    
    public static ObjModel LoadModel(InputStream fileData)
    {
        try
        {
            // Always work with InputStreams if working with resources
            BufferedReader br = new BufferedReader(new InputStreamReader(fileData, "UTF-8"));
            String line;
            
            ObjModel model = new ObjModel();

            while ((line = br.readLine()) != null)
            {
                if (line.length() > 0 && line.charAt(0) != '#') {
                    String[] data = line.replace("  ", " ").split(" ");
                    
                    if (data.length > 1) {

                        if (data[0].compareTo("o") == 0) {
                            model.setName(data[1].trim());
                        }
                        
                        if (data[0].compareTo("v") == 0 && data.length >= 4) {
                            model.addVertexData(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        }
                        
                        if (data[0].compareTo("vt") == 0 && data.length >= 3) {
                            // TODO: texture coordinates
                            model.addTextureCoord(Float.parseFloat(data[1]), Float.parseFloat(data[2]));
                        }
                        
                        if (data[0].compareTo("vn") == 0 && data.length >= 4) {
                            model.addNormalData(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        }
                        
                        if (data[0].compareTo("f") == 0 && data.length >= 3) {
                            
                            // Determine VertexFormat
                            if (data[1].contains("//")) {
                                //Unsupported
                                System.out.println("Unsupported vertex format: point");
                                continue;
                            } else if (data[1].contains("/")) {
                                
                                if (data[1].split("/").length > 2) {
                                    model.setVertexFormat(VertexFormat.POINT_NORMAL_TEXCOORD);
                                } else {
                                    model.setVertexFormat(VertexFormat.POINT_TEXCOORD);
                                }
                                
                            } else {
                                //Unsupported
                                System.out.println("Unsupported vertex format: point//normal");
                                continue;
                            }
                            
                            for (int i = 1; i < data.length; i++) {
                                String[] dat = data[i].split("/");
                                
                                if (model.getMesh().getVertexFormat() == VertexFormat.POINT_NORMAL_TEXCOORD) {
                                    model.addFace(Integer.parseInt(dat[0]) - 1, Integer.parseInt(dat[2]) - 1, Integer.parseInt(dat[1]) - 1);
                                } else {
                                    model.addFace(Integer.parseInt(dat[0]) - 1, Integer.parseInt(dat[1]) - 1);
                                }
                            }
                        }
                    }
                    //System.out.println(line);
                }
            }

            br.close();

            model.updateMesh();
            return model;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}

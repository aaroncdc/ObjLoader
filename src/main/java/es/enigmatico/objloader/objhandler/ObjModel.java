/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.enigmatico.objloader.objhandler;

import java.util.Arrays;

/* 3D stuff */
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.DrawMode;
import javafx.scene.paint.PhongMaterial;
import javafx.geometry.Point3D;
import javafx.scene.shape.VertexFormat;

/**
 *
 * @author Enigmatico
 */
public class ObjModel {
    private String name;
    
    private float width;
    private float height;
    private float depth;
    
    private float[] vertexData;
    private float[] normals;
    private int[] faces;
    private float[] textureCoordinates;
    
    private TriangleMesh mesh;
    
    private static float[] increaseArray(float[] array, int addItems)
    {
        return Arrays.copyOf(array, array.length + addItems);
    }
    
    private static int[] increaseArray(int[] array, int addItems)
    {
        return Arrays.copyOf(array, array.length + addItems);
    }
    
    public ObjModel()
    {
        this.name = "";
        this.width = 1.0f;
        this.height = 1.0f;
        this.depth = 1.0f;
        
        this.vertexData = new float[0];
        this.faces = new int[0];
        this.normals = new float[0];
        this.textureCoordinates = new float[0];
        
        mesh = new TriangleMesh();
    }
    
    public ObjModel(String objName)
    {
        this.name = objName;
        this.width = 1.0f;
        this.height = 1.0f;
        this.depth = 1.0f;
        
        this.vertexData = new float[0];
        this.faces = new int[0];
        this.normals = new float[0];
        this.textureCoordinates = new float[0];
        
        mesh = new TriangleMesh();
    }
    
    public void addVertexData(float x, float y, float z)
    {
        this.vertexData = increaseArray(this.vertexData, 3);
        
        this.vertexData[this.vertexData.length - 3] = x;
        this.vertexData[this.vertexData.length - 2] = y;
        this.vertexData[this.vertexData.length - 1] = z;
    }
    
    public void addNormalData(float x, float y, float z)
    {
        this.normals = increaseArray(this.normals, 3);
        
        this.normals[this.normals.length - 3] = x;
        this.normals[this.normals.length - 2] = y;
        this.normals[this.normals.length - 1] = z;
    }
    
    public void addFace(int val)
    {
        this.faces = increaseArray(this.faces, 1);
        this.faces[this.faces.length - 1] = val;
    }
    
    public void addFace(int v1, int vt1, int vn1)
    {
        this.faces = increaseArray(this.faces, 3);
        this.faces[this.faces.length - 3] = v1;
        this.faces[this.faces.length - 2] = vt1;
        this.faces[this.faces.length - 1] = vn1;
    }
    
    public void addFace(int v1, int vt1)
    {
        this.faces = increaseArray(this.faces, 2);
        this.faces[this.faces.length - 2] = v1;
        this.faces[this.faces.length - 1] = vt1;
    }
    
    public void addTextureCoord(float vt1, float vt2)
    {
        this.textureCoordinates = increaseArray(this.textureCoordinates, 2);
        this.textureCoordinates[this.textureCoordinates.length - 2] = vt1;
        this.textureCoordinates[this.textureCoordinates.length - 1] = vt2;
    }
    
    public void setName(String objName)
    {
        this.name = objName;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public TriangleMesh getMesh()
    {
        //this.updateMesh();
        return this.mesh;
    }
    
    public void setVertexFormat(VertexFormat format)
    {
       this.mesh.setVertexFormat(format);
    }
    
    public void updateMesh()
    {
        /*for(int i = 0; i < this.vertexData.length; i++)
        {
            this.mesh.getPoints().addAll(this.vertexData[i]);
        }
        for(int i = 0; i < this.faces.length; i++)
        {
            this.mesh.getFaces().addAll(this.faces[i]);
        }
        for(int i = 0; i < this.normals.length; i++)
        {
            this.mesh.getNormals().addAll(this.normals[i]);
        }
        for(int i = 0; i < this.textureCoordinates.length; i++)
        {
            this.mesh.getTexCoords().addAll(this.textureCoordinates[i]);
        }*/
        this.mesh.getPoints().addAll(this.vertexData);
        this.mesh.getFaces().addAll(this.faces);
        this.mesh.getTexCoords().addAll(this.textureCoordinates);
        this.mesh.getNormals().addAll(this.normals);
        
        
        /*System.out.println("Model name: " + this.name);
        System.out.println("Vertex Format: " + this.mesh.getVertexFormat().toString());
        System.out.println("Vertex data: (" + this.vertexData.length + "): " + Arrays.toString(this.vertexData));
        System.out.println("Faces: (" + this.faces.length + "): " + Arrays.toString(this.faces));
        System.out.println("Tex coords: (" + this.textureCoordinates.length + "): " + Arrays.toString(this.textureCoordinates));
        System.out.println("Normals: (" + this.normals.length + "): " + Arrays.toString(this.normals));
        
        System.out.println("Mesh Vertex data: (" + this.mesh.getPoints().size() + "): " + this.mesh.getPoints().toString());
        System.out.println("Mesh Faces: (" + this.mesh.getFaces().size() + "): " + this.mesh.getFaces().toString());
        System.out.println("Mesh Tex Coords: (" + this.mesh.getTexCoords().size() + "): " + this.mesh.getTexCoords().toString());
        System.out.println("Mesh Normals: (" + this.mesh.getNormals().size() + "): " + this.mesh.getNormals().toString());*/
        return;
    }
}

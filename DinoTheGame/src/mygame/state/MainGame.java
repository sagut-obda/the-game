///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package mygame.state;
//
//import com.jme3.app.Application;
//import com.jme3.app.SimpleApplication;
//import com.jme3.app.state.AbstractAppState;
//import com.jme3.app.state.AppStateManager;
//import com.jme3.asset.AssetManager;
//import com.jme3.bounding.BoundingBox;
//import com.jme3.bullet.BulletAppState;
//import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
//import com.jme3.bullet.control.CharacterControl;
//import com.jme3.bullet.control.RigidBodyControl;
//import com.jme3.collision.CollisionResults;
//import com.jme3.input.ChaseCamera;
//import com.jme3.input.FlyByCamera;
//import com.jme3.input.InputManager;
//import com.jme3.material.Material;
//import com.jme3.math.ColorRGBA;
//import com.jme3.math.Vector3f;
//import com.jme3.renderer.Camera;
//import com.jme3.scene.CameraNode;
//import com.jme3.scene.Node;
//import com.jme3.scene.Spatial;
//import java.util.LinkedList;
//import java.util.Iterator;
//import mygame.models.MoveableCharacter;
//
///**
// *
// * @author Ferdian
// * This class already depricated
// */
//public class MainGame extends AbstractAppState {
//
//    private final Node rootNode;
//    private final Node localRootNode = new Node("Main Game");
//    private final AssetManager assetManager;
//    private BulletAppState bulletAppState;
//    private CharacterControl playerControl;
//    private Spatial player;
//    private final InputManager inputManager;
//    private final FlyByCamera flyCamera;
//    private final Camera camera;
//    private ChaseCamera chaseCamera;
//    private final Vector3f playerWalkDirection = Vector3f.ZERO;
//    private CameraNode cameraNode;
//    private Spatial floor, floor2;
//    private LinkedList<Spatial> poolFloor;
//    private LinkedList<Spatial> poolObstacle;
//    private MoveableCharacter character;
//    private Spatial obstacle;
//    private Material mainMaterial;
//
//    //private LinkedList<Spatial> floor ;
//    public MainGame(SimpleApplication app) {
//        rootNode = app.getRootNode();
//        assetManager = app.getAssetManager();
//        inputManager = app.getInputManager();
//        flyCamera = app.getFlyByCamera();
//        camera = app.getCamera();
//        poolFloor = new LinkedList<>();
//        mainMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        character = new MoveableCharacter(2, 10, 0, 0, 0, 0, "PlayerTest", 1, 1, assetManager, null, mainMaterial, 2, 0);
//        character.changeColor(ColorRGBA.Green);
//        poolObstacle = new LinkedList<>();
//
//    }
//
//    @Override
//    public void initialize(AppStateManager stateManager, Application app) {
//        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
//        //System 3-person harus mematikan flycamera
//        flyCamera.setEnabled(false);
//        // sebenernya ini gw ga tau fungsinya , tapi tadi di tutorial dia ngomong
//        // biar character dan lantai bisa saling kenal jadinya si character bisa
//        // berdiri tepat diatas si lantai
//        bulletAppState = new BulletAppState();
//        bulletAppState.setDebugEnabled(true);
//        //bawaan harus dimasukin 
//        stateManager.attach(bulletAppState);
//        // memasukan semua object ke root node -> kumpulan object utama
//        rootNode.attachChild(localRootNode);
//        //load scenenya ( tanah dan character)
//        Spatial scene = assetManager.loadModel("Scenes/LandmarkScene.j3o");
//
//        // karena ini adalah state bermain jadi dimasukin ke local root
//        localRootNode.attachChild(scene);
//        localRootNode.attachChild(character.getSpatial());
////       localRootNode.attachChild(obstacle);
//        // ada 2 lantai, untuk membedakan , ambil ke 2nya
//        floor = localRootNode.getChild("Lantai");
//        floor2 = localRootNode.getChild("Lantai2");
//        obstacle = localRootNode.getChild("Obstacle");
//        //masukin ke linkedlist biar gampang di updatenya
//
//        poolFloor.addFirst(floor);
//        poolFloor.addFirst(floor2);
//        poolObstacle.addFirst(obstacle);
//        //buat kedua lantai saling kenal dengan character
//        bulletAppState.getPhysicsSpace().add(floor.getControl(RigidBodyControl.class));
//        bulletAppState.getPhysicsSpace().add(floor2.getControl(RigidBodyControl.class));
//        bulletAppState.getPhysicsSpace().add(obstacle.getControl(RigidBodyControl.class));
//        //load player
//        player = localRootNode.getChild("PlayerTest");
//        BoundingBox bb = (BoundingBox) player.getWorldBound();
//        float r = bb.getXExtent();
//        float h = bb.getYExtent();
//        CapsuleCollisionShape ccs = new CapsuleCollisionShape(r, h);
//        playerControl = new CharacterControl(ccs, 1.0f);
//
//        //masukan player control agar dia ga terlalu dumb
//        player.addControl(playerControl);
//        //buat character kenal dengan lantai
//
//        bulletAppState.getPhysicsSpace().add(playerControl);
//        //fly camera diganti sama chase camera , targetnya player
//        chaseCamera = new ChaseCamera(camera, player, inputManager);
//        //rotasi ini membuat camera tepat dibelakang player
//        chaseCamera.setDefaultHorizontalRotation(-3.2f);
//        chaseCamera.setDefaultVerticalRotation(0.3f);
//
//    }
//
//    @Override
//    public void cleanup() {
//
//        rootNode.detachChild(localRootNode);
//        super.cleanup(); //To change body of generated methods, choose Tools | Templates.
//    }
//    private int score = 0 ;
//    private float delayTime = 0 ;
//    @Override
//    public void update(float tpf) {
//        if(delayTime>=1f){
//            System.out.println(score);
//            delayTime = 0 ;
//            score = 0;
//        }else{
//            score++;
//            delayTime += tpf;
//        }
//        Vector3f v2 = player.getLocalTranslation();
//        Iterator<Spatial> obit = poolObstacle.iterator();
//        CollisionResults res = new CollisionResults();
//        while (obit.hasNext()) {
//            Spatial s = obit.next();
//            s.move(tpf*-20, 0, 0);
//            player.collideWith(s.getWorldBound(), res) ;
//            //System.out.println(res.size());
//        //            Spatial s = obit.next();
//                    //            Vector3f v1 = s.getLocalTranslation();
//                    //            float x = Math.abs(v1.x - v2.x);
//                    //            float y = Math.abs(v1.y - v2.y);
//                    //            float z = Math.abs(v1.z - v2.z);
//                    //            if (x <= 0.5 && y <= 1.6 && z <= 0.5) {
//                    //                System.out.println("Collided");
//                    //            }
//                               
//                    //            if (s.getLocalTranslation().getX() <= -5) {
//                    //                s.getLocalTranslation().setX(50);
//                    //            }
//        }
//
//        Iterator<Spatial> it = poolFloor.iterator();
//
//        while (it.hasNext()) {
//
//            Spatial s = it.next();
//            s.move(-20 * tpf, 0, 0);
//            if (s.getLocalTranslation().getX() <= -100) {
//                s.getLocalTranslation().setX(s.getLocalTranslation().getX() + 200);
//            }
//        }
//
//    }
//
//}

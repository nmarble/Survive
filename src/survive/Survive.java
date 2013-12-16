package survive;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import survive.Entities.*;

public class Survive
        extends Canvas
{

  private BufferStrategy strategy;

  int totalRandom = 4;
  int[] randomChance = new int[totalRandom];
  int[] randomDefault = new int[totalRandom];

  private LowerLayer grass;
  private LowerLayer gravel;
  private LowerLayer water;
  private LowerLayer woodFloor;

  private MiddleLayer Tree;
  private MiddleLayer Boulder;
  private MiddleLayer LogWall;
  private MiddleLayer Log;
  private MiddleLayer Stone;
  private MiddleLayer Barrel;
  private MiddleLayer Axe;
  private MiddleLayer WaterBorder;
  private MiddleLayer Rifle;
  private MiddleLayer Window;
  

  private UpperLayer black;
  

  private EnemyLayer zombie;
  private EnemyLayer zombieArms;

  private PlayerEntity player;
  private BulletEntity Bullet;
  
  private Hud structureButton;
  private Hud toolButton;
  private Hud consumableButton;
  private Hud decorativeButton;
  private Hud selectionWindow;
  private Hud equipOverlay;
  private Hud bagOverlay;
  private Hud healthOverlay;
  private Hud hurt;
  private Hud use;
  
  private int bagOverlayX = -Global.xRes + 200;
  private int bagOverlayY = -Global.yRes + 200;

  private Inventory inventoryMan;
  private Inventory log;
  private Inventory stone;
  private Inventory logWall;
  private Inventory barrel;
  private Inventory axe;
  private Inventory rifle;
  private Inventory window;

  private Direction direction = Direction.UP;
  private double movementSpeed = 20;
  private String message = "";

  private boolean waitingForKeyPress = true;
  private boolean leftPressed = false;
  private boolean rightPressed = false;
  private boolean upPressed = false;
  private boolean downPressed = false;
  private boolean spacePressed = false;
  private boolean enterPressed = false;
  private boolean iPressed = false;
  private boolean ePressed = false;
  private boolean cPressed = false;
  private boolean hurtFlash = false;
  
  int[] equipped = new int [4];
  
  private int bulletSpeed = 4;
  
  private int treeLikely = 60;
  private int boulderLikely = 20;
  private int zombieChance = 100;
  private int pRotate = 0;
  private int itemSelection = 0;
  private int selectionX = 0;
  private int selectionY = 0;
  private boolean gameRunning = true;
  private boolean inventoryOpen = false;
  private boolean craftingOpen = false;
  private boolean craftingStructure = false;
  private boolean holdingItem = false;

  private boolean logWallReceipe = false;

  private Map<Coords, LowerLayer> lowerLayers = new HashMap<Coords, LowerLayer>();
  private Map<Coords, MiddleLayer> middleLayers = new HashMap<Coords, MiddleLayer>();
  private Map<Coords, UpperLayer> upperLayers = new HashMap<Coords, UpperLayer>();
  private List<Hud> huds = new ArrayList<Hud>();
  private List<Inventory> inventorys = new ArrayList<Inventory>();
  private Map<Coords, EnemyLayer> enemyLayers = new HashMap<Coords, EnemyLayer>();
  private List<EnemyLayer> enemyMovable = new ArrayList<EnemyLayer>();
  private List<BulletEntity> bullets = new ArrayList<BulletEntity>();
  private List<BulletEntity> bulletsToRemove = new ArrayList<BulletEntity>();
  private ArrayList removeList = new ArrayList();
  private ArrayList<Hud> removeHudList = new ArrayList<Hud>();
  
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  
  
  public Survive()
  {
    JFrame container = new JFrame("Survive");
    
    Global.xRes = ((int)screenSize.getWidth() /20) * 20;
    Global.yRes = ((int)screenSize.getHeight() /20) * 20;
    bagOverlayX = -Global.xRes + 200;
    bagOverlayY = -Global.yRes + 200;
    JPanel panel = (JPanel) container.getContentPane();
    panel.setPreferredSize(new Dimension(Global.xRes, Global.yRes));
    panel.setLayout(null);

    setBounds(0, 0, Global.xRes, Global.yRes);
    panel.add(this);

    setIgnoreRepaint(true);
    
    container.setUndecorated(true);
    container.pack();
    container.setResizable(false);
    container.setVisible(true);

    container.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });

    addKeyListener(new KeyInputHandler());
    addMouseListener(new MouseInputHandler());

    requestFocus();

    createBufferStrategy(2);
    strategy = getBufferStrategy();

    initEntities();
    randomChance[2] = 3;
    randomChance[3] = 3;
    for (int i = 0; i < 4; i++) {
        equipped[i] = 0;
    }
  }

  private void startGame()
  {
    lowerLayers.clear();
    initEntities();

    leftPressed = false;
    rightPressed = false;
    spacePressed = false;
  }

  private void initEntities()
  {
    
    randomDefault[0] = 150;
    randomDefault[1] = 10;
    randomDefault[2] = 0;
    randomDefault[3] = 0;

    randomChance[0] = randomDefault[0];    //Grass
    randomChance[1] = randomDefault[1];    //Gravel
    randomChance[2] = randomDefault[2];    //Pond
    randomChance[3] = randomDefault[3];    //House
    
    //Add Test entities
    testEntities();
    //Add player entity
    player = new PlayerEntity(this, "sprites/playern.png", new Coords(0, 0), "player");
    
    //Add Crafting buttons
    structureButton = new ButtonEntity(this, "sprites/structurebutton.jpg", new Coords(0, Global.yRes - 100), "crafting", 100);
    huds.add(structureButton);
    
    toolButton = new ButtonEntity(this, "sprites/toolbutton.jpg", new Coords(110, Global.yRes - 100), "crafting", 100);
    huds.add(toolButton);

    consumableButton = new ButtonEntity(this, "sprites/consumablebutton.jpg", new Coords(220, Global.yRes - 100), "crafting", 100);
    huds.add(consumableButton);

    decorativeButton = new ButtonEntity(this, "sprites/decorativebutton.jpg", new Coords(330, Global.yRes - 100), "crafting", 100);
    huds.add(decorativeButton);
    
    equipOverlay = new ButtonEntity(this, "sprites/equipoverlay.png", new Coords(0,0), "equipOverlay", 150);
    huds.add(equipOverlay);
    
    bagOverlay = new ButtonEntity(this, "sprites/bagoverlay.png", new Coords(0,0), "bagOverlay", 150);
    huds.add(bagOverlay);
    
    healthOverlay = new ButtonEntity(this, "sprites/healthoverlay.png", new Coords(0,0), "healthOverlay", 150);
    huds.add(healthOverlay);
    
    hurt = new ButtonEntity(this, "sprites/hurt.png", new Coords(0,0), "hurtOverlay", 1000);
    huds.add(hurt);
  
  }
  public void testEntities () 
  {
      addToInventory(5,1);
      addToInventory(6,1);
      addToInventory(7,20);
  }
  public void drawEquipped(Coords coords) 
  {
      String pic = "";
      int a = getRandomNum(2);
      int mouseX = MouseInfo.getPointerInfo().getLocation().x;
      int mouseY = MouseInfo.getPointerInfo().getLocation().y;
      final Coords screenOffset = new Coords(
              player.getCoords().getX() - Global.xRes / 2,
              player.getCoords().getY() - Global.yRes / 2);
      switch (equipped[3]) {
          case 5:
              pic = "sprites/axeswingn1.png";
              if (a == 1) {pic = "sprites/axeswingn2.png";}
              use = new UseEntity(this, pic, coords, "use", 20);
              huds.add(use);
              break;
          case 6:
              pic = "sprites/rifleuse.png";
              use = new UseEntity(this, pic, coords, "use", 20);
              huds.add(use);
              Bullet = new BulletEntity(this,"sprites/bullet.png", coords, new Coords(mouseX + screenOffset.getX(), mouseY + screenOffset.getY()), (int)getPlayerDirection());
              bullets.add(Bullet);
              break;
      } 
  }
  
  public void mouseInteract()
  {
     int mouseX = MouseInfo.getPointerInfo().getLocation().x;
     int mouseY = MouseInfo.getPointerInfo().getLocation().y;
     if (!holdingItem && !inventoryOpen) {
      interact();
     }
     if (holdingItem && !inventoryOpen) {
      placeItemHeld(direction);
     }
     if (inventoryOpen) {
         inventoryInteract(-mouseX, -mouseY);
     }
  }
  public void interact()
  {
    final Coords interactCoords = direction.getCoordsFrom(player.getCoords());    
    if (equipped[3] == 5 || equipped[3] == 6) {
    drawEquipped(interactCoords);
    }
    
    {
      final MiddleLayer middleLayer = middleLayers.get(interactCoords);
      if (middleLayer != null) {
        if (middleLayer.interact() == true) {
            middleLayers.remove(interactCoords);            
        }
        
      }
    }

    {
      final EnemyLayer enemyLayer = enemyLayers.get(interactCoords);
      if (enemyLayer != null) {
        if (enemyLayer.interact() == true) { 
            enemyLayer.setLife(enemyLayer.getLife() - player.getSTR());
            Coords newCoords = direction.getCoordsFrom(enemyLayer.getCoords());
            if (!enemyLayers.containsKey(newCoords)) {
            enemyLayer.setCoords(newCoords);
            enemyLayers.put(enemyLayer.getCoords(), enemyLayer);
            enemyLayers.remove(interactCoords);
            }
            if (enemyLayer.getLife() <= 0) {
            enemyLayers.remove(enemyLayer.getCoords());
            }
        }
      }
    }
  } 
  public void selectionInteract(int x, int y)
  {
      for (Inventory inventory : inventorys) {
          if (inventory.getX() >= x && inventory.getY() >= y && inventory.getX() - 20 <= x && inventory.getY() - 20 <= y ) {
              holdingItem = !holdingItem;
              itemSelection = inventory.getItemCode();
          }
      }
  }    

  public void removeMiddleLayer(MiddleLayer object)
  {
    removeList.add(object);
  }
  
  public void addToInventory(int itemCode, int quantity)
  {
    //Add entities if none are available
    if (inventorys.isEmpty()) {
      log = new Inventory("sprites/log.png", 1, 0, new Coords(0,0));
      inventorys.add(log);
      stone = new Inventory("sprites/stone.png", 2, 0, new Coords(0,0));
      inventorys.add(stone);
      logWall = new Inventory("sprites/logwall.gif", 3, 0, new Coords(0,0));
      inventorys.add(logWall);
      barrel = new Inventory("sprites/barrel.png", 4, 0, new Coords(0,0));
      inventorys.add(barrel);
      axe = new Inventory("sprites/axe.png", 5, 0, new Coords(0,0));
      inventorys.add(axe);
      rifle = new Inventory("sprites/rifle.png", 6, 0, new Coords(0,0));
      inventorys.add(rifle);
      window = new Inventory("sprites/window.png", 7, 0, new Coords(0,0));
      inventorys.add(window);
    }
    for (Inventory inventory : inventorys) {
      if (inventory.getItemCode() == itemCode) {

        inventory.addQuantity(quantity);

      }
    }
  }

  public void removeFromInventory(int itemCode, int quantity)
  {

    for (Inventory inventory : inventorys) {

      if (inventory.getItemCode() == itemCode) {
        inventory.removeQuantity(quantity);
      }
    }
  }
  public void placeItemHeld(Direction direction)
  {
    final Coords coords = direction.getCoordsFrom(player.getCoords());
    if (!middleLayers.containsKey(coords)) {
    switch (itemSelection) {
      case 1:
        Log = new LogEntity(this, "sprites/log.png", coords, "log");
        middleLayers.put(coords, Log);
        break;
      case 2:
        Stone = new StoneEntity(this, "sprites/stone.png", coords, "stone");
        middleLayers.put(coords, Stone);
        break;
      case 3:
        LogWall = new LogWallEntity(this, "sprites/logwall.gif", coords, "logWall");
        middleLayers.put(coords, LogWall);
        break;
      case 4:
        Barrel = new LogWallEntity(this, "sprites/logwall.gif", coords, "logWall");
        middleLayers.put(coords, Barrel);
        break;
      case 5:
        Axe = new AxeEntity(this, "sprites/axe.png", coords, "axe");
        middleLayers.put(coords, Axe);
        break;
      case 6:
        Rifle = new RifleEntity(this, "sprites/rifle.png", coords, "rifle");
        middleLayers.put(coords, Rifle);
        break;
      case 7:
        Window = new WindowEntity(this, "sprites/window.png", coords, "window");
        middleLayers.put(coords, Window);
        break;

    }
    removeFromInventory(itemSelection, 1);
    }
  }
  public void checkCollisionObject(Direction direction)
  {

    final Direction opposite = direction.getOpposite();
    
    final MiddleLayer middleLayer = middleLayers.get(player.getCoords());
    if (middleLayer != null && !middleLayer.passable()) {
      movePlayer(opposite);
    }

    final LowerLayer lowerLayer = lowerLayers.get(player.getCoords());
    if (lowerLayer != null && !lowerLayer.passable()) {
      movePlayer(opposite);
    }
    final EnemyLayer enemyLayer = enemyLayers.get(player.getCoords());
    if (enemyLayer != null && !enemyLayer.passable()) {
      movePlayer(opposite);
    }
  }

  public boolean checkForMoreItem(int itemCode)
  {
    boolean hasIt = false;
    for (Inventory inventory : inventorys) {
      if (inventory.getItemCode() == itemCode) {
        if (inventory.getQuantity() > 0) {
          hasIt = true;
        } else {
          hasIt = false;
        }
      }
    }
    return hasIt;
  }

  public void addFloor(int x, int y)
  {
    final Coords newLocation = new Coords(x, y);
    int[][] locs;
    int chance = 0;
    int size = getRandomNum(8);
    while (size < 5) {
       size = getRandomNum(8); 
    }
    int startX = x;
    int startY = y;
    switch (getRandomGround(x, y)) {
      case 0:
        grass = new GrassEntity(this, "sprites/grass.gif", newLocation, "grass");
        lowerLayers.put(newLocation, grass);
        chance = getRandomNum(treeLikely);
        if (chance == 1) {
          locs = PreSetGroups.tree(direction, startX, startY);
          for (int a = 0; a < 13; a++) { 
              x = locs[a][0];
              y = locs[a][1];              
              final Coords treeCoords = new Coords(x, y);
              if (a == 4) {
              Tree = new TreeEntity(this, "sprites/tree/trunk.png", treeCoords, "tree");    
              }
              if (a != 4 && a < 9) {
              Tree = new LeavesEntity(this, "sprites/tree/leaves1_1.png", treeCoords, "leaves");
              }
              if (a == 9) {
              Tree = new LeavesEntity(this, "sprites/tree/leaves1_d.png", treeCoords, "leaves");    
              }
              if (a == 10) {
              Tree = new LeavesEntity(this, "sprites/tree/leaves1_u.png", treeCoords, "leaves");    
              }
              if (a == 11) {
              Tree = new LeavesEntity(this, "sprites/tree/leaves1_r.png", treeCoords, "leaves");    
              }
              if (a == 12) {
              Tree = new LeavesEntity(this, "sprites/tree/leaves1_l.png", treeCoords, "leaves");    
              }
              middleLayers.put(treeCoords, Tree);
          } 
        }
        break;
      case 1:
        gravel = new GravelEntity(this, "sprites/gravel.gif", newLocation, "gravel");
        lowerLayers.put(newLocation, gravel);
        chance = getRandomNum(boulderLikely);
        if (chance == 1) {
          Boulder = new BoulderEntity(this, "sprites/boulder.png", newLocation, "boulder");
          middleLayers.put(newLocation, Boulder);
        }
        break;
      case 2:
        locs = PreSetGroups.pond(direction, startX, startY, size);
        for (int a = 0; a < (400); a++) {
          x = locs[a][0];
          y = locs[a][1];
          if (a > 0 && x == 0 && y == 0) break;
          final Coords waterCoords = new Coords(x, y);
          middleLayers.remove(waterCoords);
          lowerLayers.remove(waterCoords);
          water = new WaterEntity(this, "sprites/water1.png", waterCoords, "water");
          lowerLayers.put(waterCoords, water);        
        }
        break;
        case 3:
        locs = PreSetGroups.houseFloor(direction, startX, startY, size);
        for (int a = 0; a < (400); a++) {
          x = locs[a][0];
          y = locs[a][1];
          if (a > 0 && x == 0 && y == 0) break;
          final Coords floorCoords = new Coords(x, y);
          middleLayers.remove(floorCoords);
          lowerLayers.remove(floorCoords);
          woodFloor = new WoodFloorEntity(this, "sprites/woodfloor.gif", floorCoords, "woodfloor");
          lowerLayers.put(floorCoords, woodFloor);
        }
        locs = PreSetGroups.houseWalls(direction, startX, startY, size);
        for (int a = 0; a < (400); a++) { 
          x = locs[a][0];
          y = locs[a][1];
          if (a > 0 && x == 0 && y == 0) break;
          final Coords logWallCoords = new Coords(x, y);
          LogWall = new LogWallEntity(this, "sprites/logwall.gif", logWallCoords, "logwall");
          middleLayers.put(logWallCoords, LogWall);
        }
        locs = PreSetGroups.houseItems(direction, 3, startX, startY, size);
        for (int a = 0; a < (3); a++) { 
          x = locs[a][0];
          y = locs[a][1];
          if (a > 0 && x == 0 && y == 0) break;
          final Coords itemsCoords = new Coords(x, y);
          Barrel = new BarrelEntity(this, "sprites/barrel.png", itemsCoords, "barrel");
          middleLayers.put(itemsCoords, Barrel);
        }
        break;
    }
  }

  public int getRandomGround(int x, int y)
  {
    
    int choice = 0;
    int choiceNum = 0;

    for (int i = 0; i < totalRandom; i++) {
      int chance = (int) (Math.random() * randomChance[i]);

      if (chance > choice) {
        choice = chance;
        choiceNum = i;
      }
    }

    return choiceNum;
  }

  public int getRandomNum(int numRange)
  {
    int num = (int) (Math.random() * numRange);
    return num;
  }

  public void movePlayer(final Direction direction)
  {
    player.setCoords(direction.getCoordsFrom(player.getCoords()));
  }
  public void inventoryInteract(int x, int y) 
  {
      boolean inEquip = false;
      int equippedSelection = 0;
      if (x <= -(Global.xRes - 78) && y <= -120 && x >= -(Global.xRes - 78)-20 && y >= -140) {
        equippedSelection = 0;
        inEquip = true;
      }
      if (x <= -(Global.xRes - 24) && y <= -75 && x >= -(Global.xRes - 24)-20 && y >= -95) {
        equippedSelection = 1;
        inEquip = true;
      }
      if (x <= -(Global.xRes - 55) && y <= -5 && x >= -(Global.xRes - 55)-20 && y >= -25) {
        equippedSelection = 2;
        inEquip = true;
      }
      if (x <= -(Global.xRes - 145) && y <= -39 && x >= -(Global.xRes - 78)-20 && y >= -59) {
        equippedSelection = 3;
        inEquip = true;
      }
      if (holdingItem && inEquip)
        for (Inventory inventory : inventorys) {
            if (inventory.getItemCode() == itemSelection && inventory.equipable()) {
                equipped[equippedSelection] = inventory.getItemCode();
                inventory.slotEquipped[equippedSelection] = true;
                inventory.removeQuantity(1);
            }
        }
      if (!holdingItem && !inEquip) {
          selectionInteract(x, y);
      }
      if (!holdingItem && inEquip) {
          itemSelection = equipped[equippedSelection];
          for (Inventory inventory : inventorys) {
              if (inventory.getItemCode() == itemSelection) {
                  equipped[equippedSelection] = 0;
                  inventory.addQuantity(1);
                  inventory.slotEquipped[equippedSelection] = false;
              }
          }
      }
  }
  public void checkButtonPushed()
  {

    if (cPressed) {
      craftingOpen = !craftingOpen;
      if (craftingStructure = true) {
        craftingStructure = false;
      }
    }
    if (iPressed || ePressed) {
      if (!inventoryOpen) {
          holdingItem = false;
      }
      inventoryOpen = !inventoryOpen;
      selectionX = bagOverlayX - 30;
      selectionY = bagOverlayY - 35;
       
    }
    if (leftPressed && !inventoryOpen) {
      direction = Direction.LEFT;
      player.changeFrame(pRotate);
      movePlayer(direction);           
      checkCollisionObject(direction);
      pRotate ++;
    }
    
    if (rightPressed && !inventoryOpen) {
      direction = Direction.RIGHT;
      player.changeFrame(pRotate);
      movePlayer(direction);          
      checkCollisionObject(direction);
      pRotate ++;
    }
    if (upPressed && !inventoryOpen) {
      direction = Direction.UP;
      player.changeFrame(pRotate);
      movePlayer(direction);           
      checkCollisionObject(direction);
      pRotate ++;
    }
    if (downPressed && !inventoryOpen) {
      direction = Direction.DOWN;
      player.changeFrame(pRotate);
      movePlayer(direction);          
      checkCollisionObject(direction);
      pRotate ++;
    }
    if (pRotate > 1) {
        pRotate = 0;
    }
  }

  public void findAvailRec()
  {
    int logQuantity = 0;
    int stoneQuantity = 0;
    for (Inventory inventory : inventorys) {
      if (inventory.getQuantity() > 0) {
        switch (inventory.getItemCode()) {
          case 1:
            logQuantity = inventory.getQuantity();
            break;
          case 2:
            stoneQuantity = inventory.getQuantity();
            break;
        }
      }
    }
    //Log wall receipe
    if (logQuantity >= 4) {
      logWallReceipe = true;

      //Set location to put icons
      final Coords coords = new Coords(0, Global.yRes - 30);
      structureButton = new ButtonEntity(this, "sprites/logwall.gif", coords, "LogWall", 20);
      huds.add(structureButton);
    }
  }
  
  public void moveEnemy(Graphics2D g)
  {
    enemyMovable.addAll(enemyLayers.values());
    for (EnemyLayer enemyLayer : enemyMovable) {
      Coords finalMove = enemyLayer.getCoords();
      Direction newdirection = Direction.UP;
      
          int fD = 99999;
          
          for (int x = enemyLayer.getCoords().getX() - 20; x <= enemyLayer.getCoords().getX() + 20; x  = x + 20) {
              for (int y = enemyLayer.getCoords().getY() - 20; y <= enemyLayer.getCoords().getY() + 20; y  = y + 20) {
                  int dx = abs(x - player.getCoords().getX());
                  int dy = abs(y - player.getCoords().getY());              
                  int tD = dx + dy;
                  Coords coords = new Coords(x,y);
                  LowerLayer lowerLayer = lowerLayers.get(coords);
                  MiddleLayer middleLayer = middleLayers.get(coords);
                  EnemyLayer otherEnemy = enemyLayers.get(coords);
                  if (lowerLayer != null) {
                  if (middleLayer == null || middleLayer.passable()) {
                  if (otherEnemy == null || otherEnemy.passable()) {
                  if (tD < fD && lowerLayer.passable()) {
                      fD = tD;
                      finalMove = new Coords(x,y);
                      if (finalMove.getX() < enemyLayer.getCoords().getX()) {
                          newdirection = Direction.LEFT;
                      }
                      if (finalMove.getX() > enemyLayer.getCoords().getX()) {
                          newdirection = Direction.RIGHT;
                      }
                      if (finalMove.getY() < enemyLayer.getCoords().getY()) {
                          newdirection = Direction.UP;
                      }
                      if (finalMove.getY() > enemyLayer.getCoords().getY()) {
                          newdirection = Direction.DOWN;
                      }
                  }
                  }
                  }
                  }
              }
          }
          // Move to this location if not players
          if (finalMove.getX() != player.getCoords().getX() || finalMove.getY() != player.getCoords().getY()) {          
          enemyLayers.remove(enemyLayer.coords);
          zombie = enemyLayer;
          zombie.setCoords(finalMove);
          zombie.changeDirection(newdirection, false);
          enemyLayers.put(finalMove, zombie);
          }
          // If the final move is players location do this
          else {
              enemyLayers.remove(enemyLayer.coords);
              zombie = enemyLayer;
              zombie.changeDirection(newdirection, true);
              enemyLayers.put(enemyLayer.coords, zombie);
              player.setLife(player.getLife() - enemyLayer.getSTR());
              hurtFlash = true;
          }   
    }
    enemyMovable.clear();
  }

  public void checkLoS() {
      Coords startCoord = new Coords(player.getCoords().getX(),player.getCoords().getY());
      //topleft to bottom left
      for (int endY = player.getCoords().getY() - Global.yRes; endY < startCoord.getY() + Global.yRes; endY = endY +20){
      boolean unseen = false;
      Coords endCoord = new Coords(player.getCoords().getX() - Global.xRes, endY);

      int deltaX = startCoord.getX() - endCoord.getX();
      int deltaY = 0;
      if (startCoord.getY() <= endCoord.getY()) {
      deltaY = startCoord.getY() - endCoord.getY();
      }
      if (startCoord.getY() > endCoord.getY()) {
      deltaY = endCoord.getY() - startCoord.getY();
      }
      
      double error = 0;
      double deltaError = abs((double)deltaY / (double)deltaX); 
      int y = startCoord.getY();
      for (int x = startCoord.getX(); x >= endCoord.getX(); x = x - 20) {
          Coords location = new Coords(x,y);
          if (unseen) {
            black = new BlackEntity(this, "sprites/black.gif", location, "black");
            upperLayers.put(location, black);  
          }

          if (middleLayers.containsKey(location)) {
            MiddleLayer middleLayer = middleLayers.get(location); 
            if (!middleLayer.seePassed()) {
                unseen = true;
            }
            }
          
          error = error + deltaError;
          while (error >= .5) {
              if (startCoord.getY() <= endCoord.getY()) {
              y = y - 20;
              }
              if (startCoord.getY() > endCoord.getY()) {
              y = y + 20;
              }
              error = error - 1;
          }
      }
      }
      //topright to bottom right
      for (int endY = player.getCoords().getY() - Global.yRes; endY < startCoord.getY() + Global.yRes; endY = endY +20){
      boolean unseen = false;
      Coords endCoord = new Coords(player.getCoords().getX() + Global.xRes, endY);

      int deltaX = startCoord.getX() - endCoord.getX();
      int deltaY = 0;
      if (startCoord.getY() <= endCoord.getY()) {
      deltaY = startCoord.getY() - endCoord.getY();
      }
      if (startCoord.getY() > endCoord.getY()) {
      deltaY = endCoord.getY() - startCoord.getY();
      }
      
      double error = 0;
      double deltaError = abs((double)deltaY / (double)deltaX); 
      int y = startCoord.getY();
      for (int x = startCoord.getX(); x <= endCoord.getX(); x = x + 20) {
          Coords location = new Coords(x,y);
          if (unseen) {
            black = new BlackEntity(this, "sprites/black.gif", location, "black");
            upperLayers.put(location, black);  
          }

          if (middleLayers.containsKey(location)) {
            MiddleLayer middleLayer = middleLayers.get(location); 
            if (!middleLayer.seePassed()) {
                unseen = true;
            }
            }
          
          error = error + deltaError;
          while (error >= .5) {
              if (startCoord.getY() <= endCoord.getY()) {
              y = y - 20;
              }
              if (startCoord.getY() > endCoord.getY()) {
              y = y + 20;
              }
              error = error - 1;
          }
      }
      }
      //topleft to topright
      for (int endX = player.getCoords().getX() - Global.xRes; endX < startCoord.getX() + Global.xRes; endX = endX +20){
      boolean unseen = false;
      Coords endCoord = new Coords(endX, player.getCoords().getY() - Global.yRes);
      
      int deltaX = 0;
      if (startCoord.getX() <= endCoord.getX()) {
        deltaX = startCoord.getX() - endCoord.getX();
      }
      if (startCoord.getX() > endCoord.getX()) {
        deltaX = endCoord.getX() - startCoord.getX();
      }    
      int deltaY = startCoord.getY() - endCoord.getY();
      
      double error = 0;
      double deltaError = abs((double)deltaX / (double)deltaY); 
      int x = startCoord.getX();
      for (int y = startCoord.getY(); y >= endCoord.getY(); y = y - 20) {
          Coords location = new Coords(x,y);
          if (unseen) {
            black = new BlackEntity(this, "sprites/black.gif", location, "black");
            upperLayers.put(location, black);  
          }

          if (middleLayers.containsKey(location)) {
            MiddleLayer middleLayer = middleLayers.get(location); 
            if (!middleLayer.seePassed()) {
                unseen = true;
            }
            }
          
          error = error + deltaError;
          while (error >= .5) {
              if (startCoord.getX() <= endCoord.getX()) { 
              x = x - 20;
              }
              if (startCoord.getX() > endCoord.getX()) { 
              x = x + 20;
              }
              error = error - 1;
          }
      }
      }
      //bottomleft to bottomright
      for (int endX = player.getCoords().getX() - Global.xRes; endX < startCoord.getX() + Global.xRes; endX = endX +20){
      boolean unseen = false;
      Coords endCoord = new Coords(endX, player.getCoords().getY() + Global.yRes);
      
      int deltaX = 0;
      if (startCoord.getX() <= endCoord.getX()) {
        deltaX = startCoord.getX() - endCoord.getX();
      }
      if (startCoord.getX() > endCoord.getX()) {
        deltaX = endCoord.getX() - startCoord.getX();
      }    
      int deltaY = startCoord.getY() - endCoord.getY();
      
      double error = 0;
      double deltaError = abs((double)deltaX / (double)deltaY); 
      int x = startCoord.getX();
      for (int y = startCoord.getY(); y <= endCoord.getY(); y = y + 20) {
          Coords location = new Coords(x,y);
          if (unseen) {
            black = new BlackEntity(this, "sprites/black.gif", location, "black");
            upperLayers.put(location, black);  
          }

          if (middleLayers.containsKey(location)) {
            MiddleLayer middleLayer = middleLayers.get(location); 
            if (!middleLayer.seePassed()) {
                unseen = true;
            }
            }
          
          error = error + deltaError;
          while (error >= .5) {
              if (startCoord.getX() <= endCoord.getX()) { 
              x = x - 20;
              }
              if (startCoord.getX() > endCoord.getX()) { 
              x = x + 20;
              }
              error = error - 1;
          }
      }
      }
      
  }
  public double getPlayerDirection()
  {
      double mouseX = MouseInfo.getPointerInfo().getLocation().x - (Global.xRes /2);
      double mouseY = MouseInfo.getPointerInfo().getLocation().y - (Global.yRes /2);
      double xD = abs(10 - mouseX);
      double yD = abs(30 - mouseY);
      double playerRot = Math.toDegrees(Math.atan(xD/yD));
      
      if (mouseX - 10 > 0 && mouseY - 30> 0) {
          playerRot = abs(playerRot - 180);
      }
      if (mouseX - 10  < 0 && mouseY - 30  > 0) {
          playerRot = playerRot + 180;
      }
      if (mouseX - 10< 0 && mouseY  - 30 < 0) {
          playerRot = abs(playerRot - 360);
      }
      direction = Direction.UP;
      if (playerRot > 22.5 && playerRot <= 67.5) {direction = Direction.UPRIGHT;}
      if (playerRot > 67.5 && playerRot <= 112.5) {direction = Direction.RIGHT;}
      if (playerRot > 112.5 && playerRot <= 157.5) {direction = Direction.DOWNRIGHT;}
      if (playerRot > 157.5 && playerRot <= 202.5) {direction = Direction.DOWN;}
      if (playerRot > 202.5 && playerRot <= 247.5) {direction = Direction.DOWNLEFT;}
      if (playerRot > 247.5 && playerRot <= 292.5) {direction = Direction.LEFT;}
      if (playerRot > 292.5 && playerRot <= 337.5) {direction = Direction.UPLEFT;}
      return playerRot;
  }
  public void bulletAction (Coords screenOffset)
  {
     Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
      
        if (bullets.size() > 0) {
          for (BulletEntity bullet : bullets) {
              bullet.moveBullet();
              if (middleLayers.containsKey(bullet.getBlockCoord())) {
                  MiddleLayer obs = middleLayers.get(bullet.getBlockCoord());
                  if (!obs.passable()) {
                      bulletsToRemove.add(bullet);
                  }
              }
              if (enemyLayers.containsKey(bullet.getBlockCoord())) {
                EnemyLayer enemyLayer = enemyLayers.get(bullet.getBlockCoord());
                enemyLayer.setLife(enemyLayer.getLife()-player.getSTR());
              if (enemyLayer.getLife() <= 0) {
                enemyLayers.remove(enemyLayer.getCoords());
              }
              }
              if (bullet.getTime() > 40) {
                  bulletsToRemove.add(bullet);
              }
          }
      } 
  }
  //Loop of main game
  public void gameLoop()
  {
    long lastLoopTime = System.currentTimeMillis();
    long loopTime = 0;
    
    
    while (gameRunning) {
      long delta = System.currentTimeMillis() - lastLoopTime;
      loopTime++;
      lastLoopTime = System.currentTimeMillis();

      Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
      g.setColor(Color.black);
      g.fillRect(0, 0, Global.xRes, Global.yRes);

      // Moves applicable enemy within its speed compared to loop time
      if (loopTime == 10) {
        moveEnemy(g);
        loopTime = 0;
      }
      if (loopTime == 1) {
          hurtFlash = false;
      }
      
      int randomZombie = (int) (Math.random() * zombieChance);
      if (randomZombie == 1) {
        System.err.println("ZOMBIE");
        final Coords location = new Coords(player.getCoords().getX() - (Global.xRes / 2),
                player.getCoords().getY() + (getRandomNum(Global.yRes / 20) * 20) - (getRandomNum(Global.yRes / 20) * 20) 
        );
        zombie = new ZombieEntity(this, "sprites/zombie1.png", location, "zombie", Direction.UP);
        enemyLayers.put(location, zombie);
      }
      if (bulletsToRemove.size() > 0) {
      bullets.removeAll(bulletsToRemove);
      bulletsToRemove.clear();
      }
      checkLoS(); 
      checkButtonPushed();
      
      //Draw all ground that is on screen
      // The upper right coordinates of the screen in game world coordinates.
      final Coords screenOffset = new Coords(
              player.getCoords().getX() - Global.xRes / 2,
              player.getCoords().getY() - Global.yRes / 2);
      final int xStart = screenOffset.getX();
      final int xStop = screenOffset.getX() + Global.xRes;
      final int xStep = 20;
      final int yStart = screenOffset.getY();
      final int yStop = screenOffset.getY() + Global.yRes;
      final int yStep = 20;
      for (int x = xStart; x <= xStop; x += xStep) {
        for (int y = yStart; y <= yStop; y += yStep) {
          final Coords coords = new Coords(x, y);
          if (!lowerLayers.containsKey(coords)) {
            addFloor(x, y);
          }
            if(lowerLayers.get(coords).getType() == "water" && (loopTime % 5) == 0) {
                LowerLayer lowerLayer = lowerLayers.get(coords);
                lowerLayers.remove(coords);
                lowerLayer.changeFrame(lowerLayer.nextFrame());
                lowerLayers.put(coords, lowerLayer);
            }
          lowerLayers.get(coords).draw(g, screenOffset);
          if (middleLayers.containsKey(coords)) {
            middleLayers.get(coords).draw(g, screenOffset);
          }
          if (enemyLayers.containsKey(coords) && !upperLayers.containsKey(coords)) {
            enemyLayers.get(coords).rotDraw(g, screenOffset, enemyLayers.get(coords).getRotation());          
          }
          if (upperLayers.containsKey(coords)) {
            upperLayers.get(coords).draw(g, screenOffset);
            upperLayers.remove(coords);
          }
        }
      }
      //Draws Player
      player.rotDraw(g, screenOffset, (int)getPlayerDirection());
      for (Hud hud : huds)
      if ("use".equals(hud.getType())) {
        use.rotDraw(g, screenOffset, (int)getPlayerDirection());
        removeHudList.add(hud);
      }
      //Draw bullets
      for (BulletEntity bullet : bullets) {
           bullet.rotDraw(g, screenOffset, bullet.getRot());
      }
      //What is to be removed gets removed
      for (Hud hud : removeHudList){
        huds.remove(hud);
      }
      //Resets what is to be removed
      removeList.clear();
      
      //Draw Health Bar
      for(Hud hud : huds) {
          if ("healthOverlay".equals(hud.getType())) {
            String life = String.valueOf(player.getLife());
            g.setColor(Color.red);
            for (int x = 67; x <= (player.getLife()*2)+67; x = x +2) {
            g.drawLine(x, 0, x, 25);
            g.drawLine(x+1, 0, x+1, 25);
            }
            hud.draw(g, new Coords(0, 0));
            g.setColor(Color.white);
            g.drawString(life, hud.getCoords().getX() + 155, hud.getCoords().getY()+17);
            
          } 
      }
      //Draws what is being held
      if (holdingItem == true) {
          for(Inventory inventory : inventorys) {
              if (inventory.getItemCode() == itemSelection) {
                  Coords center = new Coords(-Global.xRes / 2, -Global.yRes / 2);
                  Coords drawCoords = direction.getCoordsTo(center);
                  inventory.draw(g, drawCoords);
              }
          }        
      }
      
      //Draws if Player can walk under;
      final MiddleLayer middleLayer = middleLayers.get(player.getCoords());
      if (middleLayer != null && middleLayer.walkUnder()) {
          middleLayers.get(player.getCoords()).draw(g, screenOffset);
      }
      //When Crafting is open
      if (craftingOpen == true) {
        for (Hud hud : huds) {
            if ("crafting".equals(hud.getType())) {
                hud.draw(g, new Coords(0, 0));
            }
        }
      }
      //When Crafting for structures is open
      if (craftingStructure == true) {
        findAvailRec();
        for (int i = 5; i < huds.size(); i++) {
          Hud hud = huds.get(i);
          hud.draw(g, new Coords(0, 0));
          if (hud.getType() != "selection") {
              removeHudList.add(hud);
          }
        }
      }
      //Change holding status if nothing is there
      if (holdingItem == true && checkForMoreItem(itemSelection) == false) {
        itemSelection = 0;
        holdingItem = false;
      }

      if (waitingForKeyPress) {
        g.setColor(Color.white);
        g.drawString(message, (Global.xRes - g.getFontMetrics().stringWidth(message)) / 2, 250);
        g.drawString("Press any key", (Global.xRes - g.getFontMetrics().stringWidth("Press space to continue")) / 2, (Global.yRes / 2));
      }
      
      int time = 0;
      while (time < bulletSpeed) {
      bulletAction(screenOffset);
      time++;
      }
      //Draws inventory background and items
      if (inventoryOpen == true) {       
        int col = 5;
        int row = 1;
        
        for (Hud hud : huds) {
            if ("equipOverlay" == hud.getType()) { 
            hud.draw(g, new Coords(-(Global.xRes -150),0));
            }
            if ("bagOverlay" == hud.getType()) {
            hud.draw(g, new Coords(bagOverlayX, bagOverlayY));
            }
            
        }
        
        for (Inventory inventory : inventorys) {
          int drawX = 0;
          int drawY = 0;
          if (inventory.getQuantity() > 0) {
            col = col + 25;
            drawX = (bagOverlayX - (col));
            drawY = (bagOverlayY - 20) - (15 * row);

            inventory.draw(g, new Coords(drawX, drawY));
            String quantity = String.valueOf(inventory.getQuantity());
            g.drawString(quantity, -drawX, -drawY);
            inventory.setXY(drawX, drawY);
          }
          if (inventory.isEquipped()) {
              if (inventory.slotEquipped[0]) {
                  int x = -(Global.xRes - 78);
                  int y = -120;
                  inventory.draw(g, new Coords(x, y));
              }
              if (inventory.slotEquipped[1]) {
                  int x = -(Global.xRes - 24);
                  int y = -75;
                  inventory.draw(g, new Coords(x, y));
              }
              if (inventory.slotEquipped[2]) {
                  int x = -(Global.xRes - 55);
                  int y = -5;
                  inventory.draw(g, new Coords(x, y));
              }
              if (inventory.slotEquipped[3]) {
                  int x = -(Global.xRes - 145);
                  int y = -39;
                  inventory.draw(g, new Coords(x, y));
              }
          }
        }
      }
      for (int i = 0; i < totalRandom; i++) {
        if (randomChance[i] > randomDefault[i] + 20) {
          randomChance[i] = randomDefault[i] + 10;
        }
      }
      //Draw hurt screen
      
      if (hurtFlash == true) {
          for (Hud hud : huds) {
          if ("hurtOverlay" == hud.getType()) {
                double x = 0;
                double y = 0;
                x = ((double)Global.xRes) / (double)hud.getImageSize();
                y = ((double)Global.yRes) / (double)hud.getImageSize();
                hud.scaleDraw(g, new Coords(0, 0), x, y);    
          }
          }
      }
         
      // Check for death
      if (player.getLife() <= 0) {
          System.err.println("DEAD");
          System.exit(0);
      }
 
      strategy.show();
      if (loopTime > 10) {
        loopTime = 0;
      }
      try {
        Thread.sleep(100L);
      } catch (Exception e) {
      }
    }
  }

  private class MouseInputHandler
          extends MouseAdapter
  {
    
    public void mousePressed(MouseEvent e)
    {
     mouseInteract();
     
    }
  }

  private class KeyInputHandler
          extends KeyAdapter
  {

    private int pressCount = 1;

    private KeyInputHandler()
    {
    }

    public void keyPressed(KeyEvent e)
    {
      if (waitingForKeyPress) {
        return;
      }
      switch (e.getKeyCode()) {
        
        case 32:
          spacePressed = true;
          break;
        case 37:
          leftPressed = true;
          break;
        case 38:
          upPressed = true;
          break;
        case 39:
          rightPressed = true;
          break;
        case 40:
          downPressed = true;
          break;
        case 67:
          cPressed = true;
          break;
        case 69:
          ePressed = true;
          break;    
        case 73:
          iPressed = true;
          break;
        case 87:
          upPressed = true;
          break;
        case 83:
          downPressed = true;
          break;
        case 65:
          leftPressed = true;
          break;
        case 68:
          rightPressed = true;
          break;
            
      }
    }

    public void keyReleased(KeyEvent e)
    {
      if (waitingForKeyPress) {
        return;
      }
      switch (e.getKeyCode()) {
        case 13:
          enterPressed = false;
          break;
        case 32:
          spacePressed = false;
          break;
        case 37:
          leftPressed = false;
          break;
        case 38:
          upPressed = false;
          break;
        case 39:
          rightPressed = false;
          break;
        case 40:
          downPressed = false;
          break;
        case 67:
          cPressed = false;
          break;
        case 69:
          ePressed = false;
          break; 
        case 73:
          iPressed = false;
          break;
        case 87:
          upPressed = false;
          break;
        case 83:
          downPressed = false;
          break;
        case 65:
          leftPressed = false;
          break;
        case 68:
          rightPressed = false;
          break;
      }
    }

    public void keyTyped(KeyEvent e)
    {
      if (waitingForKeyPress) {
        if (pressCount == 1) {
          waitingForKeyPress = false;

          pressCount = 0;
        } else {
          pressCount += 1;
        }
      }
      if (e.getKeyChar() == '\033') {
        System.exit(0);
      }
    }
  }

  public static void main(String[] args)
  {
    Survive g = new Survive();

    g.gameLoop();
  }
}

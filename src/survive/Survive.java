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

  private LowerLayer grass, gravel, water, woodFloor;

  private MiddleLayer Tree, Boulder, LogWall, Log, Stone, Barrel, Axe, WaterBorder, Rifle, Window, Ammo, DeadBody;

  private UpperLayer black;
  
  private EnemyLayer zombie;

  private PlayerEntity player;
  private BulletEntity Bullet;
  
  private Hud button, selectionWindow, equipOverlay, bagOverlay, healthOverlay, hurt, use;
  
  private int bagOverlayX = -Global.xRes + 200;
  private int bagOverlayY = -Global.yRes + 200;

  private Inventory log, stone, logWall, barrel, axe, rifle, window, ammo;

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
  
  private int nextDay = 0;
  private boolean dayTime = true;
  private boolean dayTrans = true;
  private int daySpeed = 1;
  private int timeOfDay = 10;
  private float darkness = 0.8f;
  private int treeLikely = 60;
  private int boulderLikely = 20;
  private int zombieChance = 400;
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
  
  private int xLLimit = 0;
  private int xRLimit = 0; 
  private int yULimit = 0;
  private int yDLimit = 0;
   
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
    randomDefault[1] = 15;
    randomDefault[2] = 0;
    randomDefault[3] = 0;

    randomChance[0] = randomDefault[0];    //Grass
    randomChance[1] = randomDefault[1];    //Gravel
    randomChance[2] = randomDefault[2];    //Pond
    randomChance[3] = randomDefault[3];    //House
    
    //Add Test entities
    testEntities();
    //Add player entity
    player = new PlayerEntity(this, "sprites/player/playern.png", new Coords(0, 0), "player");
    
    //Add Crafting buttons
    button = new ButtonEntity(this, "sprites/hud/structurebutton.jpg", new Coords(0, Global.yRes - 100), "crafting", 100);
    huds.add(button);
    
    button = new ButtonEntity(this, "sprites/hud/toolbutton.jpg", new Coords(110, Global.yRes - 100), "crafting", 100);
    huds.add(button);

    button = new ButtonEntity(this, "sprites/hud/consumablebutton.jpg", new Coords(220, Global.yRes - 100), "crafting", 100);
    huds.add(button);

    button = new ButtonEntity(this, "sprites/hud/decorativebutton.jpg", new Coords(330, Global.yRes - 100), "crafting", 100);
    huds.add(button);
    
    equipOverlay = new ButtonEntity(this, "sprites/hud/equipoverlay.png", new Coords(0,0), "equipOverlay", 150);
    huds.add(equipOverlay);
    
    bagOverlay = new ButtonEntity(this, "sprites/hud/bagoverlay.png", new Coords(0,0), "bagOverlay", 150);
    huds.add(bagOverlay);
    
    healthOverlay = new ButtonEntity(this, "sprites/hud/healthoverlay.png", new Coords(0,0), "healthOverlay", 150);
    huds.add(healthOverlay);
    
    hurt = new ButtonEntity(this, "sprites/hud/hurt.png", new Coords(0,0), "hurtOverlay", 1000);
    huds.add(hurt);
    
    if (inventorys.isEmpty()) {
      log = new Inventory("sprites/object/log.png", 1, 0, new Coords(0,0));
      inventorys.add(log);
      stone = new Inventory("sprites/object/stone.png", 2, 0, new Coords(0,0));
      inventorys.add(stone);
      logWall = new Inventory("sprites/object/logwall.gif", 3, 0, new Coords(0,0));
      inventorys.add(logWall);
      barrel = new Inventory("sprites/object/barrel.png", 4, 0, new Coords(0,0));
      inventorys.add(barrel);
      axe = new Inventory("sprites/object/axe.png", 5, 0, new Coords(0,0));
      inventorys.add(axe);
      rifle = new Inventory("sprites/object/rifle.png", 6, 0, new Coords(0,0));
      inventorys.add(rifle);
      window = new Inventory("sprites/object/window.png", 7, 0, new Coords(0,0));
      inventorys.add(window);
      ammo = new Inventory("sprites/object/ammobox.png", 9, 0, new Coords(0,0));
      inventorys.add(ammo);
    }
  }
  public void testEntities () 
  {

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
              pic = "sprites/action/axeswingn1.png";
              if (a == 1) {pic = "sprites/action/axeswingn2.png";}
              use = new UseEntity(this, pic, coords, "use", 20);
              huds.add(use);
              break;
          case 6:
              if (checkForMoreItem(9)) {
              pic = "sprites/action/rifleuse.png";
              use = new UseEntity(this, pic, coords, "use", 20);
              huds.add(use);
              Bullet = new BulletEntity(this,"sprites/object/bullet.png", coords, new Coords(mouseX + screenOffset.getX(), mouseY + screenOffset.getY()), (int)getPlayerDirection());
              bullets.add(Bullet);
              removeFromInventory(9,1);
              }
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
    for (Inventory inventory : inventorys) {
        if (inventory.getItemCode() == equipped[3] || equipped[3] == 0 ) {
            int[] interacable = inventory.interactableCodes(equipped[3]);

      final MiddleLayer middleLayer = middleLayers.get(interactCoords);
      if (middleLayer != null) {
        if (middleLayer.interact() == true) {
            for (int i = 0; i < interacable.length; i++) {
                if (middleLayer.getType() == interacable[i]) {               
                middleLayers.remove(interactCoords);
                }
            }
        } 
      }
        }
    }

    {
      final EnemyLayer enemyLayer = enemyLayers.get(interactCoords);
      if (enemyLayer != null) {
        if (enemyLayer.interact() == true) { 
            enemyLayer.setLife(enemyLayer.getLife() - player.getSTR());
            Coords newCoords = direction.getCoordsFrom(enemyLayer.getCoords());
            if (!enemyLayers.containsKey(newCoords) && !middleLayers.containsKey(newCoords) || middleLayers.get(newCoords).passable()) {
            enemyLayer.setCoords(newCoords);
            enemyLayers.put(enemyLayer.getCoords(), enemyLayer);
            enemyLayers.remove(interactCoords);
            }
            if (enemyLayer.getLife() <= 0) {
            enemyLayers.remove(newCoords);
            DeadBody = new DeadBodyEntity(this, "sprites/object/deadzomb.png", interactCoords, 10);
            middleLayers.put(interactCoords, DeadBody);
            }
        }
      }
    }
  } 
  public void selectionInteract(int x, int y)
  {
      for (Inventory inventory : inventorys) {
          if (inventory.getX() >= x && inventory.getY() >= y && inventory.getX() - 20 <= x && inventory.getY() - 20 <= y ) {
              if (inventory.getQuantity() > 0) {
              holdingItem = !holdingItem;
              itemSelection = inventory.getItemCode();
              }
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
        Log = new LogEntity(this, "sprites/object/log.png", coords, 1);
        middleLayers.put(coords, Log);
        break;
      case 2:
        Stone = new StoneEntity(this, "sprites/object/stone.png", coords, 2);
        middleLayers.put(coords, Stone);
        break;
      case 3:
        LogWall = new LogWallEntity(this, "sprites/object/logwall.gif", coords, 3);
        middleLayers.put(coords, LogWall);
        break;
      case 4:
        Barrel = new BarrelEntity(this, "sprites/object/barrel.png", coords, 4);
        middleLayers.put(coords, Barrel);
        break;
      case 5:
        Axe = new AxeEntity(this, "sprites/object/axe.png", coords, 5);
        middleLayers.put(coords, Axe);
        break;
      case 6:
        Rifle = new RifleEntity(this, "sprites/object/rifle.png", coords, 6);
        middleLayers.put(coords, Rifle);
        break;
      case 7:
        Window = new WindowEntity(this, "sprites/object/window.png", coords, 7);
        middleLayers.put(coords, Window);
        break;
      case 9:
        Ammo = new AmmoEntity(this, "sprites/object/ammobox.png", coords, 9);
        middleLayers.put(coords, Ammo);
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
        grass = new GrassEntity(this, "sprites/lowerlayer/grass.gif", newLocation, 11);
        lowerLayers.put(newLocation, grass);
        chance = getRandomNum(treeLikely);
        if (chance == 1) {
          locs = PreSetGroups.tree(direction, startX, startY);
          for (int a = 0; a < 13; a++) { 
              x = locs[a][0];
              y = locs[a][1];              
              final Coords treeCoords = new Coords(x, y);
              if (a == 4) {
              Tree = new TreeEntity(this, "sprites/object/tree/trunk.png", treeCoords, 1);    
              }
              if (a != 4 && a < 9) {
              Tree = new LeavesEntity(this, "sprites/object/tree/leaves1_1.png", treeCoords, 8);
              }
              if (a == 9) {
              Tree = new LeavesEntity(this, "sprites/object/tree/leaves1_d.png", treeCoords, 8);    
              }
              if (a == 10) {
              Tree = new LeavesEntity(this, "sprites/object/tree/leaves1_u.png", treeCoords, 8);    
              }
              if (a == 11) {
              Tree = new LeavesEntity(this, "sprites/object/tree/leaves1_r.png", treeCoords, 8);    
              }
              if (a == 12) {
              Tree = new LeavesEntity(this, "sprites/object/tree/leaves1_l.png", treeCoords, 8);    
              }
              middleLayers.put(treeCoords, Tree);
          } 
        }
        break;
      case 1:
        gravel = new GravelEntity(this, "sprites/lowerlayer/gravel.gif", newLocation, 12);
        lowerLayers.put(newLocation, gravel);
        chance = getRandomNum(boulderLikely);
        if (chance == 1) {
          Boulder = new BoulderEntity(this, "sprites/object/boulder.png", newLocation, 2);
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
          water = new WaterEntity(this, "sprites/lowerlayer/waterborder.png", waterCoords, 13);
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
          woodFloor = new WoodFloorEntity(this, "sprites/lowerlayer/woodfloor.gif", floorCoords, 14);
          lowerLayers.put(floorCoords, woodFloor);
        }
        locs = PreSetGroups.houseWalls(direction, startX, startY, size);
        for (int a = 0; a < (400); a++) { 
          x = locs[a][0];
          y = locs[a][1];
          if (a > 0 && x == 0 && y == 0) break;
          final Coords logWallCoords = new Coords(x, y);
          LogWall = new LogWallEntity(this, "sprites/object/logwall.gif", logWallCoords, 3);
          middleLayers.put(logWallCoords, LogWall);
        }
        locs = PreSetGroups.houseItems(direction, 3, startX, startY, size);
        for (int a = 0; a < (3); a++) { 
          x = locs[a][0];
          y = locs[a][1];
          if (a > 0 && x == 0 && y == 0) break;
          final Coords itemsCoords = new Coords(x, y);
          Barrel = new BarrelEntity(this, "sprites/object/barrel.png", itemsCoords, 4);
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
      //Check to see if you are clicking on the equipment slots
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

      //What happens if you are clicking on the equipment slots
      if (holdingItem && inEquip) {
        for (Inventory inventory : inventorys) {
            if (inventory.getItemCode() == itemSelection && inventory.equipable() && equipped[equippedSelection] == 0) {
                equipped[equippedSelection] = inventory.getItemCode();
                inventory.removeQuantity(1);
            }
            if (inventory.getItemCode() == itemSelection && inventory.equipable() && equipped[equippedSelection] >= 0) {
                addToInventory(equipped[equippedSelection], 1);
                equipped[equippedSelection] = inventory.getItemCode();
                inventory.removeQuantity(1);
            }
        }
      }
      if (!holdingItem && inEquip) {
          itemSelection = equipped[equippedSelection];
          for (Inventory inventory : inventorys) {
              if (inventory.getItemCode() == itemSelection) {
                  equipped[equippedSelection] = 0;
                  inventory.addQuantity(1);
              }
          }
      }
      if (!inEquip) {
          selectionInteract(x, y);
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
      button = new ButtonEntity(this, "sprites/object/logwall.gif", coords, "LogWall", 20);
      huds.add(button);
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
                  if (lowerLayer == null || lowerLayer.passable()) {
                  if (middleLayer == null || middleLayer.passable()) {
                  if (otherEnemy == null || otherEnemy.passable()) {
                  if (tD < fD) {
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
            black = new BlackEntity(this, "sprites/hud/fullblack.png", location, "black");
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
            black = new BlackEntity(this, "sprites/hud/fullblack.png", location, "black");
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
            black = new BlackEntity(this, "sprites/hud/fullblack.png", location, "black");
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
            black = new BlackEntity(this, "sprites/hud/fullblack.png", location, "black");
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
      double mouseX, mouseY;
      try {
      mouseX = MouseInfo.getPointerInfo().getLocation().x - (Global.xRes /2);
      mouseY = MouseInfo.getPointerInfo().getLocation().y - (Global.yRes /2);
      }
      catch (NullPointerException ex) {
          mouseX = 0;
          mouseY = 0;  
      }
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
          for (int i = 0; i < bullets.size(); i++) {
              BulletEntity bullet = bullets.get(i);
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
  public void setSpawnLimit()
  {
      if (player.getCoords().getX() - Global.xRes / 2 < xLLimit) {
        xLLimit = player.getCoords().getX() - Global.xRes / 2;
      }
      if (player.getCoords().getX() + Global.xRes / 2 > xRLimit) {
        xRLimit = player.getCoords().getX() + Global.xRes / 2;
      }
      if (player.getCoords().getY() - Global.yRes / 2 < yULimit) {
        yULimit = player.getCoords().getY() - Global.yRes / 2;
      }
      if (player.getCoords().getY() + Global.yRes / 2 > yDLimit) {
        yDLimit = player.getCoords().getY() + Global.yRes / 2;
      }
  }
  public Coords spawnZombie(int randomZombie)
  {
   
      Coords location = new Coords(0,0);
      Random r = new Random();
      int y = 0;
      int x = 0;
      switch (randomZombie) {
          case 0:
              y = r.nextInt(yDLimit-yULimit) + yULimit;
              y = Math.round(y / 20)* 20;
              location = new Coords(xLLimit, y);
              break;
          case 1:
              y = r.nextInt(yDLimit-yULimit) + yULimit;
              y = Math.round(y / 20)* 20;
              location = new Coords(xRLimit, y);
              break;
          case 2:
              x = r.nextInt(xRLimit - xLLimit) + xLLimit;
              x = Math.round(x / 20)* 20;
              location = new Coords(x, yULimit);
              break;
          case 3:
              x = r.nextInt(xRLimit - xLLimit) + xLLimit;
              x = Math.round(x / 20)* 20;
              location = new Coords(x, yDLimit);
              break;
      }
      return location;
  }
  public void setPlayerSTR()
  {
      switch (equipped[3]) {
          case 0:
              player.setSTR(10);
              break;
          case 5:
              player.setSTR(25);
              break;
          case 6:
              player.setSTR(100);
              break;
      }
  }
  public boolean getSimBlock (Coords coords, Direction dFromBlock, int itemCode)
  {
      Coords returnCoords = dFromBlock.getCoordsFrom(coords);
      if (middleLayers.containsKey(returnCoords)) {
      MiddleLayer middleLayer = middleLayers.get(returnCoords);
      if (middleLayer.getType() == itemCode) {
          return true;
      }
      }
      if (lowerLayers.containsKey(returnCoords)) {
      LowerLayer lowerLayer = lowerLayers.get(returnCoords);
      if (lowerLayer.getType() == itemCode) {
          return true;
      }
      }
      return false;
  }
  public void addBlack(Coords coords) {
      black = new BlackEntity(this, "sprites/hud/fullblack.png", coords, "fullblack");
      upperLayers.put(coords, black); 
  }
  public void setTimeOfDay(Coords coords, int size) {
      int xLoc = coords.getX();
      int yLoc = coords.getY();
      int y = 0 - size;
      int x = 0;
      for (int i = 0; i<=size*2; i++)
          { 

              x = (int)Math.sqrt((size*size) - y*y);
              int dist = Math.abs(-x)+ x;
              
              
              Coords setCoords = new Coords((x*20) + xLoc,((y*20) + yLoc));
              if (upperLayers.containsKey(setCoords)) {
                  upperLayers.remove(setCoords);
              }
              addBlack(setCoords);
              for (int r = 1; r <= dist; r++)
              {
                  setCoords = new Coords(((x-r)*20)+xLoc,((y*20) + yLoc));
                  if (upperLayers.containsKey(setCoords)) {
                  upperLayers.remove(setCoords);
                  }
                  setCoords = new Coords(((-x+r)*20)+xLoc,((y*20) + yLoc));
                  if (upperLayers.containsKey(setCoords)) {
                  upperLayers.remove(setCoords);
                  }
                  
              }
              setCoords = new Coords((-x*20) + xLoc,((y*20) + yLoc));
              if (upperLayers.containsKey(setCoords)) {
                  upperLayers.remove(setCoords);
              }
              y++;
          }
  }
  public Surrounding setSimBlockImage (Coords coords, int checkCode)
  {
            if (getSimBlock(coords, Direction.UP, checkCode)) {
                if (getSimBlock(coords, Direction.RIGHT, checkCode)) {
                    if (getSimBlock(coords, Direction.DOWN, checkCode)) {
                        if (getSimBlock(coords, Direction.LEFT, checkCode)) {
                           return new Surrounding (0, 0); 
                        }
                        if (getSimBlock(coords, Direction.DOWNLEFT, checkCode)) {
                            if (getSimBlock(coords, Direction.UPLEFT, checkCode)) {
                                return new Surrounding (3, 0);
                            }
                            return new Surrounding (7, 270);
                        }
                        if (getSimBlock(coords, Direction.UPLEFT, checkCode)) {
                            return new Surrounding (8, 270);
                        }
                        return new Surrounding (6, 270);
                    }
                    if (getSimBlock(coords, Direction.LEFT, checkCode)) {
                        if (getSimBlock(coords, Direction.DOWNRIGHT, checkCode)) {
                            if (getSimBlock(coords, Direction.DOWNLEFT, checkCode)) {
                                return new Surrounding (3, 270);
                            }
                            return new Surrounding (7, 180);
                        }
                        if (getSimBlock(coords, Direction.DOWNLEFT, checkCode)) {
                            return new Surrounding (8, 180);
                        }
                        return new Surrounding (6, 180);
                    }
                    if (getSimBlock(coords, Direction.UPLEFT, checkCode)) {                       
                        if (getSimBlock(coords, Direction.DOWNRIGHT, checkCode)) {
                        return new Surrounding (1, 0);
                        }
                        return new Surrounding (10, 270);
                    }
                    if (getSimBlock(coords, Direction.DOWNRIGHT, checkCode)) {
                        return new Surrounding (9, 180);
                    }
                    return new Surrounding (1, 0);
                }
                if (getSimBlock(coords, Direction.DOWN, checkCode)) {
                    if (getSimBlock(coords, Direction.LEFT, checkCode)) {
                        if (getSimBlock(coords, Direction.UPRIGHT, checkCode)) {
                            if (getSimBlock(coords, Direction.DOWNRIGHT, checkCode)) {
                                return new Surrounding (3, 180);
                            }
                            return new Surrounding (7, 90);
                        }
                        if (getSimBlock(coords, Direction.DOWNRIGHT, checkCode)) {
                            return new Surrounding (8, 90);
                        }
                        return new Surrounding (6, 90);
                    }
                    return new Surrounding (2, 0);
                }
                if (getSimBlock(coords, Direction.LEFT, checkCode)) {
                    if (getSimBlock(coords, Direction.DOWNLEFT, checkCode)) {
                        if (getSimBlock(coords, Direction.UPRIGHT, checkCode)) {
                            return new Surrounding (1, 270);
                        }
                        return new Surrounding (10, 180);
                    }
                    if (getSimBlock(coords, Direction.UPRIGHT, checkCode)) {
                        return new Surrounding (9, 90);
                    }
                    return new Surrounding (1, 270);
                }
                return new Surrounding (4,0);
            }
            if (getSimBlock(coords, Direction.RIGHT, checkCode)) {
                if (getSimBlock(coords, Direction.DOWN, checkCode)) {
                    if (getSimBlock(coords, Direction.LEFT, checkCode)) {
                        if (getSimBlock(coords, Direction.UPLEFT, checkCode)) {
                            if (getSimBlock(coords, Direction.UPRIGHT, checkCode)) {
                                return new Surrounding (3, 90);
                            }
                            return new Surrounding (7,0);
                        }
                        if (getSimBlock(coords, Direction.UPRIGHT, checkCode)) {
                            return new Surrounding (8,0);
                        }
                        return new Surrounding (6, 0);
                    }
                    if (getSimBlock(coords, Direction.UPRIGHT, checkCode)) {
                        if (getSimBlock(coords, Direction.DOWNLEFT, checkCode)) {
                            return new Surrounding (1, 90);
                        }
                        return new Surrounding (10, 0);
                    }
                    if (getSimBlock(coords, Direction.DOWNLEFT, checkCode)) {
                        return new Surrounding (9, 270);
                    }
                    return new Surrounding (1, 90);
                }
                if (getSimBlock(coords, Direction.LEFT, checkCode)) {                   
                    return new Surrounding (2, 90);
                }
                return new Surrounding (4, 90);
            }
            if (getSimBlock(coords, Direction.DOWN, checkCode)) {
                if (getSimBlock(coords, Direction.LEFT, checkCode)) {
                    if (getSimBlock(coords, Direction.UPLEFT, checkCode)) {
                        if (getSimBlock(coords, Direction.DOWNRIGHT, checkCode)) {
                            return new Surrounding (1, 180);
                        }
                        return new Surrounding (9, 0);
                    }
                    if (getSimBlock(coords, Direction.DOWNRIGHT, checkCode)) {
                        return new Surrounding (10, 90);
                    }
                    return new Surrounding (1, 180);
                    
                }
                return new Surrounding (4, 180);
            }
            if (getSimBlock(coords, Direction.LEFT, checkCode)) {
                return new Surrounding (4,270);
            }
            return new Surrounding (5,0);
  }
  public void dayTransition() {
            if (dayTime == true) {
                darkness = darkness - .03f;
                if (darkness < 0) {darkness = 0;}
            }
            else {
                darkness = darkness + .03f; 
                if (darkness > .8f) {darkness = .8f;}
            }
            
        timeOfDay = timeOfDay + daySpeed;
        if (timeOfDay > 40) {timeOfDay = 40; daySpeed = -daySpeed; dayTrans = false;darkness = 0;}
        if (timeOfDay < 10) {timeOfDay = 10; daySpeed = -daySpeed; dayTrans = false;darkness = .8f;}    
  
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

      //Do Things Loop
      if (loopTime == 8) {
        // Moves applicable enemy within its speed compared to loop time
        moveEnemy(g);
        loopTime = 0;
        
        //Day transitions
        if (dayTrans == true) {
           dayTransition();      
        }
        else {
            nextDay++;
            System.err.println(nextDay);
            if (nextDay > 10) {dayTrans = !dayTrans; dayTime = !dayTime; nextDay = 0;}
        }
      }
      
      if (loopTime == 1) {
          hurtFlash = false;
      }
      setSpawnLimit();
      int randomZombie = (int) (Math.random() * zombieChance);
      if (randomZombie < 4) {
          Coords location = spawnZombie(randomZombie);
          zombie = new ZombieEntity(this, "sprites/npc/zombie1.png", location, "zombie", Direction.UP);
          enemyLayers.put(location, zombie);
      }
      
      checkButtonPushed();
      setPlayerSTR();
   
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
          addBlack(coords);
          if (!lowerLayers.containsKey(coords)) {
            addFloor(x, y);
          }
          if(lowerLayers.containsKey(coords)) {
          /*if(lowerLayers.get(coords).getType() == 13 && (loopTime % 5) == 0) {
                lowerLayers.get(coords).changeFrame(lowerLayers.get(coords).nextFrame());
            }    CODE FOR MOVING WATER*/
          if(lowerLayers.get(coords).getType() == 11 || lowerLayers.get(coords).getType() == 13) {
             Surrounding surround = setSimBlockImage(coords, lowerLayers.get(coords).getType());
             lowerLayers.get(coords).changeFrame(surround.getFrame());
             lowerLayers.get(coords).rotDraw(g, screenOffset, surround.getRotate());
          }
          else{
          lowerLayers.get(coords).draw(g, screenOffset);
          }
          }
          if (middleLayers.containsKey(coords)) {
              if (middleLayers.get(coords).getType() == 3) {
              Surrounding surround = setSimBlockImage(coords, 3);
              if (surround.getFrame() > 5) {surround.addRotate(90);}
              middleLayers.get(coords).changeFrame(surround.getFrame());
              middleLayers.get(coords).rotDraw(g, screenOffset, surround.getRotate());
              }
              else {
                middleLayers.get(coords).draw(g, screenOffset);
              }
          }  
          if (upperLayers.containsKey(coords) && "fullblack".equals(upperLayers.get(coords).getType()) && darkness > 0){
                upperLayers.get(coords).fadeRotDraw(g, screenOffset, 0, darkness);   
            }
        }
      }
      //Remove black in accordance with time in transition
      setTimeOfDay(player.getCoords(), timeOfDay);
      
      //Check for Line of Sight
      checkLoS(); 
      //Set Line of Sight Darkness if to low     
      float LoSDarkness = darkness;
      if (LoSDarkness < .5f) {LoSDarkness = .5f;}
      
      //Redraws upperlayer and enemys according to time
      for (int x = xStart; x <= xStop; x += xStep) {
        for (int y = yStart; y <= yStop; y += yStep) {
            final Coords coords = new Coords(x, y);
            if (enemyLayers.containsKey(coords) && !upperLayers.containsKey(coords)) {
                enemyLayers.get(coords).rotDraw(g, screenOffset, enemyLayers.get(coords).getRotation());          
            }
            if (upperLayers.containsKey(coords) && "black".equals(upperLayers.get(coords).getType())) {
                upperLayers.get(coords).fadeRotDraw(g, screenOffset, 0, LoSDarkness);
                upperLayers.remove(coords);
            }
            else if (upperLayers.containsKey(coords) && "fullblack".equals(upperLayers.get(coords).getType())){
                upperLayers.get(coords).fadeRotDraw(g, screenOffset, 0, darkness);
                upperLayers.remove(coords);    
            }
        }
      }
           
      //Draws Player
      player.rotDraw(g, screenOffset, (int)getPlayerDirection());

      //Draw Health Bar
      for(int i = 0; i <  huds.size(); i++) {
          Hud hud = huds.get(i);
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
          if ("use".equals(hud.getType())) {
            use.rotDraw(g, screenOffset, (int)getPlayerDirection());
            removeHudList.add(hud);
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
      //Draw bullets
      for (int i = 0; i < bullets.size(); i++) {
           BulletEntity bullet = bullets.get(i);
           bullet.rotDraw(g, screenOffset, bullet.getRot());
      }
      int time = 0;
      while (time < bulletSpeed) {
      bulletAction(screenOffset);
      time++;
      }
      if (bulletsToRemove.size() > 0) {
      bullets.removeAll(bulletsToRemove);
      bulletsToRemove.clear();
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
            if (col > 130) {
                col = 30;
                row ++;
            }
            drawX = (bagOverlayX - (col));
            drawY = (bagOverlayY - 5) - (30 * row);

            inventory.draw(g, new Coords(drawX, drawY));
            String quantity = String.valueOf(inventory.getQuantity());
            g.drawString(quantity, -drawX, -drawY);
            inventory.setXY(drawX, drawY);
          }
            
            if (equipped[0] == inventory.getItemCode()) {
                  inventory.draw(g, new Coords(-(Global.xRes - 78), -120));
            }
            if (equipped[1] == inventory.itemCode) {
                  inventory.draw(g, new Coords(-(Global.xRes - 24), -75));
            }
            if (equipped[2] == inventory.itemCode) {
                  inventory.draw(g, new Coords(-(Global.xRes - 55), -5));
            }
            if (equipped[3] == inventory.itemCode) {
                  inventory.draw(g, new Coords(-(Global.xRes - 145), -39));
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
          for (int i = 0; i < huds.size(); i++) {
          Hud hud = huds.get(i);
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
      //What is to be removed gets removed
      for (Hud hudremove : removeHudList){
        huds.remove(hudremove);
      }
      //Resets what is to be removed
      removeList.clear();
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

package survive;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
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

  int totalRandom = 3;
  int[] randomChance = new int[totalRandom];
  int[] randomDefault = new int[totalRandom];

  private LowerLayer grass;
  private LowerLayer gravel;
  private LowerLayer water;

  private MiddleLayer tree;
  private MiddleLayer boulder;
  private MiddleLayer LogWall;
  private MiddleLayer Log;
  private MiddleLayer Stone;

  private EnemyLayer zombie;

  private Hud player;
  private Hud structureButton;
  private Hud toolButton;
  private Hud consumableButton;
  private Hud decorativeButton;

  private Inventory inventoryMan;
  private Inventory log;
  private Inventory stone;
  private Inventory logWall;

  private Direction direction = Direction.UP;
  private double movementSpeed = 20;
  private String message = "";

  private boolean waitingForKeyPress = true;
  private boolean leftPressed = false;
  private boolean rightPressed = false;
  private boolean upPressed = false;
  private boolean downPressed = false;
  private boolean spacePressed = false;
  private boolean iPressed = false;
  private boolean cPressed = false;

  private int treeLikely = 10;
  private int boulderLikely = 20;

  private int itemSelection = 0;
  private int zombieChance = 100;
  private boolean gameRunning = true;
  private boolean inventoryOpen = false;
  private boolean craftingOpen = false;
  private boolean craftingStructure = false;
  private boolean holdingItem = false;

  private boolean logWallReceipe = false;

  private Map<Coords, LowerLayer> lowerLayers = new HashMap<Coords, LowerLayer>();
  private Map<Coords, MiddleLayer> middleLayers = new HashMap<Coords, MiddleLayer>();
  private List<Hud> huds = new ArrayList<Hud>();
  private List<Inventory> inventorys = new ArrayList<Inventory>();
  private Map<Coords, EnemyLayer> enemyLayers = new HashMap<Coords, EnemyLayer>();
  private ArrayList removeList = new ArrayList();

  public Survive()
  {
    JFrame container = new JFrame("Survive");

    JPanel panel = (JPanel) container.getContentPane();
    panel.setPreferredSize(new Dimension(Global.xRes, Global.yRes));
    panel.setLayout(null);

    setBounds(0, 0, Global.xRes, Global.yRes);
    panel.add(this);

    setIgnoreRepaint(true);

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
    randomChance[2] = 5;
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
    Global.mapXLoc = 0;
    Global.mapYLoc = 0;
    randomDefault[0] = 50;
    randomDefault[1] = 25;
    randomDefault[2] = 0;

    randomChance[0] = randomDefault[0];    //Grass
    randomChance[1] = randomDefault[1];    //Gravel
    randomChance[2] = randomDefault[2];    //Pond
    //Add player entity
    player = new PlayerEntity(this, "sprites/PlayerN.png", new Coords(0, 0), "player", 20);
    huds.add(player);
    //Add Crafting buttons
    structureButton = new ButtonEntity(this, "sprites/StructureButton.jpg", new Coords(0, Global.yRes - 100), "Structure", 100);
    huds.add(structureButton);

    toolButton = new ButtonEntity(this, "sprites/ToolButton.jpg", new Coords(110, Global.yRes - 100), "Tool", 100);
    huds.add(toolButton);

    consumableButton = new ButtonEntity(this, "sprites/ConsumableButton.jpg", new Coords(220, Global.yRes - 100), "Consumable", 100);
    huds.add(consumableButton);

    decorativeButton = new ButtonEntity(this, "sprites/DecorativeButton.jpg", new Coords(330, Global.yRes - 100), "Decorative", 100);
    huds.add(decorativeButton);

    //Add starting grass
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        final Coords coords = new Coords(x, y);
        grass = new GrassEntity(this, "sprites/grass.gif", coords, "grass");
        lowerLayers.put(coords, grass);
      }
    }
    addFloor(player.getCoords().getX(), player.getCoords().getX());
  }

  //Set cursor type
  public void getCursorType(int type)
  {
    switch (type) {
      case 1:
        setCursor(
                new Cursor(Cursor.DEFAULT_CURSOR));
        break;
      case 2:
        setCursor(
                new Cursor(Cursor.MOVE_CURSOR));
        break;
    }
  }

  public void interact()
  {
    final Coords interactCoords = direction.getCoordsFrom(player.getCoords());

    {
      final MiddleLayer middleLayer = middleLayers.get(interactCoords);
      if (middleLayer != null) {
        middleLayer.interact();
      }
    }

    {
      final EnemyLayer enemyLayer = enemyLayers.get(interactCoords);
      if (enemyLayer != null) {
        enemyLayer.interact();
      }
    }
  }

  public String mouseInteract(int x, int y)
  {
    String type = "none";
    //Check if button is there
    for (Hud hud : huds) {
      if (hud instanceof PlayerEntity) {
        continue;
      }
      int xLimit = (hud.getCoords().getX() + hud.getImageSize());
      int yLimit = (hud.getCoords().getY() + hud.getImageSize());

      if ((x >= hud.getCoords().getX()) && (y >= hud.getCoords().getY()) && (x <= xLimit) && (y <= yLimit)) {
        type = hud.getType();
      }
    }
    //Check if inventory is there
    for (Inventory inventory : inventorys) {
      int xLimit = inventory.getX() + 20;
      int yLimit = inventory.getY() + 20;
      if (x >= inventory.getX() && y >= inventory.getY() && x <= xLimit && y <= yLimit) {
        type = String.valueOf(inventory.getItemCode());
      }
    }
    return type;
  }

  public void removeMiddleLayer(MiddleLayer object)
  {
    removeList.add(object);
  }

  public void removeEnemyLayer(EnemyLayer object)
  {
    removeList.add(object);
  }

  public void removeButton(Hud object)
  {
    removeList.add(object);
  }

  public void addToInventory(int itemCode, int quantity)
  {
    //Add entities if none are available
    if (inventorys.isEmpty()) {
      log = new Inventory("sprites/log.png", 1, 0);
      inventorys.add(log);
      stone = new Inventory("sprites/stone.png", 2, 0);
      inventorys.add(stone);
      logWall = new Inventory("sprites/LogWall.gif", 3, 0);
      inventorys.add(logWall);
      inventoryMan = new Inventory("sprites/inventoryMan.png", 9999, 0);
      inventorys.add(inventoryMan);
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

  public void setItemDown(final Coords coords)
  {
    switch (itemSelection) {
      case 1:
        Log = new LogEntity(this, "sprites/log.png", coords, "log");
        Log.setCoords(coords);
        middleLayers.put(coords, Log);
        removeFromInventory(1, 1);
        break;
      case 2:
        Stone = new StoneEntity(this, "sprites/stone.png", coords, "stone");
        Stone.setCoords(coords);
        middleLayers.put(coords, Stone);
        removeFromInventory(2, 1);
        break;
      case 3:
        LogWall = new LogWallEntity(this, "sprites/logWall.gif", coords, "logWall");
        LogWall.setCoords(coords);
        middleLayers.put(coords, LogWall);
        removeFromInventory(3, 1);
        break;

    }

  }

  public void addFloor(int x, int y)
  {
    final Coords newLocation = new Coords(x, y);

    int chance = 0;
    switch (getRandomGround()) {
      case 0:
        grass = new GrassEntity(this, "sprites/grass.gif", newLocation, "grass");
        lowerLayers.put(newLocation, grass);

        chance = getRandomNum(treeLikely);
        if (chance == 1) {
          tree = new TreeEntity(this, "sprites/tree.png", newLocation, "tree");
          middleLayers.put(newLocation, tree);
        }
        break;
      case 1:
        gravel = new GravelEntity(this, "sprites/gravel.gif", newLocation, "gravel");
        lowerLayers.put(newLocation, gravel);

        chance = getRandomNum(boulderLikely);
        if (chance == 1) {
          boulder = new BoulderEntity(this, "sprites/boulder.png", newLocation, "boulder");
          middleLayers.put(newLocation, boulder);
        }
        break;
      case 2:
        int size = (int) (Math.random() * 8);
        int[][] locs = PreSetGroups.pond(direction, x, y, size);
        for (int a = 0; a < (400); a++) {
          x = locs[a][0];
          y = locs[a][1];

          final Coords waterCoords = new Coords(x, y);
          water = new WaterEntity(this, "sprites/water.gif", waterCoords, "water");
          lowerLayers.put(waterCoords, water);
          if (a > 0 && x == 0 && y == 0) {
            a = 400;
          }
        }
        break;
    }
  }

  public int getRandomGround()
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

  public void checkButtonPushed()
  {
    if (cPressed) {
      craftingOpen = !craftingOpen;
      if (craftingStructure = true) {
        craftingStructure = false;
      }
    }
    if (iPressed) {
      inventoryOpen = !inventoryOpen;
    }
    if (leftPressed) {
      direction = Direction.LEFT;

      movePlayer(direction);

      for (int i = 0; i < 1; i++) {
        Hud hud = huds.get(i);
        hud.changeFrame(2);
      }
      checkCollisionObject(direction);
    }
    if (rightPressed) {
      direction = Direction.RIGHT;

      movePlayer(direction);
      for (int i = 0; i < 1; i++) {
        Hud hud = huds.get(i);
        hud.changeFrame(3);
      }
      checkCollisionObject(direction);
    }
    if (upPressed) {
      direction = Direction.UP;

      movePlayer(direction);
      for (int i = 0; i < 1; i++) {
        Hud hud = huds.get(i);
        hud.changeFrame(0);
      }
      checkCollisionObject(direction);
    }
    if (downPressed) {
      direction = Direction.DOWN;

      movePlayer(direction);
      for (int i = 0; i < 1; i++) {
        Hud hud = huds.get(i);
        hud.changeFrame(1);
      }
      checkCollisionObject(direction);
    }
    if (spacePressed) {
      interact();
    }
  }

  public void drawInventory()
  {
    //Draw Inventory screen
    Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
    g.setColor(Color.GRAY);
    g.fill3DRect(Global.xRes - (Global.xRes / 5), 0, Global.yRes, Global.xRes, true);
    g.setColor(Color.BLACK);
    g.drawLine(Global.xRes - (Global.xRes / 5), (Global.yRes / 2), Global.xRes, (Global.yRes / 2));
    g.dispose();

  }

  //Removes all craftable item buttons
  public void removeAllButtons()
  {
    for (int i = 5; i < huds.size(); i++) {
      Hud hud = huds.get(i);
      removeButton(hud);
    }
  }

  public void findAvailReceipe()
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
      structureButton = new ButtonEntity(this, "sprites/logWall.gif", coords, "LogWall", 20);
      huds.add(structureButton);
    }
  }

  public long moveEnemy(long loopTime)
  {
    for (EnemyLayer enemyLayer : enemyLayers.values()) {
      if (10 == loopTime) {

        // Too complicated for me to fix now. Simple, random walk here instead.
        final Direction enemyDirection = Direction.getRandom();
        enemyLayer.changeDirection(enemyDirection);
        final Coords attemptedMove = enemyDirection.getCoordsFrom(enemyLayer.getCoords());

        // Allow the enemy to move to the attempted location if everything there is passable.
        final LowerLayer lower = lowerLayers.get(attemptedMove);
        final MiddleLayer middle = middleLayers.get(attemptedMove);
        final EnemyLayer otherEnemy = enemyLayers.get(attemptedMove);
        if ((lower == null || lower.passable())
                && (middle == null || middle.passable())
                && otherEnemy == null) {

          enemyLayer.setCoords(attemptedMove);
        }

      }
    }
    return loopTime;
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
      loopTime = moveEnemy(loopTime);

      //Chance to add zombie
      int randomZombie = (int) (Math.random() * zombieChance);
      if (randomZombie == 1) {
        final Coords location = new Coords(
                player.getCoords().getX() - (Global.xRes / 2),
                player.getCoords().getY() + new Random().nextInt(Global.yRes) - Global.yRes / 2
        );
        zombie = new ZombieEntity(this, "sprites/ZombieN1.png", location, "zombie", Direction.UP);
        enemyLayers.put(location, zombie);
      }

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
      for (int x = xStart; x < xStop; x += xStep) {
        for (int y = yStart; y < yStop; y += yStep) {
          final Coords coords = new Coords(x, y);

          if (!lowerLayers.containsKey(coords)) {
            addFloor(x, y);
          }
          lowerLayers.get(coords).draw(g, screenOffset);
          //Draw all Enemys
          if (enemyLayers.containsKey(coords)) {
            enemyLayers.get(coords).draw(g, screenOffset);
          }
          //Draw all Objects
          if (middleLayers.containsKey(coords)) {
            middleLayers.get(coords).draw(g, screenOffset);
          }
        }
      }
      //What is to be removed gets removed
      for (final Object object : removeList) {
        middleLayers.remove(removeList);
        huds.remove(removeList);
        enemyLayers.remove(removeList);
      }

      //Resets what is to be removed
      removeList.clear();

      //Draws Player
      player.draw(g, screenOffset);

      //When Crafting is open
      if (craftingOpen == true) {
        for (int i = 1; i <= 4; i++) {
          Hud hud = huds.get(i);
          hud.draw(g, new Coords(0, 0));
        }
      }
      //When Crafting for structures is open
      if (craftingStructure == true) {
        int x = 0;
        removeAllButtons();
        findAvailReceipe();
        for (Hud hud : huds) {
          if (hud.getType() == "LogWall" && logWallReceipe == true) {
            hud.draw(g, new Coords(0, 0));
            x = x + 25;
          }
        }
      }
      //Change holding status if nothing is there
      if (holdingItem == true && checkForMoreItem(itemSelection) == false) {
        itemSelection = 0;
        holdingItem = false;
      }
      //Change cursor for holding item
      if (holdingItem == true) {
        getCursorType(2);
      }
      //Change cursor for not holding item
      if (holdingItem == false) {
        getCursorType(1);
      }

      if (waitingForKeyPress) {
        g.setColor(Color.white);
        g.drawString(message, (Global.xRes - g.getFontMetrics().stringWidth(message)) / 2, 250);
        g.drawString("Press any key", (Global.xRes - g.getFontMetrics().stringWidth("Press space to continue")) / 2, (Global.yRes / 2));
      }
      //Draws inventory background and items
      if (inventoryOpen == true) {
        drawInventory();
        int col = -20;
        int row = 1;
        for (Inventory inventory : inventorys) {
          if (inventory.getItemCode() == 9999) {
            inventory.changeX(Global.xRes - (Global.xRes / 5) + 20);
            inventory.changeY(20);
            inventory.draw(g);

          }

          if (inventory.getQuantity() > 0) {
            col = col + 25;
            inventory.changeX(Global.xRes - (Global.xRes / 5) + (col));
            inventory.changeY((Global.yRes / 2) + (15 * row));

            inventory.draw(g);
            String quantity = String.valueOf(inventory.getQuantity());
            g.drawString(quantity, inventory.getX(), inventory.getY());
          }
        }

      }
      for (int i = 0; i < totalRandom; i++) {
        if (randomChance[i] > randomDefault[i] + 20) {
          randomChance[i] = randomDefault[i] + 10;
        }
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

    public void mouseClicked(MouseEvent e)
    {
      //Get what is clicked
      String what = mouseInteract(e.getX(), e.getY());

      if (craftingOpen == true && !"none".equals(what)) {
        craftingOpen = false;
        craftingStructure = true;
      }
      if (craftingStructure = true) {
        switch (what) {
          case "LogWall":
            addToInventory(3, 1);
            removeFromInventory(1, 4);
            break;
        }
      }
      int whatNumber = 0;
      try {
        whatNumber = Integer.parseInt(what);
      } catch (NumberFormatException a) {
        System.err.println("Nothing there");
      }
      if (whatNumber > 0 && holdingItem == false) {
        itemSelection = whatNumber;
        holdingItem = true;
      }
      if ("none".equals(what) && holdingItem == true) {
        int x = Math.round(e.getX() / 20) * 20;
        int y = Math.round(e.getY() / 20) * 20;
        setItemDown(new Coords(x, y));
      }
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
        case 73:
          iPressed = true;
          break;
      }
    }

    public void keyReleased(KeyEvent e)
    {
      if (waitingForKeyPress) {
        return;
      }
      switch (e.getKeyCode()) {

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
        case 73:
          iPressed = false;
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

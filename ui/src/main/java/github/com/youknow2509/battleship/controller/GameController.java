package github.com.youknow2509.battleship.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import github.com.youknow2509.battleship.consts.Consts;
import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.repo.BodyReq;
import github.com.youknow2509.battleship.model.repo.ResponseAI;
import github.com.youknow2509.battleship.model.ship.Ship;
import github.com.youknow2509.battleship.model.ship.ShipType;
import github.com.youknow2509.battleship.utils.gson.BodyReqAdapter;
import github.com.youknow2509.battleship.utils.gson.ResponseAIAdapter;
import github.com.youknow2509.battleship.utils.image.ImageViewUtils;
import github.com.youknow2509.battleship.utils.utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import okhttp3.*;
import okhttp3.internal.Util;

import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameController {
    @FXML
    private VBox root;
    @FXML
    private GridPane playerGrid, botGrid;
    @FXML
    private TextField playerTitle, botTitle;

    @FXML
    private ImageView battleship_player, aircraft_carrier_player, cruiser_player, submarine_player, destroyer_player;
    @FXML
    private ImageView battleship_bot, aircraft_carrier_bot, cruiser_bot, submarine_bot, destroyer_bot;
    @FXML
    private TextField imv_turn;
    //
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    //
    private final Board playerBoard, botBoard;
    private int playerTurns = 0; // 0 = player, 1 = bot
    private int[][] botBoardData;
    private final Map<ShipType, ImageView> playerShips = new HashMap<>(), botShips = new HashMap<>();

    public GameController(Board playerBoard, Board botBoard) {
        this.playerBoard = playerBoard;
        this.botBoard = botBoard;
        utils.printResult(playerBoard);
    }

    @FXML
    public void initialize() {
        initData();
        //
        botGrid.setDisable(true);
        setupShipMappings();
        renderGrid(botGrid, botBoard);
        setupPlayerClickEvents();
    }

    // init data
    private void initData() {
        //
        initBotBoardData();
        // init okhttp client
        client.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    // init botBoardData
    public void initBotBoardData() {
        // init matrix 10 x 10 full 0
        botBoardData = new int[botBoard.getRows()][botBoard.getColumns()];
        for (int i = 0; i < botBoard.getRows(); i++) {
            for (int j = 0; j < botBoard.getColumns(); j++) {
                botBoardData[i][j] = 0;
            }
        }
    }

    // handle click menu button - handle popup menu
    public void handleClickMenu(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Consts.XML_RESOURCE_GAME_MENU));
            Parent root = loader.load();

            // Get the controller instance from the FXMLLoader
            GameMenuController controller = loader.getController();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Create a new stage and set it to the controller
            Stage newStage = new Stage();
            controller.initialize(currentStage);

            // Center the stage on the screen
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            newStage.setX((screenBounds.getWidth() - newStage.getWidth()) / 2);
            newStage.setY((screenBounds.getHeight() - newStage.getHeight()) / 2);

            newStage.setTitle("Menu");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // check winner
    private void checkWinner(int playerTurns) {
        switch (playerTurns) {
            case 0:
                if (playerBoard.isAllShipsSunk()) {
                    handleWinner(0);
                }
                break;
            case 1:
                if (botBoard.isAllShipsSunk()) {
                    handleWinner(1);
                }
                break;
            default:
                break;
        }
    }

    // Handle the winner of the game
    private void handleWinner(int player) {
        // Disable both grids when the game ends
        playerGrid.setDisable(true);
        botGrid.setDisable(true);
        botTitle.setOpacity(0.0);

        // Set the winner and loser titles
        if (player == 0) {
            playerTitle.setText("You Win!");
            playerTitle.setStyle("-fx-background-color: #00ff00");
            botGrid.setOpacity(0.2);
        } else {
            playerTitle.setText("You Lose!");
            playerTitle.setStyle("-fx-background-color: #ff0000");
            playerGrid.setOpacity(0.2);
        }

        // Add animation for the title change
        utils.animateTitle(playerTitle);
    }

    // Set image ship for player and bot
    private void setupShipMappings() {
        playerShips.put(ShipType.BATTLESHIP, battleship_player);
        playerShips.put(ShipType.CARRIER, aircraft_carrier_player);
        playerShips.put(ShipType.CRUISER, cruiser_player);
        playerShips.put(ShipType.SUBMARINE, submarine_player);
        playerShips.put(ShipType.DESTROYER, destroyer_player);

        botShips.put(ShipType.BATTLESHIP, battleship_bot);
        botShips.put(ShipType.CARRIER, aircraft_carrier_bot);
        botShips.put(ShipType.CRUISER, cruiser_bot);
        botShips.put(ShipType.SUBMARINE, submarine_bot);
        botShips.put(ShipType.DESTROYER, destroyer_bot);
    }

    // listen player click event in grid
    private void setupPlayerClickEvents() {
        playerGrid.getChildren().forEach(node -> node.setOnMouseClicked(event -> {
            if (playerTurns == 1) return;

            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);
            handlePlayerClick(row, col);
            node.setDisable(true);
        }));
    }

    // handle player click event
    private void handlePlayerClick(int row, int col) {
        Cell cell = playerBoard.getCell(row, col);
        if (cell.isHasShip()) {
            handleShipHit(playerGrid, cell, 0);
        } else {
            handleMiss(playerGrid, cell);
            botTurn();
        }
    }

    // handle bot choose cell to hit
    private void botTurn() {
        // Chuẩn bị dữ liệu yêu cầu
        List<Integer> listShipNotSunk = utils.getListShipNotSunk(playerBoard);
        int[][] boardData = this.botBoardData;
        //
        Gson gsonReq = new GsonBuilder()
                .registerTypeAdapter(BodyReq.class, new BodyReqAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Gson gsonRespon = new GsonBuilder()
                .registerTypeAdapter(ResponseAI.class, new ResponseAIAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        //
        BodyReq data = new BodyReq(
                utils.convertToList(boardData),
                listShipNotSunk
        );
        String json = gsonReq.toJson(data);
        System.out.println("Data send to AI: " + json);

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://127.0.0.1:5000/get_shot")
                .post(body)
                .build();


        new Thread(() -> {
            // Sleep ngẫu nhiên từ 2 đến 4 giây (2000 - 4000 ms)
            try {
                int delay = 1000 + new Random().nextInt(2001); // random từ 0 đến 2000
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Gọi API
            int x, y;
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String resJson = response.body().string();
                    ResponseAI move = gsonRespon.fromJson(resJson, ResponseAI.class);
                    x = move.getOptimal_shot().get(0);
                    y = move.getOptimal_shot().get(1);
                } else {
                    // Nếu API không thành công, chọn cell random
                    Cell cell = getRandomCell();
                    x = cell.getPosition().getX();
                    y = cell.getPosition().getY();
                }
            } catch (IOException e) {
                // Xử lý lỗi khi gọi API và chọn cell random
                Platform.runLater(() -> {
                    e.printStackTrace();
                    System.out.println("⚠️ Lỗi gọi API bot: " + e.getMessage());
                });
                // Nếu có lỗi, tiếp tục chọn cell random
                Cell cell = getRandomCell();
                x = cell.getPosition().getX();
                y = cell.getPosition().getY();
            }

            final int finalX = x;
            final int finalY = y;
            Platform.runLater(() -> {
                Cell cell = botBoard.getCell(finalX, finalY);
                if (cell.isHasShip()) {
                    handleShipHit(botGrid, cell, 1);
                    botTurn(); // bot tiếp tục nếu trúng
                } else {
                    botBoardData[finalX][finalY] = -1; // đánh trượt
                    handleMiss(botGrid, cell);
                    playerTurns = 0; // chuyển lượt cho người chơi
                }
            });
        }).start();
    }

    // bot random cell
    private Cell getRandomCell() {
        do {
            int row = (int) (Math.random() * botBoard.getRows());
            int col = (int) (Math.random() * botBoard.getColumns());
            Cell cell = botBoard.getCell(row, col);
            if (!cell.isHit()) {
                return cell;
            }
        } while (true);
    }

    // handle when ship hit for user or bot
    private void handleShipHit(GridPane grid, Cell cell, int player)     {
        StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
        if (pane == null) return;

        new ImageViewUtils().setImageView(pane, Consts.PATH_IMAGE_DAME_HIT, Consts.SIZE_CELL, Consts.SIZE_CELL);
        cell.setHit(true);

        Ship ship = cell.getShipInCell();
        ship.setHitCount(ship.getHitCount() + 1);
        botBoardData
                [cell.getPosition().getX()]
                [cell.getPosition().getY()] = 1; // hit
        if (ship.isSunk()) {
            pane.getChildren().clear();
            // Show ship in grid
            ship.getCells().forEach(c -> c.getShipInCell().showShipInGridPane(grid));
            // opacity image hip in button grid
            setShipOpacity(ship.getShipType(), player);
            // create data botBoardData 2 is sunk
            for (int i = 0; i < ship.getCells().size(); i++) {
                botBoardData
                        [ship.getCells().get(i).getPosition().getX()]
                        [ship.getCells().get(i).getPosition().getY()] = 2; // sunk
            }
            // check winner or loser
            checkWinner(player);
        }
    }

    // Change turn and handle view in grid when miss ship
    private void handleMiss(GridPane grid, Cell cell) {
        playerTurns = playerTurns == 0 ? 1 : 0;
        StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
        if (pane != null) {
            new ImageViewUtils().setImageView(pane, Consts.PATH_IMAGE_DAME_MISS, Consts.SIZE_CELL, Consts.SIZE_CELL);
        }
        String turn = playerTurns == 0 ? "YOUR TURN" : "BOT TURN";
        imv_turn.setText(turn);
    }

    // Set opacity for ship when it's sunk
    private void setShipOpacity(ShipType type, int player) {
        ImageView imageView = player == 1 ? botShips.get(type) : playerShips.get(type);
        if (imageView != null) imageView.setOpacity(0.5);
    }

    // render grid with ship - for bot
    private void renderGrid(GridPane grid, Board board) {
        board.getCells().stream().filter(Cell::isHasShip).forEach(cell -> {
            StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
            if (pane != null)
                pane.getChildren().add(new Rectangle(Consts.SIZE_CELL, Consts.SIZE_CELL, Color.RED));
        });
    }

    // get stack pane in grid
    private StackPane getStackPane(GridPane grid, int row, int col) {
        return grid.getChildren().stream()
                .filter(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                .map(node -> (StackPane) node).findFirst().orElse(null);
    }

    // help show data send to ai
    public void showDataReq(int[][] placeShip, List<Integer> getListShipNotSunk) {
        System.out.println("Place ship: ");
        for (int i = 0; i < placeShip.length; i++) {
            for (int j = 0; j < placeShip[i].length; j++) {
                System.out.print(placeShip[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("List ship not sunk: " + getListShipNotSunk);
        for (int i = 0; i < getListShipNotSunk.size(); i++) {
            System.out.println("Ship " + getListShipNotSunk.get(i) + " not sunk");
        }
    }
}

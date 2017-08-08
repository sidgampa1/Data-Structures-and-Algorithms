package editor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Iterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A JavaFX application that displays the letter the user has typed most recently in the center of
 * the window. Pressing the up and down arrows causes the font size to increase and decrease,
 * respectively.
 */
public class Editor extends Application {
    public int WINDOW_WIDTH = 500;
    public int WINDOW_HEIGHT = 500;
    public double scrollWidth;
    public double cursorPosX;
    public double cursorPosY;
    public ScrollBar scrollBar;
    public Rectangle cursor;
    public LinkedListDeque<Text> display = new LinkedListDeque();
    public ArrayDeque<Text> lines = new ArrayDeque();
    public Text currentText;
    public Group baseRoot;
    public Text toAdd;
    public FileOps file;
    public int fontSize = 12;
    public double textWidth;
    public double textHeight;
    public String fontName = "Verdana";
    public boolean newLine = false;
    public static final int STARTING_TEXT_POSITION_X = 250;
    public static final int STARTING_TEXT_POSITION_Y = 250;

    //////////// UTILITIES /////////////

    public void setCurrentText() {
        try {
            currentText = display.currentNode();
        }
        catch (NullPointerException e) {
                currentText = new Text("");
        }
    }

    public double getWidth() {
        return currentText.getLayoutBounds().getWidth();
    }

    public double getHeight(){
        return currentText.getLayoutBounds().getHeight();
    }

    public void setCursorPos() {
        cursor.setX(cursorPosX);
        cursor.setY(cursorPosY);
    }

    public void addText(String typed){

        toAdd = new Text(cursorPosX, cursorPosY, typed);

        display.add(toAdd);
        setCurrentText();
        baseRoot.getChildren().add(toAdd);

        //READJUST CHARS AFTER INSERTION
        //renderDisplay();

    }

    public void delete(){
        Text removed = display.remove();
        if (display.size() == 0) {
            baseRoot.getChildren().remove(removed);
            cursorPosX = 5;
            cursorPosY = 0;
            setCursorPos();
            return;
        }
        setCurrentText();
        if (!currentText.equals(cursor)) {
            baseRoot.getChildren().remove(removed);
        }

        //READJUST TEXT DISPLAY
        //renderDisplay();
    }


    public void setFontOfAll() {
        Text test = new Text("T");
        test.setFont(Font.font(fontName, fontSize));
        textHeight = test.getLayoutBounds().getHeight();
        if (display.size() == 0) {
            cursor.setHeight(textHeight);
            return;
        }
        renderDisplay();
    }

    public void renderDisplay() {
        double xPos = 5;
        double yPos = 0;
        double width;
        for (Text t: display) {
            t.setFont(Font.font(fontName, fontSize));
            t.setTextOrigin(VPos.TOP);
            width = t.getLayoutBounds().getWidth();
            newLine = false;

            if (t.getText().equals("\r") || t.getText().equals("\r\n")) {
                newLine = true;
                yPos += textHeight;
                xPos = 5;
            }

            if (xPos + width >= (WINDOW_WIDTH - scrollWidth - 5)) {
                    xPos = 5;
                    yPos += textHeight;
            }
            t.setX(xPos);
            t.setY(yPos);

            xPos += width;
        }

        cursorPosX = currentText.getX() + getWidth();
        cursorPosY = currentText.getY();

        if (!currentText.getText().equals("\r") || currentText.getText().equals("\r\n")) {
            cursor.setHeight(getHeight());
        }

        setCursorPos();

    }
    //END  UTILITIES


    /** An EventHandler to handle keys that get pressed. */
    private class KeyEventHandler implements EventHandler<KeyEvent> {


        KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
            // textCenterX = 5;
            // textCenterY = 0;
            cursorPosX = 5;
            cursorPosY = 0;

            // Initialize some empty text and add it to root so that it will be displayed.
            Text displayTest = new Text(cursorPosX, cursorPosY, "T");

            baseRoot = root;
            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simpler!
            displayTest.setFont(Font.font(fontName, fontSize));

            textWidth = displayTest.getLayoutBounds().getWidth();
            textHeight = displayTest.getLayoutBounds().getHeight();


            cursor = new Rectangle(1, textHeight);
            cursor.setX(cursorPosX);
            cursor.setY(cursorPosY);

            setCurrentText();

            root.getChildren().add(cursor);
            root.getChildren().add(displayTest);
            //root.getChildren().add(display);
            makeCursorColorChange();
        }


        @Override
        public void handle(KeyEvent keyEvent) {

            boolean shortcut = keyEvent.isShortcutDown();

            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED && !shortcut) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalization.
                String characterTyped = keyEvent.getCharacter();
                //System.out.println(characterTyped);
                if (characterTyped.length() == 1 && characterTyped.charAt(0) != 8) {

                    addText(characterTyped);
                }
                else if (characterTyped.length() == 1 && characterTyped.charAt(0) == 8){
                    delete();
                }
                renderDisplay();
                }

             else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                 KeyCode code = keyEvent.getCode();

                ////CHANGE FONT SIZE
                if (shortcut) {
                    if (code == KeyCode.PLUS || code == KeyCode.EQUALS) {
                            fontSize += 4;
                            setFontOfAll();
                    }
                    else if (code == KeyCode.MINUS && fontSize > 4) {
                            fontSize -= 4;
                            setFontOfAll();
                        }
                    else if (code == KeyCode.P) {
                            System.out.println((int) (cursorPosX) + ", " + (int) (cursorPosY));
                    }
                    else if (code == KeyCode.S) {
                            file.save();
                    }
                }

                else if (code == KeyCode.LEFT) {
                            //System.out.println("left");
                            try {
                                currentText = display.toLeft();
                                if (currentText.equals(null)) {
                                    cursorPosX = 5;
                                    cursorPosY = 0;
                                }
                                else {
                                cursorPosX = currentText.getX() + getWidth();
                                cursorPosX = (int) Math.round(cursorPosX);
                                cursorPosY = (int) Math.round(currentText.getY());
                                }
                            }
                            catch (NullPointerException e) {
                                cursorPosX = 5;
                                cursorPosY = 0;
                            }

                }
                else if (code == KeyCode.RIGHT) {
                            try {
                                currentText = display.toRight();
                                if (currentText.equals(null)) {
                                    cursorPosX = 5;
                                    cursorPosY = 0;
                                }
                                cursorPosX = currentText.getX() + getWidth();
                                cursorPosX = (int) Math.round(cursorPosX);
                                cursorPosY = (int) Math.round(currentText.getY());
                            }
                            catch (NullPointerException e) {
                            }
                }
                else if (code == KeyCode.UP) {
                            cursorPosY -= getHeight();
                            cursorPosY = (int) (Math.round(cursorPosY));
                            for (Text t: display) {
                                double yPos = t.getY();
                                double xPos = t.getX();
                                double width = t.getLayoutBounds().getWidth();
                                if (cursorPosY == yPos && xPos + width > cursorPosX) {
                                    currentText = t;
                                }
                            }
                            cursorPosX = currentText.getX();
                }
                else if (code == KeyCode.DOWN) {
                            cursorPosY += getHeight();
                            cursorPosY = (int) (Math.round(cursorPosY));
                            for (Text t: display) {
                                double yPos = t.getY();
                                double xPos = t.getX();
                                double width = t.getLayoutBounds().getWidth();
                                if (cursorPosY == yPos && xPos + width > cursorPosX) {
                                    currentText = t;
                                }
                            }
                            cursorPosX = currentText.getX();
                    }

                setCursorPos();
                }

            }

    }

    /** An EventHandler to handle changing the color of the cursor. */
    private class CursorBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] boxColors = {Color.WHITE, Color.BLACK};

        CursorBlinkEventHandler() {
            // Set the color to be the first color in the list.
            changeColor();
        }

        private void changeColor() {
            cursor.setFill(boxColors[currentColorIndex]);
            if (currentColorIndex == 1) {
                currentColorIndex = 0;
            }
            else {
                currentColorIndex = 1;
            }
        }

        @Override
        public void handle(ActionEvent event) {
            changeColor();
        }
    }

    /** Makes the text bounding box change color periodically. */
    public void makeCursorColorChange() {
        // Create a Timeline that will call the "handle" function of RectangleBlinkEventHandler
        // every 1 second.
        final Timeline timeline = new Timeline();
        // The rectangle should continue blinking forever.
        timeline.setCycleCount(Timeline.INDEFINITE);
        CursorBlinkEventHandler cursorChange = new CursorBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private class MouseClickEventHandler implements EventHandler<MouseEvent> {

        public Group mouseRoot;

        MouseClickEventHandler(Group root) {
            mouseRoot = root;
        }


        @Override
        public void handle(MouseEvent mouseEvent) {

            double mousePressedX = mouseEvent.getX();
            double mousePressedY = mouseEvent.getY();

            // System.out.println("mouse pressed: " + mousePressedX + " "  + mousePressedY);

            cursorPosX = mousePressedX;
            cursorPosY = mousePressedY;

            double xPos = 0;
            double yPos = 0;
            double adjust;
            boolean changed = false;

            for (Text t: display) {
                xPos = t.getX();
                yPos = t.getY();
                adjust = t.getLayoutBounds().getWidth() / 2;
                //System.out.println(adjust);
                if ( (xPos + adjust >= cursorPosX) || ( xPos > cursorPosX && xPos - adjust < cursorPosX) ) {
                    currentText = t;
                    // System.out.println("changed currentText: " + currentText.getText() + " " + currentText.getX());
                    changed = true;
                }

                if (changed) {
                    break;
                }
            }

            cursorPosX = (int) Math.round(currentText.getX() + getWidth());
            cursorPosY = (int) Math.round(currentText.getY());
            // System.out.println("cursor changed: " + cursorPosX + " "  + cursorPosY);

            setCursorPos();
        }
    }


    @Override
    public void start(Stage primaryStage) {
        String fileName = "";
        try {
            fileName = getParameters().getRaw().get(0);
        }
        catch (RuntimeException runtime){
            System.out.println("<filename> expected");
        }
        file = new FileOps(fileName);
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        Group text = new Group();
        root.getChildren().add(text);
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.
        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler(text, WINDOW_WIDTH, WINDOW_HEIGHT);

        MouseClickEventHandler mouseClickEventHandler =
                new MouseClickEventHandler(text);

                scrollBar = new ScrollBar();
                scrollBar.setOrientation(Orientation.VERTICAL);
                scrollBar.setPrefHeight(WINDOW_HEIGHT);


                scrollBar.setMin(0);
                scrollBar.setMax(WINDOW_HEIGHT);
                root.getChildren().add(scrollBar);


                scrollWidth = scrollBar.getLayoutBounds().getWidth();
                scrollBar.setLayoutX((int) (WINDOW_WIDTH - scrollWidth));

                scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
                    public void changed(
                            ObservableValue<? extends Number> observableValue,
                            Number oldValue,
                            Number newValue) {
                        double hiddenSpace = display.getLast().getY() - WINDOW_HEIGHT;
                        if (newValue.intValue() != oldValue.intValue()) {
                            scrollBar.setMax(Math.round(hiddenSpace));
                            double toMove = oldValue.doubleValue() - newValue.doubleValue();
                            //System.out.println(toMove);
                            text.setLayoutY(toMove);
                        }
                    }
                });
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        scene.setOnMouseClicked(mouseClickEventHandler);
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenWidth,
                    Number newScreenWidth) {
                // Re-compute Allen's width.
                // System.out.println("is resizing");
                WINDOW_WIDTH = newScreenWidth.intValue();
                scrollBar.setLayoutX((int) (WINDOW_WIDTH - scrollWidth));
                renderDisplay();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenHeight,
                    Number newScreenHeight) {
                WINDOW_HEIGHT = newScreenHeight.intValue();
                scrollBar.setMax(WINDOW_HEIGHT);
                renderDisplay();
            }
        });



        primaryStage.setTitle(fileName);
        file.open();

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    ////OPENING AND SAVING////
    public class FileOps {

        public File mainFile;
        public String fileName;

        public FileOps(String fn) {
                fileName = fn;
                mainFile = new File(fn);
                // Check to make sure that the input file exists!
                try {
                    mainFile.createNewFile();
                }
                catch (IOException io){
                    System.out.println("unable to open " + fileName);
                }
        }

        public void open() {
            try {
                FileReader reader = new FileReader(mainFile);
                BufferedReader bufferedReader = new BufferedReader(reader);

                int intRead = -1;
                while ((intRead = bufferedReader.read()) != -1) {
                    // The integer read can be cast to a char, because we're assuming ASCII.
                    char charRead = (char) intRead;
                    String text = Character.toString(charRead);
                    if (intRead == 47) {
                        int nextInt = bufferedReader.read();
                        if (nextInt == 114) {
                            bufferedReader.read();
                            bufferedReader.read();
                        }
                        text = "\r";
                    }
                    addText(text);
                }
                renderDisplay();
                bufferedReader.close();

            }

            catch (FileNotFoundException fileNotFoundException) {
                System.out.println(fileName + " not found! Exception was: " + fileNotFoundException);
            } catch (IOException ioException) {
                System.out.println("Error when opening " + fileName + "; exception was: " + ioException);
            }
        }

        public void save() {
            try {

                FileWriter writer = new FileWriter(mainFile);
                for (Text t: display) {
                    String text = t.getText();
                    if (text.equals("\r")) {
                        text = "\n";
                    }
                    writer.write(text);
                }

                writer.close();
            }
            catch (FileNotFoundException fileNotFoundException) {
                System.out.println(fileName + " not found! Exception was: " + fileNotFoundException);
            } catch (IOException ioException) {
                System.out.println("Error when opening " + fileName + "; exception was: " + ioException);
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}

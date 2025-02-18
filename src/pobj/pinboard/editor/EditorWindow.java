package pobj.pinboard.editor;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;

public class EditorWindow implements EditorInterface, ClipboardListener {
	private Board board;	// drawing area
	private Tool tool;		// current tool
	private Selection selection; // list of selected clips
	private MenuItem copyItem;
	private MenuItem pasteItem;
	private MenuItem deleteItem;
	
	public EditorWindow(Stage stage) {
		
		this.board = new Board();
		this.tool = new ToolRect();
		this.selection = new Selection();
		
		/* -------------------- CONTROL PANEL -------------------- */
		
		// create a menu bar
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu toolsMenu = new Menu("Tools");
		MenuBar menuBar = new MenuBar(fileMenu, editMenu, toolsMenu);
		
		// add menus items and separators to file menu
		MenuItem newItem = new MenuItem("New");
        MenuItem closeItem = new MenuItem("Close");
		fileMenu.getItems().addAll(newItem, new SeparatorMenuItem(), closeItem);
		
		// add menus items and separators to edit menu
		this.copyItem = new MenuItem("Copy");
        this.pasteItem = new MenuItem("Paste");
        this.deleteItem = new MenuItem("Delete");
		editMenu.getItems().addAll(copyItem, new SeparatorMenuItem(), pasteItem, new SeparatorMenuItem(), deleteItem);
		
		// create a tool bar
		Button bBox = new Button("Box");
		Button bEllipse = new Button("Ellipse");
		Button bImage = new Button("Img...");
		Button bSelection = new Button("Select");
		ToolBar toolBar = new ToolBar(bBox, bEllipse, bImage, bSelection);
		// other way : toolBar.getItems().addAll(bBox, bEllipse, bImage, bSelection);
		
		// alternative toolbar
		MenuItem menuBox = new MenuItem("Box");
        MenuItem menuEllipse = new MenuItem("Ellipse");
        MenuItem menuImage = new MenuItem("Img...");
        MenuItem menuSelection = new MenuItem("Selection");
		toolsMenu.getItems().addAll(menuBox, new SeparatorMenuItem(), menuEllipse, new SeparatorMenuItem(), menuImage, new SeparatorMenuItem(), menuSelection);
		
		// display the white board in the canva
		Canvas canvas = new Canvas(1280, 720);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		board.draw(gc);
		
		// create a separator 
		Separator separator = new Separator();
//		Separator separator1 = new Separator();

		
		// create a label bar
		Label label =  new Label("No selected tool");
		
		/* -------------------- END CONTROL PANEL -------------------- */			
		
		// set the name of the window
		stage.setTitle("PinBoard");
		
		// create a layout with the differents controls
		VBox vbox = new VBox();
		vbox.getChildren().addAll(menuBar, toolBar, canvas, separator, label);
		
		// create a scene that contains the layout
		Scene scene = new Scene(vbox);

//		HBox hbox = new HBox();
//		hbox.getChildren().addAll(toolBar, separator, canvas);
//	
//		VBox vbox = new VBox();
//		vbox.getChildren().addAll(menuBar, hbox, separator1, label);
//	
//		Scene scene = new Scene(vbox);
		
		// associate the scene to the window
		stage.setScene(scene);
		
		// display the window
		stage.show();
		
		/* -------------------- EVENT HANDLERS -------------------- */
		// classes anonymes
		// lambda: item.setOnAction( (e)-> System.out.println("coucou"); );
		
		// open a new window
		newItem.setOnAction(
				new EventHandler<ActionEvent>() { 
					public void handle(ActionEvent e) { 
						new EditorWindow(new Stage());
						// subscribe to notifications
						Clipboard.getInstance().addListener(EditorWindow.this);
					}
				});
		
		// close the window
		closeItem.setOnAction(
				new EventHandler<ActionEvent>() { 
					public void handle(ActionEvent e) { 
						stage.close();
						// subscribe to notifications
						Clipboard.getInstance().removeListener(EditorWindow.this);
					}
				}); 
		
		// copy selected clip(s) from the board
		copyItem.setOnAction(
				new EventHandler<ActionEvent>() { 
					public void handle(ActionEvent e) { 
						Clipboard.getInstance().copyToClipboard(EditorWindow.this.selection.getContents());
						EditorWindow.this.clipboardChanged();
					}
				});
		
		//paste selected clip(s) on the same board or another one
		pasteItem.setOnAction(
				new EventHandler<ActionEvent>() { 
					public void handle(ActionEvent e) {
						board.addClip(Clipboard.getInstance().copyFromClipboard());
						draw(gc);
					} 
				});
		
		// delete selected clip(s) from the board
		deleteItem.setOnAction(
				new EventHandler<ActionEvent>() { 
					public void handle(ActionEvent e) { 
						board.removeClip(EditorWindow.this.selection.getContents());
						draw(gc);
					}
				});

		// set current tool to rectangle
		bBox.setOnAction( (e) -> {
			this.tool = new ToolRect();
			label.textProperty().set(this.tool.getName(this));
		});
				
		// set current tool to rectangle (alternative) 
		menuBox.setOnAction( (e) -> {
			this.tool = new ToolRect();
			label.textProperty().set(this.tool.getName(this));
		});

		// set current tool to ellipse
		bEllipse.setOnAction( (e) -> {
			this.tool = new ToolEllipse();
			label.textProperty().set(this.tool.getName(this));			
		});
		
		// set current tool to ellipse (alternative)
		menuEllipse.setOnAction( (e) -> {
			this.tool = new ToolEllipse();
			label.textProperty().set(this.tool.getName(this));			
		});
		
		// set current tool to image
		FileChooser fileChooser = new FileChooser();
		bImage.setOnAction( (e) -> {
			File selectedFile = fileChooser.showOpenDialog(stage);
			try {
				this.tool = new ToolImage(selectedFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		
		// set current tool to image (alternative)
		menuImage.setOnAction( (e) -> {
			File selectedFile = fileChooser.showOpenDialog(stage);
			try {
				this.tool = new ToolImage(selectedFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			label.textProperty().set(this.tool.getName(this));			
		});
		
		// set current tool to selection
		bSelection.setOnAction( (e) -> {
			this.tool = new ToolSelection();
			label.textProperty().set(this.tool.getName(this));
		});
		
		// set current tool to selection (alternative)
		menuEllipse.setOnAction( (e) -> {
			this.tool = new ToolSelection();
			label.textProperty().set(this.tool.getName(this));			
		});
		
		// mouse button is pressed
		canvas.setOnMousePressed(
				new EventHandler<MouseEvent>() {
					@Override
				    public void handle(MouseEvent e) {
				        EditorWindow.this.tool.press(EditorWindow.this, e); }
					// an anonymous class has its own instance, distinct from the instance of the EditorWindow class in which it is defined. To access the members of the enclosing instance of EditorWindow from this anonymous class, you need to use EditorWindow.this.
		});
		
		// mouse button is dragged
		canvas.setOnMouseDragged(
				new EventHandler<MouseEvent>() {
					@Override
				    public void handle(MouseEvent e) {
				        EditorWindow.this.tool.drag(EditorWindow.this, e);
				        draw(gc);
				        EditorWindow.this.tool.drawFeedback(EditorWindow.this, gc); }
		});
		
		// mouse button is released
		canvas.setOnMouseReleased(
				new EventHandler<MouseEvent>() {
					@Override
				    public void handle(MouseEvent e) {
				        EditorWindow.this.tool.release(EditorWindow.this, e);
				        draw(gc); }
		});
		
		// clipboard status check
		this.clipboardChanged();
		
		/* -------------------- END EVENT HANDLERS -------------------- */
	}
	
	/***
	 * Draw all the clips on the board
	 * @param ctx
	 */
	public void draw(GraphicsContext ctx) {
		board.draw(ctx);
	}
	
	/***
	 * Access to the selected tool
	 * @return
	 */
	public Tool getTool() {
		return this.tool;
	}
	
	/**
	 * Access to the board
	 */
	@Override
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Access to the selection that contains the list of selected clips
	 */
	@Override
	public Selection getSelection() {
		return this.selection;
	}
	
	@Override
	public CommandStack getUndoStack() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Gray out the paste option if the clipboard is empty
	 */
	@Override
	public void clipboardChanged() {
		// disable the paste option if the clipboard is empty
		if (Clipboard.getInstance().isEmpty()) {
			this.pasteItem.setDisable(true);
			System.out.println("disable");
		}
//		else if (this.selection.getContents().size() == 0) {
//			this.copyItem.setDisable(false);
//			this.pasteItem.setDisable(false);
//			this.deleteItem.setDisable(false);
//		}
		// enable the paste option if the clipboard if not empty
		else {
			this.pasteItem.setDisable(false);
			System.out.println("enable");			
		}
	}
}

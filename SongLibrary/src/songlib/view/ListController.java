package songlib.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ListController {
	@FXML
	TextField songInput;
	@FXML
	TextField artistInput;
	@FXML
	TextField albumInput;
	@FXML
	TextField yearInput;
	@FXML
	Text dispTitle;
	@FXML
	Text dispArtist;
	@FXML
	Text dispAlbum;
	@FXML
	Text dispYear;
	@FXML
	ListView<Song> listView;

	ObservableList<Song> obsList = FXCollections.observableArrayList();

	public void start() {
		// obsList = FXCollections.observableArrayList(new Song("Bapu", "Ryguy", "",
		// ""));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
			public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
				//System.out.println("New selected Value: "+newValue.getTitle());
				updateDisplay();
			}
		});
	}
	
	public void updateDisplay() {
		Song temp = obsList.get(listView.getSelectionModel().getSelectedIndex());
		dispTitle.setText(temp.getTitle());
		dispArtist.setText(temp.getArtist());
		dispAlbum.setText(temp.getAlbum());
		dispYear.setText(temp.getYear());
	}
	
	public void add(ActionEvent e) {
		if(songInput.getText().equals("") || artistInput.getText().equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");
			
			alert.setHeaderText("Your command was invalid.");
			String content = "Song name and Artist name must both be filled out.";
			alert.setContentText(content);
			
			alert.showAndWait();
			return;
		}
		Song input = new Song(songInput.getText(), artistInput.getText(), albumInput.getText(), yearInput.getText());
		if (!(obsList.contains(input))) {
			obsList.add(input);
			FXCollections.sort(obsList);
			listView.getSelectionModel().select(input);
			listView.scrollTo(input);
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");
			
			alert.setHeaderText("Your command was invalid.");
			String content = "A song named " + input.getTitle() + " by " + input.getArtist() + " is already in the library.";
			alert.setContentText(content);
			
			alert.showAndWait();
		}
	}

	public void edit(ActionEvent e) {
		if(obsList.size() <= 0) { //If the list is empty.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");
			
			alert.setHeaderText("Your command was invalid.");
			alert.setContentText("No song selected.");
			
			alert.showAndWait();
			return;
		} else if(songInput.getText().equals("") || artistInput.getText().equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");
			
			alert.setHeaderText("Your command was invalid.");
			String content = "Song name and Artist name must both be filled out.";
			alert.setContentText(content);
			
			alert.showAndWait();
			return;
		}
		Song input = new Song(songInput.getText(), artistInput.getText(), albumInput.getText(), yearInput.getText());
		if (!(obsList.contains(input))) {
			Song toEdit = obsList.get(listView.getSelectionModel().getSelectedIndex());
			toEdit.setTitle(songInput.getText());
			toEdit.setArtist(artistInput.getText());
			toEdit.setAlbum(albumInput.getText());
			toEdit.setYear(yearInput.getText());
			FXCollections.sort(obsList);
			listView.getSelectionModel().select(input);
			listView.scrollTo(input);
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");
			
			alert.setHeaderText("Your command was invalid.");
			String content = "A song named " + input.getTitle() + " by " + input.getArtist() + " is already in the library.";
			alert.setContentText(content);
			
			alert.showAndWait();
		}
	}

	public void delete(ActionEvent e) {
		
		if(obsList.size() <= 0) { //If the list is empty.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");
			
			alert.setHeaderText("Your command was invalid.");
			alert.setContentText("No song selected.");
			
			alert.showAndWait();
			return;
		}
		Song toDelete = obsList.get(listView.getSelectionModel().getSelectedIndex());
		int index = obsList.indexOf(toDelete);
		if(index == obsList.size() - 1) {
			listView.getSelectionModel().selectPrevious();
		}
		else if(index + 1 < obsList.size()) {
			listView.getSelectionModel().selectNext();
		}
		
		obsList.remove(toDelete);
	}
}

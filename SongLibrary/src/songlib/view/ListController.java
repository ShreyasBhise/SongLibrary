package songlib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
	ListView<Song> listView;

	ObservableList<Song> obsList = FXCollections.observableArrayList();

	public void start() {
		// obsList = FXCollections.observableArrayList(new Song("Bapu", "Ryguy", "",
		// ""));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
	}

	public void add(ActionEvent e) {
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

	}

	public void delete(ActionEvent e) {
		
		if(obsList.size() <= 0) {
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

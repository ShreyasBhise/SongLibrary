package songlib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ListController {
	@FXML TextField songInput;
	@FXML TextField artistInput;
	@FXML TextField albumInput;
	@FXML TextField yearInput;
	@FXML ListView<Song> listView;
	
	private ObservableList<Song> obsList;
	
	public void start() {
		obsList = FXCollections.observableArrayList(new Song("Bapu", "Ryguy", "", ""));
		listView.setItems(obsList);
	}
	
	public void add(ActionEvent e) {
		Song input = new Song(songInput.getText(), artistInput.getText(), albumInput.getText(), yearInput.getText());
		obsList.add(input);
		
	}
	
	public void edit(ActionEvent e) {

	}
	
	public void delete(ActionEvent e) {
	
	}
}

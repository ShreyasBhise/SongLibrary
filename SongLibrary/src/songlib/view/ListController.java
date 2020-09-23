package songlib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
				updateDisplay();
			}
		});
		String filePath = "src/songlib/view/songlist.txt";
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!sc.hasNextLine()) {
			System.out.println("Error Reading File");
			return;
		}
		String s = sc.nextLine();
		int n = Integer.parseInt(s);
		for (int i = 0; i < n; i++) {
			String input[] = new String[4];
			for (int j = 0; j < 4; j++) {
				if (!sc.hasNextLine()) {
					System.out.println("Error Reading File");
					return;
				}
				input[j] = sc.nextLine();
			}
			Song song = new Song(input[0], input[1], input[2], input[3]);
			obsList.add(song);
		}
		sc.close();
		if (obsList.size() > 0) {
			listView.getSelectionModel().select(0);
		}
	}

	public void updateDisplay() {
		if (obsList.size() <= 0) { // If the list is empty.
			dispTitle.setText("");
			dispArtist.setText("");
			dispAlbum.setText("");
			dispYear.setText("");
			return;
		}
		int n = listView.getSelectionModel().getSelectedIndex();
		if (n < 0 || n >= obsList.size())
			return;
		System.out.println(n);
		Song temp = obsList.get(n);
		dispTitle.setText(temp.getTitle());
		dispArtist.setText(temp.getArtist());
		dispAlbum.setText(temp.getAlbum());
		dispYear.setText(temp.getYear());
	}

	public void add(ActionEvent e) {
		if (songInput.getText().equals("") || artistInput.getText().equals("")) {
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
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");

			alert.setHeaderText("Your command was invalid.");
			String content = "A song named " + input.getTitle() + " by " + input.getArtist()
					+ " is already in the library.";
			alert.setContentText(content);

			alert.showAndWait();
		}
		fileWrite();
	}

	public void edit(ActionEvent e) {
		if (obsList.size() <= 0) { // If the list is empty.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");

			alert.setHeaderText("Your command was invalid.");
			alert.setContentText("No song selected.");

			alert.showAndWait();
			return;
		} else if (songInput.getText().equals("") || artistInput.getText().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");

			alert.setHeaderText("Your command was invalid.");
			String content = "Song name and Artist name must both be filled out.";
			alert.setContentText(content);

			alert.showAndWait();
			return;
		}
		Song input = new Song(songInput.getText(), artistInput.getText(), albumInput.getText(), yearInput.getText());
		if (input.equals(listView.getSelectionModel().getSelectedItem()) || !(obsList.contains(input))) {
			Song toEdit = obsList.get(listView.getSelectionModel().getSelectedIndex());
			toEdit.setTitle(songInput.getText());
			toEdit.setArtist(artistInput.getText());
			toEdit.setAlbum(albumInput.getText());
			toEdit.setYear(yearInput.getText());
			FXCollections.sort(obsList);
			listView.getSelectionModel().select(input);
			listView.scrollTo(input);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");

			alert.setHeaderText("Your command was invalid.");
			String content = "A song named " + input.getTitle() + " by " + input.getArtist()
					+ " is already in the library.";
			alert.setContentText(content);

			alert.showAndWait();
		}
		fileWrite();
	}

	public void delete(ActionEvent e) {

		if (obsList.size() <= 0) { // If the list is empty.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");

			alert.setHeaderText("Your command was invalid.");
			alert.setContentText("No song selected.");

			alert.showAndWait();
			return;
		}
		Song toDelete = obsList.get(listView.getSelectionModel().getSelectedIndex());
		int index = obsList.indexOf(toDelete);
		if (index == obsList.size() - 1) {
			listView.getSelectionModel().selectPrevious();
		} else if (index + 1 < obsList.size()) {
			listView.getSelectionModel().selectNext();
		}

		obsList.remove(toDelete);
		fileWrite();
	}

	public void fileWrite() {
		String filePath = "src/songlib/view/songlist.txt";
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(obsList.size() + "\n");
			for (int i = 0; i < obsList.size(); i++) {
				Song s = obsList.get(i);
				fileWriter.write(s.getTitle() + "\n");
				fileWriter.write(s.getArtist() + "\n");
				fileWriter.write(s.getAlbum() + "\n");
				fileWriter.write(s.getYear() + "\n");
			}
			if (fileWriter != null)
				fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

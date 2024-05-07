package com.example.pidev.controllers.back.voyage;

import com.example.pidev.entities.Voyage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import services.VoyageService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {


    @FXML
    public PieChart mostUsedTypePieChart;
    @FXML
    public PieChart mostUsedDestinationPieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Voyage> voyageList = VoyageService.getInstance().getAll();

        // Most used type
        mostUsedTypePieChart.getData().clear();

        List<String> types = new ArrayList<>();
        for (Voyage voyage : voyageList) {
            if (!types.contains(voyage.getType())) {
                types.add(voyage.getType());
            }
        }

        for (String type : types) {
            int count = 0;
            for (Voyage voyage : voyageList) {
                if (voyage.getType().equals(type)) {
                    count++;
                }
            }
            mostUsedTypePieChart.getData().add(new PieChart.Data(type, count));
        }

        // Most used destination
        mostUsedDestinationPieChart.getData().clear();

        List<String> destinations = new ArrayList<>();

        for (Voyage voyage : voyageList) {
            if (!destinations.contains(voyage.getDestination())) {
                destinations.add(voyage.getDestination());
            }
        }

        for (String destination : destinations) {
            int count = 0;
            for (Voyage voyage : voyageList) {
                if (voyage.getDestination().equals(destination)) {
                    count++;
                }
            }
            mostUsedDestinationPieChart.getData().add(new PieChart.Data(destination, count));
        }
    }
}

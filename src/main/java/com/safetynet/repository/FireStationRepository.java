package com.safetynet.repository;
import com.safetynet.model.FireStation;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository {
    private final DataRepository dataRepository;
    private List<FireStation> fireStations = new ArrayList<>();

    public FireStationRepository(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    private void initializeData() {
        if (fireStations.isEmpty()) {
            fireStations = new ArrayList<>(dataRepository.findAllFireStations());
        }
    }

    public List<FireStation> findAll() {
        initializeData();
        return new ArrayList<>(fireStations);
    }

    public List<FireStation> findByStation(String station) {
        initializeData();
        return fireStations.stream()
                .filter(fs -> fs.getStation().equals(station))
                .collect(Collectors.toList());
    }

    public Optional<FireStation> findByAddress(String address) {
        initializeData();
        return fireStations.stream()
                .filter(fs -> fs.getAddress().equals(address))
                .findFirst();
    }

    public FireStation save(FireStation fireStation) {
        initializeData();
        fireStations.removeIf(fs -> fs.getAddress().equals(fireStation.getAddress()));
        fireStations.add(fireStation);
        return fireStation;
    }

    public boolean deleteByAddress(String address) {
        initializeData();
        return fireStations.removeIf(fs -> fs.getAddress().equals(address));
    }

    public List<String> findAddressesByStation(String station) {
        initializeData();
        return fireStations.stream()
                .filter(fs -> fs.getStation().equals(station))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());
    }

    public List<FireStation> findByStations(List<String> stations) {
        initializeData();
        return fireStations.stream()
                .filter(fs -> stations.contains(fs.getStation()))
                .collect(Collectors.toList());
    }

}
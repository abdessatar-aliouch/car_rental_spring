package com.aa.carrental.vehicleService.controller;

import com.aa.carrental.vehicleService.model.Vehicle;
import com.aa.carrental.vehicleService.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping
    public Page<Vehicle> getVehicles(@RequestParam Optional<Integer> page ,
                                     @RequestParam Optional<Integer> size,
                                     @RequestParam Optional<String> keyword, @RequestParam Optional<String> order, @RequestParam Optional<String> field){
        return vehicleService.getVehicles(
                page.orElse(0),
                size.orElse(20),
                keyword.orElse(""),
                order.orElse("ASC"),
                field.orElse("id")
        );
    }

    @GetMapping("all")
    public List<Vehicle> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    @PostMapping
    public ResponseEntity<String> addVehicle(@RequestBody Vehicle vehicle){
        vehicleService.addVehicle(vehicle);
        return ResponseEntity.ok("VEHICLE CREATE SUCCESSFULLY");
    }

    @PutMapping
    public ResponseEntity<String> updateVehicle(@RequestBody Vehicle vehicle){
        vehicleService.updateVehicle(vehicle);
        return ResponseEntity.ok("VEHICLE UPDATED SUCCESSFULLY");
    }

    @DeleteMapping("{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long vehicleId){
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.ok("VEHICLE DELETED SUCCESSFULLY");
    }

    @GetMapping("{vehicleId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Long vehicleId){
        return ResponseEntity.ok(vehicleService.getVehicleById(vehicleId));
    }

}

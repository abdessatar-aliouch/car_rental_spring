package com.aa.carrental.vehicleService.service;


import com.aa.carrental.exception.MatriculeAlreadyUsedException;
import com.aa.carrental.vehicleService.model.Vehicle;
import com.aa.carrental.vehicleService.repository.VehicleRepository;
import com.aa.carrental.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.aa.carrental.vehicleService.service.VehicleValidator.*;
import static com.aa.carrental.vehicleService.service.VehicleValidator.ValidationResult.SUCCESS;


@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Page<Vehicle> getVehicles(int page, int size , String keyword, String order , String field){
        return  vehicleRepository.findAllByMakeContainingIgnoreCaseOrModelContainingIgnoreCaseOrMatriculeContainingIgnoreCase(
                keyword,
                keyword,
                keyword,
                PageRequest.of(page, size, Sort.Direction.valueOf(order),field));
    }
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void addVehicle(Vehicle vehicle) {
        isVehicleInputsValid(vehicle);
        getVehicleByMatricule(vehicle.getMatricule()).ifPresent(existingVehicle -> {
                throw new MatriculeAlreadyUsedException("The matricule " + vehicle.getMatricule() + " is already used by another vehicle.");
        });

            Vehicle newVehicle = new Vehicle();
        saveVehicle(vehicle, newVehicle);
    }

    private void saveVehicle(Vehicle vehicle, Vehicle newVehicle) {
        newVehicle.setMake(vehicle.getMake());
        newVehicle.setPrice(vehicle.getPrice() != null ? vehicle.getPrice() : 0.0);
        newVehicle.setModel(vehicle.getModel());
        newVehicle.setYear(vehicle.getYear());
        newVehicle.setImageBase64((vehicle.getImageBase64() != null && !vehicle.getImageBase64().isBlank()) ? resizeImage(vehicle.getImageBase64()) : "");
        newVehicle.setEngineSize(vehicle.getEngineSize());
        newVehicle.setFuelType(vehicle.getFuelType());
        newVehicle.setNumSeats(vehicle.getNumSeats());
        newVehicle.setTransmissionType(vehicle.getTransmissionType());
        newVehicle.setStatus(vehicle.getStatus());
        newVehicle.setRentalRate(vehicle.getRentalRate());
        newVehicle.setInsuranceCoverage(vehicle.getInsuranceCoverage());
        newVehicle.setInsuranceExpirationDate(vehicle.getInsuranceExpirationDate());
        newVehicle.setVisitExpirationDate(vehicle.getVisitExpirationDate());
        newVehicle.setAdditionalFeatures(vehicle.getAdditionalFeatures());
        newVehicle.setColor(vehicle.getColor());
        vehicleRepository.save(vehicle);
    }

    private static void isVehicleInputsValid(Vehicle vehicle) {
        ValidationResult result = isMatriculeValid()
                .and(isMakeValid())
                .and(isPriceValid())
                .and(isModelValid()).apply(vehicle);
        if (!result.equals(SUCCESS))
            throw new RuntimeException("Vehicle inputs invalid: " + result);
    }

    public void updateVehicle(Vehicle vehicle) {
        isVehicleInputsValid(vehicle);

        Vehicle oldVehicle = getVehicleById(vehicle.getId());
        getVehicleByMatricule(vehicle.getMatricule()).ifPresent(existingVehicle -> {
            if (!existingVehicle.getId().equals(vehicle.getId()))
                throw new MatriculeAlreadyUsedException("The matricule " + vehicle.getMatricule() + " is already used by another vehicle.");
        });

        saveVehicle(vehicle, oldVehicle);
    }

    private String resizeImage(String imageBase64) {

        String base64Data = imageBase64.split(",")[1];
        String base64DataExtension = imageBase64.split(",")[0];

        byte[] imageData = Base64.getDecoder().decode(base64Data);


        try {
            // Create a BufferedImage object from the image data
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));

            // Define the desired width and height for the resized image
            int desiredWidth = 800;
            int desiredHeight = 600;

            // Resize the image
            Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
            BufferedImage bufferedResizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedResizedImage.createGraphics();
            g2d.drawImage(resizedImage, 0, 0, null);
            g2d.dispose();

            // Compress the resized image to a JPEG format
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedResizedImage, "jpeg", outputStream);
            byte[] compressedImageData = outputStream.toByteArray();

            // Convert the compressed image data to a base64 string
            return  base64DataExtension +","+ Base64.getEncoder().encodeToString(compressedImageData);


        } catch (IOException e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    public Vehicle getVehicleById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle with id " + vehicleId + " not found"));
    }

    public Optional<Vehicle> getVehicleByMatricule(String matricule) {
        return  vehicleRepository.findByMatricule(matricule);
    }



    public void deleteVehicle(Long vehicleId) {
        vehicleRepository.deleteById(getVehicleById(vehicleId).getId());
    }

    public Page<Vehicle> getVehiclePaginated(int page, int size, String keyword ,String order  , String field) {
        return vehicleRepository.findAllByMakeContainingIgnoreCaseOrModelContainingIgnoreCaseOrMatriculeContainingIgnoreCase(
                keyword,
                keyword,
                keyword,
                PageRequest.of(page, size, Sort.Direction.valueOf(order), field ));
    }


}

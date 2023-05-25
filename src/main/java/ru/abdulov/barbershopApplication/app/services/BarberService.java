package ru.abdulov.barbershopApplication.app.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Barber;
import ru.abdulov.barbershopApplication.app.entitys.Image;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.repositories.BarberRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j                      //Система протоколирования, используем для логирования
public class BarberService {
    private final BarberRepository barberRepository;

    public List<Barber> listBarbers(String barbName) {
        if (barbName != null) return barberRepository.findByBarbName(barbName);
        return barberRepository.findAll();
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image=new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void saveBarber(Barber barber, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException{
        Image image1;
        Image image2;
        Image image3;
        if(file1.getSize()!=0){
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            barber.addImageToBarber(image1);
        }
        if(file2.getSize()!=0){
            image2 = toImageEntity(file2);
            image2.setPreviewImage(false);
            barber.addImageToBarber(image2);
        }
        if(file3.getSize()!=0) {
            image3 = toImageEntity(file3);
            image3.setPreviewImage(false);
            barber.addImageToBarber(image3);
        }
        for(int i=0; i<28;i++){
            barber.getTimeIsBusy()[i]=false;
        }
        log.info("Saving new barber whose name is{}",barber.getBarbName());
        Barber barberFromBd=barberRepository.save(barber);                      //нужно, чтобы бд присвоила id элементам
        barberFromBd.setPreviewImageId(barberFromBd.getImages().get(0).getId()); // после этого получаем это id и устанавливаем его
        barberRepository.save(barberFromBd);
    }

    public void deleteBarber(Long id){
        barberRepository.deleteById(id);
    }

    public Barber getBarberById(Long id){
        return barberRepository.findById(id).orElse(null);

    }

    public void editBarber(Barber barber, String description, float rating,int experience) {
        barber.setDescription(description);
        barber.setRating(rating);
        barber.setExperience(experience);
        barberRepository.save(barber);
    }
}

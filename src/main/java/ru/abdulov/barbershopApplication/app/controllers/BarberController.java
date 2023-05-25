package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Barber;
import ru.abdulov.barbershopApplication.app.services.BarberService;
import ru.abdulov.barbershopApplication.app.services.UserService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor                        //встраивает необходимые аргументы
public class BarberController {
    private final BarberService barberService;  //используется final, чтобы spring сразу его заинжектил
    private final UserService userService;

    @GetMapping("/")
    public String barbers(@RequestParam(name="barbName",required = false)String barbName, Principal principal, Model model){
        model.addAttribute("barbers",barberService.listBarbers(barbName));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "barbers";
    }

    @GetMapping("/barber/{id}")
    public String barberInfo(@PathVariable Long id, Model model,Principal principal){
        Barber barber= barberService.getBarberById(id);
        model.addAttribute("barber",barber);
        model.addAttribute("images",barber.getImages());
        model.addAttribute("schedule",barber.getTimeIsBusy());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("appointments",barber.getAppointments());
        return "barber-info";
    }

    @PostMapping("/barber/add")
    public String addBarber(@RequestParam("file1") MultipartFile file1,
                            @RequestParam("file2") MultipartFile file2,
                            @RequestParam("file3") MultipartFile file3, Barber barber) throws IOException {
        barberService.saveBarber(barber,file1,file2,file3);
        return "redirect:/";
    }

    @GetMapping("/barber/{barbId}/appointment/{timeId}")
    public String appointmentPage(@PathVariable Long barbId, @PathVariable Long timeId, Model model){
        Barber barber= barberService.getBarberById(barbId);
        model.addAttribute("barber",barber);
        model.addAttribute("schedule",barber.getTimeIsBusy());
        model.addAttribute("time",timeId);
        return "appointment";
    }

    @PostMapping("/barber/delete/{id}")
    public String deleteBarber(@PathVariable Long id){
        barberService.deleteBarber(id);
        return "redirect:/";
    }

}

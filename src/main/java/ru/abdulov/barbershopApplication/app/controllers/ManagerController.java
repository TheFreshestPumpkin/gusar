package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.abdulov.barbershopApplication.app.entitys.Barber;
import ru.abdulov.barbershopApplication.app.services.BarberService;
import ru.abdulov.barbershopApplication.app.services.UserService;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
public class ManagerController {
    private final UserService userService;
    private final BarberService barberService;

    @GetMapping("/manager")
    public String admin(Model model) {
        model.addAttribute("users", userService.userList());
        model.addAttribute("barbers", barberService.listBarbers(null));
        return "manager-page";
    }

    @GetMapping("/manager/barber/delete/{id}")
    public String barberDelete(@PathVariable("id") Long id) {
        barberService.deleteBarber(id);
        return "redirect:/";
    }

    @GetMapping("/manager/barber/edit/{barber}")
    public String barberEdit(@PathVariable("barber")Barber barber, Model model) {
        model.addAttribute("barber", barber);
        return "barber-edit";
    }

    @PostMapping("/manager/barber/edit")
    public String barberEdit(@RequestParam("barberId")Barber barber, @RequestParam("description") String description,
                           @RequestParam("rating") float rating, @RequestParam("experience") int experience) {
        barberService.editBarber(barber, description,rating,experience);
        return "redirect:/manager";
    }

}
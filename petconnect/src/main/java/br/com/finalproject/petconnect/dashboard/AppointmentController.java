package br.com.finalproject.petconnect.dashboard;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @PostMapping
    public String scheduleService(@RequestBody Appointment appointment) {
        // Here you implement the logic to save the appointment to the database
        // This could involve using a service, repository, etc.
        return "Appointment scheduled successfully!";
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        // Here you implement the logic to fetch the appointment by ID from the database
        // Return the found appointment
        Appointment appointment = new Appointment(); // Example of creating a dummy appointment
        return appointment;
    }

}

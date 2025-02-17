package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class DetailsPaymentController {
    @Autowired
    private AppointmentService appointmentService;
    @GetMapping("/appointment/details")
    public String viewAppointmentDetails(@RequestParam String appointmentId, Model model) {
        try {
            System.out.println("Received appointmentId: " + appointmentId);  // Log giá trị appointmentId
            int id = Integer.parseInt(appointmentId);
            Appointment appointment = appointmentService.getAppointmentById(id);
            if (appointment == null) {
                model.addAttribute("message", "Không tìm thấy cuộc hẹn");
                return "error";
            }

            model.addAttribute("appointment", appointment);
            return "appointmentDetails";  // Trang hiển thị thông tin cuộc hẹn
        } catch (NumberFormatException e) {
            model.addAttribute("message", "ID cuộc hẹn không hợp lệ");
            return "error";
        }
    }


}

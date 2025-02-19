package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.AppointmentDetailDTO;
import fpt.aptech.pjs4.entities.Appointment;
import fpt.aptech.pjs4.services.AppointmentService;
import fpt.aptech.pjs4.services.VIPAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class DetailsAppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private VIPAppointmentService vipAppointmentService;
    @GetMapping("/appointment/details")
    public String viewAppointmentDetails(@RequestParam String appointmentId, Model model) {
        try {
            int id = Integer.parseInt(appointmentId);
            AppointmentDetailDTO appointment = appointmentService.getAppointmentDetail(id);
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
    @GetMapping("/vip-appointment/details")
    public String viewVipAppointmentDetails(@RequestParam("VipAppointmentId") String vipappointmentId, Model model) {
        try {
            System.out.println("vipappoint" + vipappointmentId);
            int id = Integer.parseInt(vipappointmentId);
            AppointmentDetailDTO appointment = vipAppointmentService.getvipAppointmentDetail(id);
            if (appointment == null) {
                model.addAttribute("message", "Không tìm thấy cuộc hẹn");
                return "error";
            }

            model.addAttribute("appointment", appointment);
            return "vipappointmentDetails";  // Trang hiển thị thông tin cuộc hẹn
        } catch (NumberFormatException e) {
            model.addAttribute("message", "ID cuộc hẹn không hợp lệ");
            return "error";
        }
    }


}

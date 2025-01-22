package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.AppointmentDTO;
import fpt.aptech.pjs4.entities.*;
import fpt.aptech.pjs4.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorWorkingHourService doctorWorkingHourService;
    @Autowired
    private PatientInformationService patientInformationService;
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDTO appointmentrequest) {
        Patient patient = patientService.getPatientById(appointmentrequest.getPatientId());
        Doctor doctor = doctorService.getDoctorById(appointmentrequest.getDoctorId());
        Doctorworking worktime = doctorWorkingHourService.getWorkingHour(appointmentrequest.getWorktimeId());
        PatientsInformation patientsInformation = patientInformationService.getPatientsInformationById(appointmentrequest.getPatientProfileId());
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setType(appointmentrequest.getType());
        appointment.setStatus(appointmentrequest.getStatus());
        appointment.setAmount(appointmentrequest.getAmount());
        appointment.setWorktime(worktime);
        appointment.setPatientprofile(patientsInformation);
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(201).body(createdAppointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentByPatientId(@PathVariable int id) {
        List<Appointment> appointment = appointmentService.getAllAppointmentsByPatient(id);
        return ResponseEntity.ok(appointment);
    }
    @GetMapping("/doctors/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentByDoctorId(@PathVariable int id) {
        List<Appointment> doctors = appointmentService.getDoctorByidDoctor(id);
        return ResponseEntity.ok(doctors);
    }
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        return ResponseEntity.ok(updatedAppointment);
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable int id, @RequestBody Appointment appointment) {
        String status = appointment.getStatus();
        Appointment updatedAppointment = appointmentService.updateAppointmentStatusOnly(id, status);
        return ResponseEntity.ok(updatedAppointment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}

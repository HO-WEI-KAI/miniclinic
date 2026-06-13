package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class StatsController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/stats")
    public String showStatsPage(Model model) {
        model.addAttribute("totalDoctors", doctorRepo.count());
        model.addAttribute("totalPatients", patientRepo.count());
        model.addAttribute("totalAppointments", appointmentRepo.count());
        model.addAttribute("deptStats", appointmentRepo.countAppointmentsByDepartment());
        return "stats";
    }

    @GetMapping("/api/stats")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        long totalDoctors = doctorRepo.count();
        long totalPatients = patientRepo.count();
        long totalAppointments = appointmentRepo.count();

        Map<String, Long> byStatus = new LinkedHashMap<>();
        byStatus.put("BOOKED", appointmentRepo.countByStatus("BOOKED"));
        byStatus.put("COMPLETED", appointmentRepo.countByStatus("COMPLETED"));
        byStatus.put("CANCELLED", appointmentRepo.countByStatus("CANCELLED"));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalDoctors", totalDoctors);
        result.put("totalPatients", totalPatients);
        result.put("totalAppointments", totalAppointments);
        result.put("byStatus", byStatus);

        return ResponseEntity.ok(result);
    }
}
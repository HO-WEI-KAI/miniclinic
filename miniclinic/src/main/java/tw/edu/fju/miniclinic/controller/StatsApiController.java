package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;
import tw.edu.fju.miniclinic.model.AppointmentRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class StatsApiController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/api/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        // 1. 統計總數
        long totalDoctors = doctorRepo.count();
        long totalPatients = patientRepo.count();
        long totalAppointments = appointmentRepo.count();

        // 2. 統計各狀態的掛號數量
        long bookedCount = appointmentRepo.countByStatus("BOOKED");
        long completedCount = appointmentRepo.countByStatus("COMPLETED");
        long cancelledCount = appointmentRepo.countByStatus("CANCELLED");

        // 3. 依作業規範使用 LinkedHashMap 確保輸出 JSON 鍵值順序
        Map<String, Long> byStatus = new LinkedHashMap<>();
        byStatus.put("BOOKED", bookedCount);
        byStatus.put("COMPLETED", completedCount);
        byStatus.put("CANCELLED", cancelledCount);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalDoctors", totalDoctors);
        result.put("totalPatients", totalPatients);
        result.put("totalAppointments", totalAppointments);
        result.put("byStatus", byStatus);

        return ResponseEntity.ok(result);
    }
}

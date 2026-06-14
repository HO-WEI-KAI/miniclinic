package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 💡 確保這三行 import 準確對齊你的 model 資料夾路徑與檔名
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.PatientRepository; // 👈 檢查這裡的大小寫是否與你原有的檔案完全一致
import tw.edu.fju.miniclinic.model.AppointmentStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class StatsApiController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo; // 👈 注入你專案中原本就有的 PatientRepository

    @GetMapping("/api/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        // 1. 統計總數
        long totalDoctors = doctorRepo.count();
        long totalAppointments = appointmentRepo.count();
        long totalPatients = patientRepo.count();

        // 2. 統計各狀態的掛號數量
        long bookedCount = appointmentRepo.countByStatus(AppointmentStatus.BOOKED.name());
        long completedCount = appointmentRepo.countByStatus(AppointmentStatus.COMPLETED.name());
        long cancelledCount = appointmentRepo.countByStatus(AppointmentStatus.CANCELLED.name());

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

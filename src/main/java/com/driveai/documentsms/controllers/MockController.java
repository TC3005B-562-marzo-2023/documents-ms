package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.Document;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

@RestController
@RequestMapping("/v1/mock")
public class MockController {
        @GetMapping("/new-users")
        public List<Map<String, Object>> getUsersPerMonth() {
            Random random = new Random();
            List<Map<String, Object>> result = new ArrayList<>();
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

            for (String month : months) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", month);
                data.put("new_users", 100 + random.nextInt(900));
                result.add(data);
            }

            return result;
        }

    @GetMapping("/drive-tests")
    public List<Map<String, Object>> getDriveTestsPerAgency(@RequestParam(defaultValue = "month") String groupBy,
                                                            @RequestParam(required = false) String agency) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] agencies = {"Grupo Ford", "Grupo KIA", "Grupo Nissan", "Grupo BMW", "Grupo Toyota"};

        for (String dealership : agencies) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", dealership);
            data.put("drive_tests", 100 + random.nextInt(900));
            result.add(data);
        }

        return result;
    }


    @GetMapping("/sold-cars")
    public List<Map<String, Object>> getSoldCarsPerAgency(@RequestParam(defaultValue = "month") String groupBy,
                                                          @RequestParam(required = false) String agency) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] agencies = {"Grupo Ford", "Grupo KIA", "Grupo Nissan", "Grupo BMW", "Grupo Toyota"};

        for (String dealership : agencies) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", dealership);
            data.put("sold_cars", 100 + random.nextInt(900));
            result.add(data);
        }

        return result;
    }

    @GetMapping("/salesman-completed-sales")
    public List<Map<String, Object>> getSalesData(@RequestParam(defaultValue = "month") String groupBy) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();

        int month = random.nextInt(12) + 1;
        int daysInMonth = Month.of(month).length(Year.isLeap(Year.now().getValue()));

        if (groupBy.equals("month")) {
            for (int day = 1; day <= daysInMonth; day++) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + day);
                data.put("sales_completed", 100 + random.nextInt(900));
                result.add(data);
            }
        } else if (groupBy.equals("year")) {
            for (int i = 1; i <= 12; i++) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                data.put("sales_completed", 100 + random.nextInt(900));
                result.add(data);
            }
        }

        return result;
    }


    @GetMapping("/salesman-pending-sales")
    public List<Map<String, Object>> getPendingSalesData(@RequestParam(defaultValue = "month") String groupBy) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();

        int month = random.nextInt(12) + 1;
        int daysInMonth = Month.of(month).length(Year.isLeap(Year.now().getValue()));

        if (groupBy.equals("month")) {
            for (int day = 1; day <= daysInMonth; day++) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + day);
                data.put("sales_pending", 100 + random.nextInt(900));
                result.add(data);
            }
        } else if (groupBy.equals("year")) {
            for (int i = 1; i <= 12; i++) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                data.put("sales_pending", 100 + random.nextInt(900));
                result.add(data);
            }
        }

        return result;
    }

    @GetMapping("/salesStatus")
    public List<Map<String, Object>> getSalesStatusData(@RequestParam(required = false) String agency,
                                                        @RequestParam(required = false) String salesman,
                                                        @RequestParam(defaultValue = "month") String groupBy) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] agencies = {"Grupo Ford", "Grupo KIA", "Grupo Nissan", "Grupo BMW", "Grupo Toyota"};
        String[] salesmen = {"Salesman 1", "Salesman 2", "Salesman 3", "Salesman 4", "Salesman 5"};

        for (String salesmanName : salesmen) {
            // If salesman filter is provided and doesn't match current salesman, skip this iteration
            if (salesman != null && !salesman.equals(salesmanName)) continue;

            String agencyName = agencies[random.nextInt(agencies.length)];
            // If agency filter is provided and doesn't match current agency, skip this iteration
            if (agency != null && !agency.equals(agencyName)) continue;

            int open = 100 + random.nextInt(900);
            int completed = 100 + random.nextInt(900);

            Map<String, Object> data = new HashMap<>();
            data.put("agency", agencyName);
            data.put("salesman", salesmanName);
            data.put("open", open);
            data.put("completed", completed);

            result.add(data);
        }

        return result;
    }

}


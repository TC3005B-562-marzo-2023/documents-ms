package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.Document;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

@RestController
@RequestMapping("/v1/document/mock")
public class MockController {
        @GetMapping("/new-users") //super-admin/reportsAndRegisters
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

    @GetMapping("/drive-tests") //super-admin/reportsAndRegisters
    public List<Map<String, Object>> getDriveTestsPerAgency(@RequestParam(defaultValue = "month") String groupBy) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] automotiveGroups = {"Grupo Ford", "Grupo KIA", "Grupo Nissan", "Grupo BMW", "Grupo Toyota"};

        for (String ag : automotiveGroups) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", ag);
            data.put("drive_tests", 100 + random.nextInt(900));
            result.add(data);
        }

        return result;
    }


    @GetMapping("/sold-cars") //super-admin/reportsAndRegisters
    public List<Map<String, Object>> getSoldCarsPerAgency(@RequestParam(defaultValue = "month") String groupBy) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] automotiveGroups = {"Grupo Ford", "Grupo KIA", "Grupo Nissan", "Grupo BMW", "Grupo Toyota"};

        for (String dealership : automotiveGroups) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", dealership);
            data.put("sold_cars", 100 + random.nextInt(900));
            result.add(data);
        }

        return result;
    }

    @GetMapping("/salesman-completed-sales") // Salesman/SalesmanReportsRegisters
    public List<Map<String, Object>> getSalesData(@RequestParam(defaultValue = "month") String groupBy, //month or year
                                                  @RequestParam(defaultValue = "salesmanId") int salesmanId) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();

        int month = random.nextInt(12) + 1;
        int daysInMonth = Month.of(month).length(Year.isLeap(Year.now().getValue()));

        Map<String, Object> header = new HashMap<>();
        header.put("Total sales for salesman with id: " + salesmanId, 100 + random.nextInt(900));
        result.add(header);

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


    @GetMapping("/salesman-pending-sales") // Salesman/SalesmanReportsRegisters
    public List<Map<String, Object>> getPendingSalesData(@RequestParam(defaultValue = "month") String groupBy,
                                                         @RequestParam(defaultValue = "salesmanId") int salesmanId) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();

        Map<String, Object> header = new HashMap<>();
        header.put("Total sales for salesman with id: " + salesmanId, 100 + random.nextInt(900));
        result.add(header);

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

    @GetMapping("/salesStatus") // DealershipManager/ManagerReports/Salesmen
    public List<Map<String, Object>> getSalesStatusData(@RequestParam(defaultValue = "agency") String agency,
                                                        @RequestParam(defaultValue = "groupBy") String groupBy,
                                                        @RequestParam(defaultValue = "month") String month,
                                                        @RequestParam(defaultValue = "year") String year
                                                        ) //month or year
    {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] salesmen = {"Salesman 1", "Salesman 2", "Salesman 3", "Salesman 4", "Salesman 5"};

        for (String salesmanName : salesmen) {

            int open = 100 + random.nextInt(900);
            int completed = 100 + random.nextInt(900);

            Map<String, Object> data = new HashMap<>();
            data.put("salesman", salesmanName);
            data.put("open", open);
            data.put("completed", completed);

            result.add(data);
        }

        return result;
    }

}


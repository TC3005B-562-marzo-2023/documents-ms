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

    @GetMapping("/su-totals") //super-admin/reportsAndRegisters/totals
    public Map<String, Object> getSuperUserTotals() {
        Random random = new Random();
        Map<String, Object> data = new HashMap<>();
        data.put("income", 123);
        data.put("loss", 324);
        data.put("sales_in_progress", 2345);
        data.put("requests", 104);
        data.put("sales", 104);

        return data;
    }

    @GetMapping("/aga-totals") //super-admin/reportsAndRegisters/totals
    public Map<String, Object> getAGATotals(@RequestParam(defaultValue = "0") int groupId) {
        Random random = new Random();
        Map<String, Object> data = new HashMap<>();
        data.put("income", 123);
        data.put("loss", 324);
        data.put("sales_in_progress", 2345);
        data.put("requests", 104);
        data.put("sales", 104);

        return data;
    }

    @GetMapping("/salesman-totals")
    public Map<String, Object> getSalesmanTotals(@RequestParam(defaultValue = "0") int salesmanId) {
        Random random = new Random();
        Map<String, Object> data = new HashMap<>();
        data.put("income", 123);
        data.put("loss", 324);
        data.put("sales_in_progress", 2345);
        data.put("requests", 104);
        data.put("sales", 104);

        return data;
    }

    @GetMapping("/salesman-demos")
    public List<Map<String, Object>> getSalesmanDemos(@RequestParam(defaultValue = "0") int salesmanId) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October"};
        int plus = 0;
        for (String month : months) {
            plus += 33;
            data = new HashMap<>();
            data.put("month", month);
            data.put("demo", 100 + plus);
            result.add(data);
        }

        return result;
    }

    @GetMapping("/salesman-sales")
    public List<Map<String, Object>> getSalesmanSales(@RequestParam(defaultValue = "0") int salesmanId) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October"};
        int plus = 0;
        for (String month : months) {
            plus += 33;
            data = new HashMap<>();
            data.put("month", month);
            data.put("sales", 100 + plus);
            result.add(data);
        }

        return result;
    }

    @GetMapping("/aga-monthly-sales")
    public List<Map<String, Object>> getAGMonthlySales(@RequestParam(defaultValue = "0") int agId) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October"};
        int plus = 0;
        for (String month : months) {
            plus += 33;
            data = new HashMap<>();
            data.put("month", month);
            data.put("sales", 10000 + plus);
            result.add(data);
        }

        return result;
    }

    @GetMapping("/aga-sales")
    public List<Map<String, Object>> getAGASales(@RequestParam(defaultValue = "0") int groupId) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        String [] dealerships = {"Dealership 1", "Dealership 2", "Dealership 3", "Dealership 4", "Dealership 5"};
        int plus = 0;
        for (String dealership : dealerships) {
            plus += 33;
            data = new HashMap<>();
            data.put("dealership", dealership);
            data.put("sales", 100 + plus);
            result.add(data);
        }

        return result;
    }

    @GetMapping("/aga-demos")
    public List<Map<String, Object>> getAGADemosd(@RequestParam(defaultValue = "0") int groupId) {
        Random random = new Random();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        String [] dealerships = {"Dealership 1", "Dealership 2", "Dealership 3", "Dealership 4", "Dealership 5"};
        int plus = 0;
        for (String dealership : dealerships) {
            plus += 33;
            data = new HashMap<>();
            data.put("dealership", dealership);
            data.put("demos", 100 + plus);
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

        int plus = 0;
        for (String salesmanName : salesmen) {

            int open = 100 + plus;
            int completed = 100 + plus;

            Map<String, Object> data = new HashMap<>();
            data.put("salesman", salesmanName);
            data.put("open", open);
            data.put("completed", completed);

            result.add(data);
            plus = plus + 33;
        }

        return result;
    }

}


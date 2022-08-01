package com.example.swimming.controller;

import com.example.swimming.dto.Client;
import com.example.swimming.dto.Timetable;
import com.example.swimming.repository.ClientRepository;
import com.example.swimming.repository.TimetableRepository;
import com.example.swimming.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author BespoyasovaV
 */
@Controller
public class TimetableController {
    @Autowired
    TimetableRepository timetableRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TimeService timeService;


    @GetMapping("/api/v0/pool/timetable/reserve")
    public String reserve(Model model) {

        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("client", clients);
        return "timetable";
    }

    @PostMapping("/api/v0/pool/timetable/reserve")
    public String reseve(@RequestParam String date, @RequestParam String client,
                         @RequestParam String time, Model model) throws ParseException {
        Timetable timetable = new Timetable();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = dateFormat.parse(date);
        timetable.setDate(date1);
        timetable.setTime(time);
        Client client1 = clientRepository.findById(Long.valueOf(client)).get();
        timetable.setClients(client1);
        if (timeService.flag(date1, time)) {
            timetableRepository.save(timetable);
            return "timetable";
        } else return "errortime";
    }

    @GetMapping("/api/v0/pool/timetable/all")
    public String getAll(@RequestParam String datee, Model model) {
        if (datee != null) {
            List<Timetable> timetables = (List<Timetable>) timetableRepository.findAll();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            try {
                date1 = dateFormat.parse(datee);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Timetable> sortTime = timeService.timetables(date1, timetables);
            List itog = new ArrayList<Timetable>();

            if (sortTime.size() != 0) {
                for (Timetable timetable : sortTime) {
                    Client[] arr = timetable.getClients().toArray(new Client[0]);
                    int next = Integer.parseInt(timetable.getTime()) + 1;
                    String foi = timetable.getTime() + " " + arr[0].getName();
                    timetable.setTime(foi);
                    itog.add(timetable);
                }
            }
            if (itog.size() != 0) {
                model.addAttribute("itog", itog);
            }
            return "allSetTime";
        } else return "allSetTime";
    }

    @GetMapping("/api/v0/pool/client/{timeId}/delete")
    public String detele(@PathVariable(value = "timeId") String timeId, Model model) {
        Timetable timetable = timetableRepository.findById((long) Integer.parseInt(timeId)).get();
        timetableRepository.delete(timetable);
        return "allSetTime";
    }

    @GetMapping("/api/v0/pool/timetable/available")
    public String available(@RequestParam String datee, Model model) {
        if (datee != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            try {
                date1 = dateFormat.parse(datee);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<String> sortTime = new ArrayList<String>();
            for (int i = 0; i < 5; i++) {
                String time = String.valueOf(i + 9);
                if (timeService.flag(date1, time)) {
                    int t=1+ Integer.parseInt(time);
                    sortTime.add(time+":00-"+t+":00");
                }
            }
            model.addAttribute("sort", sortTime);
            model.addAttribute("date", datee);
        }


        return "available";
    }
}

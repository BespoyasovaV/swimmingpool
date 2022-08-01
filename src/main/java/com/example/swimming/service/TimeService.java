package com.example.swimming.service;

import com.example.swimming.dto.Timetable;
import com.example.swimming.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author BespoyasovaV
 */
@Service
public class TimeService {
    @Autowired
    TimetableRepository timetableRepository;
    public boolean flag(Date date, String time){
        int s=0;
        List <Timetable> timetables= (List<Timetable>) timetableRepository.findAll();
        for (int i=0;i<timetables.size(); i++){
            int c=timetables.get(i).getDate().compareTo(date);
            if (c==0 && timetables.get(i).getTime().equals(time)){
                s++;
            }
        }

        if (s>=10){
            return false;
        }
        else return true;
    }
    public List<Timetable> timetables(Date date, List<Timetable> timetables){
        List sortTime=new ArrayList<Timetable>();
        for (Timetable timetable : timetables) {
            if (timetable.getDate().compareTo(date) == 0) {

                    sortTime.add(timetable);

            }
        }
        return  sortTime;
    }

}

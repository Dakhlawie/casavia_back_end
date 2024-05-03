package com.meriem.casavia.rsetcontrollers;


import com.meriem.casavia.entities.roomType;
import com.meriem.casavia.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room_type")
@CrossOrigin
public class RoomTypeRestController {
    @Autowired
    RoomTypeRepository roomRep;
    @GetMapping("/all")
    public List<roomType> getAllTypes(){
        return this.roomRep.findAll();
    }
    @PostMapping("/save")
    public roomType addRoomType(@RequestBody roomType c){
        return  this.roomRep.save(c);
    }
    @PutMapping("/update")
    public roomType updateRoomType(@RequestBody  roomType c){
        return  this.roomRep.save(c);
    }
    @DeleteMapping("/delete/{id}")
    public void DeleteRoomType(@PathVariable("id") long id){
        this.roomRep.deleteById(id);
    }

}

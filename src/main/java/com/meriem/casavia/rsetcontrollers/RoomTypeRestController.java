package com.meriem.casavia.rsetcontrollers;


import com.meriem.casavia.entities.Categorie;
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
    public roomType updateRoomType(@RequestBody  roomType c,@RequestParam long id){
        roomType t1=roomRep.findById(id).get();
        t1.setType(c.getType());
        t1.setDescription(c.getDescription());

        return  this.roomRep.save(t1);
    }
    @DeleteMapping("/delete/{id}")
    public void DeleteRoomType(@PathVariable("id") long id){
        this.roomRep.deleteById(id);
    }
    @GetMapping("/{id}")
    public roomType getTypeById(@PathVariable("id") long id){
        return this.roomRep.findById(id).get();
    }

}

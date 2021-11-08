package com.gl.controller;

import com.gl.entity.Room;
import com.gl.entity.Talk;
import com.gl.repository.TalkRepotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/talk")
public class TalkHandler {

    @Autowired
    private TalkRepotory talkRepotory;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Talk> findAll(@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return talkRepotory.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Talk talk){
        Talk result = talkRepotory.save(talk);
        if (result!=null){
            return "success";
        }else {
            return "error";
        }
    }
//    @PostMapping("/talk")
//    public String talk(@RequestParam Map<String, String> talk1){
//        Talk talk = new Talk();
//        talk.rname = Integer.parseInt(talk1.get("rname"));
//        talk.hname = talk1.get("hname");
//        talk.content = talk1.get("content");
//        System.out.println(talk);
//        talkRepotory.save(talk);
////        TalkHandler th = new TalkHandler();
////        th.save(talk);
//        Room roomtemp = RoomHandler.findAllByRname(talk.rname).get(0);
//        roomtemp.whotalk+=1;
//        if (roomtemp.whotalk>roomtemp.hnum) {
//            roomtemp.whotalk = 1;
//            roomtemp.start = "投票";
//            return "yes";
//        }
//
//        while (true) {
//
//            if (findByRHstart(talk.rname,roomtemp.hstart).die == 1) {
//                roomtemp.hstart+=1;
//                //找到是否死亡
//            }
//            else {
//                break;
//            }
//            if (roomtemp.hstart>roomtemp.hnum){
//                roomtemp.whotalk = 1;
//                roomtemp.start = "投票";
//                break;
//            }
//        }
//        roomRepotory.save(roomtemp);
//
//        return "yes";
//    }

    @GetMapping("/findById/{id}")
    public Talk findById(@PathVariable("id") Integer id){
        return talkRepotory.findById(id).get();

    }

    @GetMapping("/findByName/{name}")
    public List<Talk> findByName(@PathVariable("name") String name){

        return talkRepotory.findAllByRname(Integer.parseInt(name));

    }

    @PutMapping("/update")
    public String update(@RequestBody Talk talk){
        Talk result = talkRepotory.save(talk);
        if (result!=null){
            return "success";
        }else {
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
         talkRepotory.deleteById(id);

    }

}

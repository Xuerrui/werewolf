package com.gl.controller;

import com.gl.entity.Talk;
import com.gl.repository.TalkRepotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

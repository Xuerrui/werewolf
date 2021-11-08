package com.gl.controller;

import com.gl.entity.Room;
import com.gl.repository.RoomRepotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/room")
public class RoomHandler {

    static public int id = 0;

    public String Cards[][] = {
            {},
            {},
            {},
            {},
            {},
            {},
            {"狼人","狼人","预言家","平民","平民","平民"},
            {"狼人","狼人","预言家","猎人","平民","平民","平民"},
            {"狼人","狼人","狼人","预言家","女巫","守卫","平民","平民","平民"},
            {"狼人","狼人","狼人","预言家","女巫","守卫","猎人","平民","平民","平民"},
            {"狼人","狼人","狼人","狼人","预言家","女巫","守卫","猎人","平民","平民","平民"},
            {"狼人","狼人","狼人","狼人","预言家","女巫","守卫","猎人","平民","平民","平民","平民"},
            {"狼人","狼人","狼人","狼人","预言家","女巫","守卫","猎人","平民","平民","平民","平民","平民"}
    };
//    public String Cards6[] = {"狼人","狼人","预言家","平民","平民","平民"};
//    public String Cards7[] = {"狼人","狼人","预言家","猎人","平民","平民","平民"};
//    public String Cards8[] = {"狼人","狼人","狼人","预言家","女巫","守卫","平民","平民"};
//    public String Cards9[] = {"狼人","狼人","狼人","预言家","女巫","守卫","平民","平民","平民"};
//    public String Cards10[] = {"狼人","狼人","狼人","预言家","女巫","守卫","猎人","平民","平民","平民"};
//    public String Cards11[] = {"狼人","狼人","狼人","狼人","预言家","女巫","守卫","猎人","平民","平民","平民"};
//    public String Cards12[] = {"狼人","狼人","狼人","狼人","预言家","女巫","守卫","猎人","平民","平民","平民","平民"};
//    public String Cards13[] = {"狼人","狼人","狼人","狼人","预言家","女巫","守卫","猎人","平民","平民","平民","平民","平民"};

    @Autowired
    private RoomRepotory roomRepotory;



    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> room1){
        Room room = new Room();
        room.hnum = Integer.parseInt(room1.get("hnum"));
        room.hname = room1.get("hname");
        room.rname = Integer.parseInt(room1.get("rname"));
        room.rnum = 1;
        room.die = 0;
        room.rstart = 0;
        room.hstart = 0;
        room.vote = "";
        room.start = 0;
        room.whotalk = 0;
        room.whodie = 0;
        Room result = roomRepotory.save(room);
        if (result!=null){
            return "success";
        }else {
            return "error";
        }
    }
    @PostMapping("/join")
    public String join(@RequestParam Map<String, String> room1){
        Room room = new Room();
        room.hname = room1.get("hname");
        room.rname = Integer.parseInt(room1.get("rname"));
        room.die = 0;
        room.rstart = 0;
        room.hstart = 0;
        room.vote = "";
        room.hnum = -1;
        room.start = 0;
        room.whotalk = 0;
        room.whodie = 0;
        Room result = roomRepotory.save(room);
        //第一个room代表room的总信息,增加一个人数
        Room roomtemp = roomRepotory.findAllByRname(room.rname).get(0);
        List<Room> roomdata = roomRepotory.findAllByRname(room.rname);
        System.out.println(roomtemp);


        roomtemp.rnum++;
        roomRepotory.save(roomtemp);

        if (roomtemp.rnum == roomtemp.hnum) {
            roomtemp.rstart = 1;
            //游戏开始后，发牌和发身份
            Random rand = new Random(25);
            int rand2;
            int rand1;
            int temp;
            String tempstr;
            int shuffleCards[] = new int[roomtemp.hnum];
            for (int i = 0; i < roomtemp.hnum; i++) {
                shuffleCards[i] = i;
            }
            for (int i = 0; i < roomtemp.hnum; i++) {
                rand1 = rand.nextInt(roomtemp.hnum-1);
                rand2 = rand.nextInt(roomtemp.hnum-1);
                temp = shuffleCards[rand1];
                shuffleCards[rand1] = shuffleCards[rand2];
                shuffleCards[rand2] = temp;
            }
            //洗身份牌
            for (int i = 0; i < roomtemp.hnum; i++) {
                rand1 = rand.nextInt(roomtemp.hnum-1);
                rand2 = rand.nextInt(roomtemp.hnum-1);
                tempstr = Cards[roomtemp.hnum][rand1];
                Cards[roomtemp.hnum][rand1] = Cards[roomtemp.hnum][rand2];
                Cards[roomtemp.hnum][rand2] = tempstr;
            }

            //发牌
            for (int i = 0; i < roomtemp.hnum; i++) {
                Room roomdatageti = roomdata.get(i);
                roomdatageti.htype = Cards[roomtemp.hnum][i];
                roomdatageti.hstart = shuffleCards[i];
                roomRepotory.save(roomdatageti);
            }
            //发牌结束

        }


        if (result!=null){
            return "success";
        }else {
            return "error";
        }
    }

    @GetMapping("/findByRH/{rname}/{hname}")
    public Room findByRH(@PathVariable("rname") Integer rname, @PathVariable("hname") String hname){
        Room room = new Room();
        System.out.println("sdfasfadsf"+roomRepotory.findAllByRnameAndAndHname(rname, hname));

        return roomRepotory.findAllByRnameAndAndHname(rname, hname).isEmpty()?room:roomRepotory.findAllByRnameAndAndHname(rname, hname).get(0);

    }

    @PostMapping("/save")
    public String save(@RequestBody Room room){
        Room result = roomRepotory.save(room);
        if (result!=null){
            return "success";
        }else {
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public Room findById(@PathVariable("id") Integer id){
        return roomRepotory.findById(id).get();

    }

    @GetMapping("/findByName/{name}")
    public Room findByName(@PathVariable("name") Integer name){
        return roomRepotory.findAllByRname(name).get(0);

    }

    @PutMapping("/update")
    public String update(@RequestBody Room room){
        Room result = roomRepotory.save(room);
        if (result!=null){
            return "success";
        }else {
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
         roomRepotory.deleteById(id);

    }

}

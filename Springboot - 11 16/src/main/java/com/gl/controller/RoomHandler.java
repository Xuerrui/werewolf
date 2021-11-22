package com.gl.controller;

import com.gl.entity.Room;
import com.gl.entity.Talk;
import com.gl.repository.RoomRepotory;
import com.gl.repository.TalkRepotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.awt.dnd.DropTarget;
import java.util.HashMap;
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
            {"狼人","狼人","预言家","猎人","女巫","平民","平民","平民"},
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
    @Autowired
    private TalkRepotory talkRepotory;


    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> room1){
        Room room = new Room();
        room.hnum = Integer.parseInt(room1.get("hnum"));
        room.hname = room1.get("hname");
        room.rname = Integer.parseInt(room1.get("rname"));
        room.rnum = 1;
        room.die = 0;
        room.rstart = 0;
        room.hstart = 1;
        room.vote = "";
        room.start = "";
        room.whotalk = 1;
        room.whodie = "";
//        room.kill = "";
        room.killvote = "";
        room.personnum = 0;
        room.predict = "";
        System.out.println(room);
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
        room.predict = "";

        Room result = roomRepotory.save(room);
        //第一个room代表room的总信息,增加一个人数
        Room roomtemp = roomRepotory.findAllByRname(room.rname).get(0);
        List<Room> roomdata = roomRepotory.findAllByRname(room.rname);
        System.out.println(roomtemp);


        roomtemp.rnum++;
        roomRepotory.save(roomtemp);

        if (roomtemp.rnum == roomtemp.hnum) {
            roomtemp.count = roomtemp.rnum;
            roomtemp.rstart = 1;
            roomtemp.start = "";
            roomtemp.whotalk = 1;
            roomtemp.whodie = "";
            roomtemp.wolfnum = 0;
            roomtemp.godnum = 0;
            roomtemp.personnum = 0;
            roomtemp.start = "狼人";
//            roomtemp.votecount = 0;
//            roomtemp.predict = "";
//            roomtemp.killvote = "";
            //计算出各种族数目
            for (int i = 0; i < roomtemp.hnum; i++) {
                if(Cards[roomtemp.hnum][i] == "狼人") {
                    roomtemp.wolfnum++;
                }
                else if(Cards[roomtemp.hnum][i] == "平民") {
                    roomtemp.personnum++;
                }
                else {
                    roomtemp.godnum++;
                }
            }
            //游戏开始后，发牌和发身份
            Random rand = new Random(25);
            int rand2;
            int rand1;
            int temp;
            String tempstr;
            int shuffleCards[] = new int[roomtemp.hnum];
            for (int i = 0; i < roomtemp.hnum; i++) {
                shuffleCards[i] = i+1;

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
                //遍历同时计算出各种族数目
                roomdatageti.htype = Cards[roomtemp.hnum][i];
                roomdatageti.hstart = shuffleCards[i];
                roomRepotory.save(roomdatageti);
            }
            //发牌结束
            talk(roomtemp.rname, "天黑了");
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
    @GetMapping("/findByRHstart/{rname}/{hstart}")
    public Room findByRHstart(@PathVariable("rname") Integer rname, @PathVariable("hstart") Integer hstart){
        Room room = new Room();
        System.out.println("sdfasfadsf"+roomRepotory.findAllByRnameAndAndHstart(rname, hstart));

        return roomRepotory.findAllByRnameAndAndHstart(rname, hstart).isEmpty()?room:roomRepotory.findAllByRnameAndAndHstart(rname, hstart).get(0);

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

    @GetMapping("/findAllByRname/{name}")
    public List<Room> findAllByRname(@PathVariable("name") Integer rname){
        return roomRepotory.findAllByRname(rname);

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

    @GetMapping("/guard/{name}/{hname2}")
    public String guard(@PathVariable("name") String name, @PathVariable("hname2") String hname2){

        int rname = Integer.parseInt(name);
        Room self = findByRH(rname,hname2);
        self.guard=1;

        roomRepotory.save(self);

        Room roomtemp = roomRepotory.findAllByRname(rname).get(0);
        roomtemp.start = "狼人";
        roomRepotory.save(roomtemp);
        return "yes";
    }

    @GetMapping("/wolfvote/{name}/{hname}/{hname2}")
    public String wolfvote(@PathVariable("name") String name, @PathVariable("hname") String hname, @PathVariable("hname2") String hname2){
        //获得第一个元素
        int rname = Integer.parseInt(name);
        Room roomtemp = roomRepotory.findAllByRname(rname).get(0);
        //获得自己元素
        Room self = findByRH(rname,hname);
        int count = 0;
        self.killvote = hname2;
        int pdie = 0;//预言家狗带
        int wdie = 0;
        System.out.println(self);
        roomRepotory.save(self);

        List<Room> roomdata = roomRepotory.findAllByRname(rname);
        //数一下投票了几个
        for(Room room : roomdata) {
            if (room.killvote.length()>0) {
                count++;
                if(count==roomtemp.wolfnum) {
                    //执行计数操作，计算投票结果
                    HashMap<String,Integer> killvote = new HashMap<String,Integer>();
                    int max=0;
                    String killedOne = "";
                    for(Room room2 : roomdata) {
                        if(room2.killvote.length()>0) {
                            if(killvote.containsKey(room2.killvote)) {
                                killvote.put(room2.killvote,killvote.get(room2.killvote)+1);
                            }
                            else {
                                killvote.put(room2.killvote,1);
                            }
                        }
                    }
                    System.out.println(killvote);
                    for (String key : killvote.keySet()) {
                        if(killvote.get(key) > max) {
                            max = killvote.get(key);
                            killedOne = key;
                         }
                        else if(killvote.get(key) == max) {
                            //平票
                            for(Room room2 : roomdata) {
                                if(room2.htype.equals("预言家")&&room2.die == 1){
                                    pdie = 1;
                                }
                                if(room2.htype.equals("女巫")&&room2.die == 1){
                                    wdie = 1;
                                }
                                room2.guard = 0;//守卫归零
                                room2.killvote = "";
                                roomRepotory.save(room2);
                            }
                            roomtemp.whodie = "";
                            roomtemp.killvote = "";
                            roomtemp.guard = 0;
                            if(roomtemp.rnum <= 7) {
                                roomtemp.start = "预言家";
                                if (pdie==1) {
                                    roomtemp.start = "讨论";
                                    talk(roomtemp.rname,"天亮了");
                                }
                            }
                            if(roomtemp.rnum >= 8) {
                                roomtemp.start = "女巫";
                                if (wdie==1) {
                                    roomtemp.start = "预言家";
                                    if (pdie==1) {
                                        roomtemp.start = "讨论";
                                        talk(roomtemp.rname, "天亮了");
                                    }
                                }
                            }

                            roomRepotory.save(roomtemp);
                            return "nobodyDie";
                        }
                    }
                    //kill max
                    int g=0;//是否被抵消
                    //归零
                    for(Room room2 : roomdata) {
                        if(room2.hname.equals(killedOne)) {

                            room2.die = 1;
                            //胜利判定
                            if (room2.guard == 1) {
                                room2.die = 0;
                                g=1;
                                //return "玩家被守护";
                            }
                            else if (room2.htype.equals("狼人") ) {
                                roomtemp.wolfnum--;
                                roomtemp.count--;
                            }
                            else if (room2.htype.equals("预言家") ||room2.htype.equals("守卫") ||room2.htype.equals("猎人")||room2.equals("女巫")) {
                                roomtemp.godnum--;
                                roomtemp.count--;
                            }
                            else if (room2.htype.equals("平民")) {
                                roomtemp.personnum--;
                                roomtemp.count--;
                            }
                            if (roomtemp.rnum>6) {
                                if(roomtemp.wolfnum == 0) {
                                    return "好人胜利";
                                }
                                if(roomtemp.godnum == 0||room2.personnum == 0) {
                                    return "狼人胜利";
                                }
                            }
                            else if (roomtemp.wolfnum==roomtemp.count) {
                                return "狼人胜利";
                            }
                            else if (roomtemp.wolfnum==0) {
                                return "好人胜利";
                            }
                        }
                        //阶段跳跃不平票到遗言
                        if(room2.htype.equals("预言家")&&room2.die == 1){
                            pdie = 1;
                        }
                        if(room2.htype.equals("女巫")&&room2.die == 1){
                            wdie = 1;
                        }
                        room2.killvote = "";
                        room2.guard=0;
                        roomRepotory.save(room2);
                    }
                    roomtemp.whodie = killedOne;
                    roomtemp.killvote = "";
                    roomtemp.guard = 0;
                    if(roomtemp.rnum <= 7) {
                        roomtemp.start = "预言家";
                        if (pdie==1) {
                            roomtemp.start = "天亮了";
                            talk(roomtemp.rname,"天亮了");
                        }
                    }
                    if(roomtemp.rnum >= 8) {
                        roomtemp.start = "女巫";
                        if (wdie==1) {
                            roomtemp.start = "预言家";
                            if (pdie==1) {
                                roomtemp.start = "天亮了";
                                talk(roomtemp.rname, "天亮了");
                            }
                        }
                    }
                    roomRepotory.save(roomtemp);

                    if (g == 1) {
                        return "玩家被守护";
                    }
                    return killedOne+"Die";

                }
            }
        }
        return "yes";
    }

    //直接读即可
    @GetMapping("/predict/{name}/{hname}/{hname2}")
    public String predict(@PathVariable("name") String name, @PathVariable("hname") String hname, @PathVariable("hname2") String hname2){

        int rname = Integer.parseInt(name);
        Room self = findByRH(rname,hname);
        self.predict+=findByRH(rname, hname2).hname+":"+findByRH(rname, hname2).htype+";";
        roomRepotory.save(self);

        Room roomtemp = roomRepotory.findAllByRname(rname).get(0);

        roomtemp.start = "天亮了";//天亮了代表遗言1轮
        if (roomtemp.whodie.equals("")) {
            roomtemp.start = "讨论";
        }
        //被守护了
        if (roomRepotory.findAllByRnameAndAndHname(roomtemp.rname,roomtemp.whodie).get(0).die==0) {
            roomtemp.start = "讨论";
        }
        talk(rname,"天亮了");
        roomRepotory.save(roomtemp);
        return "yes";
    }

    @PostMapping("/talk")
    public String talk(@RequestParam Map<String, String> talk1){
        Talk talk = new Talk();
        talk.rname = Integer.parseInt(talk1.get("rname"));
        talk.hname = talk1.get("hname");
        talk.content = talk1.get("content");
        System.out.println(talk);
        talkRepotory.save(talk);
//        TalkHandler th = new TalkHandler();
//        th.save(talk);
        Room roomtemp = findAllByRname(talk.rname).get(0);
        roomtemp.whotalk+=1;
        if (roomtemp.whotalk>roomtemp.hnum) {
            roomtemp.whotalk = 1;
            roomtemp.start = "投票";
            return "yes";
        }

        while (true) {

            if (findByRHstart(talk.rname,roomtemp.hstart).die == 1) {
                roomtemp.hstart+=1;
                //找到是否死亡
            }
            else {
                break;
            }
            if (roomtemp.hstart>roomtemp.hnum){
                roomtemp.whotalk = 1;
                roomtemp.start = "投票";
                break;
            }
        }
        roomRepotory.save(roomtemp);

        return "yes";
    }

    @PostMapping("/lasttalk")
    public String lasttalk(@RequestParam Map<String, String> talk1){
        Talk talk = new Talk();
        talk.rname = Integer.parseInt(talk1.get("rname"));
        talk.hname = talk1.get("hname")+"遗言";
        talk.content = talk1.get("content");
        System.out.println(talk);
        talkRepotory.save(talk);
        return "yes";
    }
    //如天黑了等宣言文字
    public String talk(Integer rname,String str){
        Talk talk = new Talk();
        talk.rname = rname;
        talk.hname = "";
        talk.content = str;
        System.out.println(talk);
        talkRepotory.save(talk);
        return "yes";
    }

    @GetMapping("/vote/{name}/{hname}/{hname2}")
    public String vote(@PathVariable("name") String name, @PathVariable("hname") String hname, @PathVariable("hname2") String hname2){
        //获得第一个元素
        int rname = Integer.parseInt(name);
        Room roomtemp = roomRepotory.findAllByRname(rname).get(0);
        //获得自己元素
        Room self = findByRH(rname,hname);
        int count = 0;
        self.vote = hname2;
        int gdie = 0;
        System.out.println(self);
        roomRepotory.save(self);

        List<Room> roomdata = roomRepotory.findAllByRname(rname);
        //数一下投票了几个
        for(Room room : roomdata) {
            if (room.vote.length()>0) {
                count++;
                if(count==roomtemp.count) {
                    //执行计数操作，计算投票结果
                    HashMap<String,Integer> vote = new HashMap<String,Integer>();
                    int max=0;
                    String killedOne = "";
                    for(Room room2 : roomdata) {
                        if(room2.vote.length()>0) {
                            if(vote.containsKey(room2.vote)) {
                                vote.put(room2.vote,vote.get(room2.vote)+1);
                            }
                            else {
                                vote.put(room2.vote,1);
                            }
                        }
                    }
                    System.out.println(vote);
                    for (String key : vote.keySet()) {
                        if(vote.get(key) > max) {
                            max = vote.get(key);
                            killedOne = key;
                        }
                        else if(vote.get(key) == max) {
                            //平票归零
                            for(Room room2 : roomdata) {
                                if(room2.htype.equals("守卫")&&room2.die == 1){
                                    gdie = 1;
                                }
                                room2.vote = "";
                                roomRepotory.save(room2);
                            }
                            roomtemp.whodie = "";
                            roomtemp.vote = "";
                            if (roomtemp.rnum<=8){
                                roomtemp.start = "狼人";

                            }
                            else {
                                roomtemp.start = "守卫";
                                if (gdie == 1) {
                                    roomtemp.start = "狼人";
                                }
                            }
                            roomRepotory.save(roomtemp);

                            return "nobodyDie";
                        }
                    }
                    //kill max

                    //归零
                    for(Room room2 : roomdata) {
                        if(room2.hname.equals(killedOne)) {
                            room2.die = 1;
                            //胜利判定
                            if (room2.htype.equals("狼人") ) {
                                roomtemp.wolfnum--;
                            }
                            if (room2.htype.equals("预言家") ||room2.htype.equals("守卫") ||room2.htype.equals("猎人")||room2.equals("女巫")) {
                                roomtemp.godnum--;
                            }
                            if (room2.htype.equals("平民")) {
                                roomtemp.personnum--;
                            }
                            roomtemp.count--;
                            if (roomtemp.rnum>6) {
                                if(roomtemp.wolfnum == 0) {
                                    return "好人胜利";
                                }
                                if(roomtemp.godnum == 0||room2.personnum == 0) {
                                    return "狼人胜利";
                                }
                            }
                            else if (roomtemp.wolfnum==roomtemp.count) {
                                return "狼人胜利";
                            }
                            else if (roomtemp.wolfnum==0) {
                                return "好人胜利";
                            }
                        }
                        if(room2.htype.equals("守卫")&&room2.die == 1){
                            gdie = 1;
                        }
                        room2.vote = "";
                        roomRepotory.save(room2);
                    }
                    roomtemp.whodie = killedOne;
                    roomtemp.vote = "";
//                    if (roomtemp.rnum<=8){
//
//                        roomtemp.start = "狼人";
//                    }
//                    else {
//                        roomtemp.start = "守卫";
//                        if (gdie == 1) {
//                            roomtemp.start = "狼人";
//                        }
//                    }
                    roomtemp.start = "天黑了";
                    roomRepotory.save(roomtemp);
                    return killedOne+"Die";

                }
            }
        }
        return "yes";
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
         roomRepotory.deleteById(id);

    }

}

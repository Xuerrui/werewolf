package com.gl.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Integer rname;

    public Integer hnum;
    public Integer rnum;
    public String hname;

    public String htype;
    public Integer die;
    public Integer rstart;
    public Integer hstart;//号码
    public String vote;
    public String start;//阶段
    public Integer  whotalk;
    public String whodie;
    public String killvote;
    public String predict;
    public Timestamp time;

//    public String kill;
    public Integer wolfnum;
    public Integer godnum;
    public Integer personnum;
//
    public Integer votecount;

    public Integer witchdrug;

    public Integer guard;

    public Integer count;//剩余的人数


}

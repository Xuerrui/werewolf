package com.gl.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    public Integer start;//阶段
    public Integer whotalk;
    public Integer whodie;
}

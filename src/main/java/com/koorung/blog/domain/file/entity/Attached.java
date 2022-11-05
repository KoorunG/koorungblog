package com.koorung.blog.domain.file.entity;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("AT")
public class Attached extends File{
}

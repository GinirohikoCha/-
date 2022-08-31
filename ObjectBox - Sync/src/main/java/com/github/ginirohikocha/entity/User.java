package com.github.ginirohikocha.entity;

import io.objectbox.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author GinirohikoCha
 * @since 2022/8/31
 */
@Data
@Sync // important!!!
@Entity
public class User {
    @Id
    private long id;

    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    private String username;

    private String password;

    private Date createTime;
}

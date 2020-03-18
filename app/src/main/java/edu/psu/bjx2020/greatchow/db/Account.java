package edu.psu.bjx2020.greatchow.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Account {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "accountID")
    public int accountID;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    public Account(int accountID, String username, String password) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
    }
}

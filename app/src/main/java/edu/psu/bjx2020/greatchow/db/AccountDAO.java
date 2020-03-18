package edu.psu.bjx2020.greatchow.db;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface AccountDAO {
    @Transaction
    @Query("SELECT * FROM account ORDER BY username COLLATE NOCASE")
    LiveData<List<Account>> getAllAlphabetical();

    @Transaction
    @Query("SELECT * FROM account WHERE accountID = :accountID")
    Account getById(int accountID);

    @Insert
    void insert(Account... accounts);

    @Update
    void update(Account... accounts);

    @Delete
    void delete(Account... accounts);

    @Query("DELETE FROM account WHERE accountID = :accountID")
    void delete(int accountID);
}

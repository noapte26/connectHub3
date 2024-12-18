/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Account;

/**
 *
 * @author hebai
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class GetAccount {
    public static UserAccount getAccount(String userId){
        AccountLoad accountLoad = new AccountLoad();
        ArrayList<UserAccount> accounts = new ArrayList<>();
        accounts = accountLoad.loadAccounts();
        for(UserAccount account : accounts){
            if(account.getUser().getUserId().equals(userId)){
                return account;
            }
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infinitysolucoes.dal;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe Augusto
 */
public class BancoSQlite {
    
    static Connection conn = null;
    static String NOME_BANCO = "C:\\Marceneiro\\bancoSQlite\\conf.db";

    public static Connection conectarSQLite() throws ClassNotFoundException {
        
        try {
            
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+NOME_BANCO);
            conn.setAutoCommit(true);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
    
    }
    

}

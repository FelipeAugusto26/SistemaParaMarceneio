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
public class ModuloConexao {

    // Variáveis para conectar o banco SQLite
    static Connection conexao;
    static PreparedStatement pst;
    static ResultSet rs;
    static String PORTA;
    static String USER;
    static String PASS;
    static String NOME_BANCO;

    // Método responsável por estabelecer a conexão com o banco
    public static Connection conector() {

        // Conectar SQLite para pegar configurações
        String sql = "SELECT * FROM configuracao WHERE id = ?";
        try {
            conexao = BancoSQlite.conectarSQLite();
            String id = "1";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, id);
                rs = pst.executeQuery();

                if (rs.next()) {
                    NOME_BANCO = rs.getString(2);
                    PORTA = rs.getString(3);
                    USER = rs.getString(4);
                    PASS = rs.getString(5);
                    conexao.close();

                } else {
                    JOptionPane.showMessageDialog(null, "Banco nao conectado");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }

        conexao = null; // (variavel)atribuido nulo
        // Carregar o Driver - "Chamar" o driver
        String DRIVER = "com.mysql.jdbc.Driver";
        // Armazenando informações referente ao banco
        String URL = "jdbc:mysql://localhost:" + PORTA + "/" + NOME_BANCO;

        // Estabelecendo a conexão com o banco
        try {
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USER, PASS);
            return conexao;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Banco nao conectado Verifique usuario e Senha ");

            return null;
        }

    }

}
